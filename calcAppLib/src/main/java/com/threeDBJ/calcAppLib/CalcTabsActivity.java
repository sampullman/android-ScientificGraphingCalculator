package com.threeDBJ.calcAppLib;

import android.content.ActivityNotFoundException;
import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class CalcTabsActivity extends AppCompatActivity {

    static final int CALC_TAB = 0, CONV_TAB = 1, GRAPH_TAB = 2;

    int prevMenu = -1;

    CalcTabsAdapter calcTabsAdapter;
    CalcViewPager viewPager;
    Toolbar toolBar;
    TabLayout tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_tabs);
        viewPager = findViewById(R.id.calc_pager);
        toolBar = findViewById(R.id.toolbar);
        tabs = findViewById(R.id.tabs);
        //bar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);
        setSupportActionBar(toolBar);

        setupViewPager(viewPager);
        tabs.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        calcTabsAdapter = new CalcTabsAdapter(getSupportFragmentManager());
        calcTabsAdapter.addFragment(new mainCalc(), "MAIN");
        calcTabsAdapter.addFragment(new ConvCalc(), "CONVERSION");
        calcTabsAdapter.addFragment(new GraphCalc(), "GRAPH");
        viewPager.setAdapter(calcTabsAdapter);
    }

    @Override
    public final boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        int item = viewPager.getCurrentItem();
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
        if(item.getItemId() == R.id.buy_adfree) {
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
                mainCalc calc = (mainCalc) calcTabsAdapter.getItem(CALC_TAB);
                calc.handleOptionsItemSelected(getSupportFragmentManager(), item.getItemId());
                break;
            case CONV_TAB:
                ConvCalc conv = (ConvCalc) calcTabsAdapter.getItem(CONV_TAB);
                conv.handleOptionsItemSelected(getSupportFragmentManager(), item.getItemId());
                break;
            case GRAPH_TAB:
                GraphCalc graph = (GraphCalc) calcTabsAdapter.getItem(GRAPH_TAB);
                graph.handleOptionsItemSelected(getSupportFragmentManager(), item.getItemId());
                break;
            }
        }
        return true;
    }

    class CalcTabsAdapter extends FragmentPagerAdapter {
        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> fragmentTitleList = new ArrayList<>();

        CalcTabsAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            Timber.e("GOT ITEM %s", position);
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        void addFragment(Fragment fragment, String title) {
            fragmentList.add(fragment);
            fragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitleList.get(position);
        }
    }

}