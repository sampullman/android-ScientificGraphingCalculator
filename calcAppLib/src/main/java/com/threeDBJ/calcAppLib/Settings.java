package com.threeDBJ.calcAppLib;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.Menu;
import android.view.MenuItem;

public class Settings extends PreferenceActivity {

    static final int BACK_ID = 1;

    public static void save(SharedPreferences prefs, String key, boolean b) {
        SharedPreferences.Editor edit = prefs.edit();
        edit.putBoolean(key, b);
        edit.apply();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.settings);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, BACK_ID, 0, "Back")
            .setIcon(R.drawable.ic_menu_back)
            .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case BACK_ID:
            finish();
            break;
        }
        return true;
    }


}
