package com.example.chenqiuyang.younginterview.algorithm;

import java.util.Scanner;

public class Baidu {

    public static void main(String arg[]) {

        Scanner in = new Scanner(System.in);
        int n = Integer.parseInt(in.nextLine().trim());
        int[] arr = new int[n];
        String[] str = in.nextLine().trim().split(" ");
        int small = 0;
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(str[i]);
            if (arr[i] < 5900){
                small++;
            }
        }

        float result = (float) (n-small)/(float)n;
        System.out.printf("%.2f%%",result);

    }
}
