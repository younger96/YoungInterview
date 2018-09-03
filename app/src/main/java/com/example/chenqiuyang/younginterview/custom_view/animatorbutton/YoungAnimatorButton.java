package com.example.chenqiuyang.younginterview.custom_view.animatorbutton;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by young on 17-11-24.
 *
 */

public class YoungAnimatorButton extends View {
    private static String TAG = YoungAnimatorButton.class.getSimpleName();

    private float mWidth, mHeight;
    private String strBtn = "确定";
    private float mCircleRadius;//绘制圆的半径为控件高度的一半


    //画笔设置
    private Paint mPaint;
    private Paint mTestPaint;
    private Paint mLoadingPaint;
    private int PAINT_COLOR = Color.RED;
    private int PAINT_LOADING_COLOR = Color.WHITE;
    private int TEST_COLOR = Color.WHITE;
    private float PAINT_WIDTH = 5f;
    private float LOADING_WIDTH = 8f;
    private float TEST_WIDTH = 20f;

    //画布设置
    private int COLOR_BG = 0xff00ff00;

    //形状路径设置
    private PathMeasure mPathMeasure = new PathMeasure();
    private PathMeasure mOKPathMeasure = new PathMeasure();
    private Path mOKPath = new Path();
    private Path mOKDst = new Path();
    private Path mLoadingPath = new Path(); //加载的实时路径
    private Path mLoadingDst = new Path();
    private Path mRectPath;
    private RectF rectF;
    private Rect textRect = new Rect();
    private float circleAngle = 30f;//按钮角度


    //动画的状态
    private static final int STATE_NORMAL = 510;//普通状态
    private static final int STATE_B_LOADING = 194;//加载之前
    private static final int STATE_LOADING = 20;//正在加载
    private static final int STATE_COMP = 666;//完成
    private int nowState = STATE_NORMAL;

    //动画监听器
    private float currentValue;//加载进度
    private float currentValueRTC;//矩形到长圆形动画变换的进度
    private float currentValueCTC;//长圆形变成圆型动画变换进度
    private int ANIM_DURATION = 500;//动画时间
    private ValueAnimator.AnimatorUpdateListener mAnimatorUpdateListener;  //动画的变换  0.00-1.00
    private ValueAnimator mRectToCircleAnimator; //矩形变成长圆形动画监听
    private ValueAnimator mCircleToCircleAnimator; //长圆形变成圆形动画监听
    private ValueAnimator mLoadingAnimator;//加载动画
    private ValueAnimator mCompleteAnimator;//完成动画
    private AnimatorSet mAnimatorSet = new AnimatorSet();


    public YoungAnimatorButton(Context context) {
        this(context, null);
    }

