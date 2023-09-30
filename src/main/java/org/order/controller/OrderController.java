package org.order.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.order.dto.request.OrderRequestDto;
import org.order.nebagafeature.KafkaSender;
import org.order.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final KafkaSender kafkaSender;

    private final OrderService orderService;


//    @GetMapping("/{message}")
//    public ResponseEntity<?> sendMessage(@PathVariable String message) {
//        kafkaSender.sendMessage(message);
//        return ResponseEntity.status(418).body("JEP-JEP-HOORAY!!)))00 *(^_^)*");
//    }

    @PostMapping("/create")
    public ResponseEntity<String> saveOrder(@RequestBody @Valid OrderRequestDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new RuntimeException(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }

        orderService.saveOrder(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body("заказ передан");
    }

    @PatchMapping("/payment/{id}")
    public ResponseEntity<String> confirmPayment(@PathVariable UUID id) {
        String result = "";
        if (!orderService.findById(id).getIsPaid()) {
            orderService.setStatusIsPaid(id);
            kafkaSender.sendMessage(id.toString());
            result = "заказ передан в доставку";
        } else {
            result = "заказ уже был передан ранее";
        }
        return ResponseEntity.ok(result);
    }
}
