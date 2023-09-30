package org.order.service;

import org.order.dto.request.OrderRequestDto;
import org.order.entity.Order;

public interface OrderService {
    Order saveOrder(OrderRequestDto dto);
}
