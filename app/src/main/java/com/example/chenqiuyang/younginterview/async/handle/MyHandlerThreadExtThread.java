package com.example.chenqiuyang.younginterview.async.handle;

import android.os.Looper;

/**
 * 继承Thread创建自己的Handler线程。
 */
public class MyHandlerThreadExtThread extends Thread {

    @Override
    public void run() {
        // 创建一个Handler线程。
        Looper.prepare();

        // 在Looper执行死循环之前做一些准备工作。

        // 启动Looper，进入死循环不断处理消息队列中的消息。
        Looper.loop();
    }

    /**
     * 以不安全的方式停止Handler线程。
     */
    public void quit() {
        //        Looper looper =  getLooper();
        Looper looper = Looper.getMainLooper();
        if (looper != null) {
            looper.quit();
        }
    }

    /**
     * 以安全的方式停止Handler线程。
     */
    public void quitSafely() {
//        Looper looper =  getLooper();
        Looper looper =  Looper.getMainLooper();
        if (looper != null) {
            looper.quitSafely();
        }
    }

}