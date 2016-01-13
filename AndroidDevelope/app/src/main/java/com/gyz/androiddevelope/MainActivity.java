package com.gyz.androiddevelope;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.gyz.androiddevelope.base.BaseActivity;
import com.gyz.androiddevelope.bean.WeatherInfo;
import com.gyz.androiddevelope.net.RequestCallback;
import com.gyz.androiddevelope.net.RequestParams;
import com.gyz.androiddevelope.service.RemoteService;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {


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
        ButterKnife.bind(this);
    }

    @Override
    protected void loadData() {

    }

    @OnClick(R.id.btnOnClick)
    public void OnClick() {
        //网络操作.
        List<RequestParams> paramses = new ArrayList<>();
        paramses.add(new RequestParams("cityId", "111"));
        paramses.add(new RequestParams("cityName", "Beijing"));

        RemoteService.getInstance().invoke(this, "getWeatherInfo", paramses, new RequestCallback() {
            @Override
            public void onSuccess(String result) {

                WeatherInfo info = JSON.parseObject(result, WeatherInfo.class);
                txtInfo.setText(info.getCity());
            }

            @Override
            public void onFail(String errorMessage) {
                Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

    }

}
