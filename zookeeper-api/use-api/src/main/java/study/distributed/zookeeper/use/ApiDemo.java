package study.distributed.zookeeper.use;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;

public class ApiDemo {


    public static void main(String[] args) throws Exception {
        CuratorFramework client = ClientFactory.getClient();
        client.start();
        createWithSequential(client);
    }

    /**
     * 创建
     */
    public static void create(CuratorFramework curatorFramework) throws Exception {
        curatorFramework.create().forPath("/demo","你好".getBytes());
    }

    /**
     * 删除
     */
    public static void delete(CuratorFramework curatorFramework) throws Exception {
        curatorFramework.delete().forPath("/demo");
    }

    /**
     * 创建时间节点，客户端关闭，该连接就没了
     */
    public static void createWithEphemeral(CuratorFramework curatorFramework) throws Exception {
        System.out.println(curatorFramework.create().withMode(CreateMode.EPHEMERAL).forPath("/demo","你好".getBytes()));
    }

    /**
     * 创建有序节点
     */
    public static void createWithSequential(CuratorFramework curatorFramework) throws Exception {
        for (int i = 0; i < 5; i++) {
            curatorFramework.create().creatingParentsIfNeeded()
                    .withMode(CreateMode.PERSISTENT_SEQUENTIAL).forPath("/demo/lock","你好".getBytes());
        }
    }
}
