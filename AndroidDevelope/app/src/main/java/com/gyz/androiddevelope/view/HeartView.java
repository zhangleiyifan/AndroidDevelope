package com.gyz.androiddevelope.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.gyz.androiddevelope.R;

import java.util.Random;

/**
 * @version V1.0
 * @FileName: com.gyz.androiddevelope.view.HeartView.java
 * @author: ZhaoHao
 * @date: 2016-05-18 17:39
 */
public class HeartView extends ImageView {
    private static final String TAG = "HeartView";

    private Paint paint;
    private Random random;
    private Bitmap bitmap, borderBitmap;

    public HeartView(Context context) {
        this(context, null);
    }

    public HeartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HeartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        random = new Random();
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.heart);
        borderBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.heart_border);
        int color = getColor();
        //设置颜色过滤器
        paint.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(borderBitmap,0,0,paint);
        canvas.drawBitmap(bitmap,0,0,paint);
        paint.setColorFilter(null);//重置过滤器
    }

    private int getColor(){
        return Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255));
    }
}
