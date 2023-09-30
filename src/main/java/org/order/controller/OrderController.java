package org.order.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.order.dto.request.OrderRequestDto;
import org.order.dto.response.OrderResponseDto;
import org.order.nebagafeature.KafkaSender;
import org.order.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final KafkaSender kafkaSender;

    private final OrderService orderService;

    @GetMapping("/{message}")
    public ResponseEntity<?> sendMessage(@PathVariable String message) {
        kafkaSender.sendMessage(message);
        return ResponseEntity.status(418).body("JEP-JEP-HOORAY!!)))00 *(^_^)*");
    }

    @PostMapping("/create")
    public ResponseEntity<String> saveOrder(@RequestBody @Valid OrderRequestDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new RuntimeException("###################################################" + Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }

        orderService.saveOrder(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body("заказ передан");
    }

}
