package com.example.chenqiuyang.younginterview.bus.eventbus;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;

import com.example.chenqiuyang.younginterview.R;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 发布消息
 * 被观察者
 */
public class SubjectActivity extends Activity {

    @BindView(R.id.button3)
    Button btn;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);
        ButterKnife.bind(this);

    }



    @OnClick(R.id.button3)
    public void click(){
        EvDispatcher.dispatch(new MyEvent("Just do it"));

        //发送粘性事件
       EvDispatcher.dispatchSticky(new MyEvent("Later do it"));
    }
}
