package com.example.chenqiuyang.younginterview.butterkinfe;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.chenqiuyang.younginterview.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;


public class ButterKnifeActivity extends Activity {

    @BindView(R.id.button)
    Button mButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_butter_knife);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.button)
    public void onBtnClick(View v){
        Toast.makeText(ButterKnifeActivity.this, "BtnClick", Toast.LENGTH_SHORT).show();
    }

    @OnLongClick(R.id.button)
    public boolean onBtnLongClick(View v){
        Toast.makeText(ButterKnifeActivity.this, "BtnLongClick", Toast.LENGTH_SHORT).show();
        return true;
    }
}
