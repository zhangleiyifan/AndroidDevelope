package com.gyz.androiddevelope.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.gyz.androiddevelope.R;
import com.gyz.androiddevelope.base.BaseFragment;
import com.gyz.androiddevelope.response_bean.GalleryBean;
import com.gyz.androiddevelope.retrofit.MySubscriber;
import com.gyz.androiddevelope.retrofit.ReUtil;
import com.gyz.androiddevelope.retrofit.RxUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;
import rx.Observable;
import rx.functions.Func1;

/**
 * @author: guoyazhou
 * @date: 2016-04-21 13:08
 */
public class TngouPicFragment extends BaseFragment implements WaveSwipeRefreshLayout.OnRefreshListener{
    private static final String TAG = "TngouPicFragment";

    @Bind(R.id.listView)
    ListView listView;
    @Bind(R.id.waveRefreshLayout)
    WaveSwipeRefreshLayout waveRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tg_pic, container, false);
        ButterKnife.bind(this, view);
        return view;

    }

    @Override
    public void initView() {
        waveRefreshLayout.setWaveColor(getResources().getColor(R.color.colorPrimary));
        waveRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorWhite));
        waveRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void initData() {

        RxUtil.subscribeAll(new Func1<String, Observable<GalleryBean>>() {
            @Override
            public Observable<GalleryBean> call(String s) {
                return ReUtil.getApiManager(false).getGalleryClass();
            }
        },new MySubscriber<GalleryBean>(){
            @Override
            public void onCompleted() {
                super.onCompleted();
                waveRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onNext(GalleryBean o) {
                super.onNext(o);

            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);

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
//        刷新
        initData();
    }
}
