package com.gyz.androiddevelope.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @version V1.0
 * @FileName: com.gyz.androiddevelope.fragment.TransformFragment.java
 * @author: ZhaoHao
 * @date: 2016-05-23 16:20
 */
public class TransformFragment extends Fragment {
    private static final String TAG = "TransformFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(getArguments().getInt("layoutId"), null);
    }
}
