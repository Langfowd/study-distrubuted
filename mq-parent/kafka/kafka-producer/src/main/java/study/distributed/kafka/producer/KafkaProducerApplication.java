package study.distributed.kafka.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;

@SpringBootApplication
public class KafkaProducerApplication {

    @Autowired
    KafkaTemplate kafkaTemplate;

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(KafkaProducerApplication.class, args);
        KafkaProducerApplication bean = run.getBean(KafkaProducerApplication.class);
        bean.send();

    }

    public void send() {
        try {
            System.out.println(kafkaTemplate.send("test-topic","你好").get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

}
