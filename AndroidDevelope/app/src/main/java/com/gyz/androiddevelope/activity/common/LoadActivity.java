package com.gyz.androiddevelope.activity.common;

import android.os.Bundle;
import android.os.Handler;

import com.gyz.androiddevelope.R;
import com.gyz.androiddevelope.activity.MainActivity;
import com.gyz.androiddevelope.activity.account.LoginActivity;
import com.gyz.androiddevelope.base.BaseActivity;
import com.gyz.androiddevelope.engine.AppContants;
import com.gyz.androiddevelope.engine.User;
import com.gyz.androiddevelope.util.SPUtils;

/**
 * @author: guoyazhou
 * @date: 2016-01-26 11:46
 */
public class LoadActivity extends BaseActivity {
    private static final String TAG = "LoadActivity";

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        setContentView(R.layout.activity_load);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if ((boolean) SPUtils.get(LoadActivity.this, AppContants.FIRST_LOAD, false)) {

                    SPUtils.put(LoadActivity.this, AppContants.FIRST_LOAD, true);
                    LoginActivity.startActivity(LoadActivity.this, false);//或者跳引导页
                } else {
                    if (User.getInstantce().isLogin()) {
                        MainActivity.startActivity(LoadActivity.this);
                    } else {
                        LoginActivity.startActivity(LoadActivity.this, false);
                    }
                }

                finish();
            }
        }, 3000);


    }

    @Override
    protected void loadData() {

    }
}
