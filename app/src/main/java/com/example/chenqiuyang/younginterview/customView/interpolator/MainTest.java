package com.example.chenqiuyang.younginterview.customView.interpolator;

import android.app.Activity;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.chenqiuyang.younginterview.R;

/**
 * 2019/2/18
 * from 陈秋阳
 * 功能描述：
 */
public class MainTest extends Activity {
    private static final String TAG = "MainTest";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_test);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CubcBezier cubcBezier = new CubcBezier(new PointF(0.18f,1.39f),new PointF(0.15f,0.95f));
                for (float i = 0;i<=1;i+=0.01){
                    float a = cubcBezier.getY(i);
                    if (a == 0){
                        cubcBezier.getY(i);
                    }
                    Log.i(TAG, "onCreate "+(int)(i*100)+":            "+a);
                }
            }
        });
    }

}
