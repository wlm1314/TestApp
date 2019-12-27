package com.wang.myapplication.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

public class MyScrollView extends ScrollView {
    private MyLinearLayout mContent;
    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mContent = (MyLinearLayout) getChildAt(0);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        int scrollHeight = getHeight();

        for (int i = 0; i < mContent.getChildCount(); i++) {
            View child = mContent.getChildAt(i);
            if (!(child instanceof MyFrameLayout)) continue;
            DiscrollInterface mf = (DiscrollInterface) child;

            int childTop = child.getTop();
            int childHeight = child.getHeight();
            int absoluteTop = childTop - t;

            if (absoluteTop <= scrollHeight){
                //child浮现高度=scrollview的高度-child离屏幕顶部的高度
                int visibleCap = scrollHeight - absoluteTop;
                float ratio = visibleCap/(float)childHeight;
                mf.onDiscroll(clmp(ratio, 1f, 0f));
            }else {
                mf.onResetDiscroll();
            }
        }
    }

    private float clmp(float value, float max, float min){
        return Math.max(Math.min(value, max), min);
    }
}
