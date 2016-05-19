package com.gyz.androiddevelope.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import com.gyz.androiddevelope.R;
import com.gyz.androiddevelope.util.DensityUtils;

import java.util.Random;

/**
 * @version V1.0
 * @FileName: com.gyz.androiddevelope.view.HorizontalProgressBar.java
 * @author: ZhaoHao
 * @date: 2016-05-18 14:34
 */
public class HorizontalProgressBar extends ProgressBar {
    private static final String TAG = "HorizontalProgressBar";

//    <attr name="textColor" />
//    <attr name="textSize" />
//    <attr name="reachColor" />
//    <attr name="reachHeight" />
//    <attr name="unreachColor" />
//    <attr name="offset" />

    private Paint paintText, paintLine, paintUnreach;
    private int textColor;
    private int textSize;
    private int reachColor;
    private int reachHeight;
    private int unreachColor;
    private int offset;
    private int widthTotal,HeightTotal;

    public HorizontalProgressBar(Context context) {
        this(context, null);
    }

    public HorizontalProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.HorizontalProgressBar);
        textColor = array.getColor(R.styleable.HorizontalProgressBar_textColor, getResources().getColor(R.color.colorPrimary));
        textSize = (int) array.getDimension(R.styleable.HorizontalProgressBar_textSize, 13);
        reachColor = array.getColor(R.styleable.HorizontalProgressBar_reachColor, getResources().getColor(R.color.colorPrimary));
        unreachColor = array.getColor(R.styleable.HorizontalProgressBar_unreachColor, getResources().getColor(R.color.color_63C3FF));
        reachHeight = (int) array.getDimension(R.styleable.HorizontalProgressBar_reachHeight, 10);
        offset = (int) array.getDimension(R.styleable.HorizontalProgressBar_offset, 13);
        array.recycle();

        paintText = new Paint();
        paintText.setStyle(Paint.Style.FILL);
        paintText.setColor(textColor);
        paintText.setTextSize(textSize);

        paintLine = new Paint();
        paintLine.setStyle(Paint.Style.FILL);
        paintLine.setStrokeCap(Paint.Cap.ROUND);
        paintLine.setColor(reachColor);
        paintLine.setStrokeWidth(reachHeight);

        paintUnreach = new Paint();
        paintUnreach.setStyle(Paint.Style.FILL);
        paintUnreach.setColor(unreachColor);
        paintUnreach.setStrokeCap(Paint.Cap.ROUND);
        paintUnreach.setStrokeWidth(reachHeight);

    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

//        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = getHeightCustomer(heightMeasureSpec);
        //确定view的宽高
        setMeasuredDimension(width, height);
        widthTotal = getMeasuredWidth();
        HeightTotal = getMeasuredHeight();
    }

    private int getHeightCustomer(int heightMeasureSpec) {
        int size = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int result = 0;
        if (heightMode == MeasureSpec.EXACTLY) {
            return size;
        } else {
            int textHeight = (int) (paintLine.descent() - paintLine.ascent());
            result = Math.max(textHeight, reachHeight);

            if (heightMode == MeasureSpec.AT_MOST) {
//                测量值不超过给定的size值
                result = Math.min(size, result);
            }
        }
        return result;
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {

        boolean isBegin = false;
        boolean isEnd = false;
        int progress = getProgress();
        if (progress <= 0) {
            isBegin = false;
            isEnd = true;
        } else if (progress > 0 && progress < 100) {
            isBegin = true;
            isEnd = true;
        } else if (progress >= 100) {
            isEnd = false;
            isBegin = true;
        }
        // draw reach
        float reachStopX = 0;
        if (isBegin) {
           reachStopX= (widthTotal / 100 * progress) - offset - paintText.measureText(progress + "%")/2;
            canvas.drawLine(0, HeightTotal / 2,reachStopX , HeightTotal / 2 , paintLine);
        }
        //draw text
        int y =(int)(-(paintText.descent()+paintText.ascent())/2) ;
         canvas.drawText(progress+"%",reachStopX+offset,HeightTotal/2+15,paintText);

        //draw unreach
        if (isEnd){
            float unreachStartX = reachStopX+2* offset +paintText.measureText(progress + "%");
            canvas.drawLine(unreachStartX,HeightTotal/2,widthTotal,HeightTotal/2,paintUnreach);
        }
    }
}
