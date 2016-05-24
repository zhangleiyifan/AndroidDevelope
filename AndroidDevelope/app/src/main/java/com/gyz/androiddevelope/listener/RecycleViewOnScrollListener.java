package com.gyz.androiddevelope.listener;

import android.support.v7.widget.RecyclerView;

/**
 * @version V1.0
 * @FileName: com.gyz.androiddevelope.listener.RecycleViewOnScrollListener.java
 * @author: ZhaoHao
 * @date: 2016-05-24 08:45
 */
public abstract class RecycleViewOnScrollListener extends RecyclerView.OnScrollListener {
    private static final String TAG = "RecycleViewOnScrollListener";

    private static final int MOVE_DISTANCE = 20;
    private int moveDistance;
    private boolean isVisible;

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        if (dy < 0 && isVisible && moveDistance > MOVE_DISTANCE) {
            //做显示操作
            show();
            isVisible = false;
            moveDistance = 0;
        } else if (dy > 0 && !isVisible && moveDistance > MOVE_DISTANCE) {
//            做隐藏操作
            hide();
            isVisible = true;
            moveDistance = 0;
        }

        if (dy < 0 || dy > 0) {
            moveDistance += Math.abs(dy);
        }

        normalScroll();
    }

    public abstract void show();

    public abstract void hide();

    public abstract void normalScroll();

}
