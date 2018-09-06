package com.example.chenqiuyang.younginterview.algorithm.leetcode;

public class Test {


    public static void main(String arg[]) {

        // 3 无重复字符的最长子串
        System.out.println(Solution.lengthOfLongestSubstring("abcabcbb"));
        System.out.println(Solution.lengthOfLongestSubstring("bbbbb"));
        System.out.println(Solution.lengthOfLongestSubstring("pwwkew"));

        // 209. 长度最小的子数组 测试用例
//        int[] arr = new int[]{1,2,3,4,5};
//        System.out.println(Solution.minSubArrayLen(15,arr));


        // 167 两数之和 II - 输入有序数组 测试用例
//        int[] arr = new int[]{2,7,11,15};
//        int[] result = Solution.twoSum(arr,9);
//        System.out.println(result[0]+" "+result[1]);

        // 75 颜色分类 测试用例
//        int[] arr = new int[]{2,0,1};
//        Solution.sortColors(arr);
//        Solution.printArr(arr);

        // 283 赋值0 测试用例
//        int[] arr = new int[]{1,2,0,0,3};
//        Solution.moveZeroes(arr);
//        Solution.printArr(arr);

        //二分查找测试用例
//        int[] arr = getIntArr(50);
//        System.out.println("5存在50中的哪个位置:"+Solution.binarySearch(arr,5));
//        System.out.println("35存在50中的哪个位置:"+Solution.binarySearch(arr,35));
//        System.out.println("-1存在50中的哪个位置:"+Solution.binarySearch(arr,-1));
//        System.out.println("50存在50中的哪个位置:"+Solution.binarySearch(arr,50));
//        System.out.println("0存在50中的哪个位置:"+Solution.binarySearch(arr,0));

    }

    //获取一个正序的数组，大小为n
    private static int[] getIntArr(int n){
        if (n<=0){
            return null;
        }
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = i;
        }

        return arr;
    }
}
