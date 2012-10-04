package com.threeDBJ.calcAppLib;

import android.app.TabActivity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.TabHost;
import android.content.res.Resources;
import android.content.SharedPreferences;
import android.widget.TextView;
import android.content.res.Configuration;
import android.content.pm.ActivityInfo;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.util.Log;

public class mainTab extends TabActivity implements OnGestureListener {

    public static String shared = "";
    TabHost tabHost;
    private GestureDetector gestureScanner;

    public static final String PREFS_NAME = "SciGraphPrefsFile";

    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;

    // Not needed?
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
	gestureScanner = new GestureDetector(this);
	tabHost = getTabHost();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
	gestureScanner = new GestureDetector(this);

        Resources res = getResources(); // Resource object to get Drawables
        tabHost = getTabHost();  // The activity TabHost
        TabHost.TabSpec spec;  // Resusable TabSpec for each tab
        Intent intent;  // Reusable Intent for each tab
	int tabHeight = 65;

        // Create an Intent to launch an Activity for the tab (to be reused)
        intent = new Intent().setClass(this, mainCalc.class);

        // Initialize a TabSpec for each tab and add it to the TabHost
        spec = tabHost.newTabSpec("0").setIndicator("Main").setContent(intent);
        tabHost.addTab(spec);
        tabHost.getTabWidget().getChildAt(0).getLayoutParams().height = tabHeight;

        // Do the same for the other tabs
        intent = new Intent().setClass(this, convCalc.class);
        //TextView convTitle = new TextView(this);
        //mainTitle.setText("Conversion");
        spec = tabHost.newTabSpec("1").setIndicator("Conversions")
            //res.getDrawable(R.drawable.ic_tab_convcalc))
            .setContent(intent);
        tabHost.addTab(spec);
        tabHost.getTabWidget().getChildAt(1).getLayoutParams().height = tabHeight;

        intent = new Intent().setClass(this, graphCalc.class);
        //TextView imagTitle = new TextView(this);
        //mainTitle.setText("Imaginary");
        spec = tabHost.newTabSpec("2").setIndicator("Graphing")
            //res.getDrawable(R.drawable.ic_tab_imagecalc))
            .setContent(intent);
        tabHost.addTab(spec);
	tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
		public void onTabChanged(String tabID) {
		    Log.e("fack", tabID);
		    SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		    SharedPreferences.Editor editor = settings.edit();
		    editor.putInt("current_tab", Integer.parseInt(tabID));
		    editor.commit();
		}
	    }
	    );
        tabHost.getTabWidget().getChildAt(2).getLayoutParams().height = tabHeight;
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
	tabHost.setCurrentTab(settings.getInt("current_tab", 0));
    }

    @Override
    public boolean onTouchEvent(MotionEvent me) {
        return gestureScanner.onTouchEvent(me);
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public boolean onSingleTapUp(MotionEvent me) {
        return false;
    }

    @Override
    public boolean onDown(MotionEvent me) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent me) {
    }

    @Override
    public void onShowPress(MotionEvent me) {
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
	if (gestureScanner != null) {
	    if (gestureScanner.onTouchEvent(ev))
		return true;
	}
	return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
            float velocityY) {

        // Check movement along the Y-axis. If it exceeds SWIPE_MAX_OFF_PATH, then dismiss the swipe.
        if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
            return false;
	SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
	SharedPreferences.Editor editor = settings.edit();
        // Swipe from right to left.
        // The swipe needs to exceed a certain distance (SWIPE_MIN_DISTANCE) and a certain velocity (SWIPE_THRESHOLD_VELOCITY).
        if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
                && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
            if(tabHost.getCurrentTab() < 2) {
		tabHost.setCurrentTab(tabHost.getCurrentTab()+1);
		editor.putInt("current_tab", tabHost.getCurrentTab()+1);
		editor.commit();
	    }
            return true;
        }

        // Swipe from left to right.
        // The swipe needs to exceed a certain distance (SWIPE_MIN_DISTANCE) and a certain velocity (SWIPE_THRESHOLD_VELOCITY).
        if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
                && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
            if(tabHost.getCurrentTab() == 1) {
		tabHost.setCurrentTab(tabHost.getCurrentTab()-1);
		editor.putInt("current_tab", tabHost.getCurrentTab()-1);
		editor.commit();
	    }
            return true;
        }

        return false;
    }

}