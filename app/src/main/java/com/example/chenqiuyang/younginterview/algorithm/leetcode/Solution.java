package com.example.chenqiuyang.younginterview.algorithm.leetcode;


import com.example.chenqiuyang.younginterview.algorithm.struct.ListNode;

import java.util.ArrayList;
import java.util.List;


/**
 * 存储各个被调用的方法
 */
public class Solution {



//    /**
//     * 343. 整数拆分
//     * 动态规划
//     */
//    public static int integerBreak(int n) {
//        if (n<0){
//            return 0;
//        }
//
//
//        if (n == 1){
//            return 1;
//        }
//
//        return integerBreak()
//    }
//
//    // 343. 整数拆分 递归算法
//    //将n进行分割（至少为两部分，可以获得最大乘机）
//    public static int integerBreak(int n) {
//        if (n<0){
//            return 0;
//        }
//
//
//        if (n == 1){
//            return 1;
//        }
//
//
//    }


    //判断是否是回文串  125
    public static boolean isPalindrome(String str) {
        if (str == null || str.equals("")) {
            return false;
        }
        char[] arr = str.toCharArray();
        int i = 0, j = arr.length - 1;
        while (i <= j) {
            if (arr[i] == arr[j]) {
                i++;
                j--;
            } else {
                return false;
            }
        }
        return true;
    }


    /**
     * 122. 买卖股票的最佳时机 II
     * 滑动窗口  将大的数加起来
     */
    public static int maxProfitII(int[] prices) {
        if (null == prices || prices.length <= 1) {
            return 0;
        }
        int result = 0, cur;
        int i = 0, j = 0;//设立滑动窗口的前后指针
        while (j < prices.length) {
            cur = prices[j] - prices[i];
            if (cur < 0 && i < j) {
                i++;
                if (i == j) {
                    j++;
                }
            } else {
                result += cur;
                i = j;
                j++;
            }
        }
        return result;
    }


    /**
     * 121. 买卖股票的最佳时机
     * 滑动窗口
     */
    public static int maxProfit(int[] prices) {
        if (null == prices || prices.length <= 1) {
            return 0;
        }
        int result = 0, cur;
        int i = 0, j = 0;//设立滑动窗口的前后指针
        while (j < prices.length) {
            cur = prices[j] - prices[i];
            if (cur < 0 && i < j) {
                i++;
                if (i == j) {
                    j++;
                }
            } else {
                result = result > cur ? result : cur;
                j++;
            }
        }
        return result;
    }

    /**
     * 70. 爬楼梯
     * 动态规划  同lintcode 366. 斐波纳契数列
     */
    public static int climbStairs(int n) {
        if (n <= 0) {
            return 0;
        }
        if (n <= 2) {
            return n == 2 ? 2 : 1;
        }
        int[] arr = new int[n];
        arr[0] = 1;
        arr[1] = 2;

        for (int i = 2; i < n; i++) {
            arr[i] = arr[i - 1] + arr[i - 2];
        }

        return arr[n - 1];
    }


    /**
     * 448. 找到所有数组中消失的数字
     */
    static int[] findDisappearedNum(int[] nums) {


        return new int[]{};

    }


    /**
     * lintcode 366. 斐波纳契数列
     * 记忆化搜索
     */
    static List<Integer> fibonacciList = new ArrayList<>();

    public static int fibonacci(int n) {
        if (n <= 0) {
            return 0;
        }

        //检查n在ArrayList中有没有存储
        if (n <= fibonacciList.size()) {
            return fibonacciList.get(n - 1);
        }

        if (n == 1 || n == 2) {
            fibonacciList.add(0);
            fibonacciList.add(1);
            return n - 1;
        }

        int result = fibonacci(n - 2) + fibonacci(n - 1);
        fibonacciList.add(result);
        return result;
    }


    /**
     * 93. 复原IP地址
     * 今日头条笔试题目
     */
    public static int slipIpAddress(String s) {
        if (s.length() < 4 || s.length() > 12)
            return 0;
        List result = new ArrayList<>();
        List list = new ArrayList<>();

        slipIp(result, list, s, 0);
        return result.size();
    }

    // 93. 复原IP地址
    public static void slipIp(List<String> result, List<String> list, String s, int start) {
        if (list.size() == 4) {
            if (start != s.length())
                return;

            StringBuffer sb = new StringBuffer();
            for (String tmp : list) {
                sb.append(tmp);
                sb.append(".");
            }
            sb.deleteCharAt(sb.length() - 1);
            result.add(sb.toString());
            return;
        }

        for (int i = start; i < s.length() && i < start + 3; i++) {
            String tmp = s.substring(start, i + 1);
            if (isIps(tmp)) {
                list.add(tmp);
                slipIp(result, list, s, i + 1);
                list.remove(list.size() - 1);
            }
        }
    }

