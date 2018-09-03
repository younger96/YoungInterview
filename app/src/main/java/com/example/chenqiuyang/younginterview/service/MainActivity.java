package com.example.chenqiuyang.younginterview.service;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.chenqiuyang.younginterview.R;

public class MainActivity extends AppCompatActivity {
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent = new Intent(this, PlayerService.class);
        startService(intent);// 启动服务
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(intent);// 在退出Activity时停止该服务
    }
}
