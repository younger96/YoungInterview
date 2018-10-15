package com.example.chenqiuyang.younginterview.sourse.handle;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

class LooperThread extends Thread {
    public Handler mHandler;

    public void run() {
        Looper.prepare();   //【见 2.1】

        mHandler = new Handler() {  //【见 3.1】
            public void handleMessage(Message msg) {
                //TODO 定义消息处理逻辑. 【见 3.2】



            }
        };

        Looper.loop();  //【见 2.2】
    }
}