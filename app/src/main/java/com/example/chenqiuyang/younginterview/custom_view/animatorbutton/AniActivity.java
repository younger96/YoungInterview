package com.example.chenqiuyang.younginterview.custom_view.animatorbutton;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.chenqiuyang.younginterview.R;

public class AniActivity extends AppCompatActivity {
    private Button btnLoad,btnBack,btnComp;
    private YoungAnimatorButton btnYoung;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.btnactivity_main);
        btnLoad = findViewById(R.id.btn_load);
        btnBack = findViewById(R.id.btn_back);
        btnYoung = findViewById(R.id.btn_young);
        btnComp= findViewById(R.id.btn_comp);

        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnYoung.startLoading();
            }
        });

        btnComp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnYoung.completeLoading();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnYoung.backRect();
            }
        });
    }
}
