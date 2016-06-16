package com.gyz.androiddevelope.fragment.huaban;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.gyz.androiddevelope.base.BaseRecyclerAdapter;
import com.gyz.androiddevelope.base.BaseRecyclerFragment;
import com.gyz.androiddevelope.engine.AppContants;
import com.gyz.androiddevelope.response_bean.ListPinsBean;
import com.gyz.androiddevelope.response_bean.PinsMainEntity;
import com.gyz.androiddevelope.retrofit.ReUtil;
import com.gyz.androiddevelope.retrofit.RxUtil;
import com.gyz.androiddevelope.util.L;
import com.gyz.androiddevelope.util.UserUtils;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @version V1.0
 * @FileName: com.gyz.androiddevelope.fragment.huaban.UserLikeFragment.java
 * @author: ZhaoHao
 * @date: 2016-06-16 16:09
 */
public class UserLikeFragment extends BaseRecyclerFragment {
    private static final String TAG = "UserLikeFragment";
    public static final String USERID = "USERID";
    public static final String TYPE = "type";
    public static final int TYPE_PIN = 234;
    public static final int TYPE_LIKE = 1234;

    private String userId;
    private int type;

    public static UserLikeFragment newInstance(String userId,int type){
        UserLikeFragment instance = new UserLikeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TYPE, type);
        bundle.putString(USERID, userId);
        instance.setArguments(bundle);
        return instance;
    }


    /**
     * 获取list数据
     *
     * @param isAdd
     */
    @Override
    protected void addListNetData(boolean isAdd) {

    }

    /**
     * @return 这里初始化 adapter
     */
    @Override
    protected BaseRecyclerAdapter getAdapter() {
        return null;
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
    }

    @Override
    public void getBundleDatas(Bundle bundle) {
            userId = bundle.getString(USERID);
            type = bundle.getInt(TYPE);
    }

    /**
     * 初始化数据
     */
    @Override
    public void initData() {
        requestData();
    }

    private void requestData() {

        ReUtil.getApiManager(AppContants.HUABAN_HTTP).httpsUserLikePinsRx(UserUtils.getAuthorizations(true,getContext()),userId,AppContants.LIMIT)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).map(new Func1<ListPinsBean, List<PinsMainEntity>>() {
            @Override
            public List<PinsMainEntity> call(ListPinsBean listPinsBean) {
                return listPinsBean.getPins();
            }
        }).subscribe(new Subscriber<List<PinsMainEntity>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                L.e(TAG,"onError=======");
                e.printStackTrace();

            }

            @Override
            public void onNext(List<PinsMainEntity> pinsMainEntities) {
                L.e(TAG,"onNext=======");
            }
        });
    }

    @Override
    public String getTitle() {
        return null;
    }
}
