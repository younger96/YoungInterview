package com.example.chenqiuyang.younginterview.customView.view;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.chenqiuyang.younginterview.R;
import com.example.chenqiuyang.younginterview.util.MyApp;

/**
 * 2018/11/27
 * from 陈秋阳
 * 功能描述：彩色的TextView
 */
public class ColorFulTextView extends android.support.v7.widget.AppCompatTextView {


    public ColorFulTextView(Context context) {
        super(context);
    }

    public ColorFulTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ColorFulTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        Bitmap shaderBitmap = ((BitmapDrawable) MyApp.getmContext().getDrawable(
                R.drawable.icon_now_list_nick_mask)).getBitmap();
        BitmapShader mNickShader = new BitmapShader(shaderBitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        this.getPaint().setShader(mNickShader);
        super.setText(text, type);
    }
}
