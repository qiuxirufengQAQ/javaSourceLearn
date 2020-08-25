package test.juc.zhouyang;

import java.util.Arrays;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// 资源类 = 实例变量 + 实例方法
class Ticket {
    private int number = 30;

    Lock lock = new ReentrantLock();

    public void sale() {
        lock.lock();
        try {
            if (number > 0) {
                System.out.println(Thread.currentThread().getName() + "\t卖出第：" + (number--) + "\t 还剩下：" + number);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}


/**
 * 题目：三个售票员 卖出 30张票
 * 笔记：如何编写企业级的多线程代码
 * 固定的编程套路+模板是什么？
 * <p>
 * 1、在高内聚低耦合的前提下，线程     操作      资源类
 * <p>
 * 1.1 一言不合，先创建一个资源类
 */
public class SaleTicketDemo01 {


    // 主线程，一切程序的入口
    public static void main(String[] args) {
        Ticket ticket = new Ticket();

        String[] arr = new String[]{"A", "B", "C"};

        Arrays.stream(arr).forEach(item -> new Thread(() -> {
            for (int i = 1; i < 40; i++) ticket.sale();
        }, item).start());


    }
}
