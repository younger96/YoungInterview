package com.example.chenqiuyang.younginterview.customView;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;

import com.example.chenqiuyang.younginterview.R;

/**
 * TODO:功能说明
 *
 * @author: chenqiuyang
 * @date: 2018-07-23 14:48
 */
public class CustomActivity extends Activity {
    private static final String TAG = "Young CustomActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);


    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i(TAG, "dispatchTouchEvent: "+ev.getAction());
        return super.dispatchTouchEvent(ev);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG, "onTouchEvent ACTION_DOWN: ");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i(TAG, "onTouchEvent ACTION_MOVE: ");
                break;
            case MotionEvent.ACTION_UP:
                Log.i(TAG, "#### onTouchEvent ACTION_UP");

                break;
            case MotionEvent.ACTION_CANCEL:
                Log.i(TAG, "#### onTouchEvent ACTION_CANCEL");
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }
}
