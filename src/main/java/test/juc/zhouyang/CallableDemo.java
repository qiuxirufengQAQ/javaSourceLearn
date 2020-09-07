package test.juc.zhouyang;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;


public class CallableDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        FutureTask futureTask = new FutureTask(new MyThread());
        FutureTask futureTask = new FutureTask(() -> {
            System.out.println("运行了函数式call方法");
            if (Thread.currentThread().getName().equals("A")) {
                return "A";
            } else if (Thread.currentThread().getName().equals("B")) {
                return "B";
            } else {
                return 1024;
            }
        });
        new Thread(futureTask, "A").start();
        new Thread(futureTask, "B").start();
        new Thread(futureTask, "C").start();
        Thread.sleep(3000);
        System.out.println(futureTask.get());
        System.out.println(Thread.currentThread().getName() + "运算结束");
    }

    class MyThread implements Callable<Integer> {

        @Override
        public Integer call() throws Exception {
            System.out.println("运行了call方法。。。");
            return 1024;
        }
    }
}
