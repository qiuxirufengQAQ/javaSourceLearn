package test.juc.zhouyang;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 多个线程同时读一个资源类没有任何问题，读取共享资源应该可以同时进行。
 * 但是
 * 如果有一个线程想去写共享资源来，就不应该再有其它线程可以对该资源进行读或写
 * 小总结：
 * --读-读能共存
 * --读-写不能共存
 * --写-写不能共存
 */
public class ReadWriteLockDemo {
    public static void main(String[] args) {
        MyCache myCache = new MyCache();


        for (int i = 1; i <= 5; i++) {
            int finalI = i;
            new Thread(() -> {
                myCache.put(String.valueOf(finalI), String.valueOf(finalI));
            }, String.valueOf(i)).start();
        }
        for (int i = 1; i <= 5; i++) {
            int finalI = i;
            new Thread(() -> {
                myCache.get(String.valueOf(finalI));
            }, String.valueOf(i)).start();
        }
    }

    public static class MyCache {
        private volatile Map<String, Object> map = new HashMap<>();
        ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

        public void put(String key, Object value) {
            readWriteLock.writeLock().lock();

            try {
                System.out.println(Thread.currentThread().getName() + "\t-----写入开始");
                Thread.sleep(300);
                map.put(key, value);
                System.out.println(Thread.currentThread().getName() + "\t-----写入结束");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                readWriteLock.writeLock().unlock();
            }

        }

        public void get(String key) {
            readWriteLock.readLock().lock();
            try {
                System.out.println(Thread.currentThread().getName() + "\t读取开始");
                Object object = map.get(key);

                System.out.println(Thread.currentThread().getName() + "\t读取结束" + object);
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                readWriteLock.readLock().unlock();
            }

        }

    }
}
