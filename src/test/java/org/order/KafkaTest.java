package org.order;

import org.junit.jupiter.api.Test;
import org.order.nebagafeature.KafkaSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092",
        "port=9092"})
class KafkaTest {
    @Autowired
    private KafkaConsumer consumer;

    @Autowired
    private KafkaSender producer;

    @Test
    void givenEmbeddedKafkaBroker_whenSendingWithSimpleProducer_thenMessageReceived() throws InterruptedException {
        String data = "Я в своем познании настолько преисполнился что я как будто бы уже сто триллионов миллиардов лет " +
                "проживаю на триллионах триллионах таких же планет, как эта Земля, мне этот мир абсолютно понятен, и я " +
                "здесь ищу только одного - покоя, умиротворения и вот этой гармонии, от слияния с бесконечно вечным, от" +
                " созерцания великого фрактального подобия от вот этого замечательного всеединства существа, бесконечно " +
                "вечного, куда ни посмотри, хоть вглубь - бесконечно малое,хоть ввысь - бесконечное большое, понимаешь?";

        producer.sendMessage(data);

        boolean messageConsumed = consumer.getLatch().await(3, TimeUnit.SECONDS);
        assertThat(consumer.getData(), containsString(data));
        assertThat(messageConsumed, is(true));
    }
}
