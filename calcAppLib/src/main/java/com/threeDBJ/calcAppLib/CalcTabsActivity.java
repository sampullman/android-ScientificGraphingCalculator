package com.threeDBJ.calcAppLib;

import android.content.ActivityNotFoundException;
import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar.TabListener;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CalcTabsActivity extends AppCompatActivity {

    static final int CALC_TAB = 0, CONV_TAB = 1, GRAPH_TAB = 2;

    int prevMenu = -1;

    CalcTabsAdapter mCalcTabsAdapter;
    @BindView(R2.id.calc_pager) CalcViewPager viewPager;
    @BindView(R2.id.toolbar) Toolbar toolBar;
    @BindView(R2.id.tabs) TabLayout tabs;

    public static final String PREFS_NAME = "SciGraphPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_tabs);
        ButterKnife.bind(this);
        //bar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);
        setSupportActionBar(toolBar);

        setupViewPager(viewPager);
        tabs.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        mCalcTabsAdapter = new CalcTabsAdapter(getSupportFragmentManager());
        mCalcTabsAdapter.addFragment(new mainCalc(), "MAIN");
        mCalcTabsAdapter.addFragment(new ConvCalc(), "CONVERSION");
        mCalcTabsAdapter.addFragment(new GraphCalc(), "GRAPH");
        viewPager.setAdapter(mCalcTabsAdapter);
    }

    @Override
    public final boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        int item = mCalcTabsAdapter.curItem;
        Timber.e("sfsfds %d", item);
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
        if(item.getItemId() == R2.id.buy_adfree) {
            final String pkgName = "com.threeDBJ.calcApp";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            try {
                intent.setData(Uri.parse("market://details?id=" + pkgName));
            } catch(ActivityNotFoundException e) {
                intent.setData(Uri.parse("http://play.google.com/store/apps/details?id=" + pkgName));
            }
            startActivity(intent);
        } else {
            switch(viewPager.getCurrentItem()) {
            case CALC_TAB:
                mainCalc calc = (mainCalc) mCalcTabsAdapter.getItem(CALC_TAB);
                calc.handleOptionsItemSelected(getSupportFragmentManager(), item.getItemId());
                break;
            case CONV_TAB:
                ConvCalc conv = (ConvCalc) mCalcTabsAdapter.getItem(CONV_TAB);
                conv.handleOptionsItemSelected(getSupportFragmentManager(), item.getItemId());
                break;
            case GRAPH_TAB:
                GraphCalc graph = (GraphCalc) mCalcTabsAdapter.getItem(GRAPH_TAB);
                graph.handleOptionsItemSelected(getSupportFragmentManager(), item.getItemId());
                break;
            }
        }
        return true;
    }

    class CalcTabsAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public CalcTabsAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}