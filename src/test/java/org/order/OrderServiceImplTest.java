package org.order;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.order.dto.request.OrderRequestDto;
import org.order.entity.Order;
import org.order.mapper.OrderMapper;
import org.order.repository.OrderRepository;
import org.order.service.impl.OrderServiceImpl;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {
    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderMapper orderMapper;

    @InjectMocks
    private OrderServiceImpl service;

    @Test
    void saveOrder_withValidRequest_orderSaved() {
        OrderRequestDto orderRequestDto = new OrderRequestDto(
                UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa0"),
                1L,
                1L,
                100.0f,
                Instant.parse("2023-10-02T11:10:00.923Z"),
                "ул. К.Маркса 24 кв. 1",
                List.of(new OrderRequestDto.GoodDto(1L, 5L))
        );

        when(orderRepository.findById(orderRequestDto.id())).thenReturn(Optional.empty());

        Order order = new Order();

        when(orderMapper.toEntity(orderRequestDto)).thenReturn(order);
        when(orderRepository.saveAndFlush(order)).thenReturn(order);

        Order savedOrder = service.saveOrder(orderRequestDto);

        assertEquals(order, savedOrder);
        verify(orderRepository).findById(orderRequestDto.id());
    }

    @Test
    void setStatusIsPaid_orderExists_setOrderAsPaid() {
        UUID orderId = UUID.randomUUID();
        Order order = new Order();

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        assertDoesNotThrow(() -> service.setStatusIsPaid(orderId));
        assertTrue(order.getIsPaid());
    }

    @Test
    void setStatusIsPaid_orderDoesNotExist_throwNoSuchElementException() {
        UUID orderId = UUID.randomUUID();
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        NoSuchElementException exception = assertThrows(
                NoSuchElementException.class,
                () -> service.setStatusIsPaid(orderId)
        );

        assertEquals("Заказ с id: " + orderId + " не найден!", exception.getMessage());
    }

    @Test
    void findById_orderExists_returnOrder() {
        UUID orderId = UUID.randomUUID();
        Order order = new Order();

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        Order foundOrder = service.findById(orderId);

        assertEquals(order, foundOrder);
    }

    @Test
    void findById_orderDoesNotExist_throwNoSuchElementException() {
        UUID orderId = UUID.randomUUID();
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        NoSuchElementException exception = assertThrows(
                NoSuchElementException.class,
                () -> service.findById(orderId)
        );

        assertEquals("Заказ с id: " + orderId + " не найден!", exception.getMessage());
    }
}

