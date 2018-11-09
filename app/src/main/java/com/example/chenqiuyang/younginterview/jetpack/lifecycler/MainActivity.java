package com.example.chenqiuyang.younginterview.jetpack.lifecycler;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.chenqiuyang.younginterview.R;

public class MainActivity extends AppCompatActivity {
    private IPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("tag", "onCreate" + this.getClass().toString());
        setContentView(R.layout.activity_main);
        mPresenter = new MainPresenter(this);
        getLifecycle().addObserver(mPresenter);//添加LifecycleObserver
    }

    @Override
    protected void onDestroy() {
        Log.d("tag", "onDestroy" + this.getClass().toString());
        super.onDestroy();
    }
}
