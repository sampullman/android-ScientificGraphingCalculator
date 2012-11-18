package com.threeDBJ.calcAppLib;

import android.content.Intent;
import android.os.Bundle;
import com.actionbarsherlock.app.SherlockPreferenceActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class Settings extends SherlockPreferenceActivity {

    static final int BACK_ID=1;

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
	Intent intent;
	switch (item.getItemId()) {
	case BACK_ID:
	    finish();
	    break;
	}
	return true;
    }


}
