package com.gyz.androiddevelope.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Region;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.gyz.androiddevelope.R;
import com.gyz.androiddevelope.util.DensityUtils;

/**
 * @author: guoyazhou
 * @date: 2016-03-10 11:17
 */
public class DynamicWave extends View implements SensorEventListener {

    private static final String TAG = "DynamicWave";

    private Context context;
    private Paint mWavePaint,mCirclePaint;

    private int mTotalWidth, mTotalHeight,mCenterX,mCenterY,mRadiu;

    // 第一条水波移动速度  dp
    private static final int TRANSLATE_X_SPEED_ONE = 7;
    // 第二条水波移动速度  dp
    private static final int TRANSLATE_X_SPEED_TWO = 5;
    //    y = Asin(wx+b)+h ，这个公式里：w影响周期，A影响振幅，h影响y位置，b为初相；
    private static final float STRETCH_FACTOR_A = 20;
    private static final int OFFSET_Y = 0;
    // 周期
    private float mCycleFactorW;
    // 用于保存原始波纹的y值
    private float[] mYPositions;
    private float[] mResetOneYPositions;
    private float[] mResetTwoYPositions;
    //    移动速度px
    private int mXOffsetSpeedOne;
    private int mXOffsetSpeedTwo;
    private int mXOneOffset;//当前第一条水波纹要移动的距离
    private int mXTwoOffset;//当前第2条水波纹要移动的距离

    private float percent =1f;

    public DynamicWave(Context context) {
        this(context, null);
    }

    public DynamicWave(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DynamicWave(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {

        mXOffsetSpeedOne = DensityUtils.dp2px(context, TRANSLATE_X_SPEED_ONE);
        mXOffsetSpeedTwo = DensityUtils.dp2px(context, TRANSLATE_X_SPEED_TWO);

        mWavePaint = new Paint();
        mWavePaint.setStyle(Paint.Style.FILL);
        mWavePaint.setAntiAlias(true);
        mWavePaint.setColor(context.getResources().getColor(R.color.colorPrimaryDark));

        mCirclePaint = new Paint();
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setColor(context.getResources().getColor(R.color.colorWhite));
    }

    public void setProcess(float percent){
        this.percent = percent;
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTotalWidth = w;
        mTotalHeight = h;
        mCenterX = mTotalWidth/2;
        mCenterY = mTotalHeight/2;

        if (mCenterX>mCenterY){
            mRadiu = mCenterY;
        }else {
            mRadiu = mCenterX;
        }

        mYPositions = new float[mTotalWidth];
        mResetOneYPositions = new float[mTotalWidth];
        mResetTwoYPositions = new float[mTotalWidth];

        // 将周期定为view总宽度
        mCycleFactorW = (float) (2 * Math.PI / mTotalWidth);
        for (int i = 0; i < mTotalWidth; i++) {
            mYPositions[i] = (float) (STRETCH_FACTOR_A * Math.sin(mCycleFactorW * i) + OFFSET_Y);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        resetPositionY();

        for (int i = 0; i < mTotalWidth; i++) {
            canvas.drawLine(i, mTotalHeight - mResetOneYPositions[i] -(mTotalHeight*percent), i, mTotalHeight, mWavePaint);
            canvas.drawLine(i, mTotalHeight - mResetTwoYPositions[i] -(mTotalHeight*percent) , i, mTotalHeight, mWavePaint);
        }
        // 改变两条波纹的移动点
        mXOneOffset += mXOffsetSpeedOne;
        mXTwoOffset += mXOffsetSpeedTwo;
        // 如果已经移动到结尾处，则重头记录
        if (mXOneOffset > mTotalWidth) {
            mXOneOffset = 0;
        }
        if (mXTwoOffset > mTotalWidth) {
            mXTwoOffset = 0;
        }

        canvas.save();
        canvas.clipRect(0,0,100,100, Region.Op.XOR);
        canvas.restore();

        invalidate();
    }

    private void resetPositionY() {

        // mXOneOffset代表当前第一条水波纹要移动的距离
        int yOneInterval = mYPositions.length - mXOneOffset;
        // 使用System.arraycopy方式重新填充第一条波纹的数据
        System.arraycopy(mYPositions, mXOneOffset, mResetOneYPositions, 0, yOneInterval);
        System.arraycopy(mYPositions, 0, mResetOneYPositions, yOneInterval, mXOneOffset);

        int yTwoInterval = mYPositions.length - mXTwoOffset;
        System.arraycopy(mYPositions, mXTwoOffset, mResetTwoYPositions, 0,yTwoInterval);
        System.arraycopy(mYPositions, 0, mResetTwoYPositions, yTwoInterval, mXTwoOffset);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {



    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
