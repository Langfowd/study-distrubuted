package study.distributed.zookeeper.use;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.BoundedExponentialBackoffRetry;

public class ClientFactory {
    private static String CLIENT_STR = "192.168.93.128:2181";

    public static CuratorFramework getClient() {
       return CuratorFrameworkFactory.builder().connectString(CLIENT_STR).
                retryPolicy(new BoundedExponentialBackoffRetry(1000,3000,3))
                .build();
    }
}
