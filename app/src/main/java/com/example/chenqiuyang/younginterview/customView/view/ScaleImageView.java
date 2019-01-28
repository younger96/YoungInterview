package com.example.chenqiuyang.younginterview.customView.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;

/**
 * 2019/1/22
 * from 陈秋阳
 * 功能描述：
 */
public class ScaleImageView extends ImageView implements ScaleGestureDetector.OnScaleGestureListener {
    private static final String TAG = "ScaleImageView";

    float mCurScale = 1f;
    float mMinScale = 1f;
    float mMaxScale = 5f;

    private ScaleGestureDetector scaleGestureDetector;

    public ScaleImageView(Context context) {
        this(context,null);
    }

    public ScaleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        scaleGestureDetector=new ScaleGestureDetector(context,this);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return scaleGestureDetector.onTouchEvent(event);
    }


    @Override
    public boolean onScale(ScaleGestureDetector detector) {

        Log.i(TAG, "onScale: " + detector.getPreviousSpan() + "  " + detector.getCurrentSpan());
        if (Math.abs(detector.getPreviousSpan() - detector.getCurrentSpan()) > 10) {
            if (detector.getPreviousSpan() > detector.getCurrentSpan()) {  // 缩小
                mCurScale = mCurScale - 0.04f;
            } else {
                // 放大
                mCurScale = mCurScale + 0.04f;
            }
            startScale();
            return true;
        }

        return false;
    }

    //开始缩放动画
    private void startScale() {
        Log.i(TAG, "startScale: " + mCurScale);
        if (mCurScale > mMaxScale) {
            mCurScale = mMaxScale;
        } else if (mCurScale < mMinScale) {
            mCurScale = mMinScale;
        }

        this.setScaleX(mCurScale);
        this.setScaleY(mCurScale);
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

    }
}
