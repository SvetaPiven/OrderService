package org.order.service.impl;

import lombok.RequiredArgsConstructor;
import org.order.dto.request.OrderRequestDto;
import org.order.entity.Order;
import org.order.mapper.OrderMapper;
import org.order.repository.OrderRepository;
import org.order.service.OrderService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@CacheConfig(cacheNames = "customerCache")
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper;

    @Transactional
    @Override
    public Order saveOrder(OrderRequestDto requestDto) {
        if (orderRepository.findById(requestDto.id()).isPresent()) {
            throw new UnsupportedOperationException("Такой заказ уже создан");
        }
        return orderRepository.saveAndFlush(orderMapper.toEntity(requestDto));
    }

    @Transactional
    @Override
    public void setStatusIsPaid(UUID id) {
        Order order = this.findById(id);
        if (Boolean.FALSE.equals(order.getIsPaid())) {
            order.setIsPaid(true);
        } else throw new IllegalArgumentException("Заказ уже был передан в службку доставки");
    }

    @Override
//    @Cacheable(value = "orders")
    public Order findById(UUID id) {
        return orderRepository.findOrderWithGoodsById(id).orElseThrow(()
                -> new NoSuchElementException("Заказ с id: " + id + " не найден!"));
    }

}
