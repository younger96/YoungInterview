package com.example.chenqiuyang.younginterview.customView.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.LinearInterpolator;



/**
 * Created by Young on  2018
 * 日出日落自定义View
 * 重点是我们的Width的要是Height的两倍，这样半圆才好看
 * <p>
 * getWidth()返回的值是px
 */

public class SunriseView extends View {
    private int mWidth; //自定义控件的宽度
    private int mHeight;//自定义控件的高度
    private int mCircleColor;  //圆弧颜色
    private int mFontColor;  //字体颜色

    private float mSweepAngle; //半圆划过的角度

    private int mContentPaddingTop = 20;//离顶部的高度
    private int mContentPadding = 10; //内容离View左右的宽度


    private boolean isDay;//  false为日未出， true为白天和日落

    private Paint mPathPaint;
    private Paint mAnmationPaint;
    private int mStartPointX;
    private int mStartPointY;
    private int mEndPointX;
    private int mEndPointY;

    private int mMovePointX;
    private int mMovePointY;

    private int mCirclePointX;
    private int mCirclePointY;

    private Bitmap mSunBitmap;
    private int mBitmapW;
    private int mBitmapH;


    private int mRadius;//圆的半径
    private RectF mRectF;

    public SunriseView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SunriseView(Context context) {
        this(context, null);
    }

    public SunriseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        if (mWidth > mHeight) {  //设定我们半圆的半径
            mRadius = mHeight - 20; //减去20，防止太阳上边界出线
        } else {
            mRadius = mWidth - 20;//一般不要出现这种情况
        }

        initView();
    }

    /**
     * 画笔设置
     */
    private void initView() {
        //半圆曲线初始化
        mPathPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPathPaint.setDither(true);//防止抖动
        mPathPaint.setStyle(Paint.Style.STROKE);
        mPathPaint.setStrokeWidth(2);
        mPathPaint.setColor(Color.parseColor("#ffffff"));
        PathEffect effects = new DashPathEffect(new float[]{10, 10, 10, 10}, 0);
        mPathPaint.setPathEffect(effects);

        //日出动画  阴影背景部分初始化
        mAnmationPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mAnmationPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mAnmationPaint.setColor(Color.parseColor("#32ffffff"));

        //圆的半径
        mRadius = (mHeight - 20);

        //开始坐标的XY
        mStartPointX = (mWidth / 2 - mRadius - mContentPadding);
        mStartPointY = (mHeight);

        //结束坐标的XY
        mEndPointX = (mWidth - mContentPadding);
        mEndPointY = mStartPointY;

        //太阳的移动坐标的XY
        mMovePointX = mStartPointX;
        mMovePointY = mStartPointY;

        //圆心坐标
        mCirclePointX = (mWidth / 2);
        mCirclePointY = (mHeight);

        //圆的初始化
        mRectF = new RectF(mCirclePointX - mRadius, mCirclePointY - mRadius,
                mCirclePointX + mRadius, mCirclePointY + mRadius);


        //给日出一个初始值
//        mSunBitmap = BitmapFactory.decodeResource(getResources(),
//                R.drawable.daily_deatil_sun_up);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
         // 首次没有数据不需要绘画
        if (isDay) {
            //白天 和 已经日落
            //第一步，绘制画曲线
            canvas.save();
            canvas.clipRect(mStartPointX, mContentPaddingTop,
                    mEndPointX, mEndPointY, Region.Op.INTERSECT);  //裁剪矩形
            canvas.clipRect(mMovePointX - mBitmapW / 2, mMovePointY - mBitmapH / 2,
                    mMovePointX + mBitmapW / 2, mMovePointY + mBitmapH / 2, Region.Op.DIFFERENCE);
            canvas.drawArc(mRectF, 180, 180, true, mPathPaint);


            //画透明背景用圆的角度来控制
            canvas.clipRect(mStartPointX, 0, mMovePointX, mStartPointY, Region.Op.INTERSECT);
            canvas.drawArc(mRectF, 180,
                    mSweepAngle, true, mAnmationPaint);
            canvas.restore();


            //画小太阳
            canvas.drawBitmap(mSunBitmap, mMovePointX - mBitmapW,
                    mMovePointY - mBitmapH, null);


        } else {
//未日出
            //画曲线
            canvas.save();
            canvas.clipRect(mMovePointX - mBitmapW / 2,
                    mMovePointY - mBitmapH / 2, mMovePointX + mBitmapW / 2,
                    mMovePointY + mBitmapH / 2, Region.Op.DIFFERENCE);
            canvas.clipRect(0, 0, getWidth(), mStartPointY, Region.Op.INTERSECT);
            canvas.drawCircle(mCirclePointX, mCirclePointY, mRadius, mPathPaint);
            canvas.restore();

            //画小太阳
            canvas.drawBitmap(mSunBitmap, mStartPointX - mBitmapW,
                    mStartPointY - mBitmapH, null);
        }


    }


    /**
     * 太阳移动的动画
     *
     * @param a 现在的时间的太阳的位置   -1为还没凌晨    -2为过了下山时间   0~1为其他时间
     */
    public void sunAnim(final float a) {
        if (a == -1) {  //现在的时间还没升太阳
            isDay = false;
//            mSunBitmap = BitmapFactory.decodeResource(getResources(),
//                    R.drawable.daily_deatil_sun_up);
            mBitmapW = mSunBitmap.getWidth() / 2;
            mBitmapH = mSunBitmap.getHeight();
            invalidate();
        } else { //现在的时间太阳已经落山 或者 现在的时间太阳还在天上
            isDay = true;

            if (a == -2){
                mSweepAngle = 180;
            }else {
                mSweepAngle = a * 180;
            }

//            mSunBitmap = BitmapFactory.decodeResource(getResources(),
//                    R.drawable.daily_deatil_sun);
            mBitmapW = mSunBitmap.getWidth() / 2;
            mBitmapH = mSunBitmap.getHeight() / 2;

            ValueAnimator progressAnimator = ValueAnimator.ofFloat(0, mSweepAngle);
            progressAnimator.setDuration(3000);
            progressAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    //每次在初始值和结束值之间产生的一个平滑过渡的值，逐步去更新进度
                    float x = (float) animation.getAnimatedValue();

                    mMovePointX = mCirclePointX - (int) (mRadius * (Math.cos(x * 3.14 / 180)));
                    mMovePointY = mCirclePointY - (int) (mRadius * (Math.sin(x * 3.14 / 180)));
                    invalidate();
                }
            });
            progressAnimator.setInterpolator(new LinearInterpolator());
            progressAnimator.start();
        }
    }


    /**
     * dp转px
     */
    public int dp2px(float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal,
                getResources().getDisplayMetrics());
    }
}