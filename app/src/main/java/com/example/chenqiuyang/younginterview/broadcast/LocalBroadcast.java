package com.example.chenqiuyang.younginterview.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import java.util.HashMap;
import java.util.Map;

public class LocalBroadcast {
    public static final String ACTION_1 = "";
    private static Map<String,BroadcastReceiver> broadcastReceiverMap = new HashMap<>();

    private static LocalBroadcastManager getManagerInstance(Context context){
        return  LocalBroadcastManager.getInstance(context);
    }

    //注册广播
    public static void registLocalBroadcast(Context context, BroadcastReceiver receiver,String action){
        LocalBroadcastManager.getInstance(context).registerReceiver(receiver,new IntentFilter(action));
        broadcastReceiverMap.put(action,receiver);
    }

    //注销广播
    public static void unRegistLocalBroadcast(Context context,String action){
        getManagerInstance(context).unregisterReceiver(broadcastReceiverMap.get(action));
    }

    public static void sendMsg(Context context,String action) {
        getManagerInstance(context).sendBroadcast(new Intent(action));
    }
}
