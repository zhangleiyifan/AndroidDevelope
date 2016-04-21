package com.gyz.androiddevelope.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gyz.androiddevelope.db.CacheDbHelper;
import com.gyz.androiddevelope.engine.AppContants;

/**
 * @author: guoyazhou
 * @date: 2016-03-08 15:52
 */
public abstract class BaseFragment extends Fragment {
    private static final String TAG = "BaseFragment";
    private CacheDbHelper cacheDbHelper;
    public Context context;
    private Gson gson;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context= getActivity();
        cacheDbHelper = new CacheDbHelper(context, AppContants.DATABASE_VERSION);
        gson = new GsonBuilder().create();
        initView();
        initData();
    }

    /**
     * 初始化界面
     */
    public abstract void initView();

    /**
     * 初始化数据
     */
    public abstract  void  initData();

    public abstract String getTitle();

    public CacheDbHelper getCacheDbHelper(){
        return cacheDbHelper;
    }

    public Gson getGson(){
        return gson;
    }
}
