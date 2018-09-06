package com.example.chenqiuyang.younginterview.algorithm;

import java.util.Random;

/**
 * TODO:功能说明
 *
 * @author: chenqiuyang
 * @date: 2018-08-07 11:23
 */
public class Sort {



    //三路快排
    public static void quickSort3Way(int[] arr, int l, int r) {
        if (l > r){
            return;
        }
        //可以选择随机比较的值
        //swap(arr,l,getRandom(arr.length-1));
        int v = arr[l];
        int i = l + 1, lt=l,rt = r;

        while (i<rt) {
            if (arr[i] < v) {
                swap(arr,i, lt+1);
                lt++;
                i++;
            }

            if (arr[i] == v) {
                i++;
            }
            if (arr[i] > v) {
                swap(arr,i, rt);
                rt--;

            }

        }

        swap(arr,l, lt);

        quickSort3Way(arr, l, lt-1 );
        quickSort3Way(arr, rt, r);


    }

    //快速排序
    public static void quickSort(int[] a) {
        if (null != a && a.length > 0){
            quickSort(a,0,a.length);
        }
    }

    //快速排序
    public static void quickSort(int[] a, int l, int r) {
        if (l >= r) {
            return;
        }

        int key = a[l];//选第一个作为中轴
        int i = l,j = r;

        while(i < j ){
            while(i < j && a[j] > key){//从右往左找到比key小的
                j--;
            }
            if (j > i){//i位置的数替换为小的
                a[i] = a[j];
                i++;
            }

            while(i < j && a[i] < key){//从左往右找到比key大的
                i++;
            }
            if (j > i ){
                a[j] = a[i];
                j--;
            }
        }
        //i==j
        a[i] = key;


        quickSort(a,l,i-1);//递归
        quickSort(a,i+1,r);
    }


    //交换函数
    public static void swap(int[] arr,int i,int j){
        int a = arr[i];
        arr[i] = arr[j];
        arr[j] = a;
    }

    public static void main(String arg[]) {
        for (int i = 0; i < 100; i++) {
            System.out.println(getRandom(10));
        }
    }

    //随机函数  [0,n-1]
    public static int getRandom(int n){
        return (int) (Math.random()*n);
    }


}
