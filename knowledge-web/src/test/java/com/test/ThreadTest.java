package com.test;

import org.junit.Test;

/**
 * Created by nihao on 18/3/27.
 * interrupt
 * Thread.yield();
 *
 *
 */
public class ThreadTest {

    @Test
    public void test01(){
        new Thread(new T()).start();
    }

    class T implements Runnable{

        @Override
        public void run() {
            while (true){
                System.out.println(222);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
