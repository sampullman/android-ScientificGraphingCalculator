package com.threeDBJ.calcAppLib.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.appcompat.widget.AppCompatButton;

public class AutoRepeatButton extends AppCompatButton {

      private long initialRepeatDelay = 600;
      private long repeatIntervalInMilliseconds = 100;

    @Override
    public boolean performClick() {
        super.performClick();
        return true;
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