package com.example.chenqiuyang.younginterview.act_frag.mvvm.viewmodel;

import android.content.Context;

import com.example.chenqiuyang.younginterview.act_frag.mvvm.model.UserBean;

public class MainViewModel {
    private Context context;
    private UserBean user;

    public MainViewModel(Context context, UserBean user) {
        this.context = context;
        this.user = user;
    }

    public String getUsername(){
        return user.getName();
    }
    public String getPassword(){
        return user.getPassword();
    }
}
