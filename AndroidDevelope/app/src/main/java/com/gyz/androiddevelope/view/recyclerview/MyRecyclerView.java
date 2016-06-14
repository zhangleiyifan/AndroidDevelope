package com.gyz.androiddevelope.view.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import java.util.ArrayList;

/**
 * 自定义recyclerView 可以添加header和footer
 *
 * @version V1.0
 * @FileName: com.gyz.androiddevelope.view.recyclerview.MyRecyclerView.java
 * @author: ZhaoHao
 * @date: 2016-06-14 10:17
 */
public class MyRecyclerView extends RecyclerView {
    private static final String TAG = "MyRecyclerView";

    private ArrayList<View> mHeaderViewInfos = new ArrayList<View>();
    private ArrayList<View> mFooterViewInfos = new ArrayList<View>();
    private Adapter mAdapter;

    public MyRecyclerView(Context context) {
        this(context, null);
    }

    public MyRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    /**
     * 添加头部View
     *
     * @param view 头部View
     */
    public void addHeadView(View view) {
        mHeaderViewInfos.add(view);

        // Wrap the adapter if it wasn't already wrapped.
        if (mAdapter != null) {
            if (!(mAdapter instanceof MyRecyclerViewAdapter)) {
                mAdapter = new MyRecyclerViewAdapter(mHeaderViewInfos, mFooterViewInfos, mAdapter);
            }
        }
    }

    /**
     * add footer
     *
     * @param view foot view
     */
    public void addFootView(View view) {
        mFooterViewInfos.add(view);

        // Wrap the adapter if it wasn't already wrapped.
        if (mAdapter != null) {
            if (!(mAdapter instanceof MyRecyclerViewAdapter)) {
                mAdapter = new MyRecyclerViewAdapter(mHeaderViewInfos, mFooterViewInfos, mAdapter);
            }
        }
    }

    public void setAdapter(Adapter adapter){
        if (mHeaderViewInfos.size()>0||mFooterViewInfos.size()>0){
                mAdapter = new MyRecyclerViewAdapter(mHeaderViewInfos, mFooterViewInfos, adapter);
        }else {
            mAdapter = adapter;
        }
        super.setAdapter(mAdapter);
    }

}
