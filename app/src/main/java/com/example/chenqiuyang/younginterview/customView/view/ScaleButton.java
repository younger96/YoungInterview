package com.example.chenqiuyang.younginterview.customView.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.example.chenqiuyang.younginterview.R;

/**
 * 2018/11/28
 * from 陈秋阳
 * 功能描述：
 */
public class ScaleButton extends android.support.v7.widget.AppCompatButton {
    private static final String TAG = "ScaleButton";
    Animator animatorXIn,animatorYIn,animatorXOut,animatorYOut;
    AnimatorSet animatorSet,animatorSetIn,animatorSetOut;
    private  long curTime;

    public ScaleButton(Context context) {
        this(context,null);
    }

    public ScaleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        animatorXIn = ObjectAnimator.ofFloat(this,"ScaleY",0.95f);
        animatorYIn = ObjectAnimator.ofFloat(this,"ScaleX",0.95f);
        animatorYOut= ObjectAnimator.ofFloat(this,"ScaleY",1f);
        animatorXOut = ObjectAnimator.ofFloat(this,"ScaleX",1f);
        animatorSet = new AnimatorSet();
        animatorSetIn = new AnimatorSet();
        animatorSetOut = new AnimatorSet();
        animatorSetIn.play(animatorXIn).with(animatorYIn);
        animatorSetOut.play(animatorXOut).with(animatorYOut);
        animatorSetIn.setDuration(200);
        animatorSetOut.setDuration(200);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                curTime = System.currentTimeMillis();
                animatorSetIn.start();
                break;
            case MotionEvent.ACTION_UP:
                Log.i(TAG, "onTouchEvent: up");
                long space = System.currentTimeMillis() - curTime;
                Log.i(TAG, "onTouchEvent: "+space);
                if (space <= 200){
                    animatorSetOut.setStartDelay((200-space));
                    animatorSetOut.start();
                }else {
                    animatorSetOut.start();
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.i(TAG, "onTouchEvent: cancel");
                animatorSetOut.start();
                break;
        }
        return super.onTouchEvent(event);
    }
}
