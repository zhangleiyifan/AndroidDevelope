package com.gyz.androiddevelope.base;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;

import com.gyz.androiddevelope.R;
import com.gyz.androiddevelope.listener.OnRecyclerRefreshListener;
import com.gyz.androiddevelope.listener.RecycleViewOnScrollListener;
import com.gyz.androiddevelope.util.L;

import butterknife.Bind;

/**
 * @version V1.0
 * @FileName: com.gyz.androiddevelope.base.BaseRecyclerFragment.java
 * @author: ZhaoHao
 * @date: 2016-06-07 09:50
 */
public abstract class BaseRecyclerFragment extends BaseFragment implements OnRecyclerRefreshListener {
    private static final String TAG = "BaseRecyclerFragment";
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    BaseRecyclerAdapter mAdapter;

    protected final float percentageScroll = 0.8f;//滑动距离的百分比
    //是否还监听滑动的联网 标志位 默认为true 表示需要监听
    protected boolean isScorllLisener = true;
    @Override
    public int getLayoutId() {
        return R.layout.base_recycler_fragment;
    }

    @Override
    public void initView() {

        recyclerView.setLayoutManager(getLayoutManager());
        mAdapter = getAdapter();
        recyclerView.setAdapter(mAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());//设置默认动画
        recyclerView.addOnScrollListener(new RecycleViewOnScrollListener() {
            @Override
            public void show() {
            }

            @Override
            public void hide() {
            }

            @Override
            public void normalScroll() {
            }

            @Override
            public void onScrollStateChange(RecyclerView recyclerView, int newState) {
                if (RecyclerView.SCROLL_STATE_IDLE == newState) {
                    //滑动停止
                    L.d("滑动停止 position=" + mAdapter.getPosition());
                    int size = (int) (mAdapter.getItemCount() * percentageScroll);
                    if (mAdapter.getPosition() >= --size && isScorllLisener) {
                        addListNetData(true);
                    }
                } else if (RecyclerView.SCROLL_STATE_DRAGGING == newState) {
                    //用户正在滑动
//                    L.d("用户正在滑动 position=" + mAdapter.getAdapterPosition());
                } else {
                    //惯性滑动
//                    L.d("惯性滑动 position=" + mAdapter.getAdapterPosition());
                }
            }
        });
    }

    /**
     * 获取list数据
     */
    protected abstract void addListNetData(boolean isAdd);

    /**
     *
     * @return 这里初始化 adapter
     */
    protected abstract BaseRecyclerAdapter getAdapter();

    /**
     *
     * @return 获取recyclerView的layoutmanager
     */
    protected abstract RecyclerView.LayoutManager getLayoutManager();

    @Override
    public void onRecyclerRefresh() {
        addListNetData(false);
    }


    public RecyclerView getRecyclerView(){
        return recyclerView;
    }
}
