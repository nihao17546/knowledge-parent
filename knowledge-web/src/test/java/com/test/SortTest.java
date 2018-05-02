package com.test;

import org.junit.Test;

/**
 * Created by nihao on 18/3/26.
 */
public class SortTest {

    @Test
    public void test01(){
        int[] a = {99,12,67,89,45,543,0};
        int[] b = {99,12,67,89,45,543,0};
        int[] c = {99,12,67,89,45,543,0};
        int[] d = {9,8,7,6,5,4,3,2,1,0};
        int[] e = {99,12,67,89,45,543,0};
        int[] f = {99,12,67,89,45,543,0};
        printOut(maopao(a));
        printOut(xuanze(b));
        printOut(charu(c));
        printOut(shell(d));
        printOut(shell(e));
        fast(f, 0, f.length-1);
        printOut(f);
    }

    private void printOut(int[] a){
        System.out.println("");
        for(int i : a){
            System.out.print(i + ",");
        }
    }

    /*
    冒泡排序
     */
    private int[] maopao(int[] a){
        for(int i = 0;i < a.length - 1;i ++){
            for(int j = 0;j < a.length - i - 1;j ++){
                if(a[j] > a[j+1]){
                    int temp = a[j];
                    a[j] = a[j+1];
                    a[j+1] = temp;
                }
            }
        }
        return a;
    }

    /*
    选择排序
     */
    private int[] xuanze(int[] a){
        for(int i=0;i<a.length-1;i++){
            int minIndex = i;
            int minValue = a[i];
            for(int j=i+1;j<a.length;j++){
                if(a[j]<minValue){
                    minIndex = j;
                    minValue = a[j];
                }
            }
            if(minIndex != i){
                a[minIndex] = a[i];
                a[i] = minValue;
            }
        }
        return a;
    }

    private int[] charu(int[] a){
        for(int i=0;i<a.length-1;i++){
            int currentIndex = i+1;
            int currentValue = a[currentIndex];
            for(int j=0;j<i+1;j++){
                if(currentValue<a[j]){
                    for(int x=currentIndex;x>j;x--){
                        a[x] = a[x-1];
                    }
                    a[j]=currentValue;
                    break;
                }
            }
        }
        return a;
    }

    /*
    希尔排序
     */
    private int[] shell(int[] a){
        for(int gap=a.length/2;gap>=1;gap/=2){
            for(int i=0;i<gap;i++){
                for(int j=i;j<a.length-gap;j+=gap){
                    int currentIndex = j+gap;
                    int currentValue = a[currentIndex];
                    for(int x=i;x<currentIndex;x+=gap){
                        if(currentValue<a[x]){
                            for(int y=currentIndex;y>x;y-=gap){
                                a[y] = a[y-gap];
                            }
                            a[x]=currentValue;
                            break;
                        }
                    }
                }
            }
        }
        return a;
    }

    /*
     * 排序的核心算法
     *
     * @param array
     *      待排序数组
     * @param startIndex
     *      开始位置
     * @param endIndex
     *      结束位置
     */
    private void fast(int[] array, int startIndex, int endIndex) {
        if (startIndex >= endIndex) {
            return;
        }

        int boundary = boundary(array, startIndex, endIndex);

        fast(array, startIndex, boundary - 1);
        fast(array, boundary + 1, endIndex);
    }

    /*
     * 交换并返回分界点
     *
     * @param array
     *      待排序数组
     * @param startIndex
     *      开始位置
     * @param endIndex
     *      结束位置
     * @return
     *      分界点
     */
    private int boundary(int[] array, int startIndex, int endIndex) {
        int standard = array[startIndex]; // 定义标准
        int leftIndex = startIndex; // 左指针
        int rightIndex = endIndex; // 右指针

        while(leftIndex < rightIndex) {
            while(leftIndex < rightIndex && array[rightIndex] >= standard) {
                rightIndex--;
            }
            array[leftIndex] = array[rightIndex];

            while(leftIndex < rightIndex && array[leftIndex] <= standard) {
                leftIndex++;
            }
            array[rightIndex] = array[leftIndex];
        }

        array[leftIndex] = standard;
        return leftIndex;
    }

    private int huafen(int[] array, int left, int right){
        int stander = array[left];
        while (left<right){
            while (left<right && array[right]>=stander){
                right--;
            }
            array[left]=array[right];
            while (left<right && array[left]<=stander){
                left++;
            }
            array[right]=array[left];
        }
        array[left]=stander;
        return left;
    }


}
