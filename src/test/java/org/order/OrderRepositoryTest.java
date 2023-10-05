package org.order;

import org.junit.jupiter.api.Test;
import org.order.dto.request.OrderRequestDto;
import org.order.entity.Order;
import org.order.mapper.OrderMapper;
import org.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Testcontainers
@TestPropertySource(locations = "classpath:application.yml")
class OrderRepositoryTest {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderMapper orderMapper;

    @Container
    static PostgreSQLContainer<?> postgreSQLContainer =
            new PostgreSQLContainer<>("postgres:alpine");

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @Test
    void saveAndFindById_withValidOrder_returnOrderAndSaveOrder() {
        OrderRequestDto orderRequestDto = new OrderRequestDto(
                UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa0"),
                1L,
                1L,
                100.0f,
                Instant.parse("2023-10-02T11:10:00.923Z"),
                "ул. К.Маркса 28 кв. 1",
                List.of(new OrderRequestDto.GoodDto(1L, 5L))
        );

        Order orderForSave = orderMapper.toEntity(orderRequestDto);
        Order savedOrder = orderRepository.saveAndFlush(orderForSave);
        Order findedOrder = orderRepository.findById(UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa0")).orElse(null);

        assertEquals(orderForSave, savedOrder);
        assertEquals(orderForSave, findedOrder);

    }
}
