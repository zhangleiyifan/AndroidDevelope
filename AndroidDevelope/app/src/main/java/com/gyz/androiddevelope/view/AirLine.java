package com.gyz.androiddevelope.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.gyz.androiddevelope.R;

/**
 * @author: guoyazhou
 * @date: 2016-05-06 15:15
 */

public class AirLine extends View {

    private Paint paint;
    private float stopX, stopY, startX, startY, offset, icCenter;
    private Bitmap bitmap;

    public AirLine(Context context) {
        this(context, null);
    }

    public AirLine(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AirLine(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        paint = new Paint();

        paint.setColor(Color.BLUE);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(3);

        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.zhihu);

        icCenter = bitmap.getWidth() / 2;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:

                stopX = event.getX();
                stopY = event.getY();

                updatePoint(stopX, stopY);

                break;

            default:
                break;
        }

        return false;
    }

    private void updatePoint(final float stopX, final float stopY) {

        ValueAnimator animator = ValueAnimator.ofFloat(0, 1f);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                offset = animation.getAnimatedFraction();
                startX = startX + (stopX - startX) * offset;
                startY = startY + (stopY - startY) * offset;
                invalidate();
            }
        });
        animator.setDuration(1000);
        animator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawLine(startX, startY, stopX, stopY, paint);
        canvas.drawBitmap(bitmap, startX - icCenter, startY - icCenter, paint);

        super.onDraw(canvas);
    }

}
