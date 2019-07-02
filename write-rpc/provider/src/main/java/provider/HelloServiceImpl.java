package provider;

import api.service.HelloService;

public class HelloServiceImpl implements HelloService {

    @Override
    public String hello(String name) {
        return "hello "+name+"!";
    }
}
