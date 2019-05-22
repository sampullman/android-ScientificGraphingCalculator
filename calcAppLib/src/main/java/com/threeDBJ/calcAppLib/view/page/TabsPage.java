package com.threeDBJ.calcAppLib.view.page;

import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;

import com.threedbj.viewbuilder.LinearLayoutBuilder;
import com.threedbj.viewbuilder.style.Style;

public class TabsPage {



    public View getView(Activity context) {
        LinearLayout root = new LinearLayoutBuilder(Style.MATCH)
            .vertical()
            .build(context);

        return root;
    }
}
