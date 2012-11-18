package com.threeDBJ.calcAppLib;

import android.view.MotionEvent;
import android.util.AttributeSet;
import android.content.Context;
import android.support.v4.view.ViewPager;

public class MyViewPager extends ViewPager {

    private boolean enabled, swipeEnabled;

    public MyViewPager(Context context) {
        super(context);
        this.enabled = true;
	this.swipeEnabled = true;
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.enabled = true;
	this.swipeEnabled = true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (this.enabled && this.swipeEnabled) {
            return super.onTouchEvent(event);
	}
	return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (this.enabled && this.swipeEnabled) {
            return super.onInterceptTouchEvent(event);
	}
	return false;
    }

    public void setSwipeEnabled(boolean swipeEnabled) {
	this.swipeEnabled = swipeEnabled;
    }

    public void setPagingEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}