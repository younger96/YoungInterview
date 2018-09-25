package com.example.chenqiuyang.younginterview.eventbus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Button;


import com.example.chenqiuyang.younginterview.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 接受消息
 * 观察者
 */
public class EBObserverActivity extends Activity {
    private static final String TAG = "EBObserverActivity";
    @BindView(R.id.button4)
    Button button4;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventbus);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }

    /**
     * 事件响应方法
     * 接收消息
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MyEvent event) {
        /* Do something */
        Log.i(TAG, "onEvent: " + event.getString());

    }

    @OnClick(R.id.button4)
    public void click(){
       startActivity(new Intent(this,SubjectActivity.class));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
