package com.threeDBJ.calcAppLib;

import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.util.Log;
import android.widget.TextView;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import android.support.v4.app.ActivityCompat;

import java.util.ArrayList;
import java.util.HashMap;

public class ActionBarTabs extends SherlockFragmentActivity {

    static final int CALC_TAB=0, CONV_TAB=1, GRAPH_TAB=2;

    int prevMenu=-1;

    MyViewPager mViewPager;
    TabsAdapter mTabsAdapter;
    TextView tabCenter;
    TextView tabText;
    public static String shared = "";

    public static final String PREFS_NAME = "SciGraphPrefsFile";

    @Override
	protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	ActionBar bar = getSupportActionBar();
	bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        bar.setDisplayShowHomeEnabled(false);
        bar.setDisplayShowTitleEnabled(false);
	getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
			     WindowManager.LayoutParams.FLAG_FULLSCREEN);
	mViewPager = new MyViewPager(this);
	mViewPager.setId(R.id.pager);

	setContentView(mViewPager);
	//bar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);
	mTabsAdapter = new TabsAdapter(this, mViewPager);
	mTabsAdapter.addTab(bar.newTab().setText("Main"),
			    mainCalc.class);
	mTabsAdapter.addTab(bar.newTab().setText("Conversion"),
			    convCalc.class);
	mTabsAdapter.addTab(bar.newTab().setText("Graph"),
			    graphCalc.class);
    }

    @Override
    public final boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getSupportMenuInflater();
	int item = mTabsAdapter.curItem;
	Log.e("wat", "sfsfds "+item);
	if(prevMenu == item) {
	    return true;
	}
	menu.clear();
	prevMenu = item;
	switch(item) {
	case CALC_TAB:
	    inflater.inflate(R.menu.calc_menu, menu);
	    break;
	case CONV_TAB:
	    inflater.inflate(R.menu.conv_menu, menu);
	    break;
	case GRAPH_TAB:
	    inflater.inflate(R.menu.graph_menu, menu);
	    break;
	}
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
	if(item.getItemId() == R.id.buy_adfree) {
	    final String pkgName = "com.threeDBJ.calcApp";
	    Intent intent = new Intent(Intent.ACTION_VIEW);
	    try {
		intent.setData(Uri.parse("market://details?id="+pkgName));
	    } catch (android.content.ActivityNotFoundException anfe) {
		intent.setData(Uri.parse("http://play.google.com/store/apps/details?id="+pkgName));
	    }
	    startActivity(intent);
	} else {
	    switch(mViewPager.getCurrentItem()) {
	    case CALC_TAB:
		mainCalc calc = (mainCalc) mTabsAdapter.instantiateItem(mViewPager, CALC_TAB);
		calc.handleOptionsItemSelected(getSupportFragmentManager(), item.getItemId());
		break;
	    case CONV_TAB:
		convCalc conv = (convCalc) mTabsAdapter.instantiateItem(mViewPager, CONV_TAB);
		conv.handleOptionsItemSelected(getSupportFragmentManager(), item.getItemId());
		break;
	    case GRAPH_TAB:
		graphCalc graph = (graphCalc) mTabsAdapter.instantiateItem(mViewPager, GRAPH_TAB);
		graph.handleOptionsItemSelected(getSupportFragmentManager(), item.getItemId());
		break;
	    }
	}
	return true;
    }

    public void reportListDialogResult(int id, int ind) {
	switch(id) {
	case CALC_TAB:
	    ((mainCalc) mTabsAdapter.instantiateItem(mViewPager, CALC_TAB)).reportListDialogResult(ind);
	    break;
	}
    }

    public class TabsAdapter extends FragmentPagerAdapter implements
		      ActionBar.TabListener, ViewPager.OnPageChangeListener {
	private final Context mContext;
	private final ActionBar mActionBar;
	private final MyViewPager mViewPager;
	private final ArrayList<String> mTabs = new ArrayList<String>();
	public int curItem=0;

	public TabsAdapter(SherlockFragmentActivity activity, MyViewPager pager) {
	    super(activity.getSupportFragmentManager());
	    mContext = activity;
	    mActionBar = activity.getSupportActionBar();
	    mViewPager = pager;
	    mViewPager.setAdapter(this);
	    mViewPager.setOnPageChangeListener(this);
	}

	public void addTab(Tab tab, Class<?> clss) {
	    mTabs.add(clss.getName());
	    mActionBar.addTab(tab.setTabListener(this));
	    notifyDataSetChanged();
	}

	@Override
	public int getCount() {
	    return mTabs.size();
	}

	@Override
	public Fragment getItem(int position) {
	    Fragment ret = Fragment.instantiate(mContext, mTabs.get(position), null);
	    return ret;
	}

	@Override
	public void onPageScrolled(int position, float positionOffset,
				       int positionOffsetPixels) {
	}

	@Override
	public void onPageSelected(int position) {
	    curItem = position;
	    Log.e("calc", "onPageSelected");
	    mActionBar.setSelectedNavigationItem(position);
	    mViewPager.setSwipeEnabled(position != 2);
	    ActivityCompat.invalidateOptionsMenu(ActionBarTabs.this);
	}

	@Override
	public void onPageScrollStateChanged(int state) {
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
	    Log.e("calc", "onTabSelected");
	    mViewPager.setCurrentItem(tab.getPosition());
	    curItem = tab.getPosition();
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}
    }


}