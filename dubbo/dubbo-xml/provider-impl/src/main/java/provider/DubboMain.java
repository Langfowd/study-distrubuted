package provider;

import org.apache.dubbo.container.Main;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.CountDownLatch;

/**
 * META-INF 容器启动
 * 实际也是封装了spring容器的启动，会自动加载 META-INF/spring/*.xml文件
 */
public class DubboMain {
    public static void main(String[] args) throws InterruptedException {
        Main.main(args);
    }
}
