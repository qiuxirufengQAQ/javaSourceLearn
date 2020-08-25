package test.juc.zhouyang;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;


/**
 * 1、故障现象
 *java.util.ConcurrentModificationException
 * 2、导致原因
 *
 * 3、解决方法
 *      3.1、new Vector<>();
 *      3.2、Collections.synchronizedList(new ArrayList<>());
 *      3.3、
 * <p>
 * 4、优化建议（同样的错误不犯第2次）
 */
public class NotSafeDemo03 {
    public static void main(String[] args) {
//        List<String> list = new Vector<>();
        List<String> list = Collections.synchronizedList(new ArrayList<>());

        for (int i = 1; i <= 30; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(list);
            }, String.valueOf(i)).start();
        }
    }
}
