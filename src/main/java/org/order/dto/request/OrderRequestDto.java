package org.order.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public record OrderRequestDto(@NotNull UUID userId,
                              @NotNull Long storeId,
                              @NotNull Float totalPrice,
                              @NotNull Instant deliveryDatetime,
                              @NotNull @Size(max = 256) String deliveryAddress,
                              Set<GoodDto> goods) {

    public record GoodDto(@NotNull Long productId,
                          @NotNull Long goodsQuantity) {

    }
}