package com.gyz.androiddevelope.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.gyz.androiddevelope.R;
import com.gyz.androiddevelope.base.BaseActivity;
import com.gyz.androiddevelope.base.BaseApplication;
import com.gyz.androiddevelope.engine.AppContants;
import com.gyz.androiddevelope.util.SPUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author: guoyazhou
 * @date: 2016-04-26 18:20
 */
public class SettingsActivity extends BaseActivity {
    private static final String TAG = "SettingsActivity";
    @Bind(R.id.checkboxPic)
    CheckBox checkboxPic;

    public static void startActivity() {
        Intent intent = new Intent(BaseApplication.getInstantce(), SettingsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        BaseApplication.getInstantce().startActivity(intent);

    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);

        boolean boo = (Boolean) SPUtils.get(getApplicationContext(),AppContants.SP_LOAD_PIC_BY_MOBILE_NET,true);
        checkboxPic.setChecked(boo);
    }

    @Override
    protected void loadData() {

    }


    @OnClick(R.id.checkboxPic)
    public void onClick(View view) {

        SPUtils.put(getApplicationContext(), AppContants.SP_LOAD_PIC_BY_MOBILE_NET, checkboxPic.isChecked());
    }
}
