package com.wang.uiapp.dispatch;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class ParentView extends LinearLayout {
    public ParentView(Context context) {
        super(context);
    }

    public ParentView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ParentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return false;
            case MotionEvent.ACTION_MOVE:   //表示父类需要
                return true;
            case MotionEvent.ACTION_UP:
                return true;
            default:
                break;
        }
        return false;    //如果设置拦截，除了down,其他都是父类处理
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e("ParentView", "You down layout");
                break;
            case MotionEvent.ACTION_UP:
                Log.e("ParentView", "You up layout");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e("ParentView", "You move layout");
        }
        return true;
    }
}
