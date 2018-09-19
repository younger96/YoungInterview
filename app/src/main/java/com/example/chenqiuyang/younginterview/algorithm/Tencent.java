package com.example.chenqiuyang.younginterview.algorithm;

import java.util.Scanner;

public class Tencent {

    public static void test1() {
        int t = 3;//输入一行,删除多余空格
        int[] arr = new int[t];
        for (int i = 0; i < t; i++) {
//            arr[i] = Integer.parseInt(in.nextLine().trim());
        }
        int sum;
        int cur;
        for (int i = 0; i < t; i++) {
            sum = 0;
            cur = arr[i];
            while (cur != 0) {
                sum += cur % 10;
                cur /= 10;
            }

            if (arr[i] % sum == 0) {
                System.out.println("Yes");
            } else {
                System.out.println("No");
            }
        }
    }

    public static void main(String arg[]) {
        Scanner in = new Scanner(System.in);
        String s = in.nextLine().trim();
        String target = in.nextLine().trim();
        char[] arr = s.toCharArray();
        char[] trr = target.toCharArray();
        int sum = 0;
        int pre = 0, next = 0;//前后指针
        int tInt = 0;
        int trrLength = trr.length;
        int cur = 0;// 目前最长串
        if (isPalindrome(target)) {
            while (next < arr.length && pre < arr.length) {
                if (arr[pre] != trr[tInt]) {
                    pre++;
                    next = pre;
                } else {
                    //判断接下来有多少个晶石
                    while (next < arr.length && arr[next] == trr[tInt]) {
                        if (tInt == trr.length - 1) {
                            cur += trrLength;
                            tInt = 0;
                            if (arr[next] == trr[0] && next+1<arr.length && arr[next + 1] == trr[0]) {
                                next++;
                            }
                        } else {
                            next++;
                            tInt++;
                        }
                    }
                    sum += cur * cur;
                    cur = 0;
                    tInt = 0;
                    pre = next;
                }
            }
            System.out.println(sum);
            return;
        }
        while (next < arr.length && pre < arr.length) {
            if (arr[pre] != trr[tInt]) {
                pre++;
                next = pre;
            } else {
                //判断接下来有多少个晶石
                while (next < arr.length && arr[next] == trr[tInt]) {
                    if (tInt == trr.length - 1) {
                        cur += trrLength;
                        tInt = 0;
                        next++;
                    } else {
                        next++;
                        tInt++;
                    }
                }
                sum += cur * cur;
                cur = 0;
                tInt = 0;
                pre = next;
            }
        }
        System.out.println(sum);
    }


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
}
