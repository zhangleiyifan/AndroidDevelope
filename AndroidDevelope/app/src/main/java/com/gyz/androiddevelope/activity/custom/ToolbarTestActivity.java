package com.gyz.androiddevelope.activity.custom;

import android.os.Bundle;

import com.gyz.androiddevelope.R;
import com.gyz.androiddevelope.base.BaseToolbarActivity;

/**
 * @version V1.0
 * @FileName: com.gyz.androiddevelope.activity.custom.ToolbarTestActivity.java
 * @author: ZhaoHao
 * @date: 2016-05-26 16:19
 */
public class ToolbarTestActivity extends BaseToolbarActivity {
    private static final String TAG = "ToolbarTestActivity";


    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        setContentView(R.layout.activity_mountain);
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void onResume() {

        super.onResume();
        toolbar.setTitle("sddffdsfds");
    }
}
