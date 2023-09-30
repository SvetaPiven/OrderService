package org.order.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.order.dto.request.OrderRequestDto;
import org.order.entity.Order;
import org.order.mapper.OrderMapper;
import org.order.repository.OrderRepository;
import org.order.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper;

    @Override
    public Order saveOrder(OrderRequestDto requestDto) {
        return orderRepository.saveAndFlush(orderMapper.toEntity(requestDto));
    }

    @Override
    public Boolean setStatusIsPaid(UUID id) {
        int affectedRows = orderRepository.updateStatusById(id);
        return affectedRows != 0;
    }

    @Override
    public Order findById(UUID id) {
        return orderRepository.findById(id).orElseThrow(()
                -> new EntityNotFoundException("Заказ с id: " + id + " не найден!"));
    }
}
