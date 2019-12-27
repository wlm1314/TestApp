package com.wang.myapplication.layout;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyFrameLayout extends FrameLayout implements DiscrollInterface{
    private static final int TRANSLATION_FROM_TOP = 0x01;
    private static final int TRANSLATION_FROM_BOTTOM = 0x02;
    private static final int TRANSLATION_FROM_LEFT = 0x04;
    private static final int TRANSLATION_FROM_RIGHT = 0x08;

    private static ArgbEvaluator argbEvaluator = new ArgbEvaluator();

    private int mDiscrollViewFromBgColor;//背景颜色开始值
    private int mDiscrollViewToBgColor;//背景颜色结束值
    private boolean mDiscrollViewAlpha;//是否需要透明度动画
    private int mDiscrollViewTranslation;//平移动画
    private boolean mDiscrollViewScaleX;//X缩放
    private boolean mDiscrollViewScaleY;//Y缩放
    private int mHeight;
    private int mWidth;

    public MyFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setmDiscrollViewFromBgColor(int mDiscrollViewFromBgColor) {
        this.mDiscrollViewFromBgColor = mDiscrollViewFromBgColor;
    }

    public void setmDiscrollViewToBgColor(int mDiscrollViewToBgColor) {
        this.mDiscrollViewToBgColor = mDiscrollViewToBgColor;
    }

    public void setmDiscrollViewAlpha(boolean mDiscrollViewAlpha) {
        this.mDiscrollViewAlpha = mDiscrollViewAlpha;
    }

    public void setmDiscrollViewTranslation(int mDiscrollViewTranslation) {
        this.mDiscrollViewTranslation = mDiscrollViewTranslation;
    }

    public void setmDiscrollViewScaleX(boolean mDiscrollViewScaleX) {
        this.mDiscrollViewScaleX = mDiscrollViewScaleX;
    }

    public void setmDiscrollViewScaleY(boolean mDiscrollViewScaleY) {
        this.mDiscrollViewScaleY = mDiscrollViewScaleY;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    public void onDiscroll(float ratio) {
        scroll(ratio);
    }

    @Override
    public void onResetDiscroll() {
        scroll(0);
    }

    private void scroll(float ratio){
        if (mDiscrollViewAlpha){
            setAlpha(ratio);
        }
        if (mDiscrollViewScaleX){
            setScaleX(ratio);
        }
        if (mDiscrollViewScaleY){
            setScaleY(ratio);
        }
        if (isTranslationFram(TRANSLATION_FROM_TOP)){
            setTranslationY(-mHeight*(1-ratio));
        }
        if (isTranslationFram(TRANSLATION_FROM_BOTTOM)){
            setTranslationY(mHeight*(1-ratio));
        }
        if (isTranslationFram(TRANSLATION_FROM_LEFT)){
            setTranslationX(-mWidth*(1-ratio));
        }
        if (isTranslationFram(TRANSLATION_FROM_RIGHT)){
            setTranslationX(mWidth*(1-ratio));
        }
        if (mDiscrollViewFromBgColor != -1 && mDiscrollViewToBgColor != -1){
            setBackgroundColor((Integer) argbEvaluator.evaluate(ratio, mDiscrollViewFromBgColor, mDiscrollViewToBgColor));
        }
    }

    private boolean isTranslationFram(int from){
        if (mDiscrollViewTranslation == -1){
            return false;
        }
        return (mDiscrollViewTranslation & from) == from;
    }
}
