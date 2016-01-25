package com.gyz.androiddevelope.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.anthonycr.grant.PermissionsManager;
import com.anthonycr.grant.PermissionsResultAction;
import com.gyz.androiddevelope.R;
import com.gyz.androiddevelope.base.BaseActivity;
import com.gyz.androiddevelope.bean.WeatherInfo;
import com.gyz.androiddevelope.cache.CacheManager;
import com.gyz.androiddevelope.engine.AppContants;
import com.gyz.androiddevelope.engine.User;
import com.gyz.androiddevelope.net.RequestParams;
import com.gyz.androiddevelope.service.RemoteService;
import com.gyz.androiddevelope.util.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {


    public static final int GO_TO_INFO = 3001;

    @Bind(R.id.btnOnClick)
    Button btnOnClick;
    @Bind(R.id.txtInfo)
    TextView txtInfo;

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        setContentView(R.layout.activity_main);


        if (!PermissionsManager.hasAllPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS})) {
//            没有权限，申请权限
            PermissionsManager.getInstance().requestAllManifestPermissionsIfNecessary(this, new PermissionsResultAction() {

                @Override
                public void onGranted() {
                    CacheManager.getInstance().initCacheDir();
                }

                @Override
                public void onDenied(String permission) {
                    Toast.makeText(MainActivity.this, "拒绝", Toast.LENGTH_SHORT).show();
                }
            });

        } else {

            CacheManager.getInstance().initCacheDir();
        }


        ButterKnife.bind(this);
        dlg = Utils.createProgressDialog(this,
                this.getString(R.string.str_loading));
    }

    @Override
    protected void loadData() {

    }

    @OnClick({R.id.btnOnClick, R.id.btnGo})
    public void OnClick(View view) {

        switch (view.getId()) {
            case R.id.btnOnClick:

                dlg.show();

                //网络操作.
                List<RequestParams> paramses = new ArrayList<>();
                paramses.add(new RequestParams("cityId", "111"));
                paramses.add(new RequestParams("cityName", "Beijing"));

                RemoteService.getInstance().invoke(this, "getWeatherInfo", paramses, new AbstractRequestCallback() {
                    @Override
                    public void onSuccess(String result) {

                        Log.v(TAG, "回调onSuccess==result" + result);
                        dlg.dismiss();
                        WeatherInfo info = JSON.parseObject(result, WeatherInfo.class);
                        txtInfo.setText(info.getCity());
                    }

                });

                break;

            case R.id.btnGo:

                if (User.getInstantce().isLogin()) {
                    ShowInfoActivity.startActivity(MainActivity.this);
                } else {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    intent.putExtra(AppContants.NEED_CALLBACK, true);
                    startActivityForResult(intent, GO_TO_INFO);
                }

                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case GO_TO_INFO:

                    ShowInfoActivity.startActivity(MainActivity.this);
                    break;
            }
        }

    }
}
