package com.threeDBJ.calcAppLib;

import android.widget.EditText;
import android.content.Context;
import android.util.AttributeSet;

public class CalcEditText extends EditText {

    SelectionChangedListener sListener;

    public CalcEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onSelectionChanged(int selStart, int selEnd) {
        if(sListener != null) {
            sListener.onSelectionChanged(selStart, selEnd);
        }
    }

    public void setSelectionChangedListener(SelectionChangedListener l) {
        sListener = l;
    }

    interface SelectionChangedListener {
        public void onSelectionChanged(int selStart, int selEnd);
    }

}