package com.example.chenqiuyang.younginterview.broadcast;

import com.example.chenqiuyang.younginterview.base.BaseActivity;

public class SendAct extends BaseActivity {

    @Override
    protected void init() {
        LocalBroadcast.sendMsg(this,LocalBroadcast.ACTION_1);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutId() {
        return 0;
    }
}
