package study.distributed.dubbo.annation.client.controller.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:dubbo-client.properties")
public class ConsumerConfig {
}
