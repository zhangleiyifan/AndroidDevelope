package com.gyz.androiddevelope.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gyz.androiddevelope.R;
import com.gyz.androiddevelope.adapter.BaseRecyclerAdapter;
import com.gyz.androiddevelope.adapter.HomeNewsAdapter;
import com.gyz.androiddevelope.base.BaseFragment;
import com.gyz.androiddevelope.response_bean.LatestNewsBean;
import com.gyz.androiddevelope.response_bean.Story;
import com.gyz.androiddevelope.retrofit.ReUtil;
import com.gyz.androiddevelope.retrofit.RxUtil;
import com.gyz.androiddevelope.util.L;
import com.gyz.androiddevelope.util.ToastUtil;
import com.gyz.androiddevelope.view.MarqueeView;
import com.gyz.androiddevelope.view.PullToRefreshRecyeclerView;

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
public class MainFragment2 extends BaseFragment implements PullToRefreshRecyeclerView.RefreshLoadMoreListener {
    private static final String TAG = "MainFragment2";

    @Bind(R.id.pullView)
    public PullToRefreshRecyeclerView pullView;

    List<Story> list;
    HomeNewsAdapter adapter;
    MarqueeView marqueeView;
    LinearLayoutManager mLayoutManager;
    private int page = 1, rows, id, lastVisibleItemPosition;
    private boolean isAdd;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main2, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initView() {

        adapter= new HomeNewsAdapter(getContext());
        //添加头部view
        View  header=  LayoutInflater.from(getContext()).inflate(R.layout.kanner,pullView.getRecyclerView(),false);
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
            public void onItemClick(int position, Object data,View v) {
                 Story object = ( Story) data;
            }
        });

        pullView.setRecyclerAdapter(adapter);
        pullView.setmRefreshLoadMoreListener(this);
    }

    @Override
    public void initData() {
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
                    pullView.setPullRefreshEnable(false);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(LatestNewsBean latestNewsBean) {

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

    @Override
    public void onRefresh() {
        L.e("onrefresh---------------------------");
    }

    @Override
    public void onLoadMore() {
//        TODO
        L.e("onloadmoreeeeeeeeeeeeeeeeeeeeeeeeeeee");
        pullView.setLoadMoreCompleted();
    }
}
