package com.example.chenqiuyang.younginterview.algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO:功能说明
 *
 * @author: chenqiuyang
 * @date: 2018-08-01 08:43
 */
public class Test {
    public static void main(String arg[]) {
        System.out.println("hello java");

    }


    public static void fizzBuzz(int n) {
        // write your code here
        if(n<=0){
            return;
        }


        List<String> arr = new ArrayList<>();
        for (int i=1;i<=n ;i++){
            String a;
            a = ""+i;
            if(i%3 == 0 || i%5 == 0){
                a = (i%15 == 0)?"fizz buzz":((n%3 == 0)? "fizz":"buzz");
            }
            arr.add(a);
            System.out.println(""+a);
        }

       // return arr;
    }
}
