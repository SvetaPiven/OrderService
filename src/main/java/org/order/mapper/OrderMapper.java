package org.order.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.order.dto.OrderDto;
import org.order.entity.Order;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderMapper {

    Order toEntity(OrderDto orderDto);

    @AfterMapping
    default void linkGoods(@MappingTarget Order order) {
        order.getGoods().forEach(good -> good.setOrder(order));
    }

    OrderDto toDto(Order order);

}