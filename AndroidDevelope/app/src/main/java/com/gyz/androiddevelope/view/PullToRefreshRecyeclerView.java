package com.gyz.androiddevelope.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.gyz.androiddevelope.R;
import com.gyz.androiddevelope.util.L;


/**
 * @version V1.0
 * @FileName: com.gyz.androiddevelope.view.PullToRefreshRecyeclerView.java
 * @author: ZhaoHao
 * @date: 2016-03-12 16:57
 */
public class PullToRefreshRecyeclerView extends LinearLayout{

    private Context context;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RefreshLoadMoreListener mRefreshLoadMoreListener;
    private boolean hasMore =true;
    private boolean isRefreshing;
    private boolean isLoadMore;

    public PullToRefreshRecyeclerView(Context context) {
        this(context, null);
    }

    public PullToRefreshRecyeclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullToRefreshRecyeclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    private void initView() {

        View view = LayoutInflater.from(context).inflate(R.layout.pull_to_refresh_recyclerview, null);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark, R.color.colorAccent, R.color.color_f98435, R.color.color_ef5350);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                L.e("触发下拉刷新");
//        swipeRefreshLayout 下拉监听
                if (!isRefreshing) {
                    isRefreshing = true;
                    refresh();
                }
            }
        });

        recyclerView.addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                int totalItemCount = layoutManager.getItemCount();

                if (hasMore&& lastVisibleItem >=totalItemCount-1&& !isLoadMore){
                    isLoadMore = true;
                    loadMore();
                }

            }
        });

        addView(view);
    }

    public void setRecyclerAdapter(RecyclerView.Adapter adapter) {
        if (adapter != null)
            recyclerView.setAdapter(adapter);
    }

    public void setmRefreshLoadMoreListener(RefreshLoadMoreListener mRefreshLoadMoreListener) {
        this.mRefreshLoadMoreListener = mRefreshLoadMoreListener;
    }

    //设置上拉
    public void setPullLoadMoreEnable(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public boolean getPullLoadMoreEnable() {
        return hasMore;
    }

    /**
     * 加载更多完毕,为防止频繁网络请求,isLoadMore为false才可再次请求更多数据
     */
    public void setLoadMoreCompleted(){
        isLoadMore = false;
    }

    //设置下拉
    public void setPullRefreshEnable(boolean enable) {
        swipeRefreshLayout.setEnabled(enable);
    }

    public boolean getPullRefreshEnable() {
        return swipeRefreshLayout.isEnabled();
    }

    //设置reclyerview的方向
    public void setOrientation(int orientation) {
        if (orientation != HORIZONTAL && orientation != VERTICAL) {
            layoutManager.setOrientation(VERTICAL);
        } else {
            layoutManager.setOrientation(orientation);
        }
    }

//    public int getOrientation(){
//        return layoutManager.getOrientation();
//    }

    public void stopSwipRefresh() {
        isRefreshing = false;
        swipeRefreshLayout.setRefreshing(false);
    }


    public void refresh() {
        if (mRefreshLoadMoreListener != null) {
            mRefreshLoadMoreListener.onRefresh();
        }
    }

    public void loadMore() {
        if (mRefreshLoadMoreListener != null && hasMore) {
            mRefreshLoadMoreListener.onLoadMore();
        }
    }

    public RecyclerView getRecyclerView(){
        return recyclerView;
    }


    public interface RefreshLoadMoreListener {
        /**
         * 下拉刷新
         **/
        void onRefresh();

        /**
         * 加载更多
         **/
        void onLoadMore();
    }
}
