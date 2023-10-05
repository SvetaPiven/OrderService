package org.order;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class KafkaConsumer {

    private final CountDownLatch latch = new CountDownLatch(1);

    private String data;

    public String getData() {
        return data;
    }

    public CountDownLatch getLatch() {
        return latch;
    }

    @KafkaListener(topics = "${test.topic}")
    public void receive(ConsumerRecord<?, ?> consumerRecord) {
        System.out.println("Received data '{}'" + consumerRecord.toString());
        data = consumerRecord.toString();
        latch.countDown();
    }
}
