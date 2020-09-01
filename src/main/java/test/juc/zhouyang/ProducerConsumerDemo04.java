package test.juc.zhouyang;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class AirCondition {
    private int number = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void increment() {
        lock.lock();

        try {
            //1、判断
            while (number != 0) {
                condition.await();
            }
            // 2、干活
            number++;
            System.out.println(Thread.currentThread().getName() + " " + number);

            // 3、通知
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }

    }

    public void decrement()  {
        lock.lock();
        try {
            //1、判断
            while (number == 0) {
                condition.await();
            }
            // 2、干活
            number--;
            System.out.println(Thread.currentThread().getName() + " " + number);

            // 3、通知
            condition.signalAll();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
        
    }


//    public synchronized void increment() throws Exception {
//        //1、判断
//        while (number != 0) {
//            this.wait();
//        }
//        // 2、干活
//        number++;
//        System.out.println(Thread.currentThread().getName() + " " + number);
//
//        // 3、通知
//        this.notifyAll();
//    }
//
//    public synchronized void decrement() throws Exception {
//        //1、判断
//        while (number == 0) {
//            this.wait();
//        }
//        // 2、干活
//        number--;
//        System.out.println(Thread.currentThread().getName() + " " + number);
//
//        // 3、通知
//        this.notifyAll();
//    }
}

/**
 * 题目：现在两个线程，可以操作初始值为零的一个变量
 * 实现一个线程对该变量加1，一个线程对该变量减1
 * 实现交替，来10轮，变量初始值为零
 * <p>
 * 1、高内聚低耦合前提下，线程操作资源类
 * 2、判断+干活+通知
 * 3、防止线程的虚假唤醒，只要有wait需要用while判断
 * <p>
 * 知识小总结 = 多线程编程套路+while判断+
 */
public class ProducerConsumerDemo04 {
    public static void main(String[] args) {
        AirCondition airCondition = new AirCondition();

        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                try {
                    airCondition.increment();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "A1").start();
        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                try {
                    airCondition.decrement();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "B1").start();
        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                try {
                    airCondition.increment();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "A2").start();
        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                try {
                    airCondition.decrement();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "B2").start();
    }
}
