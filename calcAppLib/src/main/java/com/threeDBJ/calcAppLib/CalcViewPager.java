package com.threeDBJ.calcAppLib;

import android.view.MotionEvent;
import android.util.AttributeSet;
import android.content.Context;
import androidx.viewpager.widget.ViewPager;

public class CalcViewPager extends ViewPager {

    private boolean enabled, swipeEnabled;

    public CalcViewPager(Context context) {
        super(context);
        this.enabled = true;
        this.swipeEnabled = true;
    }

    public CalcViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.enabled = true;
        this.swipeEnabled = true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return this.enabled && this.swipeEnabled && super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return this.enabled && this.swipeEnabled && super.onInterceptTouchEvent(event);
    }

    public void setSwipeEnabled(boolean swipeEnabled) {
        this.swipeEnabled = swipeEnabled;
    }

    public void setPagingEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}