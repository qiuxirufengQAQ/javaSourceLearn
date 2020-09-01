package test.juc.zhouyang;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

class MyThread implements Callable<Integer>{

    @Override
    public Integer call() throws Exception {
        System.out.println("运行了call方法。。。");
        return 1024;
    }
}

public class CallableDemo {
    public static void main(String[] args) {
//        FutureTask futureTask = new FutureTask(new MyThread());
        FutureTask futureTask = new FutureTask(()->{
            System.out.println("运行了函数式call方法");
            return 1024;
        });
        new Thread(futureTask,"A").start();
    }
}
