package com.gyz.androiddevelope.activity.custom;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Button;

import com.gyz.androiddevelope.R;
import com.gyz.androiddevelope.base.BaseActivity;
import com.gyz.androiddevelope.view.HorizontalProgressBar;
import com.gyz.androiddevelope.view.LoveLayout;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @version V1.0
 * @FileName: com.gyz.androiddevelope.activity.custom.MyProgressBarActivity.java
 * @author: ZhaoHao
 * @date: 2016-05-18 16:09
 */
public class MyProgressBarActivity extends BaseActivity {
    private static final String TAG = "MyProgressBarActivity";
    @Bind(R.id.progress)
    HorizontalProgressBar progress;
    @Bind(R.id.btn)
    Button btn;
    @Bind(R.id.loveLayout)
    LoveLayout loveLayout;

    Timer timer;
    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        setContentView(R.layout.activity_my_progress_bar);
        ButterKnife.bind(this);
    }

    @Override
    protected void loadData() {
        mHandler.sendEmptyMessage(MSG_UPDATE);

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                loveLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        loveLayout.addHeart();
                    }
                });
            }
        }, 100, 200);
    }

    private static final int MSG_UPDATE = 23;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int pro = progress.getProgress();
            progress.setProgress(++pro);
            if (pro > 100) {
                mHandler.removeMessages(MSG_UPDATE);
            } else {
                mHandler.sendEmptyMessageDelayed(MSG_UPDATE, 100);
            }
        }
    };

    @OnClick(R.id.btn)
    public void onClick() {

    }

    @Override
    protected void onDestroy() {
        timer.cancel();
        super.onDestroy();
    }
}

