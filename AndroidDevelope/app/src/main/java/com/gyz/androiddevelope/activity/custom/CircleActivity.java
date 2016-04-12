package com.gyz.androiddevelope.activity.custom;

import android.os.Bundle;
import android.os.Handler;

import com.gyz.androiddevelope.R;
import com.gyz.androiddevelope.base.BaseActivity;
import com.gyz.androiddevelope.view.LvProgress;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author: guoyazhou
 * @date: 2016-02-03 14:22
 */
public class CircleActivity extends BaseActivity {
    private static final String TAG = "CircleActivity";
    @Bind(R.id.lvProgress)
    LvProgress lvProgress;

    protected void initVariables() {

    }

    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_circle);
        ButterKnife.bind(this);
    }

    protected void loadData() {


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                String[] Ls = getResources().getStringArray(R.array.lvs);
                lvProgress.setValue(4000, 500, "V3", Arrays.asList(Ls));
            }
        }, 100);


    }

}
