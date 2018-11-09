package com.example.chenqiuyang.younginterview.customView.view.brick;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.SparseArray;

import com.example.chenqiuyang.younginterview.customView.view.brick.base.AbstractBrickHolder;
import com.example.chenqiuyang.younginterview.customView.view.brick.base.BrickRecyclerItemDecoration;
import com.example.chenqiuyang.younginterview.customView.view.brick.bean.BrickInfo;
import com.example.chenqiuyang.younginterview.customView.view.brick.bean.BrickPositionInfo;

import java.util.ArrayList;
import java.util.List;

public class BrickRecyclerView extends RecyclerView {
    private static final String TAG = "BrickRecyclerView";

    private List<BrickInfo> mCompletedBrickInfoList = new ArrayList<>();

    private BrickRecyclerAdapter mAdapter;
    private SparseArray<BrickPositionInfo> mBrickPositionCache = new SparseArray<>();

    public BrickRecyclerView(Context context) {
        super(context);
        init(context, null);
    }

    public BrickRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public BrickRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public BrickInfo getBrickInfo(int pos) {
        if (mAdapter != null) {
            return mAdapter.getItemData(pos);
        }
        return null;
    }

    /**
     * 设置封装BrickInfo数据
     *
     * @param data
     */
    public void setBrickList(List<BrickInfo> data) {
        mCompletedBrickInfoList = data;
        setCompletedData(mCompletedBrickInfoList);
    }

    /**
     * 设置单一类型数据列表
     *
     * @param type
     * @param datas
     */
    public void setSingleTypeData(String type, List<? extends Object> datas) {
        List<BrickInfo> infos = new ArrayList<>();
        for (Object data : datas) {
            infos.add(new BrickInfo(type, data));
        }
        setBrickList(infos);
    }

    /**
     * 添加Brick数据列表
     *
     * @param data
     */
    public void addBrickList(List<BrickInfo> data) {
        mCompletedBrickInfoList.addAll(data);
        setCompletedData(mCompletedBrickInfoList);
    }

    public void addBrickList(List<BrickInfo> data, int pos) {
        mCompletedBrickInfoList.addAll(pos, data);
        setCompletedData(mCompletedBrickInfoList);
    }

    /**
     * 添加Brick数据列表 局部刷新
     *
     * @param data
     */
    public void addBrickListPartial(List<BrickInfo> data) {
        int pos = mCompletedBrickInfoList.size() - 1;
        int count = data.size();
        mCompletedBrickInfoList.addAll(data);
        rebuildPositionCache(mCompletedBrickInfoList);
        mAdapter.addDataList(mCompletedBrickInfoList, pos, count);
    }

    public void addBrickListPartial(List<BrickInfo> data, int pos) {
        int count = data.size();
        mCompletedBrickInfoList.addAll(pos, data);
        rebuildPositionCache(mCompletedBrickInfoList);
        mAdapter.addDataList(mCompletedBrickInfoList, pos, count);
    }

    /**
     * 添加单一类型列表
     *
     * @param datas
     */
    public void addSingleDataListIndex(String type, List<? extends Object> datas, int pos) {
        addSingleDataListIndex(type, datas, pos, 1);
    }

    /**
     * 添加单一类型列表
     *
     * @param datas
     */
    public void addSingleDataListIndex(String type, List<? extends Object> datas, int pos, int columns) {
        for (Object data : datas) {
            mCompletedBrickInfoList.add(pos, new BrickInfo(type, data, columns));
        }
        setCompletedData(mCompletedBrickInfoList);
    }

    /**
     * 添加单一类型列表
     *
     * @param datas
     */
    public void addSingleDataList(String type, List<? extends Object> datas) {
        addSingleDataList(type, datas, 1);
    }

    /**
     * 添加单一类型列表
     *
     * @param datas
     */
    public void addSingleDataList(String type, List<? extends Object> datas, int columns) {
        for (Object data : datas) {
            mCompletedBrickInfoList.add(new BrickInfo(type, data, columns));
        }
        setCompletedData(mCompletedBrickInfoList);
    }

    /**
     * 添加单一类型列表
     *
     * @param datas
     */
    public void addSingleDataListPartialIndex(String type, List<? extends Object> datas, int pos) {
        addSingleDataListPartialIndex(type, datas, pos, 1);
    }

    /**
     * 添加单一类型列表
     *
     * @param datas
     */
    public void addSingleDataListPartialIndex(String type, List<? extends Object> datas, int pos, int columns) {
        List<BrickInfo> bricks = new ArrayList<>();
        for (Object data : datas) {
            bricks.add(new BrickInfo(type, data, columns));
        }
        int count = datas.size();
        mCompletedBrickInfoList.addAll(pos, bricks);
        rebuildPositionCache(mCompletedBrickInfoList);
        mAdapter.addDataList(mCompletedBrickInfoList, pos, count);
    }

