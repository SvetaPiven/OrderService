package org.order.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record OrderRequestDto(@NotNull UUID id,
                              @NotNull Long userId,
                              @NotNull Long storeId,
                              @NotNull Float totalPrice,
                              @NotNull Instant deliveryDatetime,
                              @NotNull @Size(max = 256) String deliveryAddress,
                              List<GoodDto> goods) {

    public record GoodDto(@NotNull Long productId,
                          @NotNull Long goodsQuantity) {

    }
}