package com.example.chenqiuyang.younginterview.customView.interpolator;

import android.graphics.PointF;
import android.view.animation.Interpolator;

/**
 * 2019/2/18
 * from 陈秋阳
 * 功能描述：先放大再缩小一点
 * 三阶贝塞尔曲线, P0 = (0,0),P1 = (0.18,1),P2 = (0.15,0.95),P3 = (1,1)
 */
public class RecorInterpolator implements Interpolator {
    PointF p1;
    PointF p2;
    CubcBezier cubcBezier;
    public RecorInterpolator(PointF p1, PointF p2) {
        this.p1 = p1;
        this.p2 = p2;
        cubcBezier = new CubcBezier(p1,p2);
    }



    @Override
    public float getInterpolation(float input) {
        return 0;
    }
}