    /**
     * 添加单一类型列表 局部刷新
     *
     * @param datas
     */
    public void addSingleDataListPartial(String type, List<? extends Object> datas) {
        addSingleDataListPartial(type, datas, 1);
    }

    /**
     * 添加单一类型列表 局部刷新
     *
     * @param datas
     */
    public void addSingleDataListPartial(String type, List<? extends Object> datas, int columns) {
        List<BrickInfo> bricks = new ArrayList<>();
        for (Object data : datas) {
            bricks.add(new BrickInfo(type, data, columns));
        }
        int pos = mCompletedBrickInfoList.size() - 1;
        int count = datas.size();
        mCompletedBrickInfoList.addAll(bricks);
        rebuildPositionCache(mCompletedBrickInfoList);
        mAdapter.addDataList(mCompletedBrickInfoList, pos, count);
    }

    /**
     * 替换列表数据
     *
     * @param type
     * @param datas
     * @param startPos
     */
    public void replaceDataList(String type, List<? extends Object> datas, int startPos) {
        replaceDataList(type, datas, startPos, 1);
    }

    /**
     * 替换列表数据
     *
     * @param type
     * @param datas
     * @param startPos
     */
    public void replaceDataList(String type, List<? extends Object> datas, int startPos, int columns) {
        List<BrickInfo> bricks = new ArrayList<>();
        for (Object data : datas) {
            bricks.add(new BrickInfo(type, data, columns));
        }
        mCompletedBrickInfoList = mCompletedBrickInfoList.subList(0, startPos);
        mCompletedBrickInfoList.addAll(bricks);
        rebuildPositionCache(mCompletedBrickInfoList);
        setCompletedData(mCompletedBrickInfoList);
    }

    /**
     * 替换列表数据 局部刷新
     *
     * @param type
     * @param datas
     * @param startPos
     */
    public void replaceDataListPartial(String type, List<? extends Object> datas, int startPos) {
        replaceDataListPartial(type, datas, startPos, 1);
    }

    /**
     * 替换列表数据 局部刷新
     *
     * @param type
     * @param datas
     * @param startPos
     */
    public void replaceDataListPartial(String type, List<? extends Object> datas, int startPos, int columns) {
        List<BrickInfo> bricks = new ArrayList<>();
        for (Object data : datas) {
            bricks.add(new BrickInfo(type, data, columns));
        }
        int count = datas.size();
        mCompletedBrickInfoList = mCompletedBrickInfoList.subList(0, startPos);
        mCompletedBrickInfoList.addAll(bricks);
        rebuildPositionCache(mCompletedBrickInfoList);
        mAdapter.replaceDataList(mCompletedBrickInfoList, startPos, count);
    }


    /**
     * 添加Brick数据
     *
     * @param data
     */
    public void addBrick(BrickInfo data) {
        mCompletedBrickInfoList.add(data);
        setCompletedData(mCompletedBrickInfoList);
    }

    /**
     * 添加Brick数据
     *
     * @param data
     */
    public void addBrick(int position, BrickInfo data) {
        mCompletedBrickInfoList.add(position, data);
        setCompletedData(mCompletedBrickInfoList);
    }

    /**
     * 添加Brick数据 局部刷新
     *
     * @param data
     */
    public void addBrickPartial(BrickInfo data) {
        mCompletedBrickInfoList.add(data);
        rebuildPositionCache(mCompletedBrickInfoList);
        mAdapter.addData(mCompletedBrickInfoList);
    }

    /**
     * 添加Brick数据 局部刷新
     *
     * @param data
     */
    public void addBrickPartial(int position, BrickInfo data) {
        mCompletedBrickInfoList.add(position, data);
        rebuildPositionCache(mCompletedBrickInfoList);
        mAdapter.addData(mCompletedBrickInfoList);
    }

    /**
     * 添加源数据
     *
     * @param type
     * @param extra
     */
    public void addData(String type, Object extra) {
        addBrick(new BrickInfo(type, extra));
    }

    /**
     * 添加源数据
     *
     * @param type
     * @param extra
     */
    public void addData(int position, String type, Object extra) {
        addBrick(position, new BrickInfo(type, extra));
    }

    /**
     * 添加源数据 局部刷新
     *
     * @param type
     * @param extra
     */
    public void addDataPartial(String type, Object extra) {
        addBrickPartial(new BrickInfo(type, extra));
    }

