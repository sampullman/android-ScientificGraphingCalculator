package com.threeDBJ.calcAppLib;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.threeDBJ.calcAppLib.view.GraphView;
import com.threeDBJ.calcAppLib.view.page.GraphPage;
import com.threeDBJ.calcAppLib.view.page.GraphPage.GraphPageInterface;

public class GraphCalc extends Fragment implements GraphPageInterface {
    private GraphPage page;
    private GraphView graph;
    private Dialog zeros;
    private ArrayAdapter<String> zerosArr;
    private static final String help_text = "-Press and drag to move the graph.\n-Pinch to zoom.\n-The fns button takes you to the function entry screen.\n-Reset will reset the bounds of the graph to default values.\n-Zeros will bring up a list of all the zeros on the screen. Press back to return from the list. Note - if the zero is any type of minima or maxima it will not show, this will be fixed by the next update.\n-Trace switches from regular graphing to trace mode. Press again to switch back.\n\n-Please submit bugs and feature requests to 3dbj.dev@gmail.com";

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        page = new GraphPage(this);
        View v = page.getView(getActivity());
        graph = page.getGraph();
        return v;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        CalcApp appState = (CalcApp) getActivity().getApplicationContext();
        appState.setGraphView(graph);

        zerosArr = new ArrayAdapter<>(getActivity(), R.layout.list_item);
        zeros = new Dialog(getActivity());
        zeros.setContentView(R.layout.zeros_disp);
        zeros.setTitle("Zeros (Y Intercepts)");
        zeros.setCancelable(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void functions() {
        graph.graphMode = graph.GRAPH;
        Intent myIntent = new Intent(getContext(), FnEntry.class);
        startActivityForResult(myIntent, 0);
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

    private void showHelp(FragmentManager fm) {
        CalcApp.showTextDialog(fm, "Graph Help", help_text);
    }


    void handleOptionsItemSelected(FragmentManager fm, int itemId) {
        // Handle item selection
        if(itemId == R.id.graph_help) {
            showHelp(fm);
        }
    }

}