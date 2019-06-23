package com.richzjc.arithmetic.排序;

public class 插入排序算法 {
    //这是一个插入排序的算法, 其基本的原理是将一个无序的数组分成两部分
    // 前面一个是排序好的，后面一部分是未排序好的
    //将开头元素视为以排序部分。接着执行如下的处理，直到没有未排序部分。
    //取出未排序部分的开头元素赋值给临时保存数据的变量v。
    //在已排列的部分将所有比v大的元素向后移动一个位置。
    //将取出的元素v插入空位。
    //插入排序 和冒泡排序的共同点就是都会涉及到数据的交换操作
    //其时间复杂度是O(n2)
    public static void main(String[] args){
        System.out.println("测试java  项目");
        int[] a = new int[]{19,4, 999, 289, 0, 1999, 800, 100};
        int length = a.length;
        if(length > 1){
            int temp, j;
            for(int i = 1; i < length; i ++){
                temp = a[i];
                inner:
                j = i - 1;
                while (j >= 0 && a[j] > temp) {
                    a[j + 1] = a[j];
                    j--;
                }
                a[j + 1] = temp;
            }
        }

        for(int i = 0; i < length; i++)
            System.out.println(a[i]);
    }
}
