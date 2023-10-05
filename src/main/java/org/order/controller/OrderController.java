package org.order.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.order.dto.request.OrderRequestDto;
import org.order.dto.response.ApiErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@Tag(name = "Контроллер для создания и хранения информации о заказе и обновления статуса заказа")
public interface OrderController {

    @Operation(summary = "Сохранение информации о заказе",
            description = "Данный эндпоинт предназначен для сохранения информации о заказе в таблицы order и goods")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Запрос выполнен успешно",
                    content = {@Content(schema = @Schema(implementation = String.class))}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Ошибка валидации",
                    content = {@Content(schema = @Schema(implementation = ApiErrorResponse.class))}
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Такой заказ уже создан",
                    content = {@Content(schema = @Schema(implementation = ApiErrorResponse.class))}
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error",
                    content = {@Content(schema = @Schema(implementation = ApiErrorResponse.class))}
            )
    })
    ResponseEntity<String> saveOrder(@RequestBody @Valid OrderRequestDto request);

    @Operation(summary = "Обновления статуса заказа по идентификатору",
            description = "Данный эндпоинт предназначен для обновления статуса заказа по идентификатору transferId")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Запрос выполнен успешно",
                    content = {@Content(schema = @Schema(implementation = String.class))}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Заказ уже был передан в службку доставки",
                    content = {@Content(schema = @Schema(implementation = ApiErrorResponse.class))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Заказ с таким идентификатором не найден",
                    content = {@Content(schema = @Schema(implementation = ApiErrorResponse.class))}
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error",
                    content = {@Content(schema = @Schema(implementation = ApiErrorResponse.class))}
            )
    })
    ResponseEntity<String> confirmPayment(@PathVariable UUID transferId);
}
