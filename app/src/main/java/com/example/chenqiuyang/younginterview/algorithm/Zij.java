package com.example.chenqiuyang.younginterview.algorithm;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Zij {
    public static void main(String[] args) {
        int m = 0;


        Scanner in = new Scanner(System.in);
        m = in.nextInt();

        List<String> list = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            list.add(in.next());
        }

        HashMap<Integer,Integer> map = new HashMap<>();
        for (String a :
                list) {
            String[] array = a.split(";");
            for (String b :
                    array) {
                String[] array1 = b.split(",");
                map.put(Integer.getInteger(array1[0]),Integer.getInteger(array1[1]));
            }
        }

        for (Map.Entry<Integer, Integer> entry:
        map.entrySet()){
            for (Map.Entry<Integer, Integer> entry1:
                    map.entrySet()){
                // a,b  c,d
                if (entry.getKey() >=  entry1 .getKey() && entry.getKey() <=  entry1 .getValue()) { //a>=c  a<=d
                    if (entry.getValue() <  entry1 .getValue()){// b <=d
                           map.remove(entry.getKey());//remove a,b
                    }else {  //  b>d                 a,b c,d   --> c,b
                        entry1.setValue(entry.getValue());
                        map.remove(entry.getKey());
                    }

                    continue;
                }
                if(entry.getValue() >=  entry1 .getKey() && entry.getValue() <=  entry1 .getValue()){//b>=c  b<=d
                    // a < c    a,b c,d --->  a,d
                    entry.setValue(entry1.getValue());
                    map.remove(entry1.getKey());
                }
            }
        }


        for (Map.Entry<Integer, Integer> entry:
                map.entrySet()){
            System.out.println(entry.getKey()+","+entry.getValue());
        }

    }
}