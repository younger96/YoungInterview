package com.example.chenqiuyang.younginterview.custom_view.qqredpoint;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.BounceInterpolator;

/**
 * Created by young on 17-11-29.
 */

public class RedPointView extends View {
    private static String TAG = RedPointView.class.getSimpleName();

    private float mWidth,mHeight;
    private boolean isDisappear = false;//判断里面的圆是否消失
    private boolean isInCircle = false;//判断点击事件是否在圆圈内

    //点设置
    private PointF mPointNow,mPointStart,mPointMiddle; //记录现在的位置坐标,原本的位置坐标,中间的位置坐标(为贝塞尔曲线控制点)
    private int nowX,nowY;//现在移动时候的X Y坐标
    private PointF[] mPointNows,mPointStarts;

    //Pain设置
    private Paint mPaint = new Paint();
    private Paint mTestPaint = new Paint();
    private float paintStockWidth = 5f;
    private int PaintColor = ConstantColor.color_flyme_red_bright;
    private String strNum = "99+";

    //圆设置
    private float mRadiusCurrent, mRadiusStart;//绘制圆的半径,绘制在原地圆的半径
    private float maxDistance;//两圆之间的最大距离
    private Path pathBezier = new Path();

    //圆的状态
    private static final int StateNormal = 236;//初始状态
    private static final int StateMoveIn = 155;//圆拉伸移动状态
    private static final int StateMoveOut = 646;//圆移动到范围外状态
    private static final int StateMoveOver = 450;//松手两圆消失状态
    private static final int StateDisappear = 573;
    private int nowState = StateNormal;


    public RedPointView(Context context) {
        this(context,null);
    }

    public RedPointView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RedPointView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    private void initView() {
        //画笔设置
        mPaint.setColor(PaintColor);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setStrokeWidth(paintStockWidth);
        mPaint.setAntiAlias(true);

        mTestPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTestPaint.setTextSize(mRadiusCurrent);
        mTestPaint.setColor(ConstantColor.color_cn_chabai);
        mTestPaint.setAntiAlias(true);//反锯齿
        mTestPaint.setStrokeWidth(5f);
        mTestPaint.setTextAlign(Paint.Align.CENTER);


        //点设置
        mPointNow = new PointF();
        mPointStart = new PointF();
        mPointMiddle = new PointF();
        mPointNows = new PointF[2];
        mPointStarts = new PointF[2];

        //圆设置
        mPointStart.set(mWidth/2,mHeight/2);


    }



    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        if(mWidth>mHeight){
            mRadiusCurrent = mHeight*1/15;
        }else {
            mRadiusCurrent = mWidth*1/15;
        }
        mRadiusStart = mRadiusCurrent;
        maxDistance = mRadiusCurrent*3.5f;

