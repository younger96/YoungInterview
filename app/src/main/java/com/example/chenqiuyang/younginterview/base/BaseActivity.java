package com.example.chenqiuyang.younginterview.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;


public abstract class BaseActivity extends Activity {
    protected Context mContext;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mContext = this;
        initView();
        init();
    }

    protected abstract void init();

    protected abstract void initView();

    protected abstract int getLayoutId();
}
