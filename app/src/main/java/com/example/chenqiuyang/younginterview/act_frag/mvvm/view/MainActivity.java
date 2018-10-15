package com.example.chenqiuyang.younginterview.act_frag.mvvm.view;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.chenqiuyang.younginterview.R;
import com.example.chenqiuyang.younginterview.act_frag.mvvm.model.UserBean;
import com.example.chenqiuyang.younginterview.act_frag.mvvm.viewmodel.MainViewModel;
import com.example.chenqiuyang.younginterview.databinding.ActivityMainBinding;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final String EXTRA_USER = "extra_user";
    ActivityMainBinding binding ;
    MainViewModel mainViewModel;

    public static Intent newIntent(Context context, UserBean user){
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(EXTRA_USER, user);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        UserBean userBean = getIntent().getParcelableExtra(EXTRA_USER);
        mainViewModel = new MainViewModel(this,userBean);
        binding.setViewmodel(mainViewModel);

    }


}
