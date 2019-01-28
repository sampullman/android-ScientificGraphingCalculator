package com.threeDBJ.calcAppLib;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class GraphCalc extends Fragment {

    public static final int UNIQUE_ID = 3;

    private GraphView graph;
    private Dialog zeros;
    private ArrayAdapter<String> zerosArr;
    private static final String help_text = "-Press and drag to move the graph.\n-Pinch to zoom.\n-The fns button takes you to the function entry screen.\n-Reset will reset the bounds of the graph to default values.\n-Zeros will bring up a list of all the zeros on the screen. Press back to return from the list. Note - if the zero is any type of minima or maxima it will not show, this will be fixed by the next update.\n-Trace switches from regular graphing to trace mode. Press again to switch back.\n\n-Please submit bugs and feature requests to 3dbj.dev@gmail.com";

    Drawable btn_default;

    View v;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Configuration config = getResources().getConfiguration();
        v = inflater.inflate(R.layout.graph, container, false);
        return v;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        graph = (GraphView) v.findViewById(R.id.graph);
        CalcApp appState = (CalcApp) getActivity().getApplicationContext();
        appState.setGraphView(graph);

        Button n = (Button) v.findViewById(R.id.graph_fns);
        n.setOnClickListener(fnsBtn);
        n = (Button) v.findViewById(R.id.graph_reset);
        n.setOnClickListener(resetBtn);
        n = (Button) v.findViewById(R.id.graph_zeros);
        n.setOnClickListener(zerosBtn);

        zerosArr = new ArrayAdapter<String>(getActivity(), R.layout.list_item);
        zeros = new Dialog(getActivity());
        zeros.setContentView(R.layout.zeros_disp);
        zeros.setTitle("Zeros (Y Intercepts)");
        zeros.setCancelable(true);

        n = (Button) v.findViewById(R.id.graph_trace);
        n.setOnClickListener(traceBtn);
        btn_default = n.getBackground();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private OnClickListener fnsBtn = new OnClickListener() {
        public void onClick(View v) {
            graph.graphMode = graph.GRAPH;
            Intent myIntent = new Intent(v.getContext(), FnEntry.class);
            startActivityForResult(myIntent, 0);
        }
    };

    private OnClickListener resetBtn = new OnClickListener() {
        public void onClick(View v) {
            graph.reset();
        }
    };

    private OnClickListener zerosBtn = new OnClickListener() {
        public void onClick(View v) {
            zerosArr.clear();
            graph.zeros(zerosArr);
            ListView lst = (ListView) zeros.findViewById(R.id.zeros_disp);
            lst.setAdapter(zerosArr);
            zeros.show();
        }
    };

    private OnClickListener traceBtn = new OnClickListener() {
        public void onClick(View v) {
            graph.trace();
            Button trace = v.findViewById(R.id.graph_trace);
            if(graph.graphMode == graph.TRACE) {
                trace.setBackgroundResource(R.drawable.abs__btn_cab_done_pressed_holo_light);
            } else {
                trace.setBackgroundDrawable(btn_default);
            }
        }
    };

    private void showHelp(FragmentManager fm) {
        CalcApp.showTextDialog(fm, "Graph Help", help_text);
    }


    public void handleOptionsItemSelected(FragmentManager fm, int itemId) {
        // Handle item selection
        if(itemId == R.id.graph_help) {
            showHelp(fm);
        }
    }

}