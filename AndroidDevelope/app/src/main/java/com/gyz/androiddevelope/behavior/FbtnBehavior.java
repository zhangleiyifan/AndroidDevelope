package com.gyz.androiddevelope.behavior;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

import com.gyz.androiddevelope.util.L;

/**
 * @version V1.0
 * @FileName: com.gyz.androiddevelope.behavior.FbtnBehavior.java
 * @author: ZhaoHao
 * @date: 2016-05-19 08:30
 */
public class FbtnBehavior extends FloatingActionButton.Behavior {
    private static final String TAG = "FbtnBehavior";

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, FloatingActionButton child, View dependency) {
        L.e(TAG,"layoutDependsOn++++++++++++++++++++++");
        return super.layoutDependsOn(parent, child, dependency);
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, FloatingActionButton child, View dependency) {
        L.e(TAG,"onDependentViewChanged++++++++++++++++++++++");
        return super.onDependentViewChanged(parent, child, dependency);
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, FloatingActionButton child, int layoutDirection) {
        L.e(TAG,"onLayoutChild++++++++++++++++++++++");
        return super.onLayoutChild(parent, child, layoutDirection);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View directTargetChild, View target, int nestedScrollAxes) {
        L.e(TAG,"onStartNestedScroll++++++++++++++++++++++");
        return super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);

    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        L.e(TAG,"onNestedScroll-------------------");
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
    }
}
