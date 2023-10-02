package org.order.nebagafeature;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaSender {

    private static final String TOPIC_NAME = "ordersTopic";

    private final KafkaTemplate<String, String> kafkaTemplate;

//    public void sendMessage(String message) {
//        CompletableFuture<SendResult<String, String>> futureMessage = kafkaTemplate.send(TOPIC_NAME, message);
//        checkForDelivery(futureMessage);
//    }
//
//    private void checkForDelivery(CompletableFuture<SendResult<String, String>> futureMessage) {
//        try {
//            futureMessage.get(3, TimeUnit.SECONDS);
//        } catch (Exception ex) {
//            throw new UnsupportedOperationException("Сообщение не отправлено. Попробуйте позже");
//        }
//    }
}
