package study.distributed.dubbo.annation.client.controller;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.distributed.dubbo.IPayService;

@RestController
public class PayController {

    @Reference
    IPayService iPayService;

    @RequestMapping("/pay")
    public String pay(int money) {
        return iPayService.pay(money);
    }
}