    /**
     * 添加源数据 局部刷新
     *
     * @param type
     * @param extra
     */
    public void addDataPartial(int position, String type, Object extra) {
        addBrickPartial(position, new BrickInfo(type, extra));
    }

    /**
     * 添加源数据
     *
     * @param type
     * @param extra
     * @param columns 列数
     */
    public void addData(String type, Object extra, int columns) {
        mCompletedBrickInfoList.add(new BrickInfo(type, extra, columns));
        setCompletedData(mCompletedBrickInfoList);
    }

    /**
     * 添加源数据
     *
     * @param type
     * @param extra
     * @param columns 列数
     */
    public void addData(int position, String type, Object extra, int columns) {
        mCompletedBrickInfoList.add(position, new BrickInfo(type, extra, columns));
        setCompletedData(mCompletedBrickInfoList);
    }

    /**
     * 添加源数据 局部刷新
     *
     * @param type
     * @param extra
     */
    public void addDataPartial(String type, Object extra, int columns) {
        BrickInfo brickInfo = new BrickInfo(type, extra, columns);
        mCompletedBrickInfoList.add(brickInfo);
        rebuildPositionCache(mCompletedBrickInfoList);
        mAdapter.addData(mCompletedBrickInfoList);
    }

    /**
     * 添加源数据 局部刷新
     *
     * @param type
     * @param extra
     */
    public void addDataPartial(int position, String type, Object extra, int columns) {
        BrickInfo brickInfo = new BrickInfo(type, extra, columns);
        mCompletedBrickInfoList.add(position, brickInfo);
        rebuildPositionCache(mCompletedBrickInfoList);
        mAdapter.addData(mCompletedBrickInfoList);
    }

    /**
     * 删除item
     *
     * @param position
     */
    public void removeItem(int position) {
        mCompletedBrickInfoList.remove(position);
        setCompletedData(mCompletedBrickInfoList);
    }

    /**
     * 删除
     *
     * @param startPos
     */
    public void removeRange(int startPos, int count) {
        mCompletedBrickInfoList.removeAll(new ArrayList<>(mCompletedBrickInfoList).subList(startPos, startPos + count));
        setCompletedData(mCompletedBrickInfoList);
    }


    /**
     * 删除item 局部刷新
     *
     * @param position
     */
    public void removeItemPartial(int position) {
        mCompletedBrickInfoList.remove(position);
        rebuildPositionCache(mCompletedBrickInfoList);
        mAdapter.remove(position);
    }

    /**
     * 删除
     *
     * @param startPos
     */
    public void removeRangePartial(int startPos, int count) {
        mCompletedBrickInfoList.removeAll(new ArrayList<>(mCompletedBrickInfoList).subList(startPos, startPos + count));
        rebuildPositionCache(mCompletedBrickInfoList);
        mAdapter.removeRange(startPos, count);
    }

    /**
     * 删除brick
     *
     * @param info
     */
    public void removeBrickInfo(BrickInfo info) {
        mCompletedBrickInfoList.remove(info);
        setCompletedData(mCompletedBrickInfoList);
    }

    /**
     * 删除brick 局部刷新
     *
     * @param info
     */
    public void removeBrickInfoPartial(BrickInfo info) {
        int position = mCompletedBrickInfoList.indexOf(info);
        mCompletedBrickInfoList.remove(info);
        rebuildPositionCache(mCompletedBrickInfoList);
        mAdapter.remove(position);
    }

