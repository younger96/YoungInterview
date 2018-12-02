package com.example.chenqiuyang.younginterview.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.example.chenqiuyang.younginterview.base.BaseActivity;

public class ReceiveAct extends BaseActivity {


    @Override
    protected void init() {
        LocalBroadcast.registLocalBroadcast(mContext, new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

            }
        },LocalBroadcast.ACTION_1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcast.unRegistLocalBroadcast(mContext,LocalBroadcast.ACTION_1);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutId() {
        return 0;
    }
}
