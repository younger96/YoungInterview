package com.example.chenqiuyang.younginterview.service;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;

public class PlayerActivity extends Activity {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        Intent intent = new Intent(this, PlayerService.class);
        this.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);//绑定目标Service
        
    }


    @Override
    protected void onDestroy() {
      super.onDestroy();
      unbindService(serviceConnection);// 解除绑定，断开连接
   }

   //内部类持有外部引用
    // 在Activity中，我们通过ServiceConnection接口来取得建立连接与连接意外丢失的回调
    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service){
            // 建立连接
            // 获取服务的操作对象
               PlayerService.MyBinder binder = (PlayerService.MyBinder)service;
               binder.getService();// 获取到的Service即PlayerService
               binder.callEat();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
                  // 连接异常断开
        }
    };
}

