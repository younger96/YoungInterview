package com.example.chenqiuyang.younginterview.algorithm;

import android.os.Handler;
import android.os.Message;

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
        StaticTe staticTe1 = new StaticTe();
        StaticTe staticTe2 = new StaticTe();
        staticTe1.ST  += 1 ;
        staticTe2.ST  += 1 ;
        System.out.println(staticTe1.ST);
        System.out.println(staticTe2.ST);
        Handler handler = new Handler();
        Message message = handler.obtainMessage();

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
