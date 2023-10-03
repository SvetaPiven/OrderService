package org.order.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Schema(description = "Информация о заказе")
public record OrderRequestDto(
        @Schema(description = "ID заказа", example = "3fa85f64-5717-4562-b3fc-2c963f66afa0") @NotNull UUID id,
        @Schema(description = "ID покупателя", example = "1") @NotNull Long userId,
        @Schema(description = "ID магазина", example = "1") @NotNull Long storeId,
        @Schema(description = "Конечная цена", example = "100") @NotNull Float totalPrice,
        @Schema(description = "Дата формирования", example = "2023-10-02T11:10:00.923Z") @NotNull Instant deliveryDatetime,
        @Schema(description = "Адрес покупателя", example = "ул. К.Маркса 24 кв. 1") @NotNull @Size(max = 256) String deliveryAddress,
        List<GoodDto> goods) {

    public record GoodDto(
            @Schema(description = "ID продукта", example = "1") @NotNull Long productId,
            @Schema(description = "Количество продуктов", example = "5") @NotNull Long goodsQuantity) {

    }
}