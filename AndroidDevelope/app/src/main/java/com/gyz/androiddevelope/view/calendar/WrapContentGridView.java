package com.gyz.androiddevelope.view.calendar;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;


public class WrapContentGridView extends GridView {
	public WrapContentGridView(Context context) {
		super(context);
	}

	public WrapContentGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public WrapContentGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = MeasureSpec.getSize(widthMeasureSpec);
		heightMeasureSpec = MeasureSpec.makeMeasureSpec(width,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
}
