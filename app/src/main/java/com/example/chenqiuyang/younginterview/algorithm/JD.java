package com.example.chenqiuyang.younginterview.algorithm;

import com.example.chenqiuyang.younginterview.algorithm.leetcode.Solution;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JD {

    public static void main(String arg[]) {
        //京东笔试算法题目
        Scanner in = new Scanner(System.in);
        int m  = Integer.parseInt(in.nextLine().trim());
        int[][] arr = new int[m][m];
        for (int i = 0; i < m; i++) {
            String[] strArr = in.nextLine().trim().split(" ");
            arr[i][0] = Integer.parseInt(strArr[0]);
            arr[i][1] = Integer.parseInt(strArr[1]);
            arr[i][2] = Integer.parseInt(strArr[2]);
        }
        System.out.println();

        for (int i = 0; i < arr[0].length; i++) {
            Solution.printArr(arr[i]);
        }

        System.out.println();

        //第一次对第一位进行排序
        int temp;
        for(int i=0;i<m;i++){
            for(int j=0;j<m-i;j++){
                if(arr[j][0]>arr[j+1][0]){
                    temp=arr[j][0];
                    arr[j][0]=arr[j+1][0];
                    arr[j+1][0]=temp;
                }
            }
        }

        //记录第二位b中 arr[][b大][] 比 arr[][b小][] 小的数字
        int sum = 0;
        for (int i = 0; i < m; i++) {
            for (int j = i+1; j < m; j++) {
                if (arr[i][0]<arr[j][0] && arr[i][1] < arr[j][1] && arr[i][2] < arr[j][2]){
                    sum++;
                }
            }
        }

        System.out.println(sum);

    }
}
