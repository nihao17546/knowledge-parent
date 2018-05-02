package com.test;

import com.google.common.collect.Lists;
import com.test.proxy.DyProxy;
import com.test.proxy.IUserService;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by nihao on 18/4/9.
 */
public class SimpleTest {
    class PuKe implements Comparable<PuKe>{
        private int score;// 2-14
        private int color;// 花色:1-4

        public PuKe(int score, int color) {
            this.score = score;
            this.color = color;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }

        @Override
        public int compareTo(PuKe o) {
            return score - o.getScore();
        }

        @Override
        public String toString() {
            return "PuKe{" +
                    "score=" + score +
                    ", color=" + color +
                    '}';
        }
    }

    /**
     * 判断是否是同花顺
     * @param list
     * @return
     */
    public boolean is同花顺(List<PuKe> list){
        if(list.size() != 5){
            return false;
        }
        for(int i=0;i<4;i++){
            PuKe current = list.get(i);
            PuKe next = list.get(i+1);
            if(current.getScore()+1 != next.getScore()
                    || current.getColor() != next.getColor()){
                return false;
            }
        }
        return true;
    }

    /**
     * 判断是否是同花
     * @param list
     * @return
     */
    public boolean is同花(List<PuKe> list){
        if(list.size() != 5){
            return false;
        }
        int color = list.get(0).getColor();
        for(int i=1;i<5;i++){
            if(color != list.get(i).getColor()){
                return false;
            }
        }
        return true;
    }

    /**
     * 比较ab两人的牌
     * @param userA
     * @param userB
     * @return 0:不分上下,1:a大于b,-1:a小于b
     */
    public int compare(List<PuKe> userA, List<PuKe> userB){
        Collections.sort(userA);
        Collections.sort(userB);
        boolean a同花顺 = is同花顺(userA);
        boolean b同花顺 = is同花顺(userB);
        if(a同花顺){
            if(b同花顺){
                return userA.get(4).getScore() - userB.get(4).getScore();
            }
            return 1;
        }
        else{
            if(b同花顺){
                return -1;
            }
            boolean a同花 = is同花(userA);
            boolean b同花 = is同花(userB);
            if(a同花){
                if(b同花){
                    return userA.get(4).getScore() - userB.get(4).getScore();
                }
                return 1;
            }
            else{
                if(b同花){
                    return -1;
                }
                else {
                    return userA.get(4).getScore() - userB.get(4).getScore();
                }
            }
        }

    }


    @Test
    public void test01(){
        List<PuKe> userA = Arrays.asList(new PuKe[]{
                new PuKe(2,1),
                new PuKe(3,1),
                new PuKe(4,1),
                new PuKe(5,1),
                new PuKe(6,1)
        });
        List<PuKe> userB = Arrays.asList(new PuKe[]{
                new PuKe(7,1),
                new PuKe(7,2),
                new PuKe(7,3),
                new PuKe(7,4),
                new PuKe(8,1)
        });
        int result = compare(userA, userB);
        System.out.println(result);
    }

    @Test
    public void dasdas(){
        Integer a = null;
        Integer b = null;
        System.out.println(a==b);
    }
}
