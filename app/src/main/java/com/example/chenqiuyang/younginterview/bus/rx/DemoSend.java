package com.example.chenqiuyang.younginterview.bus.rx;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.chenqiuyang.younginterview.bus.rx.message.MyMsg;

/**
 * 2018/12/5
 * from 陈秋阳
 * 功能描述：
 */
public class DemoSend extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MyMsg myMsg = new MyMsg(1,"hh");
        RxBus.getInstance().post(myMsg);
    }
}
