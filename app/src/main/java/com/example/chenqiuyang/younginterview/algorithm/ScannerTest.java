package com.example.chenqiuyang.younginterview.algorithm;

import java.util.Scanner;

/**
 * 输入示范
 */
public class ScannerTest {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
//        String res;
//        int _m;
//        _m = Integer.parseInt(in.nextLine().trim());//输入一行,删除多余空格
//        String _n;
//        _n = in.nextLine();//输入字符串
//        String[] array1 = _n.split(",");//切割字符串

        //输入二维数组
        int m;
        m = Integer.parseInt(in.nextLine().trim());
        int[][] arr = new int[m][m];
        for (int i = 0; i < m; i++) {
            String[] str = in.nextLine().trim().split(" ");
            for (int j = 0; j < m; j++) {
                arr[i][j] = Integer.parseInt(str[j]);
            }
        }
    }
}