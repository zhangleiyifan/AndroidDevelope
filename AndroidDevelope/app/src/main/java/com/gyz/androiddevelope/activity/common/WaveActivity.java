package com.gyz.androiddevelope.activity.common;

import android.os.Bundle;
import android.view.animation.RotateAnimation;

import com.gyz.androiddevelope.R;
import com.gyz.androiddevelope.base.BaseActivity;
import com.gyz.androiddevelope.util.WaveHelper;
import com.gyz.androiddevelope.view.DynamicWave;
import com.gyz.androiddevelope.view.WaveView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author: guoyazhou
 * @date: 2016-03-10 12:00
 */
public class WaveActivity extends BaseActivity{
    private static final String TAG = "WaveActivity";
    @Bind(R.id.dynamicWave)
    DynamicWave dynamicWave;
    @Bind(R.id.waveView)
    WaveView waveView;

    RotateAnimation animation;

    int rotateAngle = 0;
    private WaveHelper mWaveHelper;
    @Override
    protected void initVariables() {
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_wave);
        ButterKnife.bind(this);

        /**得到SensorManager对象**/
//        SensorManager mSensorMgr = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
//        Sensor mSensor = mSensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        // 注册listener，第三个参数是检测的精确度
        //SENSOR_DELAY_FASTEST 最灵敏 因为太快了没必要使用
        //SENSOR_DELAY_GAME 游戏开发中使用
        //SENSOR_DELAY_NORMAL 正常速度
        //SENSOR_DELAY_UI 最慢的速度
//        mSensorMgr.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_GAME);


        mWaveHelper = new WaveHelper(waveView);
        waveView.postDelayed(new Runnable() {
            @Override
            public void run() {
                waveView.setWaveColor(getResources().getColor(R.color.colorPrimary), getResources().getColor(R.color.colorPrimaryDark));
                waveView.setBorder(0, R.color.colorWhite);
                waveView.registerSensorListener();
            }
        },3000);

    }

    @Override
    protected void loadData() {
        dynamicWave.setProcess(58);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mWaveHelper.cancel();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mWaveHelper.start();
    }

//    @Override
//    public void onSensorChanged(SensorEvent event) {
//        if (Sensor.TYPE_ACCELEROMETER != event.sensor.getType()) {
//            return;
//        }
//
//        float[] values = event.values;
//        float ax = values[0];
//        float ay = values[1];
//
//        double g = Math.sqrt(ax * ax + ay * ay);
//        double cos = ay / g;
//        if (cos > 1) {
//            cos = 1;
//        } else if (cos < -1) {
//            cos = -1;
//        }
//        double rad = Math.acos(cos);
//        if (ax < 0) {
//            rad = 2 * Math.PI - rad;
//        }
//
//        int uiRot = getWindowManager().getDefaultDisplay().getRotation();
//        double uiRad = Math.PI / 2 * uiRot;
//        rad -= uiRad;
//
//        if (Math.abs(rotateAngle - ((int) (180 * rad / Math.PI))) > 15) {
//
//            rotateAngle = (int) (180 * rad / Math.PI);
//            doAnimation(rotateAngle);
//            L.e(TAG, "rad==" + rotateAngle);
//
//        }
//
//
//    }
//
//    @Override
//    public void onAccuracyChanged(Sensor sensor, int accuracy) {
//
//    }
//
//    private float fromeDegrees;
//
//    private void doAnimation(float toDegree) {
//
//        animation = new RotateAnimation(fromeDegrees, toDegree, Animation.RELATIVE_TO_SELF,
//                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//
//        fromeDegrees = toDegree;
//
//        animation.setDuration(300);
//        animation.setFillAfter(true);
//
//
//        waveView.startAnimation(animation);
//    }

}
