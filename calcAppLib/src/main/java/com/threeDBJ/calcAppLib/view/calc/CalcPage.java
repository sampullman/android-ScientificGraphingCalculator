package com.threeDBJ.calcAppLib.view.calc;

import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;

import com.threeDBJ.calcAppLib.R;
import com.threedbj.viewbuilder.ButtonBuilder;
import com.threedbj.viewbuilder.LinearLayoutBuilder;
import com.threedbj.viewbuilder.TextViewBuilder;
import com.threedbj.viewbuilder.style.Style;

import static android.view.Gravity.CENTER_VERTICAL;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class CalcPage {

    public View getView(Activity context) {
        TextViewBuilder prev = new TextViewBuilder()
            .inLinear()
            .width(WRAP_CONTENT)
            .height(MATCH_PARENT)
            .textSize(18)
            .color(R.color.light)
            .gravity(CENTER_VERTICAL)
            .background(R.drawable.btn_black_normal)
            .visibility(View.GONE);

        TextViewBuilder prevResult = new TextViewBuilder()
            .load(prev)
            .background(R.drawable.btn_black);

        LinearLayout row1 =  new LinearLayoutBuilder()
            .horizontal()
            .inLinear()
            .style(Style.WIDE)
            .weight(0.7f)
            .build(context);

        new ButtonBuilder()
            .load(prevResult)
            .paddingDp(6, 0, 0, 0)
            .id(R.id.prev_input3)
            .build(row1);
        new TextViewBuilder()
            .load(prev)
            .text(" = ")
            .id(R.id.eq3)
            .build(row1);
        new ButtonBuilder()
            .load(prevResult)
            .id(R.id.prev_result3)
            .build(row1);

        return row1;
    }
}
