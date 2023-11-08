package org.order.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
//@EnableJpaAuditing
public class AppConfig {

//    @Bean
//    public NewTopic ordersTopic() {
//        return TopicBuilder.name("ordersTopic")
//                .build();
//    }
}
