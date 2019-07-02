package client;

import api.service.HelloService;

public class RpcClient {

    public static void main(String[] args) {
        HelloService helloService = RpcProxy.getProxy(HelloService.class);
        String result = helloService.hello("xiaoluo");
        System.out.println(result);
    }
}
