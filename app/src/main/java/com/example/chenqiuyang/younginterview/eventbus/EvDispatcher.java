package com.example.chenqiuyang.younginterview.eventbus;

import android.util.Log;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by lihaibiao on 2018/5/15 15:14
 * E-Mail Address：lihaibiaowork@gmail.com
 */
public class EvDispatcher {
    private static final String TAG = "EvDispatcher";
    public static void register(Object subscriber){
        Log.i(TAG,"register subscriber = "+String.valueOf(subscriber));
        EventBus.getDefault().register(subscriber);
    }

    public static void unregister(Object subscriber){
        Log.i(TAG,"unregister = "+String.valueOf(subscriber));
        EventBus.getDefault().unregister(subscriber);
    }

    public static void dispatch(String type){
        dispatch(type,null);
    }

    public static void dispatch(String type,Object data){
        dispatch(new NormalEvent(type,data));
    }

    public static void dispatch(Object object){
        Log.i(TAG,"dispatch object = "+String.valueOf(object));
        EventBus.getDefault().post(object);
    }


}