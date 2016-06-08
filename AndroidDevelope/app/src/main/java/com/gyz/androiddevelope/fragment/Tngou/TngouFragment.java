package com.gyz.androiddevelope.fragment.Tngou;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gyz.androiddevelope.R;
import com.gyz.androiddevelope.adapter.TngouPicViewPagerAdapter;
import com.gyz.androiddevelope.base.BaseApplication;
import com.gyz.androiddevelope.base.BaseFragment;
import com.gyz.androiddevelope.engine.AppContants;
import com.gyz.androiddevelope.response_bean.GalleryTypeBean;
import com.gyz.androiddevelope.response_bean.GalleryTypeRespBean;
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
 * @date: 2016-04-21 13:08
 */
public class TngouFragment extends BaseFragment {
    private static final String TAG = "TngouFragment";

    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    @Bind(R.id.viewpager)
    ViewPager viewpager;

    private List<BaseFragment> fragmentList = new ArrayList<>();
    TngouPicViewPagerAdapter pagerAdapter;

//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//
//        View view = inflater.inflate(R.layout.fragment_tg_pic, container, false);
//        ButterKnife.bind(this, view);
//        return view;
//
//    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_tg_pic;
    }

    @Override
    public void getBundleDatas(Bundle bundle) {

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

//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        ButterKnife.unbind(this);
//    }


    /**
     * 获取顶部列表信息
     */
    public void getTypeList() {
        RxUtil.subscribeAll(new Func1<String, Observable<GalleryTypeRespBean>>() {
            @Override
            public Observable<GalleryTypeRespBean> call(String s) {
                return ReUtil.getApiManager(AppContants.TNGOU_HTTP).getGalleryTypeList();
            }
        }, new MySubscriber<GalleryTypeRespBean>() {
            @Override
            public void onCompleted() {
                super.onCompleted();
                dlg.dismiss();
            }

            @Override
            public void onStart() {
                dlg.show();
            }

            @Override
            public void onNext(GalleryTypeRespBean o) {
                initTabView(o);
//                存入数据库
                SQLiteDatabase database = BaseApplication.getInstantce().getTngouDbHelper().getWritableDatabase();
                database.execSQL("replace into tngouType(date,json) values (" + AppContants.LATEST_COLUMN + ",'" + getGson().toJson(o, GalleryTypeRespBean.class) + "')");
                L.e(TAG,"存入顶部信息列表数据   replace into tngouType(date,json) values (" + AppContants.LATEST_COLUMN + ",'" + getGson().toJson(o, GalleryTypeRespBean.class) + "')");

                database.close();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            //   从数据库中获取缓存json
                SQLiteDatabase database =BaseApplication.getInstantce().getTngouDbHelper().getReadableDatabase();
                Cursor cursor = database.rawQuery("select * from tngouType where date = " + AppContants.LATEST_COLUMN, null);
                L.e(TAG," 从数据库中获取顶部信息列表缓存sql:       "+"select * from tngouType where date = " + AppContants.LATEST_COLUMN);
                if (cursor.moveToFirst()) {

                    String json = cursor.getString(cursor.getColumnIndex("json"));
                    L.e(TAG,"从数据库中获取顶部信息列表缓存json:    "+json);
                    initTabView(getGson().fromJson(json, GalleryTypeRespBean.class));
                }
                cursor.close();
                database.close();
            }
        });
    }

    private void initTabView(GalleryTypeRespBean listRespBean) {
        L.e(TAG,"init tab view------");
        List<String> list = getTabTitle(listRespBean);

        for (GalleryTypeBean bean : listRespBean.getTngouList()) {
            fragmentList.add(TgPicListFragment.startFragment(bean.getId(), bean.getTitle()));
        }

        pagerAdapter = new TngouPicViewPagerAdapter(getActivity().getSupportFragmentManager(), fragmentList, list);
        viewpager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewpager);
    }

    private List<String> getTabTitle(GalleryTypeRespBean o) {
        List<String> list = new ArrayList<>();
        if (!o.getTngouList().isEmpty()) {
            for (GalleryTypeBean bean : o.getTngouList()) {
                list.add(bean.getTitle());
            }
        }
        return list;
    }
}
