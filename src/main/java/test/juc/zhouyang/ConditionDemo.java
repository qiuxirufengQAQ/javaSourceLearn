package test.juc.zhouyang;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class ShareData {
    private int number = 1;// A-1,B-2,C-3
    private Lock lock = new ReentrantLock();
    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();
    private Condition c3 = lock.newCondition();

    public void print(int num) {
        int num1 = num / 5;
        lock.lock();

        try {
            // 判断
            while (number != num1) {
                if (num == 5) {
                    c1.await();
                } else if (num == 10) {
                    c2.await();
                } else if (num == 15) {
                    c3.await();
                } else {
                    System.out.println("结束");
                    return;
                }
            }
            // 干活
            for (int j = 1; j <= num; j++) {
                System.out.println(Thread.currentThread().getName() + "\t" + j);
            }

            // 通知
            if (num == 5) {
                number = 2;
                c2.signal();
            } else if (num == 10) {
                number = 3;
                c3.signal();
            } else {
                number = 1;
                c1.signal();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}

/**
 * 备注：多线程之间按顺序调用，实现A->B-C
 * 三个线程启动，要求如下：
 * <p>
 * AA打印5次，BB打印10次，CC打印15次
 * 接着
 * AA打印5次，BB打印10次，CC打印15次
 * 来十轮
 */

public class ConditionDemo {
    public static void main(String[] args) {
        ShareData shareData = new ShareData();

        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                shareData.print(5);
            }
        }, "A").start();
        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                shareData.print(10);
            }
        }, "B").start();
        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                shareData.print(15);
            }
        }, "C").start();
    }
}
