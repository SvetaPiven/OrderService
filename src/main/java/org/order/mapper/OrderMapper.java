package org.order.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.order.dto.request.OrderRequestDto;
import org.order.dto.response.OrderResponseDto;
import org.order.entity.Good;
import org.order.entity.Order;

import java.util.List;
import java.util.stream.Collectors;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring",
        builder = @Builder(disableBuilder = true), uses = {LinksGood.class})
public interface OrderMapper {

    @Mapping(target = "goods", source = "goods", qualifiedByName = "links")
    Order toEntity(OrderRequestDto orderRequestDto);

//    @AfterMapping
//    default void linkGoods(@MappingTarget Order order) {
//        order.getGoods().forEach(good -> good.setOrder(order));
//    }

    @Mapping(target = "orderId", source = "id")
    OrderResponseDto toOrderResponseDto(Order order);

//    @Named("links")
//    default List<Good> links(OrderRequestDto orderRequestDto) {
//        return orderRequestDto.goods().stream().map(goodDto ->
//                Good.builder().productId(goodDto.productId()).goodsQuantity(goodDto.goodsQuantity()).build()).toList();
//
//    }

}