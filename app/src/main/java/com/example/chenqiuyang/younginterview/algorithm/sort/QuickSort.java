package com.example.chenqiuyang.younginterview.algorithm.sort;

/**
 * TODO:功能说明
 *
 * @author: chenqiuyang
 * @date: 2018-08-07 11:23
 */
public class QuickSort {
    public static void quickSort(int[] a, int l, int r) {
        //判空

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
        for (int anA : a) {
            System.out.print(anA+" ");
        }
        System.out.println("");
        quickSort(a,l,i-1);//递归
        quickSort(a,i+1,r);
    }

    public static void main(String arg[]) {
        System.out.println("hello java");
        int[] a =  {5,3,1,9,8,2,4,7};
        quickSort(a,0,a.length-1);
    }

}
