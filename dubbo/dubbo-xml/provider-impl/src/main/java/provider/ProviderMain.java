package provider;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.CountDownLatch;

/**
 * spring 容器启动
 */
public class ProviderMain {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:provider.xml");
        context.start();
        latch.await();

    }
}
