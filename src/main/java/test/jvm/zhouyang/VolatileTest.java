package test.jvm.zhouyang;


class MyNumber {
    volatile int number = 10;

    public void updateNum() {
        this.number = 100;
    }
}

public class VolatileTest {
    public static void main(String[] args) {
        MyNumber myNumber = new MyNumber();

        new Thread(() -> {
            System.out.println("************************");
            try {
                Thread.sleep(3000);
                myNumber.updateNum();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "\t " + myNumber.number);
        }, "A").start();

        while (myNumber.number == 10) {

        }
        System.out.println("程序结束");
    }
}
