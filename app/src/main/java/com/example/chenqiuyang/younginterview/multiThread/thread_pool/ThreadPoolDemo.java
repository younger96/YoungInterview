package com.example.chenqiuyang.younginterview.multiThread.thread_pool;

public class ThreadPoolDemo {

    public static void main(String arg[]) {
        ThreadPool.shorter().execute(new Runnable() {
            @Override
            public void run() {
                //耗时操作

            }
        });
    }
}
