package com.gyz.androiddevelope.activity.custom;

import android.os.Bundle;

import com.gyz.androiddevelope.R;
import com.gyz.androiddevelope.base.BaseActivity;
import com.gyz.androiddevelope.view.ExperienceProgress;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author: guoyazhou
 * @date: 2016-02-03 14:22
 */
public class CircleActivity extends BaseActivity {
    private static final String TAG = "CircleActivity";
    @Bind(R.id.lvProgress)
    ExperienceProgress lvProgress;

    protected void initVariables() {

    }

    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_circle);
        ButterKnife.bind(this);
    }

    protected void loadData() {

        lvProgress.setLvValue(1839);


    }

}
