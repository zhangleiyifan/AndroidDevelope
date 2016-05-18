package com.gyz.androiddevelope.activity.custom;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.gyz.androiddevelope.R;
import com.gyz.androiddevelope.base.BaseActivity;
import com.gyz.androiddevelope.view.HorizontalProgressBar;

import butterknife.Bind;
import butterknife.ButterKnife;

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
    }
    private static final int MSG_UPDATE = 23;
 private Handler mHandler = new Handler(){
     @Override
     public void handleMessage(Message msg) {
         int pro = progress.getProgress();
         progress.setProgress(++pro);
         if (pro>100){
            mHandler.removeMessages(MSG_UPDATE);
         }
         else {
             mHandler.sendEmptyMessageDelayed(MSG_UPDATE,100);
         }
     }
 };
}
