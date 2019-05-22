package com.threeDBJ.calcAppLib;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.threeDBJ.calcAppLib.CalcTabsActivity.CalcTab;
import com.threeDBJ.calcAppLib.view.GraphView;
import com.threeDBJ.calcAppLib.view.page.GraphPage;
import com.threeDBJ.calcAppLib.view.page.GraphPage.GraphPageInterface;

public class GraphCalc extends CalcTab implements GraphPageInterface {
    private Activity activity;
    private GraphPage page;
    private GraphView graph;
    private Dialog zeros;
    private ArrayAdapter<String> zerosArr;
    private static final String help_text = "-Press and drag to move the graph.\n-Pinch to zoom.\n-The fns button takes you to the function entry screen.\n-Reset will reset the bounds of the graph to default values.\n-Zeros will bring up a list of all the zeros on the screen. Press back to return from the list. Note - if the zero is any type of minima or maxima it will not show, this will be fixed by the next update.\n-Trace switches from regular graphing to trace mode. Press again to switch back.\n\n-Please submit bugs and feature requests to 3dbj.dev@gmail.com";

    public GraphCalc(Activity activity) {
        this.activity = activity;
    }
    
    public View getView() {
        page = new GraphPage(this);
        View v = page.getView(activity);
        graph = page.getGraph();

        CalcApp appState = (CalcApp) activity.getApplicationContext();
        appState.setGraphView(graph);

        zerosArr = new ArrayAdapter<>(activity, R.layout.list_item);
        zeros = new Dialog(activity);
        zeros.setContentView(R.layout.zeros_disp);
        zeros.setTitle("Zeros (Y Intercepts)");
        zeros.setCancelable(true);

        return v;
    }

    @Override
    public void functions() {
        graph.graphMode = graph.GRAPH;
        Intent myIntent = new Intent(activity, FnEntry.class);
        activity.startActivityForResult(myIntent, 0);
    }

    @Override
    public void reset() {
        graph.reset();
    }

    @Override
    public void zeroes() {
        zerosArr.clear();
        graph.zeros(zerosArr);
        ListView lst = zeros.findViewById(R.id.zeros_disp);
        lst.setAdapter(zerosArr);
        zeros.show();
    }

    @Override
    public void trace() {
        graph.trace();
        page.setTraceBackground(graph.graphMode == graph.TRACE);
    }

    private void showHelp() {
        CalcApp.showTextDialog(activity, "Graph Help", help_text);
    }


    public void handleOptionsItemSelected(int itemId) {
        // Handle item selection
        if(itemId == R.id.graph_help) {
            showHelp();
        }
    }

}