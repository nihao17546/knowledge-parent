package com.test;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nihao on 18/4/12.
 */
public class JVMTest {
    @Test
    public void test01(){
        System.out.println(Runtime.getRuntime().maxMemory());
        System.out.println(Runtime.getRuntime().freeMemory());
        System.out.println(Runtime.getRuntime().totalMemory());
        System.out.println("---------");
        byte[] b = new byte[1*1024*1024];
        System.out.println(Runtime.getRuntime().maxMemory());
        System.out.println(Runtime.getRuntime().freeMemory());
        System.out.println(Runtime.getRuntime().totalMemory());
        System.out.println("---------");
        String a = "123";
        String aaa = "12dasdsadasdasdas3";
        List<String> aa = new ArrayList<>();
        aa.add(a);
        aa.add(aaa);
        System.out.println(Runtime.getRuntime().maxMemory());
        System.out.println(Runtime.getRuntime().freeMemory());
        System.out.println(Runtime.getRuntime().totalMemory());
    }
}
