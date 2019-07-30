package study.distributed;

import org.apache.dubbo.common.extension.ExtensionLoader;
import study.distributed.spi.dubbo.HelloService;

public class SpiTest {
    public static void main(String[] args) {
        HelloService myhello = ExtensionLoader.getExtensionLoader(HelloService.class).getExtension("myhello");
        myhello.sayHello("my");
    }
}
