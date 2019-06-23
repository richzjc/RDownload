package com.richzjc.arithmetic.排序;

public class 选择排序算法 {

    //选择排序算法是依次从后序列表中找出一个最小的数据排在前面，其效率比冒泡排序算法稍微要高
    //因为缺少了数据交换，冒泡排序每次循环都会涉及到交换顺序
    //其时间复杂度同样是O(n2)
    public static void main(String[] args) {
        int[] a = new int[]{19, 4, 999, 289, 0, 1999, 800, 100};
        int length = a.length;
        if (length > 1) {
            for(int i = 0; i < length - 1; i ++){
                int k = i;
                for(int j = i + 1; j < length; j ++){
                    if (a[j] < a[k]) {
                        k = j;
                    }
                }
                int temp = a[i];
                a[i] = a[k];
                a[k] = temp;
            }
        }

        for (int i = 0; i < length; i++)
            System.out.println(a[i]);
    }

}
