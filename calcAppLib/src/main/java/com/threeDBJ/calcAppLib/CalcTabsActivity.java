package com.threeDBJ.calcAppLib;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import java.util.ArrayList;

import timber.log.Timber;

public class CalcTabsActivity extends AppCompatActivity {

    static final int CALC_TAB = 0, CONV_TAB = 1, GRAPH_TAB = 2;

    int prevMenu = -1;

    private ArrayList<CalcTab> tabItems = new ArrayList<>();
    CalcTabsAdapter calcTabsAdapter;
    CalcViewPager viewPager;
    Toolbar toolBar;
    TabLayout tabs;

    public static abstract class CalcTab {
        public abstract View getView();
        public void pause(SharedPreferences.Editor edit) {}
        public void resume() {}
        public abstract void handleOptionsItemSelected(int itemId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_tabs);
        tabItems.add(new mainCalc(this));
        tabItems.add(new ConvCalc(this));
        tabItems.add(new GraphCalc(this));

        viewPager = findViewById(R.id.calc_pager);
        toolBar = findViewById(R.id.toolbar);
        tabs = findViewById(R.id.tabs);
        //bar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);
        setSupportActionBar(toolBar);

        calcTabsAdapter = new CalcTabsAdapter(tabItems);
        viewPager.setAdapter(calcTabsAdapter);
        tabs.setupWithViewPager(viewPager);
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
            tabItems.get(viewPager.getCurrentItem()).handleOptionsItemSelected(item.getItemId());
        }
        return true;
    }

    class CalcTabsAdapter extends PagerAdapter {
        ArrayList<CalcTab> tabItems;

        CalcTabsAdapter(ArrayList<CalcTab> tabItems) {
            this.tabItems = tabItems;
        }

        @Override @NonNull
        public Object instantiateItem(@NonNull ViewGroup collection, int position) {
            return tabItems.get(position).getView();
        }

        @Override
        public int getCount() {
            return tabItems.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }
    }

}