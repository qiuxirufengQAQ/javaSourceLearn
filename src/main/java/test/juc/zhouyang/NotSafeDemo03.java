package test.juc.zhouyang;

import com.sun.javafx.collections.MappingChange;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;


/**
 * 1、故障现象
 * java.util.ConcurrentModificationException
 *
 * 2、导致原因
 * <p>
 * 3、解决方法
 *      3.1、new Vector<>();（方法自身带着synchronized，但现在不建议使用这个类）
 *      3.2、Collections.synchronizedList(new ArrayList<>());（在每个操作方法上添加synchronized限定符，保证单线程运行）
 *      3.3、CopyOnWriteArrayList(arr使用volatile，添加使用ReentrantLock加锁，并且每新增一个都复制所有的数组到一个新的数组，
 *              删除使用ReentrantLock加锁，并且复制到一个新数组中)（CopyOnWrite容器即写时复制的容器。往一个容器添加元素的时候，
 *              不直接往当前容器Object[]添加，而是先将当前Object[]进行Copy，复制出一个新的容器Object[] newElements,然后新的
 *              容器setArray(newElements);这样的好处是可以对CopyOnWrite容器进行并发的读，而不需要加锁，因为当前容器不会添加任何元素。
 *              所以CopyOnWrite容器也是一种读写分离的思想，读和写不同的容器）
 * <p>
 *     CopyOnWriteArraySet 底层使用了CopyOnWriteArrayList，新增方法使用了CopyOnWriteArrayList.addIfAbsent()
 * 4、优化建议（同样的错误不犯第2次）
 */
public class NotSafeDemo03 {
    public static void main(String[] args) {
        notSafeMap();
    }

    private static void notSafeMap() {
        Map<String,String> map =new ConcurrentHashMap<>() ;

        for (int i = 1; i <= 30; i++) {
            new Thread(() -> {
                map.put(Thread.currentThread().getName(), UUID.randomUUID().toString().substring(0, 8));
                System.out.println(map);
            }, String.valueOf(i)).start();
        }
    }

    private static void notSafeSet() {
        Set<String> set =new CopyOnWriteArraySet<>();

        for (int i = 1; i <= 30; i++) {
            new Thread(() -> {
                set.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(set);
            }, String.valueOf(i)).start();
        }
    }

    // list线程不安全
    public static void listNoteSafe(){
//        List<String> list = new Vector<>();
//        List<String> list = Collections.synchronizedList(new ArrayList<>());
        List<String> list = new CopyOnWriteArrayList<>();

        for (int i = 1; i <= 30; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(list);
            }, String.valueOf(i)).start();
        }
    }
}
