package test.collection;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;


public class HashMapTest {

    @Test
    public void testHashMap(){
        Map<String,String> hashMap = new HashMap<>();

        hashMap.put("a","1");
        hashMap.put("b","2");
        hashMap.put("c","3");
        hashMap.put("d","4");
    }
}
