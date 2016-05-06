package com.gyz.androiddevelope.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.gyz.androiddevelope.R;

/**
 * @author: guoyazhou
 * @date: 2016-05-06 13:55
 */
public class GrowMountainView extends View{
    private static final String TAG = "GrowMountainView";

    //毫秒
    private static final int MSECONDS = 50;
    //    每次递增的坐标值
    private static final int RISE_PX = 15;
    private Context context;
    private Paint paint,paint1, paintText;
    private float width,height,centerWidth,centerHeight,pointX1,pointY1,pointX2,pointY2,pointX3,pointY3,pointX4,pointY4,
        pointTextX,pointTextY;

    public GrowMountainView(Context context) {
        this(context,null);
    }

    public GrowMountainView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public GrowMountainView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(context.getResources().getColor(R.color.color_4c4c4c));

        paint1 = new Paint();
        paint1.setAntiAlias(true);
        paint1.setStyle(Paint.Style.FILL);
        paint1.setColor(context.getResources().getColor(R.color.color_f98435));

        paintText = new Paint();
        paintText.setStyle(Paint.Style.STROKE);
        paintText.setColor(context.getResources().getColor(R.color.colorBlack));
        paintText.setTextSize(26);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        centerHeight = h/2;
        centerWidth = w/2;
        calculateSize();
    }

    private void calculateSize() {

//        左底点
        pointX1 = centerWidth/2;
        pointY1 = centerHeight;
//        右底点
        pointX2 = centerWidth + centerWidth/2;
        pointY2 = centerHeight;
//        逐渐增高的顶点
        pointX3 = centerWidth;
        pointY3 = centerHeight +1;

        //第二个三角形的点
        pointX4 = centerWidth/4;
        pointY4 = centerHeight;

        //文字坐标点
        pointTextX = centerWidth - (paintText.measureText("顶点高度为：xxx"))/2;
        pointTextY = pointY3-5;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画图形
        Path path = new Path();

        path.moveTo(pointX1,pointY1);
        path.lineTo(pointX2,pointY2);
        path.lineTo(pointX3,pointY3);
        path.close();

        canvas.drawPath(path,paint);

        Path path1 = new Path();
        path1.moveTo(pointX4,pointY4);
        path1.lineTo(pointX1,pointY1);
        path1.lineTo(pointX3,pointY3);
        path.close();
        canvas.drawPath(path1,paint1);

        canvas.drawText("顶点坐标为："+pointY3,pointTextX,pointTextY,paintText);

        postDelayed(runnable,MSECONDS);

        if (pointY3 <= centerHeight/2){
            removeCallbacks(runnable);
        }
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {

            pointY3 -=RISE_PX;
            pointTextY -=RISE_PX;
            invalidate();
        }
    };
}
