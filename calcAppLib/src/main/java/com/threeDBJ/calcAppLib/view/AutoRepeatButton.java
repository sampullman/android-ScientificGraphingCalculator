package com.threeDBJ.calcAppLib.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

import com.threedbj.viewbuilder.generic.GenericButtonBuilder;

@SuppressWarnings({"AppCompatCustomView", "unchecked"})
public class AutoRepeatButton extends Button {

    private long initialRepeatDelay = 600;
    private long repeatIntervalInMilliseconds = 100;

    static abstract class GenericAutoRepeatButtonBuilder<B extends GenericAutoRepeatButtonBuilder<B, V>, V extends AutoRepeatButton> extends GenericButtonBuilder<B, V> {

        private long initialRepeatDelay = 600;
        private long repeatIntervalInMilliseconds = 100;

        public B interval(long milliseconds) {
            this.repeatIntervalInMilliseconds = milliseconds;
            return (B)this;
        }

        public B initialDelay(long milliseconds) {
            this.initialRepeatDelay = milliseconds;
            return (B)this;
        }

        public V build(Context c, V v) {
            super.build(c, v);
            v.setInitialRepeatDelay(initialRepeatDelay);
            v.setRepeatInterval(repeatIntervalInMilliseconds);
            return v;
        }
    }

    public static class AutoRepeatButtonBuilder extends GenericAutoRepeatButtonBuilder<AutoRepeatButtonBuilder, AutoRepeatButton> {
        public AutoRepeatButton build(Context c) {
            return build(c, new AutoRepeatButton(c));
        }
    }

    @Override
    public boolean performClick() {
        super.performClick();
        return true;
    }

    public void setInitialRepeatDelay(long delay) {
        this.initialRepeatDelay = delay;
    }

    public void setRepeatInterval(long milliseconds) {
        this.repeatIntervalInMilliseconds = milliseconds;
    }

    private Runnable repeatClickWhileButtonHeldRunnable = new Runnable() {
        @Override
        public void run() {
            //Perform the present repetition of the click action provided by the user
            // in setOnClickListener().
            performClick();

            //Schedule the next repetitions of the click action, using a faster repeat
            // interval than the initial repeat delay interval.
            postDelayed(repeatClickWhileButtonHeldRunnable, repeatIntervalInMilliseconds);
        }
    };

    private void commonConstructorCode() {
        this.setOnTouchListener((v, event) -> {
            int action = event.getAction();
            if (action == MotionEvent.ACTION_DOWN) {
                //Just to be sure that we removed all callbacks,
                // which should have occurred in the ACTION_UP
                removeCallbacks(repeatClickWhileButtonHeldRunnable);

                //Schedule the start of repetitions after a one half second delay.
                postDelayed(repeatClickWhileButtonHeldRunnable, initialRepeatDelay);

            } else if (action == MotionEvent.ACTION_UP) {
                // Cancel any repetition in progress.
                removeCallbacks(repeatClickWhileButtonHeldRunnable);
                performClick();
            }

            return false;
        });
    }

    public AutoRepeatButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        commonConstructorCode();
    }

    public AutoRepeatButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        commonConstructorCode();
    }

    public AutoRepeatButton(Context context) {
        super(context);
        commonConstructorCode();
    }
}