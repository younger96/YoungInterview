package com.example.chenqiuyang.younginterview.customView.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.example.chenqiuyang.younginterview.R;

/**
 * 2019/1/17
 * from 陈秋阳
 * 功能描述：
 */
public class ClickingButton extends Button implements View.OnClickListener {
    private static final String TAG = "ClickingButton";

    private  long curTime;
    int colorClicking;
    int colorClicked;
    int resCustom;


    public ClickingButton(Context context) {
        this(context,null);
    }

    public ClickingButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        //获取系统属性
        int[] customAttrs = {android.R.attr.background};
        TypedArray a0 = context.obtainStyledAttributes(attrs, customAttrs);
        resCustom = a0.getResourceId(0, 0);
        a0.recycle();


        //获取自定义属性
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ClickingButton);
        colorClicking = a.getResourceId(R.styleable.ClickingButton_click_ing,0);
        colorClicked = a.getResourceId(R.styleable.ClickingButton_click_up,0);
        a.recycle();

        setOnClickListener(this);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setScaleX(0.95f);
                setScaleY(0.95f);
                if (colorClicking!=0){
                    this.setBackgroundResource(colorClicking);
                }
                if (colorClicking != 0 && resCustom !=0){
                    this.setBackgroundResource(colorClicking);
                }
                break;
            case MotionEvent.ACTION_UP:
                setScaleX(1.0f);
                setScaleY(1.0f);
                if (colorClicking != 0 && resCustom !=0){
                    this.setBackgroundResource(resCustom);
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                setScaleX(1.0f);
                setScaleY(1.0f);
                if (colorClicking != 0 && resCustom !=0){
                    this.setBackgroundResource(resCustom);
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onClick(View v) {
        Log.i(TAG, "onClick: ");

    }
}
