package com.gyz.androiddevelope.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import butterknife.ButterKnife;

/**
 * @author: guoyazhou
 * @date: 2016-03-08 15:52
 */
public abstract class BaseFragment extends Fragment {
    private static final String TAG = "BaseFragment";
    public Context context;
    private Gson gson;
    protected ProgressDialog dlg;

    protected View mRootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (null == mRootView) {
            mRootView = inflater.inflate(getLayoutId(), null);
        }
        ButterKnife.bind(this, mRootView);
        return mRootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context= getActivity();
        gson = new GsonBuilder().create();

        dlg = new ProgressDialog(context);
        initView();
        initData();
    }

    /**
     *
     * @return 获取布局文件id
     */
    public abstract int getLayoutId();
    /**
     * 初始化界面
     */
    public abstract void initView();

    /**
     * 初始化数据
     */
    public abstract  void  initData();

    public abstract String getTitle();

    public Gson getGson(){
        return gson;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (dlg!=null){
            dlg.dismiss();
        }
    }
}