    // 93. 复原IP地址
    //Ip地址段的取值范围在 0-255
    private static boolean isIps(String s) {
        //第一位不能为0
        if (s.charAt(0) == '0')
            return s.equals("0");
        int ipL = Integer.valueOf(s);
        if (ipL < 0 || ipL > 255) {
            return false;
        }
        return true;
    }


    /**
     * 头条笔试 2
     * 输入M，和M*M的数组
     * 输出有关联的部门数量
     */
    public static int getConnectNums(int length, int[][] arr, List<String> list) {
        if (list.isEmpty()) {
            return 0;
        }

        int i, j;
        int sum = 0;//记录总数
        for (String str : list) {
            String[] str1 = str.split(" ");
            i = Integer.parseInt(str1[0]);
            j = Integer.parseInt(str1[1]);
            if (arr[i][j] == 1) {
                sum++;
                //递归调用
                setConnetNum(i, j, length, arr);//将相邻为1的数字设置为-1
            }

        }

        return sum;
    }


    // 头条笔试 2
    // 将相邻为1的数字设置为-1 递归调用
    public static void setConnetNum(int i, int j, int length, int[][] arr) {
        if (i > length || j > length) {
            return;
        }

        arr[i][j] = -1;

        if (i + 1 < length && arr[i + 1][j] == 1) {
            setConnetNum(i + 1, j, length, arr);
        }

        if (j + 1 < length && arr[i][j + 1] == 1) {
            setConnetNum(i, j + 1, length, arr);
        }

        return;
    }


    /**
     * 46. 全排列
     * 递归回溯 树型问题
     */
    public static List<List<Integer>> permute(int[] nums) {
        if (nums == null || nums.length == 0) {
            return new ArrayList<>();
        }

        List<Integer> perList = new ArrayList<>();
        List<List<Integer>> result = new ArrayList<>();
        boolean[] isUsed = new boolean[nums.length];
        generatePermute(nums, perList, result, isUsed);

        return result;
    }

    /**
     * 46. 全排列 递归算法 深度优先
     *
     * @param nums    待添加的数字组合
     * @param perList 待添加的list
     * @param result  List集合
     * @param isUsed
     */
    public static void generatePermute(int[] nums, List<Integer> perList, List<List<Integer>> result, boolean[] isUsed) {
        if (perList.size() == nums.length) {
            result.add(new ArrayList<>(perList));
            return;
        }

        for (int j = 0; j < nums.length; j++) {
            if (!isUsed[j]) {
                isUsed[j] = true;
                perList.add(nums[j]);
                generatePermute(nums, perList, result, isUsed);
                isUsed[j] = false;
                perList.remove(perList.size() - 1);
            }
        }
    }

    /**
     * 17. 电话号码的字母组合
     * 递归回溯 树型问题
     * 0-9 不包含1
     */
    public static List<String> letterCombinations(String digits) {
        if (digits == null || digits.isEmpty()) {
            return new ArrayList<>();
        }

        char[] arr = digits.toCharArray();
        List<String> list = new ArrayList<>();
        findCombinations(digits, 0, "", list);

        return list;
    }

    /**
     * 17. 递归函数
     *
     * @param digits 当前递归的字符串
     * @param index  当前递归的字符串的位
     * @param sb     待拼接的结果字符串
     */
    public static void findCombinations(String digits, int index, String sb, List<String> list) {
        if (index == digits.length()) {
            //当index遍历到底的时保存sb
            list.add(sb);
            return;
        }

        char c = digits.charAt(index);//获取当前的数字

        if (c < '0' || c > '9' || c == '1') {
            return;
        }

        String letters = latterMap[c - '0']; //得到当前数字对应的字符串
        for (int i = 0; i < letters.length(); i++) {
            findCombinations(digits, index + 1, sb + letters.charAt(i), list);
        }

    }

    //17. 电话号码的字母组合 数字代表的字母
    public static String[] latterMap = {
            " ",//0
            "",//1
            "abc",//2
            "def",//3
            "ghi",//4
            "jkl",//5
            "mno",//6
            "pqrs",//7
            "tuv",//8
            "wxyz" //9
    };


    /**
     * 阿里笔试算法题目  给出火柴排出最大数
     *
     * @param m
     * @param n
     * @return
     */
    static String maxNum(int m, int n) {
        if (m < 0 || n < 0 || m > (8 * n) || m < (2 * n)) {
            return "你的火柴数不够或者太多";
        }

        int a = m - 2 * n;//求全部为1之后剩下的火柴数
        StringBuilder sb = new StringBuilder();
        int a1 = 0; //判断字符串中末尾是否含有  7 / 9
        for (int i = n - 1; i >= 0; i--) {
            if (a == 0) {
                sb.append("1");
                continue;
            }

            if (a >= 4) {
                a = a - 4;
                a1 = 9;
                sb.append("9");
            } else {
                a1 = 7;
                a = a - 1;
                sb.append("7");
            }
        }

        if (a == 0)
            return sb.toString();

        char[] arr = sb.toString().toCharArray();
        //如果有剩火柴
        if (a1 == 7) {   //含有7的情况，只可能剩下 a < 3 的情况; 即要补两根或者一根火柴
            if (a == 2) {
                arr[n - 1] = '5';
            } else
                arr[n - 1] = '4';

        } else if (a1 == 9) {  //从小到大把数字替换成8
            for (int i = n - 1; i >= 0; i--) {
                if (a == 0) {
                    break;
                }
                a--;
                arr[i] = '8';
            }
        }

        sb = new StringBuilder();
        sb.append(arr);
        return sb.toString();
    }


