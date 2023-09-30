package org.order.service;

import org.order.dto.request.OrderRequestDto;
import org.order.entity.Order;

import java.util.UUID;

public interface OrderService {
    Order saveOrder(OrderRequestDto dto);

    Boolean setStatusIsPaid(UUID id);

    Order findById(UUID id);
}
