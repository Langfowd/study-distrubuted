package study.distributed.spi.jdk;

import study.distributed.spi.MysqlDriver;

import java.util.ServiceLoader;

public class DriverTest {
    public static void main(String[] args) {
        ServiceLoader<Driver> serviceLoader = ServiceLoader.load(Driver.class);
        for (Driver driver : serviceLoader) {
            if (driver instanceof MysqlDriver) {
                System.out.println("mysqldriver");
            }
            System.out.println(driver.getCollection());
        }
    }
}
