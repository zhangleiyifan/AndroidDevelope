package com.gyz.androiddevelope.activity.common;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import com.gyz.androiddevelope.R;
import com.gyz.androiddevelope.activity.HomeActivity;
import com.gyz.androiddevelope.base.BaseActivity;

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

        View view = View.inflate(this, R.layout.activity_load, null);
        setContentView(view);

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
                startActivity(new Intent(getBaseContext(), HomeActivity.class));
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    protected void loadData() {

    }
}
