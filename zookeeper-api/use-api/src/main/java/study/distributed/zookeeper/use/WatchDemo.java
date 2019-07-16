package study.distributed.zookeeper.use;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;

public class WatchDemo {

    public static void main(String[] args) throws Exception {
        CuratorFramework client = ClientFactory.getClient();
        client.start();
        watchPathChildrenCache(client);
    }

    /**
     * NodeCache 监控节点变化
     * PathChildrenCache 监控子节点变化
     * TreeCache 以上2种结合，监控所有变化
     */
    public static void watchNodeCache(CuratorFramework curatorFramework) throws Exception {
        NodeCache cache = new NodeCache(curatorFramework,"/demo",false);
        NodeCacheListener listener = () -> {
            if (curatorFramework.checkExists().forPath("/demo") == null) {
                System.out.println("节点已经删除");
            }
            String data = new String(cache.getCurrentData().getData());
            System.out.println("path :"+cache.getCurrentData().getPath() + "  data:"+data);
        };
        cache.getListenable().addListener(listener);
        cache.start();
        System.in.read();
    }

    /**
     * NodeCache 监控节点变化
     * PathChildrenCache 监控子节点变化
     * TreeCache 以上2种结合，监控所有变化
     */
    public static void watchPathChildrenCache(CuratorFramework curatorFramework) throws Exception {
        PathChildrenCache cache = new PathChildrenCache(curatorFramework,"/watch",true);
        PathChildrenCacheListener pathChildrenCacheListener = (curatorFramework1, pathChildrenCacheEvent) -> {
            switch (pathChildrenCacheEvent.getType()) {
                case CHILD_ADDED:
                    System.out.println("新增节点 path:"+pathChildrenCacheEvent.getData().getPath()+"  data:"+new String(pathChildrenCacheEvent.getData().getData()));
                    break;
                case CHILD_UPDATED:
                    System.out.println("修改节点 path:"+pathChildrenCacheEvent.getData().getPath()+"  data:"+new String(pathChildrenCacheEvent.getData().getData()));
                    break;
                case CHILD_REMOVED:
                    System.out.println("删除节点 path:"+pathChildrenCacheEvent.getData().getPath()+"  data:"+new String(pathChildrenCacheEvent.getData().getData()));
                    break;
                    default:break;
            }
        };
        cache.getListenable().addListener(pathChildrenCacheListener);
        cache.start(PathChildrenCache.StartMode.NORMAL);
        System.in.read();
    }
}
