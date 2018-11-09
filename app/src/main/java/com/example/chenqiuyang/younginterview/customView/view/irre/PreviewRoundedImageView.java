package com.example.chenqiuyang.younginterview.customView.view.irre;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.example.chenqiuyang.younginterview.customView.util.ResourceHelper;
import com.example.chenqiuyang.younginterview.customView.util.SizeUtils;

/**
 * Created by coaster on 2018/5/26
 * coasterleung@gmail.com
 */
public class PreviewRoundedImageView extends android.support.v7.widget.AppCompatImageView {
    private Path mLeftTopRipPath;
    private Path mRightTopRipPath;
    private Path mLeftBottomRipPath;
    private Path mRightBottomRipPath;
    private Paint mDstOutPaint;
    private GradientDrawable mTopShadowDrawable;
    private GradientDrawable mBottomShadowDrawable;
    private int mRadius = SizeUtils.dp2px(10);

    public PreviewRoundedImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PreviewRoundedImageView(Context context) {
        super(context);
        init();
    }

    private void init() {
        mDstOutPaint = new Paint();
        mDstOutPaint.setColor(Color.RED);
        mDstOutPaint.setAntiAlias(true);
        mDstOutPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));

        mTopShadowDrawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,
            new int[] {
                Color.parseColor("#33000000"), Color.parseColor("#16000000"),
                Color.parseColor("#00000000")
            });
        mTopShadowDrawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        mTopShadowDrawable.setShape(GradientDrawable.RECTANGLE);
        mTopShadowDrawable.setDither(true);

        mBottomShadowDrawable = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP,
            new int[] {
                Color.parseColor("#33000000"), Color.parseColor("#16000000"),
                Color.parseColor("#00000000")
            });
        mBottomShadowDrawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        mBottomShadowDrawable.setShape(GradientDrawable.RECTANGLE);
        mBottomShadowDrawable.setDither(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        initPath();
        mTopShadowDrawable.setBounds(0, 0, getMeasuredWidth(),
            ResourceHelper.fromDPToPix(getContext(), 120));
        mBottomShadowDrawable.setBounds(0,
            getMeasuredHeight() - ResourceHelper.fromDPToPix(getContext(), 100), getMeasuredWidth(),
            getMeasuredHeight());
    }

    private void initPath() {
        mLeftTopRipPath = new Path();
        mLeftTopRipPath.moveTo(0, 0);
        mLeftTopRipPath.lineTo(mRadius, 0);
        mLeftTopRipPath.arcTo(0, 0, 2 * mRadius,
            2 * mRadius, 270, -90, false);
        mLeftTopRipPath.lineTo(0, 0);

        mRightTopRipPath = new Path();
        mRightTopRipPath.moveTo(getMeasuredWidth() - mRadius,
            0);
        mRightTopRipPath.lineTo(getMeasuredWidth(), 0);
        mRightTopRipPath.lineTo(getMeasuredWidth(), mRadius);
        mRightTopRipPath.arcTo(getMeasuredWidth() - 2 * mRadius, 0,
            getMeasuredWidth(), 2 * mRadius, 0, -90, false);

        mLeftBottomRipPath = new Path();
        mLeftBottomRipPath.moveTo(0,
            getMeasuredHeight() - mRadius);
        mLeftBottomRipPath.lineTo(0, getMeasuredHeight());
        mLeftBottomRipPath.lineTo(mRadius,
            getMeasuredHeight());
        mLeftBottomRipPath.arcTo(0,
            getMeasuredHeight() - mRadius * 2,
            mRadius * 2, getMeasuredHeight(), 90, 90, false);

        mRightBottomRipPath = new Path();
        mRightBottomRipPath.moveTo(getMeasuredWidth(),
            getMeasuredHeight() - mRadius);
        mRightBottomRipPath.lineTo(getMeasuredWidth(), getMeasuredHeight());
        mRightBottomRipPath.lineTo(
            getMeasuredWidth() - mRadius, getMeasuredHeight());
        mRightBottomRipPath.arcTo(getMeasuredWidth() - 2 * mRadius,
            getMeasuredHeight() - mRadius * 2, getMeasuredWidth(),
            getMeasuredHeight(), 90, -90, false);
    }

    public void updateRadius(int radius) {
        mRadius = radius;
        initPath();
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int sc = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);
        super.onDraw(canvas);
        mTopShadowDrawable.draw(canvas);
        mBottomShadowDrawable.draw(canvas);
        canvas.drawPath(mLeftTopRipPath, mDstOutPaint);
        canvas.drawPath(mRightTopRipPath, mDstOutPaint);
        canvas.drawPath(mLeftBottomRipPath, mDstOutPaint);
        canvas.drawPath(mRightBottomRipPath, mDstOutPaint);
        canvas.restoreToCount(sc);
    }
}

