package com.example.chenqiuyang.younginterview.algorithm;
import java.util.HashMap;
import java.util.Scanner;
public class WYi {
    public static void main(String[] args) {
        int n = 0 ,m = 0;


        Scanner in = new Scanner(System.in);
        n = in.nextInt();
        m = in.nextInt();

        int[] arr = new int[m];
        for (int i = 0; i < m; i++) {
            arr[i] = in.nextInt();
        }





     //   System.out.println(n + m + arr[4]);
        if (m < n){
            System.out.println(0);
        }
        HashMap<Integer,Integer> map = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            if (i<n && !map.containsKey(i+1)){
                map.put(i+1,0);
            }

            if (map.containsKey(arr[i])){
                int va = map.get(arr[i]);
                map.put(arr[i],va+1);
            }else {
                map.put(arr[i],1);
            }
        }


        int min = map.get(1);// map中最小的数，就是输出的数
        //遍历map
        for (int i = 1; i <= n; i++) {
            min = min>map.get(i)?map.get(i):min;
        }

        System.out.println(min);
    }
}