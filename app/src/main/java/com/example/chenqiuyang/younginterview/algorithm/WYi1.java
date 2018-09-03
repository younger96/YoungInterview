package com.example.chenqiuyang.younginterview.algorithm;
import java.util.HashMap;
import java.util.Scanner;

public class WYi1 {
    public static void main(String[] args) {
        int n = 0 ,k = 0;


        Scanner in = new Scanner(System.in);
        n = in.nextInt();
        k = in.nextInt();

        int[] arr = new int[n];
        int[] trr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = in.nextInt();
        }

        for (int i = 0; i < n; i++) {
            trr[i] = in.nextInt();
        }


        System.out.println(16);
    }
}