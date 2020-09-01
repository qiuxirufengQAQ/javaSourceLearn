package test.juc.zhouyang;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class ShareData2 {
    private int number = 1;// A-1,B-2,C-3
    private Lock lock = new ReentrantLock();
    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();
    private Condition c3 = lock.newCondition();


    public void print5() {
        try {
            lock.lock();
            // 判断
            while (number != 1) {
                c1.await();
            }
            // 干活
            for (int j = 1; j <= 5; j++) {
                System.out.println(Thread.currentThread().getName() + "\t" + j);
            }
            // 通知
            number = 2;
            c2.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void print10() {
        try {
            lock.lock();
            // 判断
            while (number != 2) {
                c2.await();
            }
            // 干活
            for (int j = 1; j <= 10; j++) {
                System.out.println(Thread.currentThread().getName() + "\t" + j);
            }
            // 通知
            number = 3;
            c3.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void print15() {
        try {
            lock.lock();
            // 判断
            while (number != 3) {
                c3.await();
            }
            // 干活
            for (int j = 1; j <= 15; j++) {
                System.out.println(Thread.currentThread().getName() + "\t" + j);
            }
            // 通知
            number = 1;
            c1.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}


public class Condition2Demo {
    public static void main(String[] args) {
        ShareData2 shareData = new ShareData2();

        new Thread(()->{
            for (int i = 1; i <= 10; i++) {
                shareData.print5();
            }
        },"A").start();

        new Thread(()->{
            for (int i = 1; i <= 10; i++) {
                shareData.print10();
            }
        },"B").start();

        new Thread(()->{
            for (int i = 1; i <= 10; i++) {
                shareData.print15();
            }
        },"C").start();

    }
}
