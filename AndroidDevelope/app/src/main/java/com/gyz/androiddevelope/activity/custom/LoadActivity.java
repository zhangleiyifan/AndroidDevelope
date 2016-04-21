package com.gyz.androiddevelope.activity.custom;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.gyz.androiddevelope.R;
import com.gyz.androiddevelope.activity.HomeActivity;
import com.gyz.androiddevelope.base.BaseActivity;
import com.gyz.androiddevelope.engine.AppContants;
import com.gyz.androiddevelope.response_bean.LoadImageBean;
import com.gyz.androiddevelope.retrofit.ReUtil;
import com.gyz.androiddevelope.retrofit.RxUtil;
import com.gyz.androiddevelope.util.FileUtil;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * @author: guoyazhou
 * @date: 2016-01-26 11:46
 */
public class LoadActivity extends BaseActivity {
    private static final String TAG = "LoadActivity";

    @Bind(R.id.layoutStartImg)
    ImageView layoutStartImg;
    File file;

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        View view = View.inflate(this, R.layout.activity_load, null);
        setContentView(view);
        ButterKnife.bind(this);
        getSwipeBackLayout().setEnableGesture(false);

        file = new File(AppContants.CACHE_PATH, "start.jpg");

        if (file.exists()) {
            layoutStartImg.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
        } else {
            layoutStartImg.setImageResource(R.mipmap.ic_load);
        }

        ScaleAnimation animation = new ScaleAnimation(1.0f, 1.4f, 1.0f, 1.4f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(3000);
        animation.setFillAfter(true);
        view.startAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                RxUtil.subscribeAll(new Func1<String, Observable<LoadImageBean>>() {
                    @Override
                    public Observable<LoadImageBean> call(String s) {
                        return ReUtil.getApiManager(true).getLoadImg();
                    }
                }, new Subscriber<LoadImageBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        startActivity();
                    }

                    @Override
                    public void onNext(final LoadImageBean loadImageBean) {

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    FileUtil.saveMyBitmap(file, Picasso.with(LoadActivity.this).load(loadImageBean.img).get());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                        }).start();


                        startActivity();
                    }
                });

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    @Override
    protected void loadData() {

    }

    private void startActivity() {
        startActivity(new Intent(getBaseContext(), HomeActivity.class));
        finish();
    }
}
