package com.gyz.androiddevelope.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gyz.androiddevelope.R;
import com.gyz.androiddevelope.adapter.TgPicListAdapter;
import com.gyz.androiddevelope.base.BaseFragment;
import com.gyz.androiddevelope.response_bean.GalleryBean;
import com.gyz.androiddevelope.response_bean.GalleryRespBean;
import com.gyz.androiddevelope.retrofit.MySubscriber;
import com.gyz.androiddevelope.retrofit.ReUtil;
import com.gyz.androiddevelope.retrofit.RxUtil;
import com.gyz.androiddevelope.util.L;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.functions.Func1;

/**
 * @author: guoyazhou
 * @date: 2016-04-21 15:33
 */
public class TgPicListFragment extends BaseFragment {
    private static final String TAG = "TgPicListFragment";
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    GridLayoutManager mLayoutManager;

    private int id = 1;
    //每页返回的条数
    private int rows = 10;
    private int page = 1;
    private String title;

    TgPicListAdapter mSimpleRecyclerAdapter;

    public TgPicListFragment(int id, String title) {
        super();
        this.id = id;
        this.title = title;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tg_pic_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initView() {
        List<GalleryBean> tngouList = new ArrayList<>();
        mSimpleRecyclerAdapter = new TgPicListAdapter(getActivity() );
        recyclerView.setAdapter(mSimpleRecyclerAdapter);

        mLayoutManager = new GridLayoutManager(context,2,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(mLayoutManager);
    }

    @Override
    public void initData() {

        RxUtil.subscribeAll(new Func1<String, Observable<GalleryRespBean>>() {
            @Override
            public Observable<GalleryRespBean> call(String s) {
                return ReUtil.getApiManager(false).getGalleryBeanList(id, page, rows);
            }
        }, new MySubscriber<GalleryRespBean>() {
            @Override
            public void onNext(GalleryRespBean o) {
                L.e(TAG, o.getTngouList().get(1).getTitle() + "______________________________");

                mSimpleRecyclerAdapter.addDatas(o.getTngouList());
                mSimpleRecyclerAdapter.notifyDataSetChanged();
            }
        });


    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
