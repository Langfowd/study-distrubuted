package study.distributed.dubbo.config;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableDubbo(scanBasePackages = "study.distributed.dubbo")
@PropertySource("classpath:/provider.properties")
public class DubboProviderConfig {
}
