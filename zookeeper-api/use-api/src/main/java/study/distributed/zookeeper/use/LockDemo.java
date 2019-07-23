package study.distributed.zookeeper.use;

import org.apache.curator.framework.recipes.locks.InterProcessMutex;

public class LockDemo {
    public static void main(String[] args) {
        InterProcessMutex lock = new InterProcessMutex(ClientFactory.getClient(),"/lock");
        for (int i = 0; i < 5; i++) {
            new Thread(()->{
                try {
                    lock.acquire();
                    System.out.println(Thread.currentThread().getName() +" : 获取到锁");
                } catch (Exception e) {
                }finally {
                    try {
                        lock.release();
                    } catch (Exception e) {
                    }
                }
            },"thread-"+i).start();

        }
    }
}
