package org.order.controller.impl;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.order.controller.OrderController;
import org.order.dto.request.OrderRequestDto;
import org.order.nebagafeature.KafkaSender;
import org.order.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/order")
public class OrderControllerImpl implements OrderController {

    private final KafkaSender kafkaSender;

    private final OrderService orderService;

    @Override
    @PostMapping("/create")
    @Timed(value = "order.save", description = "Время сохранения заказа")
    public ResponseEntity<String> saveOrder(@RequestBody @Valid OrderRequestDto request) {
        orderService.saveOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("Заказ передан");
    }

    @Override
    @PatchMapping("/payment/{transferId}")
    @Timed(value = "order.payment", description = "Время подтверждения оплаты")
    public ResponseEntity<String> confirmPayment(@PathVariable UUID transferId) {
        orderService.setStatusIsPaid(transferId);
        kafkaSender.sendMessage(transferId.toString());
        return ResponseEntity.ok("Заказ успешно передан в службу доставки");
    }
}
