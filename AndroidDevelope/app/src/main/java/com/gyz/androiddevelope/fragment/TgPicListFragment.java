package com.gyz.androiddevelope.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gyz.androiddevelope.R;
import com.gyz.androiddevelope.base.BaseFragment;

/**
 * @author: guoyazhou
 * @date: 2016-04-21 15:33
 */
public class TgPicListFragment extends BaseFragment {
    private static final String TAG = "TgPicListFragment";

    private int typeId = 1;
    private String title;

    public TgPicListFragment(int id,String title) {
        super();
        this.typeId = id;
        this.title = title;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_load,container,false);
        return view;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public String getTitle() {
        return title;
    }
}
