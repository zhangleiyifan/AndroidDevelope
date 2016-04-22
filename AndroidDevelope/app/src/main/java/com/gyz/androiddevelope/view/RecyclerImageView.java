package com.gyz.androiddevelope.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * @author: guoyazhou
 * @date: 2016-04-22 13:47
 */
public class RecyclerImageView extends ImageView {
    private static final String TAG = "RecyclerImageView";

    public RecyclerImageView(Context context) {
        super(context);
    }

    public RecyclerImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RecyclerImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        setImageBitmap(null);
    }
}
