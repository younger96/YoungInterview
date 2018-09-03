package com.example.chenqiuyang.younginterview.custom_view.qqredpoint;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.chenqiuyang.younginterview.R;


public class RedPointActivity extends AppCompatActivity {
    Button btnSetNum,btnAppear,btnDisappear;
    EditText etNum;
    RedPointView mRedPointView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red_point);
        etNum = findViewById(R.id.et_set_num);
        btnSetNum = findViewById(R.id.btn_set_num);
        btnAppear = findViewById(R.id.btn_appear);
        btnDisappear = findViewById(R.id.btn_disappear);
        mRedPointView  = findViewById(R.id.red_point);

        btnSetNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRedPointView.setNum(Integer.valueOf(etNum.getText().toString()));
            }
        });

        btnAppear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRedPointView.setDisappear(false);
            }
        });

        btnDisappear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRedPointView.setDisappear(true);
            }
        });
    }
}
