package com.richzjc.arithmetic.排序;

public class 归并排序算法 {

    //归并排序算法，是将数组对半分，
    // 一直分下去  直到只有一个数为止
    // 在只有一个数的时候  ，在横向的进行排序
    //这时候会得到一些排序好的数组，
    // 在对排序好的数组 进行合并 从而得到一个有序的数组

    public static void main(String[] args){
        int[] data = new int[]{19, 4, 999, 289, 0, 1999, 800, 100};
        sort(data, 0, data.length - 1);
        for (int i = 0; i < data.length; i++)
            System.out.println(data[i]);
    }

    public static int[] sort(int[] nums, int low, int high) {
        int mid = (low + high) / 2;
        if (low < high) {
            // 左边
            sort(nums, low, mid);
            // 右边
            sort(nums, mid + 1, high);
            // 左右归并
            merge(nums, low, mid, high);
        }
        return nums;
    }

    public static void merge(int[] nums, int low, int mid, int high) {
        int[] temp = new int[high - low + 1];
        int i = low;// 左指针
        int j = mid + 1;// 右指针
        int k = 0;
        // 把较小的数先移到新数组中
        while (i <= mid && j <= high) {
            if (nums[i] < nums[j]) {
                temp[k++] = nums[i++];
            } else {
                temp[k++] = nums[j++];
            }
        }
        // 把左边剩余的数移入数组
        while (i <= mid) {
            temp[k++] = nums[i++];
        }
        // 把右边边剩余的数移入数组
        while (j <= high) {
            temp[k++] = nums[j++];
        }
        // 把新数组中的数覆盖nums数组
        for (int k2 = 0; k2 < temp.length; k2++) {
            nums[k2 + low] = temp[k2];
        }
    }
}
