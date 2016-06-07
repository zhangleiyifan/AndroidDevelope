package com.gyz.androiddevelope.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;

/**
 */
public final class CompatUtils {
    public static Drawable getTintListDrawable(Context mContext, int mResDrawableId, int mResTintId){
        Drawable drawable = DrawableCompat.wrap(ContextCompat.getDrawable(mContext, mResDrawableId).mutate());
        DrawableCompat.setTintList(drawable, ContextCompat.getColorStateList(mContext, mResTintId));
        return drawable;
    }

    public static Drawable getTintDrawable(Context mContext ,int mResDrawableId, @ColorInt int tint){
        Drawable drawable = DrawableCompat.wrap(ContextCompat.getDrawable(mContext, mResDrawableId).mutate());
        DrawableCompat.setTint(drawable, tint);
        return drawable;
    }
}
