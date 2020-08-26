package test.juc.zhouyang;


/**
 * 题目：现在两个线程，可以操作初始值为零的一个变量
 * 实现一个线程对该变量加1，一个线程对该变量减1
 * 实现交替，来10轮，变量初始值为零
 *
 * 1、高内聚低耦合前提下，线程操作资源类
 * 2、判断+干活+通知
 * 3、防止线程的虚假唤醒，只要有wait需要用while判断
 *
 */
public class ProducerConsumerDemo04 {
    public static void main(String[] args) {

    }
}
