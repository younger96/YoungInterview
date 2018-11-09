package com.example.chenqiuyang.younginterview.customView.view.brick;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.chenqiuyang.younginterview.customView.view.brick.base.AbstractBrickHolder;
import com.example.chenqiuyang.younginterview.customView.view.brick.factory.BrickFactory;
import com.example.chenqiuyang.younginterview.customView.view.brick.bean.BrickInfo;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * BrickRecyclerAdapter
 *
 */
class BrickRecyclerAdapter extends RecyclerView.Adapter<AbstractBrickHolder> {

    public static final String TAG = "BrickRecyclerAdapter";

    private static final int UNKNOWN_BRICK_TYPE = -1;
    private static final Map<Integer, String> sTypeMap = new ConcurrentHashMap<>(); // 保存字符串组件类型与数字ViewHolder类型映射

    private List<BrickInfo> mData;
    private Object eventHandler;

    public void setData(List<BrickInfo> data) {
        registerItemViewType(data);
        mData = data;
        notifyDataSetChanged();
    }

    public void addData(List<BrickInfo> data) {
        registerItemViewType(data);
        mData = data;
        notifyItemInserted(mData.size() - 1);
    }

    public void addDataList(List<BrickInfo> data, int posStart, int count) {
        registerItemViewType(data);
        mData = data;
        notifyItemRangeInserted(posStart, count);
    }

    public void replaceDataList(List<BrickInfo> data, int posStart, int count) {
        registerItemViewType(data);
        mData = data;
        notifyItemRangeChanged(posStart, count);
    }

    public void remove(int position) {
        notifyItemRemoved(position);
    }

    public void removeRange(int startPos, int count) {
        notifyItemRangeChanged(startPos, count);
    }

    public void setEventHandler(Object eventHandler) {
        this.eventHandler = eventHandler;
    }

    @Override
    public void onViewRecycled(AbstractBrickHolder holder) {
        super.onViewRecycled(holder);
        holder.onViewRecycled();
    }

    @Override
    public AbstractBrickHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        return BrickFactory.createBrick(parent, getItemViewStringType(viewType));
    }

    @Override
    public void onBindViewHolder(AbstractBrickHolder holder, int position) {
        this.onBindViewHolder(holder, position, null);
    }

    @Override
    public void onBindViewHolder(AbstractBrickHolder holder, int position, List<Object> payloads) {
        if (holder != null) {
            BrickInfo info = getItemData(position);
            if (null != eventHandler) {
                BrickFactory.bindEvent(eventHandler, holder.itemView, info);
            }
            if (payloads != null && !payloads.isEmpty()) {
                holder.setBrickInfoPayload(info, payloads.get(0));
            } else {
                holder.setBrickInfo(info);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        BrickInfo info = getItemData(position);
        if (info != null && info.getType() != null) {
            return info.getType().hashCode();
        }
        return UNKNOWN_BRICK_TYPE;
    }

    BrickInfo getItemData(int position) {
        return position >= 0 && position < getItemCount() ? mData.get(position) : null;
    }

    private void registerItemViewType(List<BrickInfo> brickInfoList) {
        if (brickInfoList == null) {
            return;
        }

        for (BrickInfo brickInfo : brickInfoList) {
            if (brickInfo == null || brickInfo.getType() == null) {
                continue;
            }

            if (!sTypeMap.containsKey(brickInfo.getType().hashCode())) {
                sTypeMap.put(brickInfo.getType().hashCode(), brickInfo.getType());
            }
        }
    }

    private String getItemViewStringType(int type) {
        return sTypeMap.get(type);
    }
}
