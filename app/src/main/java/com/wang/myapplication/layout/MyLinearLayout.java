package com.wang.myapplication.layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.wang.myapplication.R;

public class MyLinearLayout extends LinearLayout {
    public MyLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MyLayoutParams(getContext(), attrs);
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        MyLayoutParams p = (MyLayoutParams) params;
        if (!isDiscrollable(p)){
            super.addView(child, params);
        }else {
            MyFrameLayout mf = new MyFrameLayout(getContext(), null);
            mf.setmDiscrollViewAlpha(p.mDiscrollViewAlpha);
            mf.setmDiscrollViewFromBgColor(p.mDiscrollViewFromBgColor);
            mf.setmDiscrollViewToBgColor(p.mDiscrollViewToBgColor);
            mf.setmDiscrollViewScaleX(p.mDiscrollViewScaleX);
            mf.setmDiscrollViewScaleY(p.mDiscrollViewScaleY);
            mf.setmDiscrollViewTranslation(p.mDiscrollViewTranslation);
            mf.addView(child);
            super.addView(mf, params);
        }
    }

    private boolean isDiscrollable(MyLayoutParams p) {
        return !p.mDiscrollViewAlpha || !p.mDiscrollViewScaleX || !p.mDiscrollViewScaleY
                || p.mDiscrollViewTranslation != -1 || (p.mDiscrollViewFromBgColor != -1 && p.mDiscrollViewToBgColor != -1);
    }

    public static class MyLayoutParams extends LayoutParams {
        private int mDiscrollViewFromBgColor;//背景颜色开始值
        private int mDiscrollViewToBgColor;//背景颜色结束值
        private boolean mDiscrollViewAlpha;//是否需要透明度动画
        private int mDiscrollViewTranslation;//平移动画
        private boolean mDiscrollViewScaleX;//X缩放
        private boolean mDiscrollViewScaleY;//Y缩放

        public MyLayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            //获取自定义属性
            TypedArray a = c.obtainStyledAttributes(attrs, R.styleable.DiscrollView_LayoutParams);
            mDiscrollViewFromBgColor = a.getColor(R.styleable.DiscrollView_LayoutParams_discrollview_fromBgColor, -1);
            mDiscrollViewToBgColor = a.getColor(R.styleable.DiscrollView_LayoutParams_discrollview_toBgColor, -1);
            mDiscrollViewAlpha = a.getBoolean(R.styleable.DiscrollView_LayoutParams_discrollview_alpha, false);
            mDiscrollViewTranslation = a.getInt(R.styleable.DiscrollView_LayoutParams_discrollview_translation, -1);
            mDiscrollViewScaleX = a.getBoolean(R.styleable.DiscrollView_LayoutParams_discrollview_scaleX, false);
            mDiscrollViewScaleY = a.getBoolean(R.styleable.DiscrollView_LayoutParams_discrollview_scaleY, false);
            a.recycle();
        }
    }
}
