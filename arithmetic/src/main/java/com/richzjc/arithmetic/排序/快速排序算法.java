package com.richzjc.arithmetic.排序;

public class 快速排序算法 {

    //随机找出一个数，可以随机取，也可以取固定位置，
    // 一般是取第一个或最后一个称为基准，
    // 然后就是比基准小的在左边，比基准大的放到右边，
    // 如何放做，就是和基准进行交换，这样交换完左边都是比基准小的，右边都是比较基准大的，这样就将一个数组分成了两个子数组，
    // 然后再按照同样的方法把子数组再分成更小的子数组，直到不能分解为止。
    //时间复杂度比较复杂，最好的情况是O(n)，最差的情况是O(n2)，所以平时说的O(nlogn)，为其平均时间复杂度。
    public static void main(String[] args){
        int[] data = new int[]{19, 4, 999, 289, 0, 1999, 800, 100};
        if (data.length > 0) {
            quickSort(data, 0, data.length - 1);
            for (int i = 0; i < data.length; i++)
                System.out.println(data[i]);
        }
    }

    private static void quickSort(int[] numbers, int low, int high) {
        if (low >= high) {
            return;
        }
        int middle = getMiddle(numbers, low, high); // 将numbers数组进行一分为二
        quickSort(numbers, low, middle - 1); // 对低字段表进行递归排序
        quickSort(numbers, middle + 1, high); // 对高字段表进行递归排序
    }


    /**
     * 查找出中轴（默认是最低位low）的在numbers数组排序后所在位置
     *
     * @param numbers
     *            带查找数组
     * @param low
     *            开始位置
     * @param high
     *            结束位置
     * @return 中轴所在位置
     */
    public static int getMiddle(int[] numbers, int low, int high) {
        int temp = numbers[low]; // 数组的第一个作为中轴
        while (low < high) {
            while (low < high && numbers[high] > temp) {
                high--;
            }
            numbers[low] = numbers[high];// 比中轴小的记录移到低端
            while (low < high && numbers[low] < temp) {
                low++;
            }
            numbers[high] = numbers[low]; // 比中轴大的记录移到高端
        }
        numbers[low] = temp; // 中轴记录到尾
        return low; // 返回中轴的位置
    }

}
