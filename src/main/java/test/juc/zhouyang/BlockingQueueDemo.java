package test.juc.zhouyang;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 两个数据结构：栈/队列
 * 栈    后进先出
 * 队列   先进先出
 * <p>
 * 阻塞队列
 * 阻塞   必须要阻塞/不得不阻塞
 */
public class BlockingQueueDemo {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);

        blockingQueue.add("a");
        blockingQueue.add("b");
        blockingQueue.add("c");

//        blockingQueue.add("x");
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue);

//        查看下一个元素
        System.out.println(blockingQueue.element());

//        添加元素的同时返回true或false，不会抛异常
        System.out.println(blockingQueue.offer("d"));

//        获取下一个元素，不会返回异常，返回对象或者返回空
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());

//        获取下一个元素，如果没有元素，则会阻塞
//        blockingQueue.take();

//        尝试在3秒内添加成功，如果不成功则返回布尔值
        System.out.println(blockingQueue.offer("a", 3L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("a", 3L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("a", 3L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("a", 3L, TimeUnit.SECONDS));

//        尝试在3秒内删除数据，如果不成功则返回布尔值
        System.out.println(blockingQueue.poll(3L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.poll(3L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.poll(3L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.poll(3L, TimeUnit.SECONDS));
    }
}
