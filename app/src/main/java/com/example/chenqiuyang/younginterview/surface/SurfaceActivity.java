package com.example.chenqiuyang.younginterview.surface;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.example.chenqiuyang.younginterview.R;
import com.example.chenqiuyang.younginterview.util.DensityUtil;

public class SurfaceActivity extends Activity {
    ImageView img;
    Context mContext;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surface);
        mContext = this;
        img = findViewById(R.id.img);
        setRoundCorner(10,img);
    }

    //设置圆角
    public void setRoundCorner(int radius,View view) {
        if (view != null) {
            view.setOutlineProvider(
                    new TextureVideoViewOutlineProvider(DensityUtil.dip2px(mContext, radius)));
            view.setClipToOutline(true);
            view.invalidateOutline();
        }
    }
}
