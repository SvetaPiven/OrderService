package org.order.mapper;

import org.mapstruct.Named;
import org.order.dto.request.OrderRequestDto;
import org.order.entity.Good;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LinksGood {

    @Named("links")
    public List<Good> links(OrderRequestDto orderRequestDto) {
        return orderRequestDto.goods().stream().map(goodDto ->
                Good.builder().productId(goodDto.productId()).goodsQuantity(goodDto.goodsQuantity()).build()).toList();

    }

//    @Named("links")
//    default List<Good> links(List<OrderRequestDto.GoodDto> orderRequestDto) {
//        return orderRequestDto.stream()
//                .map(goodDto ->
//                        Good.builder()
//                                .productId(goodDto.productId())
//                                .goodsQuantity(goodDto.goodsQuantity())
//                                .build())
//                .toList();
//
//    }
}
