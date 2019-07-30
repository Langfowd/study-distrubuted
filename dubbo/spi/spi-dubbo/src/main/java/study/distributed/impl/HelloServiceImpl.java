package study.distributed.impl;

import study.distributed.spi.dubbo.HelloService;

public class HelloServiceImpl implements HelloService {
    @Override
    public void sayHello(String name) {
        System.out.println("hello : "+name);
    }
}
