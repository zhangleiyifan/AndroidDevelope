package com.gyz.androiddevelope.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.gyz.androiddevelope.R;
import com.gyz.androiddevelope.util.DensityUtils;
import com.gyz.androiddevelope.util.L;

/**
 * @author: guoyazhou
 * @date: 2016-03-10 11:17
 */
public class DynamicWave extends View {

    private static final String TAG = "DynamicWave";

    private Context context;
    private Paint mWavePaint, mCirclePaint;

    private int mTotalWidth, mTotalHeight, mCenterX, mCenterY, mRadiu;

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

    //    水位百分比
    private int percent = 0;

    //调节水位上升的系数（逐渐递增）
    float per;


    /**
     * 重力感应X轴 Y轴 Z轴的重力值
     **/
    private float mGX = 0;
    private float mGY = 0;
    private float mGZ = 0;

    Bitmap bmp;

    private float rotateAngle = 0f;

    private RefreshProgressRunnable refreshProgressRunnable;

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
        mCirclePaint.setColor(context.getResources().getColor(R.color.color_fec500));

        bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.img_cover);

    }

    public void setProcess(int percent) {

        this.percent = percent;
        invalidate();

    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        L.d(TAG, "onSizeChanged...............");
        mTotalWidth = w;
        mTotalHeight = h;
        mCenterX = mTotalWidth / 2;
        mCenterY = mTotalHeight / 2;

        if (mCenterX > mCenterY) {
            mRadiu = mCenterY;
        } else {
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
        canvas.save();
        canvas.rotate(rotateAngle, mCenterX, mCenterY);

        for (int i = 0; i < mTotalWidth; i++) {
            if (per < ((float) percent / 100)) {
                //水位逐渐上涨
                canvas.drawLine(i, mTotalHeight - mResetOneYPositions[i] - (mTotalHeight * per), i, mTotalHeight, mWavePaint);
                canvas.drawLine(i, mTotalHeight - mResetTwoYPositions[i] - (mTotalHeight * per), i, mTotalHeight, mWavePaint);
            } else {
                canvas.drawLine(i, mTotalHeight - mResetOneYPositions[i] - (mTotalHeight * (float) percent / 100), i, mTotalHeight, mWavePaint);
                canvas.drawLine(i, mTotalHeight - mResetTwoYPositions[i] - (mTotalHeight * (float) percent / 100), i, mTotalHeight, mWavePaint);
            }
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
        canvas.restore();
        drawImage(canvas, bmp, 0, 0, mTotalWidth, mTotalHeight, 0, 0);

    }

       /*---------------------------------
     * 绘制图片
     * @param       x屏幕上的x坐标
     * @param       y屏幕上的y坐标
     * @param       w要绘制的图片的宽度
     * @param       h要绘制的图片的高度
     * @param       bx图片上的x坐标
     * @param       by图片上的y坐标
     *
     * @return      null
     ------------------------------------*/

    private void drawImage(Canvas canvas, Bitmap blt, int x, int y,
                           int w, int h, int bx, int by) {
        Rect src = new Rect();// 图片 >>原矩形
        Rect dst = new Rect();// 屏幕 >>目标矩形

        src.left = bx;
        src.top = by;
        src.right = bx + w;
        src.bottom = by + h;

        dst.left = x;
        dst.top = y;
        dst.right = x + w;
        dst.bottom = y + h;
        // 画出指定的位图，位图将自动--》缩放/自动转换，以填补目标矩形
        // 这个方法的意思就像 将一个位图按照需求重画一遍，画后的位图就是我们需要的了
        canvas.drawBitmap(blt, null, dst, null);
        src = null;
        dst = null;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        refreshProgressRunnable = new RefreshProgressRunnable();
        post(refreshProgressRunnable);

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeCallbacks(refreshProgressRunnable);
        bmp.recycle();
        L.d(TAG, "removeCallbacks...");
    }

    private class RefreshProgressRunnable implements Runnable {

        @Override
        public void run() {

            synchronized (DynamicWave.this) {
                per = per + 0.01f;
                invalidate();
                postDelayed(this, 15);
            }
        }
    }

    private void resetPositionY() {

        // mXOneOffset代表当前第一条水波纹要移动的距离
        int yOneInterval = mYPositions.length - mXOneOffset;
        // 使用System.arraycopy方式重新填充第一条波纹的数据
        System.arraycopy(mYPositions, mXOneOffset, mResetOneYPositions, 0, yOneInterval);
        System.arraycopy(mYPositions, 0, mResetOneYPositions, yOneInterval, mXOneOffset);

        int yTwoInterval = mYPositions.length - mXTwoOffset;
        System.arraycopy(mYPositions, mXTwoOffset, mResetTwoYPositions, 0, yTwoInterval);
        System.arraycopy(mYPositions, 0, mResetTwoYPositions, yTwoInterval, mXTwoOffset);
    }


}
