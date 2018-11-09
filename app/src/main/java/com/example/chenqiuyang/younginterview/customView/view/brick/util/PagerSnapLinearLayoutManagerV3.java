package com.example.chenqiuyang.younginterview.customView.view.brick.util;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by yangtian on 2018/7/5.
 * E-Mail Address: 443275705@qq.com
 */

public class PagerSnapLinearLayoutManagerV3 extends LinearLayoutManager {

    public static final String TAG = "PagerSnapLinearLayoutManagerV3";

    private PagerSnapHelper snapHelper = new PagerSnapHelper();
    private ViewPagerListener mOnViewPagerListener;
    private RecyclerView recyclerView;
    private int mDrift; //位移，用来判断移动方向
    private int curPosition;
    private boolean firstIn = true;

    private RecyclerView.OnChildAttachStateChangeListener mChildAttachStateChangeListener =
        new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {
                if (getChildCount() == 1 && firstIn) {
                    int position = getPosition(view);
                    curPosition = position;
                    if (mOnViewPagerListener != null) {
                        mOnViewPagerListener.onInitComplete(position, view);
                    }
                    firstIn = false;
                }
            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
                if (mOnViewPagerListener != null) {
                    mOnViewPagerListener.onPageRelease(getPosition(view), view);
                }
            }
        };

    public PagerSnapLinearLayoutManagerV3(Context context) {
        super(context);
    }

    public PagerSnapLinearLayoutManagerV3(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public void setPagerSnapHelper(PagerSnapHelper snapHelper) {
        this.snapHelper = snapHelper;
    }

    public void setViewPagerListener(ViewPagerListener viewPagerListener) {
        this.mOnViewPagerListener = viewPagerListener;
    }

    @Override
    public void onAttachedToWindow(RecyclerView view) {
        super.onAttachedToWindow(view);
        snapHelper.attachToRecyclerView(view); //设置RecyclerView每次滚动一页
        recyclerView = view;
        recyclerView.addOnChildAttachStateChangeListener(mChildAttachStateChangeListener);
    }

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        if (state == RecyclerView.SCROLL_STATE_IDLE) {
            View viewIdle = snapHelper.findSnapView(this);
            int positionIdle = getPosition(viewIdle);
            if (positionIdle == curPosition) {
                return;
            }
            onPageUnSelected(positionIdle);
            onPageSelected(positionIdle, viewIdle);
        }
    }

    private void onPageSelected(int position, View cur) {
        curPosition = position;
        if (mOnViewPagerListener != null) {
            View pre = null;
            View next = null;
            if (position == 0) {
                next = getChildAt(1);
            } else if (position == getItemCount() - 1) {
                pre = getChildAt(0);
            } else {
                pre = getChildAt(0);
                next = getChildAt(2);
            }
            mOnViewPagerListener.onPageSelected(position, cur, pre, next);
        }
    }

    private void onPageUnSelected(int positionIdle) {
        if (mDrift > 0) {
            if (mOnViewPagerListener != null) {
                mOnViewPagerListener.onPageUnSelected(positionIdle - 1, getChildAt(0));
            }
        } else if (mDrift < 0) {
            if (mOnViewPagerListener != null) {
                View right;
                right = getChildAt(2);
                if (right == null) {
                    right = getChildAt(1);
                }
                mOnViewPagerListener.onPageUnSelected(positionIdle + 1, right);
            }
        }
    }

    /**
     * 监听水平方向的相对偏移量
     */
    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler,
        RecyclerView.State state) {
        this.mDrift = dx;
        return super.scrollHorizontallyBy(dx, recycler, state);
    }

    public interface ViewPagerListener {
        void onPageSelected(int position, View cur, View pre, View next);

        void onPageUnSelected(int position, View view);

        void onPageRelease(int position, View view);

        void onInitComplete(int position, View view);
    }
}