package com.gyz.androiddevelope.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.gyz.androiddevelope.R;
import com.gyz.androiddevelope.activity.account.LoginActivity;
import com.gyz.androiddevelope.base.BaseActivity;
import com.gyz.androiddevelope.bean.Axiba;
import com.gyz.androiddevelope.bean.WeatherInfo;
import com.gyz.androiddevelope.engine.AppContants;
import com.gyz.androiddevelope.engine.RemoteService;
import com.gyz.androiddevelope.engine.User;
import com.gyz.androiddevelope.net.RequestParams;
import com.gyz.androiddevelope.net.okhttp.OkHttpClientManager;
import com.gyz.androiddevelope.retrofit.ApiManagerService;
import com.gyz.androiddevelope.retrofit.RxUtil;
import com.gyz.androiddevelope.util.L;
import com.gyz.androiddevelope.util.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

public class MainActivity extends BaseActivity {


    public static final int GO_TO_INFO = 3001;

    @Bind(R.id.btnOnClick)
    Button btnOnClick;
    @Bind(R.id.txtInfo)
    TextView txtInfo;

    @Override
    protected void initVariables() {

    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        dlg = Utils.createProgressDialog(this,
                this.getString(R.string.str_loading));
    }

    @Override
    protected void loadData() {

    }

    @OnClick({R.id.btnOnClick, R.id.btnGo, R.id.btnOkHttp, R.id.btnOkHttp3, R.id.view, R.id.retrofit})
    public void OnClick(View view) {

        switch (view.getId()) {

            case R.id.retrofit:

//                Retrofit retrofit = new Retrofit.Builder().baseUrl("http://www.weather.com.cn")
//                        .addConverterFactory(GsonConverterFactory.create()).build();
//                ApiManagerService service = retrofit.create(ApiManagerService.class);
//
//                Call call = service.getWeather("111", "Beijing");
//
//                call.enqueue(new Callback() {
//                    @Override
//                    public void onResponse(Response response) {
//                        Axiba axiba = (Axiba) response.body();
//                        L.d(TAG, "axiba==" + axiba.getWeatherinfo().getCity() + "    |   " + axiba.getWeatherinfo().getTime());
//                    }
//
//                    @Override
//                    public void onFailure(Throwable t) {
//                        t.printStackTrace();
//                    }
//                });

                RxUtil.subscribe("", new Func1<String, Observable<Axiba>>() {
                    @Override
                    public Observable<Axiba> call(String s) {

                        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://www.weather.com.cn/")
                                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                                .addConverterFactory(GsonConverterFactory.create()).build();
                        ApiManagerService service = retrofit.create(ApiManagerService.class);

                        return  service.getWeather("111", "Beijing");

                    }
                }, new Subscriber<Axiba>() {
                    @Override
                    public void onCompleted() {
                        L.e(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Axiba axiba) {
                        L.d(TAG, "axiba==" + axiba.getWeatherinfo().getCity() + "    |   " + axiba.getWeatherinfo().getTime());
                    }
                });



//                Observable.just("").flatMap(new Func1<String, Observable<Axiba>>() {
//                    @Override
//                    public Observable<Axiba> call(String s) {
//
//                        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://www.weather.com.cn/")
//                                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                        .addConverterFactory(GsonConverterFactory.create()).build();
//                         ApiManagerService service = retrofit.create(ApiManagerService.class);
//
//                        return  service.getWeather("111", "Beijing");
//
//                    }
//                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Axiba>() {
//                    @Override
//                    public void onCompleted() {
//                        L.e(TAG, "onCompleted");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        e.printStackTrace();
//                    }
//
//                    @Override
//                    public void onNext(Axiba axiba) {
//                        L.d(TAG, "axiba==" + axiba.getWeatherinfo().getCity() + "    |   " + axiba.getWeatherinfo().getTime());
//                    }
//                });




//
//                Observable.just("").map(new Func1<String, Axiba>() {
//                    @Override
//                    public Axiba call(String s) {
//
//                        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://www.weather.com.cn").addConverterFactory(GsonConverterFactory.create()).build();
//                        ApiManagerService service = retrofit.create(ApiManagerService.class);
//
//                        Call<Axiba> call = service.getWeather("111", "Beijing");
//
//                        Axiba axiba = null;
//                        try {
//                            axiba = call.execute().body();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        return axiba;
//                    }
//                })
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(new Subscriber<Axiba>() {
//                            @Override
//                            public void onCompleted() {
//                                L.e(TAG, "onCompleted");
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                                e.printStackTrace();
//                            }
//
//                            @Override
//                            public void onNext(Axiba axiba) {
//                                L.d(TAG, "axiba==" + axiba.getWeatherinfo().getCity() + "    |   " + axiba.getWeatherinfo().getTime());
//                            }
//                        });

                break;

            case R.id.view:

                startActivity(new Intent(MainActivity.this, CircleActivity.class));
                break;
            case R.id.btnOkHttp3:


                OkHttpClientManager.Param[] params = new OkHttpClientManager.Param[2];

                OkHttpClientManager.Param param = new OkHttpClientManager.Param("cityId", "111");
                OkHttpClientManager.Param param1 = new OkHttpClientManager.Param("cityName", "Beijing");

                params[0] = param;
                params[1] = param1;

                OkHttpClientManager.postAsyn("http://www.weather.com.cn/data/sk/101010100.html"
                        , params, new OkHttpClientManager.ResultCallback<Axiba>() {
                    @Override
                    public void onError(Request request, Exception e) {

                        Log.e(TAG, "request=" + request + "-----e=" + e);
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Axiba response) {
                        Log.e(TAG, "response=" + response.getWeatherinfo().getCity() + "   " + response.getWeatherinfo().getWD());

                    }
                }, null);
                break;

            case R.id.btnOkHttp:

                OkHttpClientManager.Param[] params2 = new OkHttpClientManager.Param[2];

                OkHttpClientManager.Param param2 = new OkHttpClientManager.Param("cityId", "111");
                OkHttpClientManager.Param param12 = new OkHttpClientManager.Param("cityName", "Beijing");

                params2[0] = param2;
                params2[1] = param12;

                OkHttpClientManager.postAsyn("http://www.weather.com.cn/data/sk/101010100.html", params2,
                        new OkHttpClientManager.ResultCallback<Axiba>() {
                            @Override
                            public void onError(Request request, Exception e) {
                                Log.e(TAG, "request=" + request + "-----e=" + e);
                                e.printStackTrace();

                            }

                            @Override
                            public void onResponse(Axiba response) {
                                Log.e(TAG, "response=" + response.getWeatherinfo().getCity() + "   " + response.getWeatherinfo().getWD());
                            }
                        }
                );
                break;

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
