package org.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.order.controller.impl.OrderControllerImpl;
import org.order.dto.request.OrderRequestDto;
import org.order.entity.Order;
import org.order.nebagafeature.KafkaSender;
import org.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderControllerImpl.class)
class OrderControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService service;

    @MockBean
    private KafkaSender kafkaSender;


    @Test
    void givenOrderService_whenOrderExists_thenReturnCreatedStatus() throws Exception {
        when(service.saveOrder(any(OrderRequestDto.class))).thenReturn(new Order());

        mockMvc.perform(post("/api/order/create")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getJsonObject()))
                .andDo(print())
                .andExpect(status().isCreated());

    }

    @Test
    void saveOrder_withValidRequest_throwIllegalArgumentException() throws Exception {
        when(service.saveOrder(any(OrderRequestDto.class)))
                .thenThrow(new IllegalArgumentException("Заказ уже был передан в службку доставки"));
        mockMvc.perform(post("/api/order/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getJsonObject())
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Заказ уже был передан в службку доставки"));
    }

    @Test
    void saveOrder_withValidRequest_throwUnsupportedOperationException() throws Exception {
        when(service.saveOrder(any(OrderRequestDto.class)))
                .thenThrow(new UnsupportedOperationException("Такой заказ уже создан"));
        mockMvc.perform(post("/api/order/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getJsonObject())
                )
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Такой заказ уже создан"));
    }

    @Test
    void confirmPayment_withValidId_throwNoSuchElementException() throws Exception {
        UUID id = UUID.randomUUID();
        willThrow(new NoSuchElementException("Заказ с id: " + id + " не найден!"))
                .given(service).setStatusIsPaid(id);

        mockMvc.perform(patch("/api/order/payment/{transferId}", id))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.message").value("Заказ с id: " + id + " не найден!"));
    }

    @Test
    void confirmPayment_ReturnsSuccess() throws Exception {
        UUID transferId = UUID.randomUUID();

        mockMvc.perform(patch("/api/order/payment/{transferId}", transferId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Заказ успешно передан в службу доставки"));
    }

    private static String getJsonObject() throws JsonProcessingException {
        OrderRequestDto orderRequestDto = new OrderRequestDto(
                UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa0"),
                1L,
                1L,
                100.0f,
                Instant.parse("2023-10-02T11:10:00.923Z"),
                "ул. К.Маркса 24 кв. 1",
                List.of(new OrderRequestDto.GoodDto(1L, 5L))
        );
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.writeValueAsString(orderRequestDto);
    }
}
