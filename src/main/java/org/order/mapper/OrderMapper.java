package org.order.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.order.dto.request.OrderRequestDto;
import org.order.dto.response.OrderResponseDto;
import org.order.entity.Order;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface OrderMapper {

    Order toEntity(OrderRequestDto orderRequestDto);

    @AfterMapping
    default void linkGoods(@MappingTarget Order order) {
        order.getGoods().forEach(good -> good.setOrder(order));
    }

    @Mapping(target = "orderId", source = "id")
    OrderResponseDto toOrderResponseDto(Order order);

}