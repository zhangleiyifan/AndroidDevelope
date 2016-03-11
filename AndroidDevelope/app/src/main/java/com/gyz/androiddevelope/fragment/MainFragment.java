package com.gyz.androiddevelope.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gyz.androiddevelope.R;
import com.gyz.androiddevelope.adapter.BaseRecyclerAdapter;
import com.gyz.androiddevelope.adapter.HomeNewsAdapter;
import com.gyz.androiddevelope.base.BaseFragment;
import com.gyz.androiddevelope.response_bean.LatestNewsBean;
import com.gyz.androiddevelope.retrofit.ReUtil;
import com.gyz.androiddevelope.retrofit.RxUtil;
import com.gyz.androiddevelope.util.L;
import com.gyz.androiddevelope.util.ToastUtil;
import com.gyz.androiddevelope.view.MarqueeView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * @author: guoyazhou
 * @date: 2016-03-08 16:00
 */
public class MainFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "HealthInfoListFragment";

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    List<LatestNewsBean.Story> list;
    HomeNewsAdapter adapter;
    MarqueeView marqueeView;
    LinearLayoutManager mLayoutManager;
    private int page = 1, rows, id, lastVisibleItemPosition;
    private boolean isAdd;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initView() {

        adapter= new HomeNewsAdapter(getContext());
        recyclerView.setAdapter(adapter);
        //下拉刷新
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark, R.color.colorAccent, R.color.color_f98435, R.color.color_ef5350);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                .getDisplayMetrics()));

        mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);

        //添加头部view
        View  header=  LayoutInflater.from(getContext()).inflate(R.layout.kanner,recyclerView,false);
        marqueeView = (MarqueeView) header.findViewById(R.id.marqueeView);
        marqueeView.setOnItemClickListener(new MarqueeView.OnItemClickListener() {
            @Override
            public void click(LatestNewsBean.TopStory entity) {
                ToastUtil.showShort(getContext(),"title==="+entity.getTitle());
            }
        });

        adapter.setHeaderView(header);
        adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object data) {
                LatestNewsBean.Story object = (LatestNewsBean.Story) data;
            }
        });

//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                L.e("lastVisibleItemPosition==" + lastVisibleItemPosition + "getItemCount==" + adapter.getItemCount());
//                if (newState == RecyclerView.SCROLL_STATE_IDLE
//                        && lastVisibleItemPosition + 1 == adapter.getItemCount()) {
//                    swipeRefreshLayout.setRefreshing(true);
//                    page++;
//                    isAdd = true;
//                    requestNetData();
//                }
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                lastVisibleItemPosition = mLayoutManager.findLastVisibleItemPosition();
//            }
//        });

    }

    @Override
    public void initData() {
        requestNetData();
    }


    @Override
    public void onRefresh() {
//      下拉时会调用
        page = 1;
        isAdd = false;
        requestNetData();
    }


    private void requestNetData() {
        RxUtil.subscribeAll(new Func1<String, Observable<LatestNewsBean>>() {
            @Override
            public Observable<LatestNewsBean> call(String s) {
                return ReUtil.getApiManager().getLatestNews();
            }
        }, new Subscriber<LatestNewsBean>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(LatestNewsBean latestNewsBean) {

                swipeRefreshLayout.setRefreshing(false);
                if (isAdd) {
                    list.addAll(latestNewsBean.stories);
                } else {
                    list = latestNewsBean.stories;
                }
                L.e(TAG, "size===" + list.size());

                adapter.addDatas(list);
                marqueeView.setTopEntities(latestNewsBean.topStories);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