    /**
     * 206. 反转链表
     */
    public ListNode reverseList(ListNode head) {
        if (head == null) {
            return head;
        }

        ListNode pre = null;  //指向上一个指针
        ListNode cur = head;   //指向待反转指针
        ListNode next; //指向下一个指针

        while (cur != null) {
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }

        return pre;
    }


    /**
     * 3 无重复字符的最长子串
     * 滑动窗口  (头条笔试)
     */
    public static int lengthOfLongestSubstring(String s) {
        if (s == null) {
            return 0;
        }

        char[] arr = s.toCharArray();
        boolean[] freq = new boolean[256];//记录窗口中的值

        int i = 0, j = -1;//设定滑动窗口[i...j]
        int sum; //[i...j]数组的长度
        int result = 0;
        sum = 0;
        while (i < arr.length) {
            if (j + 1 < arr.length && !isCharAtArr(freq, arr[j + 1])) {
                j++;
                sum++;
            } else if (j + 1 == arr.length) {
                return result;
            } else {
                // 删去[i...相同字符]部分，剩下[相同字符+1...j]部分
                while (arr[i] != arr[j + 1]) {
                    setCharAtArr(freq, arr[i], false);
                    sum--;
                    i++;
                }
                i++;
                j++;
            }

            if (sum > result) {
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
        if (freq[c]) {
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
        if (null == nums || nums.length == 0) {
            return 0;
        }

        int i = 0, j = -1;//设定滑动窗口[i...j]
        int sum; //i...j数组的值
        int result = nums.length + 1;
        sum = 0;
        while (i < nums.length) {
            if (j + 1 < nums.length && sum < s) {
                j++;
                sum = sum + nums[j];
            } else {
                sum = sum - nums[i];
                i++;
            }

            if (sum >= s) {
                result = result < (j - i + 1) ? result : (j - i + 1);
            } else if (j == nums.length - 1) { //优化，对已经到达边界的就舍弃剩下的不可能的比较
                break;
            }
        }

        return result == (nums.length + 1) ? 0 : result;
    }


    /**
     * 167 两数之和 II - 输入有序数组
     * 一定有解
     * 解法：对撞指针
     * 先从rp找，找到比目标小的，再从lp找
     */
    public static int[] twoSum(int[] numbers, int target) {
        //判空
        int[] arr = new int[]{-1, -1};
        int lp = 0, rp = numbers.length - 1;
        int sum;
        while (lp < rp) {
            sum = numbers[lp] + numbers[rp];
            if (sum == target) {
                arr[0] = lp + 1;
                arr[1] = rp + 1;
                break;
            }

            if (numbers[rp] > target || sum > target) {
                rp--;
            } else {  // numbers[rp] < target && sum <target
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
        int i = 0, two = nums.length - 1, zero = -1;

        while (i <= two) {
            if (nums[i] < v) {
                swap(nums, i, zero + 1);
                zero++;
                i++;
            } else if (nums[i] == v) {
                i++;
            } else { //1 0 2
                swap(nums, i, two);
                two--;
            }
        }
    }


    /**
     * 283 移动0
     *
     * @param nums
     */
    public static void moveZeroes(int[] nums) {
        int nowP = 0;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
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
    public static void swap(int[] arr, int i, int j) {
        int a = arr[i];
        arr[i] = arr[j];
        arr[j] = a;
    }

    //打印数组
    public static void printArr(int[] arr) {
        for (int anArr : arr) {
            System.out.print(anArr + " ");
        }
        System.out.println();
    }

    //打印数组
    public static void print2Arr(int[][] arr) {
        for (int[] arrAA : arr) {
            for (int arrA : arrAA) {
                System.out.print(arrA + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    //打印List
    public static void printList(List list) {
        for (Object s : list) {
            System.out.print(s.toString() + " ");
        }
        System.out.println();
    }


    /**
     * 二分查找
     *
     * @param arr    输入数组
     * @param target 目标查找
     */
    public static int binarySearch(int[] arr, int target) {
        if (arr == null || target < 0) {
            return -1;
        }

        int l = 0, size = arr.length, r = size - 1; //查找的区间在[l...r]之间
        int mid;
        while (l <= r) {
            mid = (l + r) / 2; //  mid = l + (r-l)/2;  考虑内存溢出
            if (arr[mid] == target) {
                return mid;
            } else if (arr[mid] < target) {
                l = mid + 1; //target在[mid+1...r]
            } else {
                r = mid - 1;  //target在[l...mid-1]
            }
        }

        return -1;
    }


}
