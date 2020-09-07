package test.jvm.zhouyang;

public class RefCountGC {
    // GC算法引用计数法 缺点代码实现
    private byte[] bigSize = new byte[2*1024*1024];
    Object instace = null;

    public static void main(String[] args) {
        RefCountGC objectA = new RefCountGC();
        RefCountGC objectB = new RefCountGC();

        objectA.instace = objectB;
        objectB.instace = objectA;
        objectA = null;
        objectB = null;

        System.gc();
    }
}
