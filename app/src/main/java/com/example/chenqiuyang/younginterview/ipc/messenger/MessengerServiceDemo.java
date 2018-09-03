package com.example.chenqiuyang.younginterview.ipc.messenger;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.Nullable;
import android.widget.Toast;

//服务端
public class MessengerServiceDemo extends Service {

    static final int MSG_SAY_HELLO = 1;

    //服务端实现一个Handler，由其接受来自客户端的每个调用的回调
    class ServiceHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_SAY_HELLO:
                    //当收到客户端的message时，显示hello
                    Toast.makeText(getApplicationContext(), "hello!", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    //使用实现的Handler创建Messenger对象
    final Messenger mMessenger = new Messenger(new ServiceHandler());

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(getApplicationContext(), "binding", Toast.LENGTH_SHORT).show();
        //通过Messenger得到一个IBinder对象，并将其通过onBind()返回给客户端
        return mMessenger.getBinder();
    }

}