    public YoungAnimatorButton(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public YoungAnimatorButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mPaint = new Paint();
        mPaint.setColor(PAINT_COLOR);
        mPaint.setAntiAlias(true);//反锯齿
        mPaint.setStrokeWidth(PAINT_WIDTH);
        mPaint.setStyle(Paint.Style.FILL);

        mTestPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTestPaint.setTextSize(30);
        mTestPaint.setColor(TEST_COLOR);
        mTestPaint.setAntiAlias(true);//反锯齿
        mTestPaint.setStrokeWidth(TEST_WIDTH);
        mTestPaint.setTextAlign(Paint.Align.CENTER);

        mLoadingPaint = new Paint();
        mLoadingPaint.setColor(PAINT_LOADING_COLOR);
        mLoadingPaint.setAntiAlias(true);//反锯齿
        mLoadingPaint.setStrokeWidth(LOADING_WIDTH);
        mLoadingPaint.setStyle(Paint.Style.STROKE);
    }



    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight = h;
        mWidth = w;
        resetData();
        initPath();
        initListener();
    }

    //重新设置值
    private void resetData() {
        mCircleRadius = mHeight / 2;
        currentValueCTC = mWidth;
        mTestPaint.setAlpha(255);
    }

    private void initPath() {
        rectF = new RectF();

        //加载Path
        RectF loadingRectF = new RectF(-mCircleRadius*17/20, -mCircleRadius*17/20, mCircleRadius*17/20, mCircleRadius*17/20);
        mLoadingDst.addArc(loadingRectF, -90, 359.9999f);//不要设置360°内部会自动优化，测量不能取到需要的数值

        mOKDst.moveTo(-mCircleRadius/3,0);
        mOKDst.lineTo(-mCircleRadius/12,mCircleRadius/4);
        mOKDst.lineTo(mCircleRadius/3,-mCircleRadius/4);

    }

    private void initListener() {
        //通用的updateListener
        ValueAnimator.AnimatorUpdateListener animatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentValue = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        };

        //矩形到长圆形动画
        mRectToCircleAnimator = ValueAnimator.ofFloat(circleAngle, mCircleRadius).setDuration(ANIM_DURATION);
        mRectToCircleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentValueRTC = (float) animation.getAnimatedValue(); //circleAngle-mCircleRadius
                postInvalidate();//  !!!一定要加上这句提醒更新
            }
        });


        //长圆形到圆形动画
        mCircleToCircleAnimator = ValueAnimator.ofFloat(mWidth, mHeight).setDuration(ANIM_DURATION);
        mCircleToCircleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentValueCTC = (float) animation.getAnimatedValue();

                int alpha = (int) (255 - 255 * (mWidth - currentValueCTC) / (mWidth - mHeight));//设置文字透明度
                mTestPaint.setAlpha(alpha);

                postInvalidate();
            }
        });
        mCircleToCircleAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                nowState = STATE_LOADING;
                mLoadingAnimator.start();
            }
        });

        //加载动画
        mLoadingAnimator = ValueAnimator.ofFloat(0f, 1f).setDuration(ANIM_DURATION * 4);
        mLoadingAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mLoadingAnimator.addUpdateListener(animatorUpdateListener);
        mLoadingAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                Log.i(TAG, "onAnimationEnd: ");
                nowState = STATE_COMP;
                mCompleteAnimator.start();
            }
        });

        //完成动画
        mCompleteAnimator = ValueAnimator.ofFloat(0f, 1f).setDuration(ANIM_DURATION * 3);
        mCompleteAnimator.addUpdateListener(animatorUpdateListener);

        mAnimatorSet.play(mCircleToCircleAnimator)
                .with(mRectToCircleAnimator);
        mAnimatorSet.setDuration(ANIM_DURATION*2);
    }

    /**
     * 不能直接在onDraw中实例RectF,所以在之前实例之后,
     * 在这里来设置Reccanvas.drawRoundRect(rectF,currentValueRTC,currentValueRTC,mPaint);
     * tF的值,从而改变参数的时候可以改变形状
     *
     * @param canvas 画布
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(mWidth / 2, mHeight / 2);
        canvas.drawColor(Color.GREEN);
        switch (nowState) {
            case STATE_NORMAL:
                rectF.left = -mWidth / 2;
                rectF.top = -mHeight / 2;
                rectF.right = mWidth / 2;
                rectF.bottom = mHeight / 2;
                canvas.drawRoundRect(rectF, circleAngle, circleAngle, mPaint);
                if (mLoadingAnimator.isRunning()||mCompleteAnimator.isRunning()){
                    mLoadingAnimator.end();
                    mCompleteAnimator.end();
                }
                break;
            case STATE_B_LOADING:
                beforeLoad(canvas);
                break;
            case STATE_LOADING:
                onLoad(canvas);
                break;
            case STATE_COMP:
                onComplete(canvas);
                break;
        }

        drawTest(canvas);
    }

    //加载完成
    private void onComplete(Canvas canvas) {
        canvas.drawRoundRect(rectF, currentValueRTC, currentValueRTC, mPaint); //背景圆

        mLoadingPath.reset();//必须重置
        mOKPath.reset();//必须重置

        //点击一下就开始搜索
        mPathMeasure.setPath(mLoadingDst, true);
        float stop = mPathMeasure.getLength()*currentValue;
        mPathMeasure.getSegment(0,stop,mLoadingPath,true);
        canvas.drawPath(mLoadingPath, mLoadingPaint);


        mPathMeasure.setPath(mOKDst,false);
        mPathMeasure.getSegment(0,mPathMeasure.getLength()*currentValue,mOKPath,true);
        canvas.drawPath(mOKPath,mLoadingPaint);

    }

    //正在加载
    private void onLoad(Canvas canvas) {
        mLoadingPath.reset();//必须重置
        //点击一下就开始搜索
        mPathMeasure.setPath(mLoadingDst, false);
        float stop = mPathMeasure.getLength()*currentValue;
        float start = (float) (stop - ((0.5 - Math.abs(currentValue - 0.5)) * mCircleRadius * 4));//abs为绝对值  公式为经验值
        mPathMeasure.getSegment(start,stop,mLoadingPath,true);
        canvas.drawRoundRect(rectF, currentValueRTC, currentValueRTC, mPaint);
        canvas.drawPath(mLoadingPath, mLoadingPaint);
    }

    //加载之前的绘制
    private void beforeLoad(Canvas canvas) {
        rectF.left = -currentValueCTC / 2;
        rectF.top = -mHeight / 2;
        rectF.right = currentValueCTC / 2;
        rectF.bottom = mHeight / 2;
        canvas.drawRoundRect(rectF, currentValueRTC, currentValueRTC, mPaint);
    }

    /**
     * 绘制画布中的文字
     *
     * @param canvas 画布
     */
    private void drawTest(Canvas canvas) {
        Paint.FontMetrics fontMetrics = mTestPaint.getFontMetrics();
        float top = fontMetrics.top;//为基线到字体上边框的距离
        float bottom = fontMetrics.bottom;//为基线到字体下边框的距离
        int baseLine = (int) (rectF.centerY() - (top + bottom) / 2);//基线中心点y坐标
        canvas.drawText(strBtn, rectF.centerX(), baseLine, mTestPaint);
    }

    /**
     * 开始加载
     */
    public void startLoading() {
        Log.i(TAG, "startLoading: ");
        nowState = STATE_B_LOADING;
        mAnimatorSet.start();
    }

    /**
     * 停止加载
     */
    public void stopLoading() {
        Log.i(TAG, "stopLoading: ");
    }

    /**
     * 回到初始状态
     */
    public void backRect() {
        Log.i(TAG, "backRect: ");
        resetData();
        nowState = STATE_NORMAL;
        postInvalidate();
    }

    /**
     * 加载成功
     */
    public void completeLoading() {
        Log.i(TAG, "completeLoading: ");
        mLoadingAnimator.end();
    }
}
