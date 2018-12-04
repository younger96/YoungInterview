package com.example.chenqiuyang.younginterview.customView.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.ImageView;

public class YoungButton extends Button {
    float beginY;
    float lastY;
    float scaleNum;
    private static final String TAG = "YoungButton";

    public YoungButton(Context context) {
        this(context,null);
        scaleNum = 1f;
    }

    public YoungButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG, "onTouchEvent: down");
                beginY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                Log.i(TAG, "onTouchEvent: up");
                lastY = event.getY();
              break;
            case MotionEvent.ACTION_MOVE:
                if ((beginY - event.getY())>0){
                    scaleNum += 0.04;
                    if (scaleNum >= 2){
                        scaleNum = 2f;
                    }
                }else {
                    scaleNum -=0.04;
                    if (scaleNum <= 0.5){
                        scaleNum = 0.5f;
                    }
                }
                this.setScaleX(scaleNum);
                this.setScaleY(scaleNum);
                Log.i(TAG, "onTouchEvent:MOVe "+scaleNum);
                break;
              default:
                  break;
        }
        return super.onTouchEvent(event);
    }
}