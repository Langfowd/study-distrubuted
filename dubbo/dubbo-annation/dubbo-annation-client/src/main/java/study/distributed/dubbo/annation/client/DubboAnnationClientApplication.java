package study.distributed.dubbo.annation.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:dubbo-client.properties")
public class DubboAnnationClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboAnnationClientApplication.class, args);
    }

}
