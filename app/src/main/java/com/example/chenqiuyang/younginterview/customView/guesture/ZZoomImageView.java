package com.example.chenqiuyang.younginterview.customView.guesture;

/**
 * Created by Luo_xiasuhuei321@163.com on 2016/9/24.
 * desc:
 */

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

public class ZZoomImageView extends ImageView implements View.OnTouchListener, ScaleGestureDetector.OnScaleGestureListener,
        ViewTreeObserver.OnGlobalLayoutListener {
    //suppress the unused warning because maybe it will be used sometime later
    @SuppressWarnings("unused")
    private static final String TAG = "ZZoomImageView";


    private int mCurLeft, mCurTop, mCurRight, mCurBottom;
    private int mMoveLeft, mMoveRight, mMoveTop, mMoveBottom;
    /**
     * 最大放大倍数
     */
    public static final float SCALE_MAX = 4.0f;

    /**
     * 默认缩放
     */
    public static final float SCALE_MIN = 0.1f;

    /**
     * 手势检测
     */
    ScaleGestureDetector scaleGestureDetector = null;

    Matrix scaleMatrix = new Matrix();

    /**
     * 处理矩阵的9个值
     */
    float[] martixValue = new float[9];

    public ZZoomImageView(Context context) {
        this(context, null);
    }

    public ZZoomImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZZoomImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setScaleType(ScaleType.MATRIX);
        scaleGestureDetector = new ScaleGestureDetector(context, this);
        this.setOnTouchListener(this);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    //suppress deprecate warning because i have dealt with it 
    @Override
    @SuppressWarnings("deprecation")
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
            getViewTreeObserver().removeOnGlobalLayoutListener(this);
        }
        getViewTreeObserver().removeGlobalOnLayoutListener(this);
    }

    /**
     * 获取当前缩放比例
     */
    public float getScale() {
        scaleMatrix.getValues(martixValue);
        return martixValue[Matrix.MSCALE_X];
    }

    /**
     * 在缩放时，控制范围
     */
    private void checkBorderAndCenterWhenScale() {
        Matrix matrix = scaleMatrix;
        RectF rectF = new RectF();
        Drawable d = getDrawable();
        if (d != null) {
            rectF.set(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
            matrix.mapRect(rectF);
        }

        float deltaX = 0;
        float deltaY = 0;
        int width = getWidth();
        int height = getHeight();
        // 如果宽或高大于屏幕，则控制范围
        if (rectF.width() >= width) {
            if (rectF.left > 0) {
                deltaX = -rectF.left;
            }
            if (rectF.right < width) {
                deltaX = width - rectF.right;
            }
        }
        if (rectF.height() >= height) {
            if (rectF.top > 0) {
                deltaY = -rectF.top;
            }
            if (rectF.bottom < height) {
                deltaY = height - rectF.bottom;
            }
        }
        // 如果宽或高小于屏幕，则让其居中
        if (rectF.width() < width) {
            deltaX = width * 0.5f - rectF.right + 0.5f * rectF.width();
        }
        if (rectF.height() < height) {
            deltaY = height * 0.5f - rectF.bottom + 0.5f * rectF.height();
        }
        scaleMatrix.postTranslate(deltaX, deltaY);
    }

    //--------------------------implement OnTouchListener----------------------------//
    private ClickCloseListener c;

    public interface ClickCloseListener {
        void close();
    }

    public void setClickCloseListener(ClickCloseListener c) {
        this.c = c;
    }

    /**
     * 按下的时间
     */
    long downTime;

    /**
     * down 和 up之间的间隔
     */
    long closeTime = 100L;

    /**
     * 设置按下的时间
     */
    //suppress unused warning for no reason
    @SuppressWarnings("unused")
    public void setClickTime(long time) {
        this.closeTime = time;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        scaleGestureDetector.onTouchEvent(event);
        //如果监听为null，消费该事件，不让onclick生效
        if (c == null)
            return true;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downTime = System.currentTimeMillis();
                break;
            case MotionEvent.ACTION_UP:
                downTime = System.currentTimeMillis() - downTime;
                if (downTime < closeTime)
                    c.close();
                break;
            default:
                break;
        }
        return true;
    }

    //----------------------implement OnScaleGestureListener------------------------//
    private float curScale = 1;
    private float preScale = 1;
    private float mFirstSpan ; //第一次按下去的时候手指间距
    private float mPreSpan ; //第一次按下去的时候手指间距
    private float mCurSpan ; //上一次按下去的时候手指间距
    private boolean isInit = false;
    @Override
    public boolean onScale(ScaleGestureDetector detector) {
//        float scaleFactor = detector.getScaleFactor();
//
//        Log.i(TAG, "onScale: " + scaleFactor);
//        if (scaleFactor < 1.5f && scaleFactor > 0.95) {
//            return false;
//        } else {
//            if (scaleFactor > 1) {
//                curScale += 0.1;
//                Log.i(TAG, "onLargeScale: " + curScale);
//            } else {
//                curScale -= 0.1;
//                Log.i(TAG, "onShortScale: " + curScale);
//            }
////            isReturn = true;
//            startScale(curScale);
//        }
//        return false;


//        Log.i(TAG, "onScale: "+detector.getScaleFactor());
//        if (detector.getScaleFactor() < 1.2) {
//            return false;
//        }
//        return true;
        if (!isInit){
            mFirstSpan = detector.getPreviousSpan();
            mPreSpan = mFirstSpan;
        }

        mCurSpan = detector.getCurrentSpan();// 本次双指间距

        Log.i(TAG, "onScale: "+mPreSpan+"  "+mCurSpan);
        if (Math.abs(mPreSpan-mCurSpan)>10){
            if (mCurSpan < mPreSpan) {  // 缩小
                curScale = curScale-0.06f;
            } else {
                // 放大
                curScale = curScale+0.06f;
            }
            startScale(curScale);
            mPreSpan = mCurSpan;
        }
        // 缩放view
        return false;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {
        preScale = curScale;// 记录本次缩放比例
    }

//    boolean isReturn = false;

    public void startScale(float scale) {
//        reLayoutView();
//        if (curScale >= SCALE_MAX) {
//            curScale = SCALE_MAX;
//        } else if (curScale <= SCALE_MIN) {
//            curScale = SCALE_MIN;
//        }
//        Log.i(TAG, "onEndScale: " + curScale);
        //设置缩放比例
        this.setScaleX(scale);
        this.setScaleY(scale);


//        isReturn = false;
    }

    boolean once = true;

    @Override
    public void onGlobalLayout() {
        if (!once)
            return;
        Drawable d = getDrawable();
        if (d == null)
            return;
        //获取imageview宽高
        int width = getWidth();
        int height = getHeight();

        //获取图片宽高
        int imgWidth = d.getIntrinsicWidth();
        int imgHeight = d.getIntrinsicHeight();

        float scale = 1.0f;

        //如果图片的宽或高大于屏幕，缩放至屏幕的宽或者高
        if (imgWidth > width && imgHeight <= height)
            scale = (float) width / imgWidth;
        if (imgHeight > height && imgWidth <= width)
            scale = (float) height / imgHeight;
        //如果图片宽高都大于屏幕，按比例缩小
        if (imgWidth > width && imgHeight > height)
            scale = Math.min((float) imgWidth / width, (float) imgHeight / height);
//        SCALE_MAX = scale;
        //将图片移动至屏幕中心
        scaleMatrix.postTranslate((width - imgWidth) / 2, (height - imgHeight) / 2);
        scaleMatrix.postScale(scale, scale, getWidth() / 2, getHeight() / 2);
        setImageMatrix(scaleMatrix);
        once = false;
    }


//    private void reLayoutView(){
//        mCurLeft = (int) (mCurLeft+mCurLeft*0.5);
//        mCurTop = (int) (mCurTop+mCurTop*0.5);
//        mCurBottom = (int) (mCurBottom-mCurBottom*0.5);
//        mCurRight = (int) (mCurRight-mCurRight*0.5);
//        this.layout(mCurLeft,mCurTop,mCurRight,mCurBottom);
//    }
}