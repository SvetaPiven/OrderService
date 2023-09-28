package org.order.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.kafka.annotation.EnableKafka;

@Configuration
@EnableJpaAuditing
@EnableKafka
public class AppConfig {

}
