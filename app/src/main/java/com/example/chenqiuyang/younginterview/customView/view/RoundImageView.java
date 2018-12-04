package com.example.chenqiuyang.younginterview.customView.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.example.chenqiuyang.younginterview.R;

import java.util.Arrays;

/**
 * Created by lihaibiao on 2018/7/12 21:18
 * E-Mail Address：lihaibiaowork@gmail.com
 */
public class RoundImageView extends android.support.v7.widget.AppCompatImageView{
    private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final PorterDuffXfermode mXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);
    private final Path mClipPath = new Path();
    private final RectF mClipRectF = new RectF();
    private final Path mRectPath = new Path();
    private final float[] mRadii = new float[8];
    private boolean mClipRadius = false;
    private boolean isCircle = false;
    private boolean isAntiAlias = false;

    public RoundImageView(Context context) {
        this(context,null);
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.WHITE);
    }

    public void setRadius(float radius) {
        if(radius < 0) radius = 0;
        Arrays.fill(mRadii,radius);
        mClipRadius = radius > 0;
        invalidate();
    }

    public void setCircle(boolean circle) {
        isCircle = circle;
        invalidate();
    }

    public void setAntiAlias(boolean antiAlias) {
        isAntiAlias = antiAlias;
        invalidate();
    }

    @Override protected void onDraw(Canvas canvas) {
        if(isCircle || isAntiAlias || mClipRadius){
            final int width = getWidth() - getPaddingStart() - getPaddingEnd();
            final int height = getHeight() - getPaddingTop() - getPaddingBottom();
            final int sc = canvas.saveLayer(getPaddingStart(),getPaddingTop(),getPaddingStart() + width,getPaddingTop() + height,null, Canvas.ALL_SAVE_FLAG);
            super.onDraw(canvas);
            mClipPath.reset();
            mRectPath.reset();
            int jagBorder = isAntiAlias ? 1 : 0;//将1像素的锯齿遮住
            if(isCircle){
                int diameter = Math.min(width,height);
                float left = getPaddingStart() + (width - diameter) * 0.5f;
                float top = getPaddingTop() + (height - diameter) * 0.5f;
                mClipPath.addCircle(left + diameter * 0.5f,top + diameter * 0.5f,diameter * 0.5f,Path.Direction.CW);
            }else if(mClipRadius || isAntiAlias){
                mClipRectF.set(getPaddingStart() + jagBorder,getPaddingTop() + jagBorder,getPaddingStart() + width - jagBorder,getPaddingTop() + height - jagBorder);
                mClipPath.addRoundRect(mClipRectF,mRadii,Path.Direction.CW);
            }
            mRectPath.addRect(getPaddingStart() - jagBorder,getPaddingTop() - jagBorder,getPaddingStart() + width + jagBorder,getPaddingTop() + height + jagBorder,Path.Direction.CW);
            mRectPath.op(mClipPath,Path.Op.DIFFERENCE);
            mPaint.setXfermode(mXfermode);
            canvas.drawPath(mRectPath,mPaint);
            canvas.restoreToCount(sc);

        }else{
            super.onDraw(canvas);
        }

    }
}
