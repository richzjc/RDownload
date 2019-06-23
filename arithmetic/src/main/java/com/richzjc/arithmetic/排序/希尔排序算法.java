package com.richzjc.arithmetic.排序;

public class 希尔排序算法 {
    // 希尔排序算法其实就是分组排序算法，是插入排序的改进版本。
    // 事先定义 一个增量，一般情况下增量定义为数据长度的一半。
    // 每次遍历完之后 增量除2，直到增量的值为1的时候 就已经完全排好序了
    public static void main(String[] args) {
        int[] data = new int[]{19, 4, 999, 289, 0, 1999, 800, 100};
        int j = 0;
        int temp = 0;
        // 每次将步长缩短为原来的一半
        for (int increment = data.length / 2; increment > 0; increment /= 2) {
            for (int i = increment; i < data.length; i++) {
                temp = data[i];
                for (j = i; j >= increment; j -= increment) {
                    if (temp < data[j - increment])// 从小到大排
                    {
                        data[j] = data[j - increment];
                        System.out.println("i = " + i + "; j = " +j + ";" + data[0] + "，" +data[1] + "，" + data[2] + "，" +data[3] + "，" +data[4] + "，" +data[5] + "，" + data[6] + "，" +data[7]);
                    } else {
                        break;
                    }
                }
                data[j] = temp;
                System.out.println("djfadfai = " + i + "; j = " +j + ";" + data[0] + "，" +data[1] + "，" + data[2] + "，" +data[3] + "，" +data[4] + "，" +data[5] + "，" + data[6] + "，" +data[7]);
            }
        }
    }
}
