package com.gyz.androiddevelope.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * @author: guoyazhou
 * @date: 2016-03-08 15:52
 */
public abstract class BaseFragment extends Fragment {
    private static final String TAG = "BaseFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
}
