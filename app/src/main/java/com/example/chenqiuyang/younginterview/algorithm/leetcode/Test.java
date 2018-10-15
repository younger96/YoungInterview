package com.example.chenqiuyang.younginterview.algorithm.leetcode;

import java.util.List;
import java.util.Scanner;

public class Test {


    public static void main(String arg[]) {
        //343. 整数拆分 动态规划 测试用例
        System.out.println(Solution.breakInteger(10));

        //122. 买卖股票的最佳时机 II
//        System.out.println(Solution.maxProfitII(new int[]{7,1,5,3,6,4}));

        //121. 买卖股票的最佳时机 滑动窗口 测试用例
//        System.out.println(Solution.maxProfit(new int[]{7,1,5,3,6,4}));

        // 70. 爬楼梯  动态规划  测试用例
//        System.out.println(Solution.climbStairs(4));

        //lintcode 366. 斐波纳契数列 测试用例
//        System.out.println(Solution.fibonacci(41));



        //46. 全排列 测试用例
//        int[] arr = new int[]{1,2,3};
//        List<List<Integer>> list = Solution.permute(arr);
//        for (int i = 0; i < list.size(); i++) {
//            Solution.printList(list.get(i));
//        }


        //17. 电话号码的字母组合 测试用例
//        Solution.printList(Solution.letterCombinations("23"));
//        Solution.printList(Solution.letterCombinations("22"));
//        Solution.printList(Solution.letterCombinations("12"));
//        Solution.printList(Solution.letterCombinations("9"));

        // 阿里笔试算法题目  给出火柴排出最大数  测试用例
//        Scanner in = new Scanner(System.in);
//        String res;
//        int _m;
//        _m = Integer.parseInt(in.nextLine().trim());
//        int _n;
//        _n = Integer.parseInt(in.nextLine().trim());
//        res = Solution.maxNum(_m, _n);
//        System.out.println(res);



        // 3 无重复字符的最长子串
//        System.out.println(Solution.lengthOfLongestSubstring("abcabcbb"));
//        System.out.println(Solution.lengthOfLongestSubstring("bbbbb"));
//        System.out.println(Solution.lengthOfLongestSubstring("pwwkew"));

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
