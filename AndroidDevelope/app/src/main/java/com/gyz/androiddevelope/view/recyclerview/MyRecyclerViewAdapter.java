package com.gyz.androiddevelope.view.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.security.PublicKey;
import java.util.ArrayList;

/**
 * @version V1.0
 * @FileName: com.gyz.androiddevelope.view.recyclerview.MyRecyclerViewAdapter.java
 * @author: ZhaoHao
 * @date: 2016-06-14 13:40
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter {
    private static final String TAG = "MyRecyclerViewAdapter";

    private RecyclerView.Adapter mAdapter;
    private ArrayList<View> mHeaderViewInfos;
    private ArrayList<View> mFooterViewInfos;

    public MyRecyclerViewAdapter(ArrayList<View> mHeaderView, ArrayList<View> mFooterView, RecyclerView.Adapter adapter) {
        mAdapter = adapter;

        if (mHeaderView == null) {
            mHeaderViewInfos = new ArrayList<>();
        } else {
            mHeaderViewInfos = mHeaderView;
        }

        if (mFooterView == null) {
            mFooterViewInfos = new ArrayList<>();
        } else {
            mFooterViewInfos = mFooterView;
        }
    }

    @Override
    public int getItemViewType(int position) {

        int numHead = getHeaderCount();
        //判断当前条目是什么类型的---决定渲染什么视图给什么数据
        if (position < numHead) {
            //是头部
            return RecyclerView.INVALID_TYPE;
        }

        //正常条目部分  走到这一步  position已经大于numhead
        int adjPosition = position - numHead;
        int adapterCount = 0;
        if (mAdapter != null) {
            adapterCount = mAdapter.getItemCount();
            if (adjPosition < adapterCount) {
                //如果比adapter的item总个数小，说明是正常条目，否则为footer
                return mAdapter.getItemViewType(position);
            }
        }

        //foot部分
        return RecyclerView.INVALID_TYPE - 1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == RecyclerView.INVALID_TYPE) {
            return new HeadViewViewHolder(mHeaderViewInfos.get(0));
        } else if (viewType == RecyclerView.INVALID_TYPE - 1) {
            return new HeadViewViewHolder(mFooterViewInfos.get(0));
        }
        // Footer (off-limits positions will throw an IndexOutOfBoundsException)
        return mAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        //也要划分三个区域
        int numHead = getHeaderCount();
        if (position<numHead){
            //是头部
            return;
        }

//        body
        int adjPosition = position - numHead;
        int adapterCount = 0;
        if (mAdapter!=null){
            adapterCount = mAdapter.getItemCount();
            if (adjPosition<adapterCount){
                mAdapter.onBindViewHolder(holder,adjPosition);
            }
        }
        // footer
    }

    @Override
    public int getItemCount() {
        if (mAdapter != null) {
            return mAdapter.getItemCount() + getFooterCount() + getHeaderCount();
        } else {
            return getFooterCount() + getHeaderCount();
        }
    }

    private int getFooterCount() {
        return mFooterViewInfos.size();
    }

    private int getHeaderCount() {
        return mHeaderViewInfos.size();
    }

    public static class HeadViewViewHolder extends RecyclerView.ViewHolder {

        public HeadViewViewHolder(View itemView) {
            super(itemView);
        }
    }

}
