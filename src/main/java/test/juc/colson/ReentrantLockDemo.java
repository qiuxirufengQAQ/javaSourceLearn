package test.juc.colson;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo {
    public static void main(String[] args) {
        Lock lock = new ReentrantLock();

//        上锁
        lock.lock();


        lock.unlock();

    }
}
