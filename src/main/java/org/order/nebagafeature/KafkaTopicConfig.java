package org.order.nebagafeature;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {


    @Bean
    public NewTopic ordersTopic() {
        return TopicBuilder.name("ordersTopic")
                .build();
    }

}
