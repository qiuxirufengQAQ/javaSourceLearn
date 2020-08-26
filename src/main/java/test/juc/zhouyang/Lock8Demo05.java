package test.juc.zhouyang;

import java.util.concurrent.TimeUnit;

class Phone {
    public static synchronized void sendEmail() throws Exception {
        TimeUnit.SECONDS.sleep(4);
        System.out.println("sendEmail");
    }

    public synchronized void sendText() throws Exception {
        System.out.println("sendText");
    }

    public void sayHello() {
        System.out.println("Hello");
    }
}

/**
 * 8 lock
 * 1、标注能访问，先打印email还是text -- 先email
 * 2、暂停4秒钟邮件，请问先邮件还是先短信 -- 还是先email
 * 3、新增普通sayHello方法，请问先打印邮件还是Hello -- hello，
 * 4、两部手机，请问先打印邮件还是短信，-- text
 * 5、两个静态同步方法，同一部手机，请问先打印邮件还是短信 ，-- email
 * 6、两个静态同步方法，2部手机，请问先打印邮件还是短信 ，-- email
 * 7、一个静态同步方法，一个普通同步方法，同一部手机，请问先邮件还是短信 -- text
 * 8、一个静态同步方法，一个普通同步方法，；两部手机，请问先邮件还是短信 -- text
 * <p>
 * <p>
 * 结论：加synchronized的方法，在访问的时候，是整个对象进行上锁，其他带synchronized的方法也不能访问
 * <p>
 * 一个对象里面如果有多个synchronized方法，某一个时刻内，只要一个线程去调用其中的一个synchronized方法了，
 * 其他的线程都只能等待，换句话说，某一个时刻内，只能有唯一一个线程去访问这些synchronized方法
 * <p>
 * 锁的是当前对象this，被锁定后，其他的线程都不能进入到当前对象的其他的synchronized方法
 * <p>
 * 加一个普通方法，和锁没有关系
 * <p>
 * 换成了两个对象，不是同一把锁了，不会存在竞争锁的问题
 * <p>
 * 静态同步方法，以类为锁，不只是指某一个对象了，只要其他的对象调用了这个方法，则整个同步方法都会加锁
 * <p>
 * synchronized实现同步的基础：Java中的每一个对象都可以作为锁
 * 具体表现为以下3种形式
 * 对于普通同步方法，锁是当前实例对象
 * 对于同步方法块，锁是synchronized括号里配置的对象
 * <p>
 * 对于静态同步方法，锁的是当前类的class对象
 * <p>
 * 当一个线程视图访问同步代码块时，它首先必须得到锁，退出或抛出异常必须释放锁
 * <p>
 * 也就是说如果一个实例对象的非静态同步方法获取锁后，该实例对象的其他非静态同步方法必须等待获取锁的方法释放锁后才能获取锁，
 * 可是别的实例对象的非静态同步方法因为跟该实例对象的非静态同步方法用的是不同的锁
 * 所以无须等待该实例对象已获取锁的非静态同步方法释放锁就可以获取他们自己的锁
 * <p>
 * 所有的静态同步方法用的也是同一把锁--类对象本身
 * 这两把锁是两个不同的对象，所以静态同步方法与非静态同步方法之间是不会有竞态条件的
 * 但是一旦一个静态同步方法获取锁后，其他的静态同步方法都必须等待该方法释放锁后才能获取锁，
 * 而不管是同一个实例对象的静态同步方法之间
 * 还是不同的实例对象的静态同步方法之间，只要他们是同一个类的实例对象
 */

public class Lock8Demo05 {
    public static void main(String[] args) throws InterruptedException {
        Phone phone = new Phone();
        Phone phone2 = new Phone();

        new Thread(() -> {
            try {
                phone.sendEmail();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "A").start();
        Thread.sleep(100);
        new Thread(() -> {
            try {
                phone.sendText();
//                phone.sayHello();
//                phone2.sendText();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "B").start();
    }
}
