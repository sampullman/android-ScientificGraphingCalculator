package com.threeDBJ.calcAppLib.view.page;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.threeDBJ.calcAppLib.R;
import com.threeDBJ.calcAppLib.view.GraphView;
import com.threeDBJ.calcAppLib.view.GraphView.GraphViewBuilder;
import com.threedbj.viewbuilder.ButtonBuilder;
import com.threedbj.viewbuilder.LinearLayoutBuilder;
import com.threedbj.viewbuilder.RelativeLayoutBuilder;
import com.threedbj.viewbuilder.style.Style;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class GraphPage {
    private GraphPageInterface pageInterface;
    private GraphView graph;
    private Button trace;
    private Drawable defaultButton;

    public interface GraphPageInterface {
        void functions();
        void reset();
        void trace();
        void zeroes();
    }

    public GraphPage(GraphPageInterface pageInterface) {
        this.pageInterface = pageInterface;
    }

    public void setTraceBackground(boolean selected) {
        if(selected) {
            trace.setBackgroundResource(R.drawable.abs__btn_cab_done_pressed_holo_light);
        } else {
            trace.setBackground(defaultButton);
        }
    }

    public GraphView getGraph() {
        return graph;
    }

    public View getView(Activity context) {
        RelativeLayout root = new RelativeLayoutBuilder()
            .style(Style.MATCH)
            .build(context);

        graph = new GraphViewBuilder()
            .inRelative()
            .style(Style.MATCH)
            .paddingDp(10, 10, 10, 10)
            .build(root);

        LinearLayout adWrap = new LinearLayoutBuilder()
            .id()
            .inRelative()
            .width(MATCH_PARENT)
            .height(WRAP_CONTENT)
            .parentBottom()
            .build(root);

        Button fns = new ButtonBuilder(Style.WRAP)
            .inRelative()
            .id()
            .textSizeSp(20)
            .text("fns")
            .above(adWrap)
            .parentStart()
            .click(v -> pageInterface.functions())
            .build(root);

        new ButtonBuilder(Style.WRAP)
            .inRelative()
            .textSizeSp(18)
            .text("reset")
            .above(adWrap)
            .endOf(fns)
            .click(v -> pageInterface.reset())
            .build(root);

        trace = new ButtonBuilder()
            .inRelative()
            .widthDp(70)
            .heightDp(49)
            .textSizeSp(18)
            .text("trace")
            .above(adWrap)
            .parentEnd()
            .click(v -> pageInterface.trace())
            .build(root);
        defaultButton = trace.getBackground();

        new ButtonBuilder(Style.WRAP)
            .inRelative()
            .textSizeSp(18)
            .text("zeroes")
            .startOf(trace)
            .above(adWrap)
            .click(v -> pageInterface.zeroes())
            .build(root);

        return root;
    }
}
