package study.distributed.dubbo.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import provider.IPayService;

@RestController
public class PayController {

    @Autowired
    private IPayService payService;

    @RequestMapping("/pay")
    public String pay(int money) {
        return payService.pay(money);
    }
}
