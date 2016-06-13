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
            showFab();
            isVisible = false;
            moveDistance = 0;
        } else if (dy > 0 && !isVisible && moveDistance > MOVE_DISTANCE) {
//            做隐藏操作
            hideFab();
            isVisible = true;
            moveDistance = 0;
        }

        if (dy < 0 || dy > 0) {
            moveDistance += Math.abs(dy);
        }

        normalScroll();
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        onScrollStateChange(recyclerView,newState);
    }

    public abstract void showFab();

    public abstract void hideFab();

    public abstract void normalScroll();

    public abstract void onScrollStateChange(RecyclerView recyclerView, int newState);

}
