package com.example.chenqiuyang.younginterview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.example.chenqiuyang.younginterview.customView.view.bounce.BounceActivity;


/**
 * 2018/11/22
 * from 陈秋阳
 * 功能描述：
 */
public class Test extends Activity {
    private static final String TAG = "TestActivity";
    Context context;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_test);
        final ImageView btn = findViewById(R.id.btn);
        context = this;

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int location[] = new int[2] ;
                btn.getLocationOnScreen(location);
                Bundle bundle = new Bundle() ;
                bundle.putInt("locationX",location[0]);
                bundle.putInt("locationY",location[1]);
                bundle.putInt("width",btn.getWidth());
                bundle.putInt("height",btn.getHeight());
                Intent intent = new Intent() ;
                intent.putExtras(bundle);
                intent.setClass(context,BounceActivity.class) ;
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

    }


}
