package com.richzjc.arithmetic.排序;

public class 冒泡排序算法 {

    //冒泡排序在我看来就是一种下层式的排序算法，
    //每循环一次得到最大值 或者最小的值排在最下面

    //比较相邻的元素。如果第一个比第二个大，就交换他们两个。
    //对每一对相邻元素作同样的工作，从开始第一对到结尾的最后一对。在这一点，最后的元素应该会是最大的数。
    //针对所有的元素重复以上的步骤，除了最后一个。持续每次对越来越少的元素重复上面的步骤，直到没有任何一对数字需要比较。
    //时间复杂度是O(n2),但是里面涉及到数据的交换，其实效率不是很高
    public static void main(String[] args) {
        int[] a = new int[]{19, 4, 999, 289, 0, 1999, 800, 100};
        int size = a.length;
        int temp;
        boolean flag = true;
        for (int i = 0; i < size - 1&&flag; i++) {
            flag = false;
            for (int j = 0; j < size - 1 - i; j++) {
                if (a[j] > a[j + 1]) // 交换两数位置
                {
                    temp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = temp;
                    flag = true;
                }
            }
        }

        for(int i = 0; i < a.length; i++)
            System.out.println(a[i]);
    }
}
