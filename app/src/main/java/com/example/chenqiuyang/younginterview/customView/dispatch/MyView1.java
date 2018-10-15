package com.example.chenqiuyang.younginterview.customView.dispatch;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * TODO:功能说明  测试自定义View
 *
 * @author: chenqiuyang
 * @date: 2018-07-23 14:30
 */
public class MyView1  extends AppCompatTextView {
    private static final String TAG = "Young MyView1";

    public MyView1(Context context) {
        super(context);
    }

    public MyView1(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.i(TAG, "dispatchTouchEvent return: "+event.getAction());
        return super.dispatchTouchEvent(event);
    }




    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG, "#### onTouchEvent ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i(TAG, "#### onTouchEvent ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.i(TAG, "#### onTouchEvent ACTION_UP");

                break;
        }
        boolean result = super.onTouchEvent(event);
        Log.i(TAG, "#### onTouchEvent return:" + result);
        return result;
    }
}