        initView();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        switch (nowState){
            case StateNormal:
                canvas.drawCircle(mPointStart.x,mPointStart.y, mRadiusCurrent,mPaint);
                drawTest(canvas,mPointStart,mRadiusStart);
                break;
            case StateMoveIn:
                canvas.drawCircle(mPointStart.x,mPointStart.y, mRadiusStart,mPaint);
                canvas.drawCircle(mPointNow.x,mPointNow.y, mRadiusCurrent,mPaint);
                if (!isDisappear){
                    getCircleDistance();
                    drawBezier(canvas);
                }
                drawTest(canvas,mPointNow,mRadiusCurrent);
                break;
            case StateMoveOut:

                break;
            case StateMoveOver:

                break;
            case StateDisappear:
                canvas.drawColor(Color.WHITE);
                break;
        }
    }

    //画贝塞尔曲线的图形
    private void drawBezier(Canvas canvas) {
        pathBezier.reset();

        pathBezier.moveTo(mPointStarts[0].x,mPointStarts[0].y);
        pathBezier.quadTo(mPointMiddle.x,mPointMiddle.y,mPointNows[0].x,mPointNows[0].y);
        pathBezier.lineTo(mPointNows[1].x,mPointNows[1].y);
        pathBezier.quadTo(mPointMiddle.x,mPointMiddle.y,mPointStarts[1].x,mPointStarts[1].y);
        canvas.drawPath(pathBezier,mPaint);
    }

    /**
     * 绘制圆圈中的文字
     *
     * @param canvas 画布
     */
    private void drawTest(Canvas canvas,PointF pointF,float radius) {
        Log.i(TAG, "drawTest: ");
        RectF rectF = new RectF();
        rectF.left = pointF.x - radius;
        rectF.top = pointF.y - radius;
        rectF.right = pointF.x +radius;
        rectF.bottom = pointF.y +radius;

        Paint.FontMetrics fontMetrics = mTestPaint.getFontMetrics();
        float top = fontMetrics.top;//为基线到字体上边框的距离
        float bottom = fontMetrics.bottom;//为基线到字体下边框的距离
        int baseLine = (int) (rectF.centerY() - (top + bottom) / 2);//基线中心点y坐标
        canvas.drawText(strNum, rectF.centerX(), baseLine, mTestPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (nowState == StateDisappear){
            return true;
        }
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                //Even的坐标是一定的,而画布的坐标是移动的,所以要相减,后面考虑适配
                RectF rectF = new RectF();
                rectF.left = mPointStart.x - mRadiusCurrent;
                rectF.top = mPointStart.y- mRadiusCurrent;
                rectF.right = mPointStart.x + mRadiusCurrent;
                rectF.bottom = mPointStart.y + mRadiusCurrent;
                isInCircle = rectF.contains(event.getX(),event.getY());
                Log.i(TAG, "onTouchEvent: "+isInCircle+"  (x,y)"+"("+event.getX()+","+event.getY()+")");
                break;
            case MotionEvent.ACTION_MOVE:
                if (isInCircle){
                    nowX = (int) event.getX();
                    nowY = (int) event.getY();
                    mPointNow.set(nowX,nowY);
                    Log.i(TAG, "onTouchEvent: nowPoint"+"("+event.getX()+","+event.getY()+")");
                    getCircleDistance();
                    nowState = StateMoveIn;
                    invalidate();
                }
                Log.i(TAG, "onTouchEvent: DRAG");
                break;
            case MotionEvent.ACTION_UP:
                if (isDisappear){
                    nowState = StateDisappear;
                }else{
                    addMoveListener();
                }
                break;
        }
        return true;
    }

    //在贝塞尔曲线存在时候回弹的动画效果
    private void addMoveListener() {
        //回弹动画
        final float a = (mPointNow.y - mPointStart.y) / (mPointNow.x - mPointStart.x);
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(mPointNow.x, mPointStart.x);
        valueAnimator.setDuration(500);
        valueAnimator.setInterpolator(new BounceInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float x = (float) animation.getAnimatedValue();

                float y = mPointStart.y + a * (x - mPointStart.x);

                mPointNow.set(x, y);

                nowState = StateMoveIn;
                invalidate();

            }
        });
        valueAnimator.start();
    }

    //获取两圆之间的距离 通过距离来算原地圆的半径
    private void getCircleDistance() {
        //两个圆之间的距离
        float distance = (float) Math.sqrt(Math.pow(mPointStart.x-mPointNow.x,2)+Math.pow(mPointStart.y-mPointNow.y,2)); //sqrt开平方 , pow(a,b) a的b次方

        //缩圆半径
        if (distance>maxDistance*0.8){
            mRadiusStart = 0;
            isDisappear =true;
        }else {
            mRadiusStart = mRadiusCurrent*(maxDistance-distance)/maxDistance;
            isDisappear = false;
        }

        //确定中心点坐标
        mPointMiddle.set((mPointStart.x+mPointNow.x)/2,(mPointStart.y+mPointNow.y)/2);
        Log.i(TAG, "getCircleDistance: Middle"+mPointMiddle.toString());


        getIntersectionPoints(mRadiusStart,mPointStart,mPointStarts);
        getIntersectionPoints(mRadiusCurrent,mPointNow,mPointNows);

    }


    /**
     * 贝塞尔曲线绘制的点坐标
     * @param radius 圆半径
     * @param point 圆心坐标
     * @param points  录入的坐标
     * @return
     */
    private void getIntersectionPoints(float radius, PointF point, PointF[] points){
        //圆心之间的斜率
        float rate  = (mPointStart.y-mPointNow.y)/(mPointStart.x-mPointNow.x);

        //通过斜率求角(弧)度
        float radian = (float) Math.atan(rate); //反切

        //x和y的偏移量
        float xOffset = radius*(float) Math.sin(radian);
        float yOffset = radius*(float)Math.cos(radian);

        points[0] = new PointF(point.x+xOffset,point.y-yOffset);
        points[1] = new PointF(point.x-xOffset,point.y+yOffset);

    }

    /**
     * 设置消息个数
     * @param num 消息数
     */
    public void setNum(int num){
        if (num<=99&&num>0){
            strNum = String.valueOf(num);
            nowState = StateNormal;
        }else if (num>99){
            strNum = "99+";
            nowState = StateNormal;
        }else {
            nowState = StateDisappear;
        }
        invalidate();
    }

    /**
     * 控件是否消失
     * @param isDisappear 是否消失
     */
    public void setDisappear(boolean isDisappear){
        if (isDisappear){
            nowState = StateDisappear;
        }else {
            nowState = StateNormal;
        }
        invalidate();
    }



}
