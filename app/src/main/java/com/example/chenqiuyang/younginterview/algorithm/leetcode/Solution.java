package com.example.chenqiuyang.younginterview.algorithm.leetcode;



/**
 * 存储各个被调用的方法
 */
public class Solution {

    /**
     * 3 无重复字符的最长子串
     * 滑动窗口
     */
    public static int lengthOfLongestSubstring(String s) {
        if (s == null){
            return 0;
        }

        char[] arr = s.toCharArray();
        boolean[] freq = new boolean[256];//记录窗口中的值

        int i=0,j=-1;//设定滑动窗口[i...j]
        int sum ; //[i...j]数组的长度
        int result = 0;
        sum = 0;
        while (i < arr.length){
            if (j+1 < arr.length && !isCharAtArr(freq,arr[j+1])){
                j++;
                sum++;
            } else if (j+1 == arr.length){
                return result;
            } else{
                // 删去[i...相同字符]部分，剩下[相同字符+1...j]部分
                while (arr[i] != arr[j+1]){
                    setCharAtArr(freq,arr[i],false);
                    sum--;
                    i++;
                }
                i++;
                j++;
            }

            if(sum > result) {
                result = sum;
            }
        }

        return result;
    }

    // 3 无重复字符的最长子串  ---  删除存在该字符数组中的字符信息
    private static void setCharAtArr(boolean[] freq, char c, boolean b) {
        freq[c] = b;
    }

    // 3 无重复字符的最长子串  ---  判断字符是否存在该字符数组中
    private static boolean isCharAtArr(boolean[] freq, char c) {
        if (freq[c]){
            return true; //已经存在
        }
        freq[c] = true;
        return false;//不存在，已经存进去
    }

    /**
     * 209 长度最小的连续子数组
     * 滑动窗口
     */
    public static int minSubArrayLen(int s, int[] nums) {
        //判空
        if (null == nums || nums.length == 0){
            return 0;
        }

        int i=0,j=-1;//设定滑动窗口[i...j]
        int sum ; //i...j数组的值
        int result = nums.length+1;
        sum = 0;
        while (i < nums.length){
            if (j+1 < nums.length && sum < s){
                j++;
                sum = sum+nums[j];
            }else{
                sum = sum-nums[i];
                i++;
            }

            if(sum >= s) {
                result = result<(j-i+1)?result:(j-i+1);
            }else if (j == nums.length-1){ //优化，对已经到达边界的就舍弃剩下的不可能的比较
                break;
            }
        }

        return result==(nums.length+1)?0:result;
    }



    /**
     * 167 两数之和 II - 输入有序数组
     * 一定有解
     * 解法：对撞指针
     * 先从rp找，找到比目标小的，再从lp找
     */
    public static int[] twoSum(int[] numbers, int target) {
        //判空
        int[] arr = new int[]{-1,-1};
        int lp =0,rp = numbers.length-1;
        int sum;
        while (lp < rp) {
            sum = numbers[lp]+numbers[rp];
            if (sum == target){
                arr[0] = lp+1;
                arr[1]= rp+1;
                break;
            }

            if (numbers[rp] > target || sum > target){
                rp--;
            }else {  // numbers[rp] < target && sum <target
                lp++;
            }
        }
        return arr;
    }



    /**
     * 75. 颜色分类
     */
    public static void sortColors(int[] nums) {
        int v = 1;
        int i=0,two = nums.length-1 ,zero = -1;

        while(i<=two){
            if (nums[i] < v ){
                swap(nums,i,zero+1);
                zero++;
                i++;
            }else if ( nums[i] == v ){
                i++;
            }else{ //1 0 2
                swap(nums,i,two);
                two--;
            }
        }
    }


    /**
     * 283 移动0
     * @param nums
     */
    public static void moveZeroes(int[] nums) {
        int nowP = 0;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0){
                nums[nowP] = nums[i];
                nowP++;
            }
        }

        //接下来的数赋值为0
        for (; nowP < nums.length; nowP++) {
            nums[nowP] = 0;
        }
    }


    //交换函数
    public static void swap(int[] arr,int i,int j){
        int a = arr[i];
        arr[i] = arr[j];
        arr[j] = a;
    }

    //打印数组
    public static void printArr(int[] arr){
        for (int anArr : arr) {
            System.out.print(anArr + " ");
        }
        System.out.println();
    }



    /**
     *   二分查找
     * @param arr 输入数组
     * @param target 目标查找
     */
    public static int  binarySearch(int[] arr,int target){
        if (arr == null || target < 0){
            return -1;
        }

        int l = 0,size = arr.length,r = size-1; //查找的区间在[l...r]之间
        int mid ;
        while(l <= r){
            mid = (l+r)/2; //  mid = l + (r-l)/2;  考虑内存溢出
            if (arr[mid] == target){
                return  mid;
            }else if (arr[mid] < target ){
                l = mid + 1; //target在[mid+1...r]
            }else {
                r = mid -1;  //target在[l...mid-1]
            }
        }

        return  -1;
    }



}
