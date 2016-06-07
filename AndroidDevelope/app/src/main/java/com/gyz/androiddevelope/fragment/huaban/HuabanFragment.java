package com.gyz.androiddevelope.fragment.huaban;

import android.view.View;
import com.gyz.androiddevelope.adapter.HuabanRecyclerAdapter;
import com.gyz.androiddevelope.base.BaseRecyclerAdapter;
import com.gyz.androiddevelope.base.BaseRecyclerFragment;
import com.gyz.androiddevelope.engine.AppContants;
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

    //花瓣联网的授权字段
    protected String mAuthorization;
    protected String mKey = "beauty";//用于联网查询的关键字
    protected static int mLimit = AppContants.LIMIT;
    private int mMaxId = 0;
    private HuabanRecyclerAdapter adapter;

    @Override
    protected void addMoreData() {
        //下拉刷新新数据
        requestData(true);
    }

    private void requestData(final boolean isAddData) {

        RxUtil.subscribeAll(new Func1<String, Observable<ListPinsBean>>() {
            @Override
            public Observable<ListPinsBean> call(String s) {
                if (isAddData) {
                    return ReUtil.getApiManager(AppContants.HUABAN_HTTP).httpsTypeMaxLimitRx(mAuthorization, mKey, mMaxId, mLimit);
                } else {
                    return ReUtil.getApiManager(AppContants.HUABAN_HTTP).httpsTypeLimitRx(mAuthorization, mKey, mLimit);
                }
            }
        }, new Subscriber<ListPinsBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
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
    public void initData() {
        requestData(false);
    }

    @Override
    public String getTitle() {
        return "花瓣";
    }
}
