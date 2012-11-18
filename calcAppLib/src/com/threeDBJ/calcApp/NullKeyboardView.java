package com.threeDBJ.calcAppLib;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.Keyboard.Key;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;
import android.view.inputmethod.InputMethodSubtype;

public class NullKeyboardView extends KeyboardView {

    static final int KEYCODE_OPTIONS = -100;

    public NullKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NullKeyboardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected boolean onLongPress(Key key) {
        return true;
    }

}
