package study.distributed.spi.dubbo;

import org.apache.dubbo.common.extension.SPI;

@SPI
public interface HelloService {
    void sayHello(String name);
}
