package com.gyz.androiddevelope.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.gyz.androiddevelope.R;
import com.gyz.androiddevelope.util.DensityUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: guoyazhou
 * @date: 2016-04-11 10:23
 * 用户等级  动画进度条
 */
public class ExperienceProgress extends View {
    private static final String TAG = "LvProgress";
    //毫秒
    private static final int MSECONDS = 50;
    //    每次递增的坐标值
    private static final int RISE_PX = 15;

    //    等级每一段 线段长
    private float partX;

    private Paint paintBg, paintLv, paintLvTotal, paintMain;
    private Context context;
    private float width, height, bgStartX, bgStartY, bgStopX, bgStopY;
    // 蓝色经验值结束坐标   ，每次递增的坐标
    private float mainStopX, riseX;
    //经验值
    private int totalValue = 4000, lvValue = 0;
    // 用于显示的经验值
    private int lvForPaint;
    //数字每次递增值
    private float txtGrowNum;
    //    文字是否到屏幕边缘
    private boolean isToEnd;
    //    文字到屏幕边缘时，显示经验值的起始坐标
    private float TxtEndRiseX;

    private float lvHeightY;
    //    等级str   各等级经验值
    private List<String> lvs;
    private List<Integer> experValue;
    private String LV = "V0";
    private int currentLv;
    private float[] lvX = new float[9];

    public ExperienceProgress(Context context) {
        this(context, null);
    }

    public ExperienceProgress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExperienceProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {

//        lvs = Arrays.asList(context.getResources().getStringArray(R.array.lvs));
        experValue = new ArrayList<>();
        int[] a = context.getResources().getIntArray(R.array.experValue);
        for (int i : a) {
            experValue.add(i);
        }
//        experValue = Arrays.asList(context.getResources().getIntArray(R.array.experValue));

        paintBg = new Paint();
        paintBg.setStyle(Paint.Style.STROKE);
        paintBg.setStrokeCap(Paint.Cap.ROUND);
        paintBg.setStrokeWidth(13);
        paintBg.setAntiAlias(true);
        paintBg.setColor(context.getResources().getColor(R.color.color_cacaca));

        paintMain = new Paint();
        paintMain.setStyle(Paint.Style.STROKE);
        paintMain.setStrokeCap(Paint.Cap.ROUND);
        paintMain.setStrokeWidth(13);
        paintMain.setAntiAlias(true);
        paintMain.setColor(context.getResources().getColor(R.color.colorPrimaryDark));

        paintLv = new Paint();
        paintLv.setAntiAlias(true);
        paintLv.setTextSize(DensityUtils.dp2px(context, 13));
        paintLv.setColor(context.getResources().getColor(R.color.colorPrimaryDark));

        paintLvTotal = new Paint();
        paintLvTotal.setAntiAlias(true);
        paintLvTotal.setTextSize(DensityUtils.dp2px(context, 13));
        paintLvTotal.setColor(context.getResources().getColor(R.color.color_cacaca));

    }

