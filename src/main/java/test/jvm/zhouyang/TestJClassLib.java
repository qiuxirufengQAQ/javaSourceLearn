package test.jvm.zhouyang;

public class TestJClassLib {
    public static void main(String[] args) {
        test();
    }

    public static void test() {
        int a = 0;
        System.out.println(a);
        int b = 2;
    }

    public static void localVal2() {
        {
            int a = 0;
            System.out.println(a);
        }
        int b = 2;
    }
}
