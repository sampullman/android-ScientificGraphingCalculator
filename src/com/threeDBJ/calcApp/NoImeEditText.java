package com.threeDBJ.calcAppLib;

import android.widget.EditText;
import android.content.Context;
import android.util.AttributeSet;

public class NoImeEditText extends EditText {

    public NoImeEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onCheckIsTextEditor() {
        return false;
    }
}