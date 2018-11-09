package com.example.chenqiuyang.younginterview.customView.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import com.example.chenqiuyang.younginterview.R;

public class CircleSexView extends ImageView {
    private static final String TAG = "CircleSexView";
    private int mSex ;
    private boolean isDraw;
    private int mSexPath = -1;
    int mLeft ,mTop,mRight,mBottom;
    int mWidth,mHeight;

    private Paint mPaint;

    public CircleSexView(Context context) {
        this(context, null);
    }

    public CircleSexView(Context context, AttributeSet attrs) {
        super(context, attrs);
        isDraw = true;
        mSexPath = R.drawable.btn_profile_addfriend;
        mPaint = new Paint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mWidth = getMeasuredWidth();
        mHeight =getMeasuredHeight();
        Log.i(TAG, "onMeasure: "+mWidth+"  "+mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i(TAG, "onDraw: "+isDraw);
        if (isDraw) {
            if (mSexPath == -1){
                return;
            }
            canvas.drawBitmap(scaleImage(mWidth,mHeight,0.15f),(int)(0.85*mWidth),(int)(0.85*mHeight),null);
        }
    }

    //设置绘制性别的logo
    public void setSex(int sex) {
        Log.i(TAG, "setSex: " + sex);
        mSex = sex;
        isDraw = true;
        drawSex();
        postInvalidate();
    }

    private void drawSex() {

    }


    //缩放图片  变为原来的scale倍
    private Bitmap scaleImage(int parentW,int parentH,float scaleF){
        Drawable drawable = ContextCompat.getDrawable(getContext(),mSexPath);
        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
        Bitmap bm = bitmapDrawable.getBitmap();
        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 设置想要的大小
        int newWidth = (int) ((float)parentW*scaleF);
        int newHeight = (int) ((float)parentH*scaleF);
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 取得想要缩放的matrix参数
        return Bitmap.createBitmap(bm, 0, 0, width, height, matrix,true);
    }
}