    /**
     * 通过设置经验值  完成初始化设置操作
     * @param lvValue
     */
    public void setLvValue(int lvValue) {

        this.lvValue = lvValue;
        if (lvValue > experValue.get(10)) {
            currentLv = 10;
            this.lvValue = totalValue;
        } else {
            for (int i = 0; i < experValue.size(); i++) {
                if (experValue.get(i) > lvValue) {
                    currentLv = i - 1;
                    break;
                }
            }
        }
        this.LV = "V" + currentLv;
        this.lvs =  getLvs(currentLv);
        calculateSize();
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;

        bgStartX = DensityUtils.dp2px(context, 20);
        bgStartY = height / 2;
        bgStopX = width - DensityUtils.dp2px(context, 20);
        bgStopY = height / 2;
//        初始化递增X 处于起始X点
        riseX = bgStartX;
        lvHeightY = bgStartY * 3 / 2;

        calculateSize();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // draw bg
        canvas.drawLine(bgStartX, bgStartY, bgStopX, bgStopY, paintBg);

        // darw main color Line
        canvas.drawLine(bgStartX, bgStartY, riseX, bgStopY, paintMain);

        //draw lv text
        if (isToEnd) {
            canvas.drawText(lvForPaint + "", TxtEndRiseX, bgStartY / 2, paintLv);
            canvas.drawText("/" + totalValue, TxtEndRiseX + paintLv.measureText(lvForPaint + ""), bgStartY / 2, paintLvTotal);
        } else {
            canvas.drawText(lvForPaint + "", riseX, bgStartY / 2, paintLv);
            canvas.drawText("/" + totalValue, riseX + paintLv.measureText(lvForPaint + ""), bgStartY / 2, paintLvTotal);
        }

//        draw gray LV
        for (int i = 0; i < lvs.size(); i++) {
            canvas.drawText(lvs.get(i), bgStartX + i * partX - (paintLvTotal.measureText("V0") / 2), lvHeightY, paintLvTotal);
            lvX[i] = bgStartX + i * partX - (paintLvTotal.measureText("V0") / 2);
        }

//        //画蓝色lv
        for (int i = 0; i < lvs.size(); i++) {
            if (riseX >= (lvX[i] + paintLvTotal.measureText("V0") / 2)) {

                canvas.drawText(lvs.get(i), lvX[i], lvHeightY, paintLv);
                if (i > 0) {
                    canvas.drawText(lvs.get(i - 1), lvX[i - 1], lvHeightY, paintLvTotal);
                }
            }
        }


        postDelayed(runnable, MSECONDS);
        if (lvForPaint >= lvValue && riseX >= mainStopX) {
            removeCallbacks(runnable);
        }
    }

    private void calculateSize() {

        partX = (bgStopX - bgStartX) / (lvs.size() - 1);
        //计算边界坐标
        if (lvValue <= 0 || totalValue <= 0) {
            mainStopX = bgStartX;
        } else {
            //根据传入的等级数  来确定蓝色进度条的模糊位置
            int index = lvs.indexOf(LV);
            mainStopX = index * partX + bgStartX;

            if ((index > 0) && (index < (lvs.size() - 1))) {
//              通过比例的方式，算出超过等级多少坐标像素
                if ((lvValue - experValue.get(currentLv)) > 0)
                    mainStopX += (partX * (lvValue - experValue.get(currentLv))) / (experValue.get(currentLv + 1) - experValue.get(currentLv));
            } else if (index <= 0) {
                if (lvValue > 0)
                    mainStopX += (partX * lvValue) / experValue.get(currentLv + 1);
            }
        }
        //计算经验值数字每次递增的值
        if (lvValue > 0 && mainStopX != 0) {
            txtGrowNum = lvValue / ((mainStopX - riseX) / RISE_PX) + 1;
            if (txtGrowNum < 0)
                txtGrowNum = lvValue;
        }
    }


    Runnable runnable = new Runnable() {
        @Override
        public void run() {
//            蓝色进度条增加
            if ((riseX + RISE_PX) < mainStopX) {
                riseX += RISE_PX;
            } else {
                riseX = mainStopX;
            }

//            经验值增加
            if ((lvForPaint + txtGrowNum) < lvValue) {
                lvForPaint += txtGrowNum;
            } else {
                lvForPaint = lvValue;
            }

//            计算 经验值txt 是否超出边界
            if ((paintLv.measureText(lvForPaint + "") + paintLvTotal.measureText("/" + totalValue) + riseX) >= bgStopX) {
                isToEnd = true;
                TxtEndRiseX = bgStopX - (paintLv.measureText(lvForPaint + "") + paintLvTotal.measureText("/" + totalValue));
            }

            invalidate();
        }
    };

    /**
     * 获取符合要求的等级数组
     * @param currentLv
     * @return
     */
    public List<String> getLvs(int currentLv) {

        List<String> list = new ArrayList<>();

        if ((currentLv - 3) <= 0) {
            for (int i = 0; i < 7; i++) {
                list.add("V" + i);
            }
            list.add("...");
            return list;
        }

        if ((currentLv + 3) >= 10) {

            list.add("...");
            for (int i = 4; i < 11; i++) {
                list.add("V" + i);
            }
            return list;
        }

        if ((currentLv > 3) && (currentLv < 7)) {

            list.add("...");
            int temp = -3;

            while (temp <= 3) {
                list.add("V" + (currentLv + temp));
                temp++;
            }

            list.add("...");
            return list;

        }

        return list;
    }
}
