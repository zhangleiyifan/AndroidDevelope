package com.gyz.androiddevelope.activity.tngou;

import android.content.Intent;
import android.os.Bundle;

import com.gyz.androiddevelope.base.BaseActivity;
import com.gyz.androiddevelope.base.BaseApplication;
import com.gyz.androiddevelope.response_bean.AlbumDetailListBean;
import com.gyz.androiddevelope.retrofit.MySubscriber;
import com.gyz.androiddevelope.retrofit.ReUtil;
import com.gyz.androiddevelope.retrofit.RxUtil;
import com.gyz.androiddevelope.util.ToastUtil;

import rx.Observable;
import rx.functions.Func1;

/**
 * @author: guoyazhou
 * @date: 2016-04-22 16:52
 */
public class AlbumDetailActivity extends BaseActivity {
    private static final String TAG = "AlbumDetailActivity";

    public static final String ALBUM_ID = "album_id";

    private int albumId;

    public static void startActivity( int id){
        Intent intent = new Intent(BaseApplication.getInstantce(),AlbumDetailActivity.class);
        intent.putExtra(ALBUM_ID,id);
        intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK);
        BaseApplication.getInstantce().startActivity(intent);

    }

    @Override
    protected void initVariables() {
        albumId=  getIntent().getIntExtra(ALBUM_ID,1);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }

    @Override
    protected void loadData() {
        RxUtil.subscribeAll(new Func1<String, Observable<AlbumDetailListBean>>() {
            @Override
            public Observable<AlbumDetailListBean> call(String s) {
                return ReUtil.getApiManager(false).getAlbumDetailList(albumId);
            }
        },new MySubscriber<AlbumDetailListBean>(){
            @Override
            public void onNext(AlbumDetailListBean o) {
                if (o.getMsg()!=null){
                    ToastUtil.showLong(AlbumDetailActivity.this,o.getMsg());
                    return;
                }

            }
        });
    }
}
