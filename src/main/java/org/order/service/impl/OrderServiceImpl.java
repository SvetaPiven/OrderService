package org.order.service.impl;

import lombok.RequiredArgsConstructor;
import org.order.dto.request.OrderRequestDto;
import org.order.entity.Order;
import org.order.mapper.OrderMapper;
import org.order.repository.OrderRepository;
import org.order.service.OrderService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper;

    @Override
    public Order saveOrder(OrderRequestDto requestDto) {
        return orderRepository.saveAndFlush(orderMapper.toEntity(requestDto));
    }
}
