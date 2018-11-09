package com.example.chenqiuyang.younginterview.customView.view.brick.base;

import android.view.View;
import android.view.ViewGroup;

import com.example.chenqiuyang.younginterview.customView.view.brick.base.AbstractBrickHolder;
import com.example.chenqiuyang.younginterview.customView.view.brick.bean.BrickInfo;

/**
 * 用来显示不支持的组件内容
 */
public class GhostBrick extends AbstractBrickHolder {

    public GhostBrick(final ViewGroup parent) {
        super(new View(parent.getContext()));
    }

    @Override
    public void setBrickInfo(BrickInfo info) {
    }

    @Override
    public void setBrickInfoPayload(BrickInfo info, Object payload) {

    }

    @Override
    public void onViewRecycled() {

    }

}
