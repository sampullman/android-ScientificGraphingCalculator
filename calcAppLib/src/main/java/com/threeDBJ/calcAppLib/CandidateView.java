package com.threeDBJ.calcAppLib;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

public class CandidateView extends View {

    public CandidateView(Context context) {
        super(context);
    }

    @Override
    public int computeHorizontalScrollRange() {
        return 0;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(0, 0);
    }

    /**
     * If the canvas is null, then only touch calculations are performed to pick the target
     * candidate.
     */
    @Override
    protected void onDraw(Canvas canvas) {
        if (canvas != null) {
            super.onDraw(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent me) {
	return true;
    }
}
