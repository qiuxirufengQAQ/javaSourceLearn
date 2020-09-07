package test.juc.zhouyang;

import java.util.concurrent.Semaphore;

/**
 * 在信号上我们定义两种操作
 * acquire（获取）当一个线程调用acquire操作时，它要么通过成功获取信号量（信号量减1），
 * 要么一直等下去，直到有线程释放信号量，或超时
 * release（释放）实际上会将信号量的值加1，然后唤醒等待的线程
 * 信号量主要用于两个目的，一个是用于多个共享资源的互斥使用，另一个用于并发线程的控制
 */
public class SemaphoreDemo {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3);

        for (int i = 0; i < 6; i++) {
            new Thread(() -> {
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + "\t占到了车位");
                    Thread.sleep(3000);
                    System.out.println(Thread.currentThread().getName() + "\t离开了车位");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                }
            }, String.valueOf(i)).start();
        }
    }
}