    public void setOrientation(int orientation) {
        LayoutManager layoutManager = getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            ((LinearLayoutManager) layoutManager).setOrientation(orientation);
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            ((StaggeredGridLayoutManager) layoutManager).setOrientation(orientation);
        }
    }

    /**
     * 获取RecycleView item 行数
     *
     * @return 行数
     */
    public int getCount() {
        return mAdapter.getItemCount();
    }

    public List<BrickInfo> getCompletedBrickInfoList() {
        return mCompletedBrickInfoList;
    }

    public void clear() {
        mBrickPositionCache.clear();
        mCompletedBrickInfoList.clear();
        setCompletedData(mCompletedBrickInfoList);
    }

    public void update() {
        mAdapter.notifyDataSetChanged();
    }

    public void updateRange(int pos, int itemCount) {
        mAdapter.notifyItemRangeChanged(pos, itemCount);
    }

    public void updateItem(int pos) {
        mAdapter.notifyItemChanged(pos);
    }


    public void updateRange(int pos, int itemCount, Object payload) {
        mAdapter.notifyItemRangeChanged(pos, itemCount, payload);
    }

    public void updateItem(int pos, Object payload) {
        mAdapter.notifyItemChanged(pos, payload);
    }

    private void setCompletedData(List<BrickInfo> data) {
        rebuildPositionCache(data);
        mAdapter.setData(data);
    }

    private void rebuildPositionCache(List<BrickInfo> extendedData) {
        mBrickPositionCache.clear();
        int idxInGroup = 0;
        int groupSize = 0;
        for (int i = 0; i < extendedData.size(); i++) {
            BrickInfo current = extendedData.get(i);
            if (groupSize == 0) {
                groupSize++;
                for (int j = i + 1; j < extendedData.size(); j++) {
                    BrickInfo next = extendedData.get(j);
                    if (next != null && TextUtils.equals(next.getType(), current.getType())) {
                        groupSize++;
                    } else {
                        break;
                    }
                }
            }

            BrickPositionInfo p = new BrickPositionInfo();
            p.setIdxInGlobal(i);
            p.setIdxInGroup(idxInGroup);
            p.setGroupSize(groupSize);
            mBrickPositionCache.put(i, p);
            current.setPositionInfo(p);

            if (++idxInGroup == groupSize) {
                idxInGroup = 0;
                groupSize = 0;
            }
        }
    }


    public void setEventHandler(Object eventHandler) {
        mAdapter.setEventHandler(eventHandler);
    }

    public void scrollToTop() {
        smoothScrollToPosition(0);
    }

    public void scrollToTopWithNoAnim() {
        scrollToPosition(0);
    }

    /**
     * 根据子View获得对应组件在整个组件集合中的位置
     *
     * @return
     */
    public BrickPositionInfo getBrickPosition(AbstractBrickHolder brick) {
        if (brick != null) {
            int pos = brick.getAdapterPosition();
            if (pos != RecyclerView.NO_POSITION) {
                return mBrickPositionCache.get(pos);
            }
        }
        return null;
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow) {
        super.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
        return false;
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        mAdapter = new BrickRecyclerAdapter();
        setLayoutManager(createLayoutManager(context, 1));
        setAdapter(mAdapter);
        addItemDecoration(new BrickRecyclerItemDecoration());
        setDefaultAnimator(false);
    }

    public void setDefaultAnimator(boolean open) {
        if (open) {
            this.getItemAnimator().setAddDuration(120);
            this.getItemAnimator().setChangeDuration(250);
            this.getItemAnimator().setMoveDuration(250);
            this.getItemAnimator().setRemoveDuration(120);
        } else {
            this.getItemAnimator().setAddDuration(0);
            this.getItemAnimator().setChangeDuration(0);
            this.getItemAnimator().setMoveDuration(0);
            this.getItemAnimator().setRemoveDuration(0);
        }
    }

    /**
     * 设置瀑布流布局
     *
     * @param columns
     * @param orientation
     */
    public void setStaggeredLayout(int columns, int orientation) {
        LayoutManager layoutManager = getLayoutManager();
        if (null != layoutManager) {
            if (layoutManager instanceof StaggeredGridLayoutManager) {
                StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                staggeredGridLayoutManager.setSpanCount(columns);
                return;
            }
        }
        setLayoutManager(createStaggeredGridLayoutManager(columns, orientation));
    }

    private LayoutManager createStaggeredGridLayoutManager(int columns, int orientation) {
        return new StaggeredGridLayoutManager(columns, orientation);
    }

    /**
     * 设置普通布局
     *
     * @param context
     * @param spanSize 占位大小 把一行分为spanSize个位置
     */
    public void setNormalLayout(Context context, int spanSize) {
        LayoutManager layoutManager = getLayoutManager();
        if (null != layoutManager) {
            if (layoutManager instanceof GridLayoutManager) {
                GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
                setGridLayoutColumns(gridLayoutManager, spanSize);
                return;
            }
        }
        setLayoutManager(createLayoutManager(context, spanSize));
    }

    private LayoutManager createLayoutManager(Context context, final int spanSize) {
        GridLayoutManager layoutManager = new GridLayoutManager(context, spanSize);
        setGridLayoutColumns(layoutManager, spanSize);
        return layoutManager;
    }

    private void setGridLayoutColumns(GridLayoutManager layoutManager, final int spanSize) {
        layoutManager.setSpanCount(spanSize);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int cols = 1;
                BrickInfo info = mAdapter.getItemData(position);
                if (info != null) {
                    cols = info.getColumns();
                }

                if (cols <= 0) {
                    return spanSize;
                } else if (cols > spanSize) {
                    return 1;
                } else {
                    return spanSize / cols;
                }
            }
        });
    }
}
