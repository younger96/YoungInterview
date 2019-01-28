package com.example.chenqiuyang.younginterview.customView.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * 2019/1/3
 * from 陈秋阳
 * 功能描述：
 */
public class ClickRect extends FrameLayout{
    private static final String TAG = "ClickRect";

    private Paint mRecPaint;
    private Rect rectFocus;
    private int rectSize = 100;
    private int mAlpha = 0;

    private int mDrawX,mDrawY;

    public ClickRect(Context context) {
        this(context,null);
    }

    public ClickRect(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
        mRecPaint = new Paint();
        mRecPaint.setStyle(Paint.Style.STROKE);
        mRecPaint.setColor(Color.WHITE);
        mRecPaint.setAntiAlias(true);
        mRecPaint.setAlpha(mAlpha);
        rectFocus = new Rect(-rectSize,-rectSize,rectSize,rectSize);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG, "onTouchEvent: "+event.getX()+"  "+event.getY());
                mDrawX = (int) event.getX();
                mDrawY = (int) event.getY();
                mAlpha = 255;
                ValueAnimator valueAnimator = ValueAnimator.ofInt(255,0);
                valueAnimator.setDuration(1000);
                valueAnimator.setInterpolator(new BounceInterpolator());
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        mAlpha = (int) animation.getAnimatedValue();
                        invalidate();
                    }
                });
                valueAnimator.start();
                this.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK);//震动
                break;
        }
        return super.onTouchEvent(event);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i(TAG, "onDraw: "+mDrawX+"  "+mDrawY);
        canvas.translate(mDrawX,mDrawY);
        mRecPaint.setAlpha((int) mAlpha);
        canvas.drawRect(rectFocus,mRecPaint);
    }


}
