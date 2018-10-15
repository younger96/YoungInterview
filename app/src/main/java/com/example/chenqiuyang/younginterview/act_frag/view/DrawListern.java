package com.example.chenqiuyang.younginterview.act_frag.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.ViewTreeObserver;

import com.example.chenqiuyang.younginterview.R;

public class DrawListern extends Activity {
    private SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.sw_draw);


        //view重绘时的回调
        refreshLayout.getViewTreeObserver().addOnDrawListener(new ViewTreeObserver.OnDrawListener() {
            @Override
            public void onDraw() {

            }
        });


        //view加载完成时回调
        refreshLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

            }
        });
    }
}
