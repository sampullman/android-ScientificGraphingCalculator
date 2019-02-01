package com.threeDBJ.calcAppLib.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

import com.threedbj.viewbuilder.generic.GenericEditTextBuilder;

@SuppressWarnings({"AppCompatCustomView", "unchecked"})
public class CalcEditText extends EditText {
    SelectionChangedListener listener;

    static abstract class GenericCalcEditTextBuilder<B extends GenericCalcEditTextBuilder<B, V>, V extends CalcEditText> extends GenericEditTextBuilder<B, V> {

        private SelectionChangedListener listener;

        public B listener(SelectionChangedListener listener) {
            this.listener = listener;
            return (B)this;
        }


        public V build(Context c, V v) {
            super.build(c, v);
            v.setSelectionChangedListener(listener);
            return v;
        }
    }

    public static class CalcEditTextBuilder extends GenericCalcEditTextBuilder<CalcEditTextBuilder, CalcEditText> {
        public CalcEditText build(Context c) {
            return build(c, new CalcEditText(c));
        }
    }

    public CalcEditText(Context context) {
        super(context);
    }

    public CalcEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onSelectionChanged(int selStart, int selEnd) {
        if(listener != null) {
            listener.onSelectionChanged(selStart, selEnd);
        }
    }

    public void setSelectionChangedListener(SelectionChangedListener l) {
        listener = l;
    }

    public interface SelectionChangedListener {
        void onSelectionChanged(int selStart, int selEnd);
    }

}