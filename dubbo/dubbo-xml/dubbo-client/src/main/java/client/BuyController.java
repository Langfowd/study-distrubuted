package client;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import provider.IPayService;

public class BuyController {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:client.xml");
        IPayService service = context.getBean(IPayService.class);
        System.out.println(service.pay(1000));
    }
}
