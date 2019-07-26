package provider.impl;

import provider.IPayService;

public class PayServiceImpl implements IPayService{

    @Override
    public String pay(int money) {
        System.out.println("收到"+money+"元");
        return "成功支付"+money+"元";
    }
}
