package study.distributed.spi;


import study.distributed.spi.jdk.Driver;

public class MysqlDriver implements Driver {
    @Override
    public String getCollection() {
        return "mysql";
    }
}
