package com.gyz.androiddevelope.view.nearby;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @author: guoyazhou
 * @date: 2016-05-09 10:43
 */
public class CustomViewPager extends ViewPager {
    private static final String TAG = "CustomViewPager";

    private long downTime;
    private float LastX;
    private float speed;

    public CustomViewPager(Context context) {
        super(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        float x = ev.getX();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downTime = System.currentTimeMillis();
                LastX = ev.getX();
                break;

            case MotionEvent.ACTION_MOVE:

                x = ev.getX();

                break;

            case MotionEvent.ACTION_UP:

                speed = (x - LastX) / (System.currentTimeMillis() - downTime);

                break;
        }

        return super.dispatchTouchEvent(ev);
    }


    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
}
