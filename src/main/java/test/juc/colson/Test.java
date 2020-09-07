package test.juc.colson;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class Test {
     public static void main(String[] args) throws InterruptedException {
         testCommonVarias();
     }

    private static void testCommonVarias() throws InterruptedException {
        AtomicInteger a = new AtomicInteger();
        AtomicInteger b = new AtomicInteger();
        AtomicInteger x = new AtomicInteger();
        AtomicInteger y = new AtomicInteger();
        CountDownLatch countDownLatch = new CountDownLatch(2);
        new Thread(() -> {
            a.set(1);
            x.set(b.get());
            countDownLatch.countDown();
        }, "A").start();
        new Thread(() -> {
            b.set(2);
            y.set(a.get());
            countDownLatch.countDown();
        }, "B").start();
        countDownLatch.await();

        System.out.println("X:" + x.get());
        System.out.println("Y:" + y.get());
    }
}
