package com.threeDBJ.calcAppLib.view;

import androidx.appcompat.widget.AppCompatEditText;
import android.content.Context;
import android.util.AttributeSet;

public class CalcEditText extends AppCompatEditText {

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

    public interface SelectionChangedListener {
        void onSelectionChanged(int selStart, int selEnd);
    }

}