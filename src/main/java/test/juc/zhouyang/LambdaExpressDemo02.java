package test.juc.zhouyang;

//@FunctionalInterface
interface Foo {
    public void sayHello();

//    public int add();

    public default int mul(int x, int y) {
        return x * y;
    }

    public static int div(int x, int y) {
        return x / y;
    }
}

/**
 * 1、函数式编程
 * <p>
 * 拷贝中括号，写死右箭头，落地大括号
 *
 * @FunctionalInterface 针对只有一个方法的接口，程序会默认添加一个@FunctionalInterface注解，针对这样的接口可以使用lambda表达式
 * <p>
 * default 接口默认的实现方法，不用去实现
 * <p>
 * static 接口直接调用，不用实现，不影响lambda使用
 */
public class LambdaExpressDemo02 {
    public static void main(String[] args) {
        Foo foo = () -> System.out.println("Hello World");
        foo.sayHello();
        System.out.println(foo.mul(3, 5));
        System.out.println(Foo.div(15, 3));
    }
}
