package com.gyz.androiddevelope.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.gyz.androiddevelope.R;

import java.util.Random;

/**
 * @version V1.0
 * @FileName: com.gyz.androiddevelope.view.LoveLayout.java
 * @author: ZhaoHao
 * @date: 2016-05-18 17:58
 */
public class LoveLayout extends RelativeLayout {
    private static final String TAG = "LoveLayout";

    private int heartWidth;
    private int heartHeight;
    private int mWidth;
    private int mHeight;
    private Random r;

    public LoveLayout(Context context) {
        this(context, null);
    }

    public LoveLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoveLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        heartWidth = getResources().getDrawable(R.mipmap.heart).getIntrinsicWidth();
        heartHeight = getResources().getDrawable(R.mipmap.heart).getIntrinsicHeight();
        r = new Random();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mHeight = h;
        mWidth = w;
    }

    public void addHeart(){
//        添加heartview

        //这几个PointF不能设成全局的变量,否则每个动画都引用的是一个对象的指向,就会造成轨迹混乱
        PointF p0 = new PointF(mWidth/2-heartWidth/2,mHeight - heartHeight);
        PointF p1 = new PointF(r.nextInt(mWidth),r.nextInt(mHeight/2)+mHeight/2);
        PointF p2 = new PointF(r.nextInt(mWidth),r.nextInt(mHeight/2));
        PointF p3 = new PointF(r.nextInt(mWidth),0);

        HeartView heartView = new HeartView(getContext());
        RelativeLayout.LayoutParams layoutParams = new LayoutParams(heartWidth+20,heartHeight);
        layoutParams.leftMargin = mWidth/2-heartHeight/2;
        layoutParams.topMargin = mHeight- heartHeight;
        setAnima(heartView,p0,p1,p2,p3);
        addView(heartView,layoutParams);
    }

    /**
     * 设置贝塞尔曲线轨迹
     * @param heartView
     * @param p0
     * @param p1
     * @param p2
     * @param p3
     */
    private void setAnima(final View heartView, final PointF p0, final PointF p1, final PointF p2, final PointF p3) {

        final ValueAnimator valueAnimator = ValueAnimator.ofFloat(0,1f);
        valueAnimator.setDuration(3000);
        heartView.setTag(valueAnimator);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if ((ValueAnimator)heartView.getTag() == animation){
                    float t = (float) animation.getAnimatedValue();
                    float floatx = p0.x*(1-t)*(1-t)*(1-t)+3*p1.x*t*(1-t)*(1-t)+3*p2.x*t*t*(1-t)+p3.x*t*t*t;
                    float floaty=p0.y*(1-t)*(1-t)*(1-t)+3*p1.y*t*(1-t)*(1-t)+3*p2.y*t*t*(1-t)+p3.y*t*t*t;
                    heartView.setX(floatx);
                    heartView.setY(floaty);
                    heartView.setAlpha(t>0.8?(1-t)*4:1);
                }
            }
        });

        if ((ValueAnimator)heartView.getTag() == valueAnimator){
            valueAnimator.setTarget(heartView);
            valueAnimator.start();
            valueAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    removeView(heartView);
                    valueAnimator.cancel();
                }

                @Override
                public void onAnimationEnd(Animator animation) {

                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }
    }
}
