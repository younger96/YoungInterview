package com.example.chenqiuyang.younginterview.customView.view.bounce;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.chenqiuyang.younginterview.R;
import com.example.chenqiuyang.younginterview.util.ScreenUtils;

/**
 * 2019/1/22
 * from 陈秋阳
 * 功能描述：
 */
public class BounceActivity extends Activity {
    private static final String TAG = " BounceActivity";

    private int mLeft,mTop;
    private int mTopCur;
    private float mScaleX,mScaleY;
    ImageView imageView;

    private Drawable mBackgroundAlpha ;

    private int animDuration = 400;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//设置透明状态栏
        setContentView(R.layout.imageview_bonce);


        final int left = getIntent().getIntExtra("locationX", 0);
        final int top = getIntent().getIntExtra("locationY", 0);
        final int width = getIntent().getIntExtra("width", 0);
        final int height = getIntent().getIntExtra("height", 0);

        mBackgroundAlpha = new ColorDrawable(Color.BLACK) ;
        final RelativeLayout content = findViewById(R.id.content);

        content.setBackground(mBackgroundAlpha);
        imageView = findViewById(R.id.img);
        imageView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                imageView.getViewTreeObserver().removeOnPreDrawListener(this);
                int location[] = new int[2];
                imageView.getLocationOnScreen(location);
                mLeft = left - location[0];
                mTop = top - location[1];
                mScaleX = width*1.0f / imageView.getWidth();
                mScaleY = height*1.0f / imageView.getHeight();
                activityEnterAnim();
                return true;
            }
        });


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        imageView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

            }
        });



        content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                content.setClickable(false);
                activityExitAnim(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                        overridePendingTransition(0, 0);
                    }
                });
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i(TAG, "onTouchEvent: "+event.getRawX()+"  "+event.getRawY());
                moveViewWithFinger(imageView, event.getRawX(), event.getRawY());
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onTouchEvent(event);
    }

    private void moveViewWithFinger(ImageView imageView, float rawX, float rawY) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) imageView
                .getLayoutParams();
        params.leftMargin = (int) rawX - imageView.getWidth() / 2;
        params.topMargin = (int) rawY  - imageView.getHeight() / 2;
        imageView.setLayoutParams(params);
    }


    private void activityEnterAnim() {
        imageView.setPivotX(0);
        imageView.setPivotY(0);
        imageView.setScaleX(mScaleX);
        imageView.setScaleY(mScaleY);
        imageView.setTranslationX(mLeft);
        imageView.setTranslationY(mTop);

        imageView.animate().scaleX(1).scaleY(1).translationX(0).translationY(0).
                setDuration(animDuration).setInterpolator(new DecelerateInterpolator()).start();
        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(mBackgroundAlpha,"alpha",0,122);
        objectAnimator.setInterpolator(new DecelerateInterpolator());
        objectAnimator.setDuration(animDuration);
        objectAnimator.start();
    }

    private void activityExitAnim(Runnable runnable) {
        imageView.setPivotX(0);
        imageView.setPivotY(0);
        imageView.animate().scaleX(mScaleX).scaleY(mScaleY).translationX(mLeft).translationY(mTop).
                withEndAction(runnable).
                setDuration(animDuration).setInterpolator(new DecelerateInterpolator()).start();
        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(mBackgroundAlpha,"alpha",122,0);
        objectAnimator.setInterpolator(new DecelerateInterpolator());
        objectAnimator.setDuration(animDuration);
        objectAnimator.start();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();

        activityExitAnim(new Runnable() {
            @Override
            public void run() {
                finish();
                overridePendingTransition(0, 0);
            }
        });
    }


}
