package study.distributed.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class AnnatationReceive {

    @KafkaListener(topics = {"test-topic"})
    public void receive(ConsumerRecord record) {
        System.out.println(record.value());
    }
}
