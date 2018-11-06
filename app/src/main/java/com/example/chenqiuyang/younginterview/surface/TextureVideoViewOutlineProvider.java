package com.example.chenqiuyang.younginterview.surface;

import android.graphics.Outline;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewOutlineProvider;

public class TextureVideoViewOutlineProvider extends ViewOutlineProvider {
    private float mRadius;

    public TextureVideoViewOutlineProvider(float radius) {

        this.mRadius = radius;
    }

    public void setRadius(int radius) {
        this.mRadius = radius;
    }

    @Override
    public void getOutline(View view, Outline outline) {
        Rect rect = new Rect();
        view.getGlobalVisibleRect(rect);
        int leftMargin = 0;
        int topMargin = 0;
        Rect selfRect = new Rect(leftMargin, topMargin,
                view.getWidth(), view.getHeight());
        if (mRadius > 0) {
            outline.setRoundRect(selfRect, mRadius);
            view.invalidate();
        } else {
            outline.setRect(selfRect);
            view.invalidate();
        }
    }
}