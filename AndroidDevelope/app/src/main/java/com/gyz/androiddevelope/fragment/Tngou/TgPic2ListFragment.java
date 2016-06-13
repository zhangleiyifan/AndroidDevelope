package com.gyz.androiddevelope.fragment.Tngou;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gyz.androiddevelope.R;
import com.gyz.androiddevelope.activity.tngou.AlbumDetailActivity;
import com.gyz.androiddevelope.adapter.TgPicListAdapter;
import com.gyz.androiddevelope.base.BaseApplication;
import com.gyz.androiddevelope.base.BaseFragment;
import com.gyz.androiddevelope.base.BaseRecyclerAdapter;
import com.gyz.androiddevelope.base.BaseRecyclerFragment;
import com.gyz.androiddevelope.engine.AppContants;
import com.gyz.androiddevelope.response_bean.GalleryBean;
import com.gyz.androiddevelope.response_bean.GalleryRespBean;
import com.gyz.androiddevelope.retrofit.MySubscriber;
import com.gyz.androiddevelope.retrofit.ReUtil;
import com.gyz.androiddevelope.retrofit.RxUtil;
import com.gyz.androiddevelope.util.L;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import rx.Observable;
import rx.functions.Func1;

/**
 * @author: guoyazhou
 * @date: 2016-04-21 15:33
 */
public class TgPic2ListFragment extends BaseRecyclerFragment {

    private static final String TAG = "TgPic2ListFragment";
    TgPicListAdapter mSimpleRecyclerAdapter;

    private int id = 1;
    //每页返回的条数
    private int rows = 10;
    private int page = 1;
    private String title;

    private static final String ID = "id";
    private static final String TITLE = "title";

    public static TgPic2ListFragment startFragment(int id, String title) {
        TgPic2ListFragment tgPicListFragment = new TgPic2ListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ID, id);
        bundle.putString(TITLE, title);
        tgPicListFragment.setArguments(bundle);
        return tgPicListFragment;
    }

    /**
     * 获取list数据
     *
     * @param isAdd
     */
    @Override
    protected void addListNetData(boolean isAdd) {
        requestData();
    }

    /**
     * @return 这里初始化 adapter
     */
    @Override
    protected BaseRecyclerAdapter getAdapter() {
        mSimpleRecyclerAdapter = new TgPicListAdapter(context);
        mSimpleRecyclerAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object data, View parent) {
                AlbumDetailActivity.startActivity(((GalleryBean) data).getId());
            }
        });
        return mSimpleRecyclerAdapter;
    }

    /**
     * @return 获取recyclerView的layoutmanager
     */
    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return  new GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false);
    }

    @Override
    public void getBundleDatas(Bundle bundle) {
        id = bundle.getInt(ID, 1);
        title = bundle.getString(TITLE);

    }

    /**
     * 初始化数据
     */
    @Override
    public void initData() {
        requestData();
    }



    private void requestData() {
        L.e(TAG, "图片列表页，进行网络请求");
        RxUtil.subscribeAll(new Func1<String, Observable<GalleryRespBean>>() {
            @Override
            public Observable<GalleryRespBean> call(String s) {
                L.e(TAG, "图片列表页，进行网络请求ReUtil");
                return ReUtil.getApiManager(AppContants.TNGOU_HTTP).getGalleryBeanList(id, page++, rows);
            }
        }, new MySubscriber<GalleryRespBean>() {

            @Override
            public void onStart() {
                dlg.show();
            }

            @Override
            public void onError(Throwable e) {
//                super.onError(e);
                //   从数据库中获取缓存json
                SQLiteDatabase database = BaseApplication.getInstantce().getTngouListDbHelper().getReadableDatabase();
                Cursor cursor = database.rawQuery("select * from tngouList where typeid = " + id, null);
                L.e(TAG, " 从数据库中获取图片列表缓存json    id=" + id);
                if (cursor.moveToFirst()) {

                    String json = cursor.getString(cursor.getColumnIndex("json"));
                    GalleryRespBean o = getGson().fromJson(json, GalleryRespBean.class);
                    L.e(TAG, "  图片列表缓存数据为=" + json);
                    mSimpleRecyclerAdapter.addDatas(o.getTngouList());
                    mSimpleRecyclerAdapter.notifyDataSetChanged();

                }
                cursor.close();
                database.close();
                dlg.hide();
            }

            @Override
            public void onNext(GalleryRespBean o) {
                mSimpleRecyclerAdapter.addDatas(o.getTngouList());
                mSimpleRecyclerAdapter.notifyDataSetChanged();

//                存入数据库  insert into sc(sno,cno) values('95020','1')
                SQLiteDatabase database = BaseApplication.getInstantce().getTngouListDbHelper().getWritableDatabase();

                database.execSQL("replace into tngouList( typeid,json)  values( " + id + ",'" + getGson().toJson(o, GalleryRespBean.class) + "')");
                L.e(TAG, "图片列表数据存入数据库++++++" + "replace into tngouList( typeid,json)  values( " + id + ",'" + getGson().toJson(o, GalleryRespBean.class) + "')");
                database.close();

            }

            @Override
            public void onCompleted() {
                dlg.hide();
            }
        });


    }

    @Override
    public String getTitle() {
        return null;
    }
}
