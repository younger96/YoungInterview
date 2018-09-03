package com.example.chenqiuyang.younginterview.customView;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import android.widget.RelativeLayout;

/**
 * TODO:功能说明
 *
 * @author: chenqiuyang
 * @date: 2018-07-23 14:53
 */
public class SlideContentLayout extends RelativeLayout {


    private static final String TAG = "Young SContentLayout";

    private int currentLayoutHeight; //当前布局的高度
    private float myY;//当前手指的位置Y坐标
    private float myX;//当前手指的位置X坐标

    private InterceptInter mInterceptInter;//判断外部是否拦截事件

    public SlideContentLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public SlideContentLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SlideContentLayout(Context context) {
        super(context);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed,l,t,r,b);
        if (changed) {//当前布局位置是否改变
            currentLayoutHeight = b - t;
            Log.i(TAG, "onLayout getLayoutParams l:" + l + ",t:" + t + ",r:" + r + ",b:" + b);
            Log.i(TAG, "onLayout getMeasuredWidth:" + getMeasuredWidth() + ",getMeasuredHeight:" + getMeasuredHeight());
            Log.i(TAG, "onLayout getWidth:" + getWidth() + ",getHeight:" + getHeight());
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i(TAG, "dispatchTouchEvent: " + ev.getAction());
        return false;
    }

    //拦截事件
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean isIntercept = false;//默认不拦截
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG, "#### onInterceptTouchEvent ACTION_DOWN");
                myY = ev.getRawY();
                myX = ev.getRawX();
                break;
            case MotionEvent.ACTION_MOVE:
                if (mInterceptInter!=null && mInterceptInter.isIntercept()){
                    isIntercept = true;
                }




                float moveY = ev.getRawY() - myY;//上下平移
                Log.i(TAG, "#### onInterceptTouchEvent ACTION_MOVE:"+moveY);
                //手指下滑判断
                if (moveY > 0 && Math.abs(moveY) > 2) {
                    //进行事件拦截判断
                    if (getY() == 0 || getY() > 0) {
                        myY = ev.getRawY();
                        return true;//拦截事件
                    }

                }

                //手指上滑判断
                if (moveY < 0 && Math.abs(moveY) > 2) {
                    //进行事件拦截判断
                    if (getY() > 0) {
                        myY = ev.getRawY();
                        return true;//拦截事件
                    }

                }

                Log.i(TAG, "#### onInterceptTouchEvent ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.i(TAG, "#### onInterceptTouchEvent ACTION_UP");

                break;
        }

        return isIntercept;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG, "onTouchEvent ACTION_DOWN: ");
                break;
            case MotionEvent.ACTION_MOVE:
                float moveY = event.getRawY() - myY;//上下平移的高度
                Log.i(TAG, "onTouchEvent ACTION_MOVE moveY: "+moveY);
                //手指下滑判断
                if (moveY > 0 && Math.abs(moveY) > 2) {
                    //进行事件拦截判断
                    if (getY() >= 0) {
                        myY = event.getRawY();
                        setY(getY() + moveY);
                        requestLayout();//刷新布局
                        return true;//消耗事件
                    }

                }

                //手指上滑判断
                if (moveY < 0 && Math.abs(moveY) > 2) {
                    //进行事件拦截判断
                    if (getY() > 0) {
                        myY = event.getRawY();
                        setY(getY() + moveY);
                        requestLayout();//刷新布局
                        return true;//消耗事件
                    }

                }


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
        Log.i(TAG, "#### onTouchEvent: "+super.onTouchEvent(event));
        return true;
    }


    public void setInterceptInter(InterceptInter interceptInter){
        mInterceptInter = interceptInter;
    }
}
