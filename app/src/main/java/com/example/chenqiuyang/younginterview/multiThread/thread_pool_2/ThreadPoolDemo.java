package com.example.chenqiuyang.younginterview.multiThread.thread_pool_2;

public class ThreadPoolDemo {

    public static void main(String arg[]) {
        ThreadManager.getThreadPool().execute(new Runnable() {
            @Override
            public void run() {

            }
        });
    }
}
