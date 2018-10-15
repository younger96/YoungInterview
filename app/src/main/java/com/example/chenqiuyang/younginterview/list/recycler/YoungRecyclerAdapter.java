package com.example.chenqiuyang.younginterview.list.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Young
 */
public abstract class YoungRecyclerAdapter<T> extends RecyclerView.Adapter<YoungViewHolder> {
    protected Context mContext;
    protected int mLayoutId;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;

    public YoungRecyclerAdapter(Context context, int layoutId, List<T> datas)
    {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;
        mDatas = datas;
    }

    @Override
    public YoungViewHolder onCreateViewHolder(final ViewGroup parent, int viewType)
    {
        YoungViewHolder viewHolder = YoungViewHolder.get(mContext, parent, mLayoutId);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(YoungViewHolder holder, int position)
    {
        holder.updatePosition(position);
        convert(holder, mDatas.get(position));
    }

    public abstract void convert(YoungViewHolder holder, T t);

    @Override
    public int getItemCount()
    {
        return mDatas.size();
    }
}