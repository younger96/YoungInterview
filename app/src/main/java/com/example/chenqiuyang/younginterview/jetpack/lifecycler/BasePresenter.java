package com.example.chenqiuyang.younginterview.jetpack.lifecycler;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.util.Log;

import org.greenrobot.greendao.annotation.NotNull;

public class BasePresenter implements IPresenter {

    private static final String TAG = "com.qingmei2.module.base.BasePresenter";    

    @Override
    public void onLifecycleChanged(@NotNull LifecycleOwner owner, @NotNull Lifecycle.Event event) {

    }

    @Override
    public void onCreate(@NotNull LifecycleOwner owner) {
        Log.d("tag", "BasePresenter.onCreate" + this.getClass().toString());
    }

    @Override
    public void onDestroy(@NotNull LifecycleOwner owner) {
        Log.d("tag", "BasePresenter.onDestroy" + this.getClass().toString());
    }
}