package com.gyz.androiddevelope.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gyz.androiddevelope.R;
import com.gyz.androiddevelope.adapter.TngouPicViewPagerAdapter;
import com.gyz.androiddevelope.base.BaseFragment;
import com.gyz.androiddevelope.response_bean.GalleryBean;
import com.gyz.androiddevelope.response_bean.GalleryListRespBean;
import com.gyz.androiddevelope.retrofit.MySubscriber;
import com.gyz.androiddevelope.retrofit.ReUtil;
import com.gyz.androiddevelope.retrofit.RxUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;
import rx.Observable;
import rx.functions.Func1;

/**
 * @author: guoyazhou
 * @date: 2016-04-21 13:08
 */
public class TngouPicFragment extends BaseFragment implements WaveSwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "TngouPicFragment";

    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    @Bind(R.id.viewpager)
    ViewPager viewpager;


    private List<BaseFragment> fragmentList = new ArrayList<>();
    TngouPicViewPagerAdapter pagerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tg_pic, container, false);
        ButterKnife.bind(this, view);
        return view;

    }

    @Override
    public void initView() {
    }

    @Override
    public void initData() {

        getTypeList();


    }

    @Override
    public String getTitle() {
        return getResources().getString(R.string.title_tngou);
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

    /**
     * 获取顶部列表信息
     */
    public void getTypeList() {
        RxUtil.subscribeAll(new Func1<String, Observable<GalleryListRespBean>>() {
            @Override
            public Observable<GalleryListRespBean> call(String s) {
                return ReUtil.getApiManager(false).getGalleryClass();
            }
        }, new MySubscriber<GalleryListRespBean>() {
            @Override
            public void onCompleted() {
                super.onCompleted();

            }

            @Override
            public void onNext(GalleryListRespBean o) {
                initTabView(o);

            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);

            }
        });
    }

    private void initTabView(GalleryListRespBean listRespBean) {

        List<String> list = getTabTitle(listRespBean);

        for (GalleryBean bean : listRespBean.getTngouList()) {
            fragmentList.add(new TgPicListFragment(bean.getId(), bean.getTitle()));
        }

        pagerAdapter = new TngouPicViewPagerAdapter(getActivity().getSupportFragmentManager(), fragmentList, list);
        viewpager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewpager);
    }

    private List<String> getTabTitle(GalleryListRespBean o) {
        List<String> list = new ArrayList<>();
        if (!o.getTngouList().isEmpty()) {
            for (GalleryBean bean : o.getTngouList()) {
                list.add(bean.getTitle());
            }
        }
        return list;
    }
}
