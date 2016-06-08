package com.gyz.androiddevelope.fragment.huaban;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import com.gyz.androiddevelope.adapter.HuabanRecyclerAdapter;
import com.gyz.androiddevelope.base.BaseRecyclerAdapter;
import com.gyz.androiddevelope.base.BaseRecyclerFragment;
import com.gyz.androiddevelope.engine.AppContants;
import com.gyz.androiddevelope.listener.OnSwipeRefreshFragmentListener;
import com.gyz.androiddevelope.response_bean.ListPinsBean;
import com.gyz.androiddevelope.response_bean.PinsMainEntity;
import com.gyz.androiddevelope.retrofit.ReUtil;
import com.gyz.androiddevelope.retrofit.RxUtil;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * @version V1.0
 * @FileName: com.gyz.androiddevelope.fragment.huaban.HuabanFragment.java
 * @author: ZhaoHao
 * @date: 2016-06-07 13:18
 */
public class HuabanFragment extends BaseRecyclerFragment {
    private static final String TAG = "HuabanFragment";
    public static final String KEY  = "key";

    //花瓣联网的授权字段
    protected String mAuthorization;
    protected String mKey = "all";//用于联网查询的关键字
    protected static int mLimit = AppContants.LIMIT;
    private int mMaxId = 0;
    private HuabanRecyclerAdapter adapter;
    //与Activity 交互接口 SWIpe 刷新结束后，停止转圈
    private OnSwipeRefreshFragmentListener mRefreshListener;

    @Override
    public void getBundleDatas(Bundle bundle) {
        mKey = bundle!=null? (String) bundle.get(KEY) :"all";
    }

    @Override
    public void initData() {
        requestData(false);
    }

    @Override
    public String getTitle() {
        return "花瓣";
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSwipeRefreshFragmentListener){
            mRefreshListener = (OnSwipeRefreshFragmentListener) context;
        }
    }

    @Override
    protected void addListNetData(boolean isAdd) {
        //下拉刷新新数据
        requestData(isAdd);
    }

    private void requestData(final boolean isAddData) {

        RxUtil.subscribeAll(new Func1<String, Observable<ListPinsBean>>() {
            @Override
            public Observable<ListPinsBean> call(String s) {
                if (isAddData) {
                    mRefreshListener.OnRefreshState(true);
                    return ReUtil.getApiManager(AppContants.HUABAN_HTTP).httpsTypeMaxLimitRx(mAuthorization, mKey, mMaxId, mLimit);
                } else {
                    return ReUtil.getApiManager(AppContants.HUABAN_HTTP).httpsTypeLimitRx(mAuthorization, mKey, mLimit);
                }
            }
        }, new Subscriber<ListPinsBean>() {
            @Override
            public void onCompleted() {
                mRefreshListener.OnRefreshState(false);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();mRefreshListener.OnRefreshState(false);
            }

            @Override
            public void onNext(ListPinsBean listPinsBean) {
                List<PinsMainEntity> result = listPinsBean.getPins();
                //保存maxId值 后续加载需要
                mMaxId = result.get(result.size() - 1).getPin_id();
                if (!isAddData) {
                    adapter.clearDatas();
                }
                adapter.addDatas(result);
            }
        });

    }

    @Override
    protected BaseRecyclerAdapter getAdapter() {
        adapter = new HuabanRecyclerAdapter(getContext());

        adapter.setRecyclerAdapterListener(new HuabanRecyclerAdapter.RecyclerAdapterListener() {
            @Override
            public void onClickImage(PinsMainEntity bean, View view) {

            }

            @Override
            public void onClickTitleInfo(PinsMainEntity bean, View view) {

            }

            @Override
            public void onClickInfoGather(PinsMainEntity bean, View view) {

            }

            @Override
            public void onClickInfoLike(PinsMainEntity bean, View view) {

            }
        });

        return adapter;
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
    }

}
