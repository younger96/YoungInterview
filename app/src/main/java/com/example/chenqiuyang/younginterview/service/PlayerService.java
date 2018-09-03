package com.example.chenqiuyang.younginterview.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

public class PlayerService extends Service {

    @Override
    public void onCreate() {
        super.onCreate(); 
    } 

    /** 
    * onBind 是 Service 的虚方法，因此我们不得不实现它。
    * 返回 null，表示客服端不能建立到此服务的连接。
    */ 
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    // 已取代onStart方法--onStart方法是在Android2.0之前的平台使用的.
    // 在2.0及其之后，则需重写onStartCommand方法，同时，旧的onStart方法则不会再被直接调用
    // （外部调用onStartCommand，而onStartCommand里会再调用 onStart。在2.0之后，
    // 推荐覆盖onStartCommand方法，而为了向前兼容，在onStartCommand依然会调用onStart方法。
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId); 
    } 

    @Override
    public boolean onUnbind(Intent intent) { 
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy(); 
    }


    private void eat() {
        Toast.makeText(getApplicationContext(), "toast开始吃东西了", Toast.LENGTH_SHORT).show();
    }

    // IBinder是远程对象的基本接口，是为高性能而设计的轻量级远程调用机制的核心部分。但它不仅用于远程
    // 调用，也用于进程内调用。这个接口定义了与远程对象交互的协议。
    // 不要直接实现这个接口，而应该从Binder派生。
    // Binder类已实现了IBinder接口
    class MyBinder extends Binder {

        void callEat(){
            eat();
        }

        /** 
        * 获取Service的方法
        * @return 返回PlayerService
        */ 
        public  PlayerService getService(){
            return PlayerService.this;
        }
    }
}