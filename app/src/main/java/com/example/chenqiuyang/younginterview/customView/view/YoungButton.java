package com.example.chenqiuyang.younginterview.customView.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;

public class YoungButton extends Button {
    float beginY;
    float lastY;
    Animator animatorY,animatorX;
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
            case MotionEvent.ACTION_UP:
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

                animatorX = ObjectAnimator.ofFloat(this,"ScaleY",scaleNum);
                animatorY = ObjectAnimator.ofFloat(this,"ScaleX",scaleNum);
                animatorY.setDuration(0);
                animatorX.setDuration(0);
                animatorY.start();
                animatorX.start();
                break;
            case MotionEvent.ACTION_DOWN:
                beginY = event.getY();

                break;
              default:
                  break;
        }
        return super.onTouchEvent(event);
    }
}