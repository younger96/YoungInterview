package com.example.chenqiuyang.younginterview.async.handle;

import android.os.HandlerThread;

/**
 * 继承HandlerThread创建自己的Handler线程。
 */
public class MyHandlerThreadExtHandlerThread extends HandlerThread {

    public MyHandlerThreadExtHandlerThread(String name) {
        super(name);
    }

    public MyHandlerThreadExtHandlerThread(String name, int priority) {
        super(name, priority);
    }

    @Override
    protected void onLooperPrepared() {
        // 在Looper执行死循环之前做一些准备工作。
    }

    @Override
    public void run() {
        super.run();

    }
}