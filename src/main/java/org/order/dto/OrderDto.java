package org.order.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.util.Set;

public record OrderDto(@NotNull Long userId,
                       @NotNull Long storeId,
                       @NotNull Float totalPrice,
                       @NotNull Instant deliveryDatetime,
                       @NotNull @Size(max = 256) String deliveryAddress,
                       Set<GoodDto> goods) {

    public record GoodDto(@NotNull Long productId,
                          @NotNull Long goodsQuantity) {

    }
}