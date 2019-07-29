package study.distributed.dubbo;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import study.distributed.dubbo.config.DubboProviderConfig;

import java.util.concurrent.CountDownLatch;

public class Start {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DubboProviderConfig.class);
        context.start();
        latch.await();
    }
}
