package com.wang.widget;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class FlowLayout extends ViewGroup {

    private int mVerticalSpacing = dp2x(8);
    private int mHorizontalSpacing = dp2x(16);

    private List<View> lineViews;
    private List<List<View>> allViews;
    private List<Integer> lineHeights;

    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void initMeasureParams(){
        allViews = new ArrayList<>();
        lineViews = new ArrayList<>();
        lineHeights = new ArrayList<>();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        initMeasureParams();
        //1、先测量自身的尺寸
        int selfWidth = MeasureSpec.getSize(widthMeasureSpec);
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();

        //记录当前行的高度和宽度
        int lineWidtUsed = 0;//当前行子view的宽度之和
        int lineHeight = 0;//当前行所有子view中高度最大值

        //整个六十布局的宽度和高度
        int wantedWidth = 0;//所有行中宽度最大值
        int wantedHeight = 0;//所有行的高度累加

        //2、获取每个子View
        //遍历所有子view，对子view进行测量，分配到具体的行
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            LayoutParams childLayoutParams = childView.getLayoutParams();
            childView.measure(getChildMeasureSpec(widthMeasureSpec, paddingLeft + paddingRight, childLayoutParams.width),
                    getChildMeasureSpec(widthMeasureSpec, paddingTop + paddingBottom, childLayoutParams.height));

            //获取当前子view的宽高
            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();

            if (lineWidtUsed + childWidth + mHorizontalSpacing > selfWidth){
                //换行
                allViews.add(lineViews);
                lineHeights.add(lineHeight);
                wantedWidth = Math.max(wantedWidth, lineWidtUsed + mHorizontalSpacing);
                wantedHeight = lineHeight + mVerticalSpacing;
                lineViews = new ArrayList<>();
                lineWidtUsed = 0;
                lineHeight = 0;
            }
            lineViews.add(childView);
            lineWidtUsed = lineWidtUsed + childWidth + mHorizontalSpacing;
            lineHeight = Math.max(lineHeight, childHeight);

            //最后一行
            if (i == childCount - 1){
                lineHeights.add(lineHeight);
                allViews.add(lineViews);
                wantedWidth = Math.max(wantedWidth, lineWidtUsed + mHorizontalSpacing);
                wantedHeight += lineHeight;
            }
        }

        //确定流式布局自身的最终宽高
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int selfHeight = MeasureSpec.getSize(heightMeasureSpec);

        int realWidth = widthMode == MeasureSpec.EXACTLY ? selfWidth : wantedWidth;
        int realHeight = heightMode == MeasureSpec.EXACTLY ? selfHeight : wantedHeight;

        setMeasuredDimension(realWidth, realHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int lineCount = allViews.size();

        int curX = getLeft();
        int curY = 0;

        for (int i = 0; i < lineCount; i++) {
            List<View> lineViews = allViews.get(i);
            int lineHeight = lineHeights.get(i);
            int lineViewCount = lineViews.size();

            for (int j = 0; j < lineViewCount; j++) {
                View child = lineViews.get(j);
                int left = curX;
                int top = curY;
                int right = left + child.getMeasuredWidth();
                int bottom = top + child.getMeasuredHeight();
                child.layout(left, top, right, bottom);
                curX = curX + child.getMeasuredWidth() + mHorizontalSpacing;
            }

            curY = curY + lineHeight + mVerticalSpacing;
            curX = getLeft();
        }
    }

    public static int dp2x(int dp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }
}
