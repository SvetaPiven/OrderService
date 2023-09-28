package org.order.controller;

import lombok.RequiredArgsConstructor;
import org.order.nebagafeature.KafkaSender;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final KafkaSender kafkaSender;

    @GetMapping("/{message}")
    public ResponseEntity<?> sendMessage(@PathVariable String message) {
        kafkaSender.sendMessage(message);
        return ResponseEntity.status(418).body("JEP-JEP-HOORAY!!)))00 *(^_^)*");
    }

}
