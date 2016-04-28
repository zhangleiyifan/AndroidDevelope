package com.gyz.androiddevelope.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author: guoyazhou
 * @date: 2016-03-08 15:52
 */
public abstract class BaseFragment extends Fragment {
    private static final String TAG = "BaseFragment";
    public Context context;
    private Gson gson;
    protected ProgressDialog dlg;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    public void onDestroy() {
        super.onDestroy();
        if (dlg!=null){
            dlg.dismiss();
        }
    }
}
