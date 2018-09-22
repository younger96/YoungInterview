package com.example.chenqiuyang.younginterview.algorithm;

import java.util.Scanner;

public class Xiaomi {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        //2
        long n = Integer.parseInt(in.nextLine().trim());
        long sum = 1;
        for (long i = 1; i <= n; i++) {
            sum*=i;
        }

        System.out.println(sum);


        //1
//        String str = in.nextLine();
//        String[] arr = str.trim().split(" ");
//        if (arr[0] == null || arr[1] == null || arr[0].equals("") || arr[1].equals("") ){
//            System.out.println("-1 -1");
//            return;
//        }
//        char[] text = arr[0].toCharArray();
//        char[] pattern = arr[1].toCharArray();
//
//
//        int cur,i = 0;//cur指向text,i指向pattern
//        //连续
//        for (int k = 0; k < text.length; k++) {
//            if (text[k] == pattern[i]){
//                cur = k;
//                while (cur<text.length && i!=pattern.length && text[cur] == pattern[i]){
//                    i++;
//                    cur++;
//                }
//
//                if (i == pattern.length){
//                    System.out.println((cur-pattern.length)+" "+(cur-1));
//                    return;
//                }
//
//                i = 0;
//            }
//        }
//
//        //不连续
//        int j = text.length; //记录第一次cur的位置
//        for (int k = 0; k < text.length; k++) {
//            if (text[k] == pattern[i]){
//                cur = k;
//                j = j>cur?cur:j;
//                while (cur<text.length && i!=pattern.length && text[cur] == pattern[i]){
//                    i++;
//                    cur++;
//                }
//
//                if (i == pattern.length){
//                    System.out.println(j+" "+(cur-1));
//                    return;
//                }
//            }
//        }
//
//        System.out.println("-1 -1");
    }
}
