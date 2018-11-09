package com.example.chenqiuyang.younginterview.customView.view.brick.base;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.chenqiuyang.younginterview.customView.view.brick.bean.BrickInfo;

/**
 * 抽象组件类
 *
 */
public abstract class AbstractBrickHolder extends RecyclerView.ViewHolder {

    public AbstractBrickHolder(@NonNull final View view) {
        super(view);
    }

    /**
     * 绑定组件内容
     *
     * @param info
     */
    public abstract void setBrickInfo(BrickInfo info);


    /**
     * 绑定组件内容
     *
     * @param info
     */
    public abstract void setBrickInfoPayload(BrickInfo info, Object payload);

    public abstract void onViewRecycled();

}
