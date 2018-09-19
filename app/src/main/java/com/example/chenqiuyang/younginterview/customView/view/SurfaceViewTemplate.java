package com.example.chenqiuyang.younginterview.customView.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * View和SurfaceView的区别:
 * 1 . View适用于主动更新的情况，而SurfaceView则适用于被动更新的情况，比如频繁刷新界面。
 * 2 . View在主线程中对页面进行刷新，而SurfaceView则开启一个子线程来对页面进行刷新。
 * 3 . View在绘图时没有实现双缓冲机制，SurfaceView在底层机制中就实现了双缓冲机制。
 * (当一个动画争先显示时，程序又在改变它，前面还没有显示完，程序又请求重新绘制，
 * 这样屏幕就会不停地闪烁。而双缓冲技术是把要处理的图片在内存中处理好之后，
 * 再将其显示在屏幕上。双缓冲主要是为了解决 反复局部刷屏带来的闪烁。把要画的东西先画到一个内存区域里，然后整体的一次性画出来。)
 */
public class SurfaceViewTemplate extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private SurfaceHolder mSurfaceHolder;
    //绘图的Canvas
    private Canvas mCanvas;
    //子线程标志位
    private boolean mIsDrawing;
    //画笔
    private Paint mPaint;
    //路径
    private Path mPath;

    /**
     * 初始化View
     */
    private void initView() {
        mSurfaceHolder = getHolder();
        //注册回调方法
        mSurfaceHolder.addCallback(this);
        //设置一些参数方便后面绘图
        setFocusable(true);
        setKeepScreenOn(true);
        setFocusableInTouchMode(true);
    }

    public SurfaceViewTemplate(Context context) {
        this(context, null);
    }

    public SurfaceViewTemplate(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SurfaceViewTemplate(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        initPaint();
    }

    //初始化画笔
    private void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(5);
        mPath = new Path();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mPath.moveTo(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                mPath.lineTo(x, y);
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //创建
        mIsDrawing = true;
        //开启子线程
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        //改变
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        //销毁
    }

    @Override
    public void run() {
        //子线程
        while (mIsDrawing){
            long start = System.currentTimeMillis();
            drawSomething();
            long end = System.currentTimeMillis();
            if (end - start < 100) {
                try {
                    Thread.sleep(100 - (end - start));//在绘制的时候，我们并没有让线程一直运行，而是让它休眠一会，从而节约系统资源
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //绘图逻辑
    private void drawSomething() {
        try {
            //获得canvas对象
            mCanvas = mSurfaceHolder.lockCanvas();
            //绘制背景
            mCanvas.drawColor(Color.WHITE);
            //绘图
            //绘制路径
            mCanvas.drawPath(mPath, mPaint);
        }catch (Exception e){

        }finally {
            if (mCanvas != null){
                //释放canvas对象并提交画布
                mSurfaceHolder.unlockCanvasAndPost(mCanvas);
            }
        }
    }

}