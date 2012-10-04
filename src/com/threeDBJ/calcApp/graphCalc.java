package com.threeDBJ.calcAppLib;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.Toast;
import android.widget.TableRow;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.pm.ActivityInfo;
import android.util.Log;
import android.app.Dialog;
import android.view.MenuInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.ArrayAdapter;

public class graphCalc extends Activity {

    private GraphView graph;
    private CalcApp appState;
    private Dialog zeros;;
    private ArrayAdapter<String> zerosArr;
    private static final String help_text = "-Press and drag to move the graph.\n-Pinch to zoom.\n-The fns button takes you to the function entry screen.\n-Reset will reset the bounds of the graph to default values.\n-Zeros will bring up a list of all the zeros on the screen. Press back to return from the list. Note - if the zero is any type of minima or maxima it will not show, this will be fixed by the next update.\n-Trace switches from regular graphing to trace mode. Press again to switch back.\n\n-Please submit bugs and feature requests to 3dbj.dev@gmail.com";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.graph);
        graph = (GraphView) findViewById(R.id.graph);
        appState = (CalcApp) getApplicationContext ();
        appState.setGraphView (graph);

        Button n = (Button) findViewById(R.id.graph_fns);
        n.setOnClickListener (fnsBtn);
        n = (Button) findViewById(R.id.graph_reset);
        n.setOnClickListener (resetBtn);
        n = (Button) findViewById(R.id.graph_zeros);
        n.setOnClickListener (zerosBtn);

        zerosArr = new ArrayAdapter<String>(this, R.layout.list_item);
        zeros = new Dialog(graphCalc.this);
        zeros.setContentView(R.layout.zeros_disp);
        zeros.setTitle("Zeros (Y Intercepts)");
        zeros.setCancelable(true);

        n = (Button) findViewById(R.id.graph_trace);
        n.setOnClickListener (traceBtn);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.v ("graph","calc");
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    private OnClickListener fnsBtn = new OnClickListener() {
            public void onClick(View v) {
                graph.graphMode = graph.GRAPH;
                Intent myIntent = new Intent(v.getContext(), fnEntry.class);
                startActivityForResult(myIntent, 0);
       	    }
        };

    private OnClickListener resetBtn = new OnClickListener() {
            public void onClick(View v) {
                graph.reset ();
            }
        };

    private OnClickListener zerosBtn = new OnClickListener() {
            public void onClick(View v) {
                zerosArr.clear ();
                graph.zeros (zerosArr);
                ListView lst = (ListView) zeros.findViewById (R.id.zeros_disp);
                lst.setAdapter(zerosArr);
                zeros.show ();
            }
        };

    private OnClickListener traceBtn = new OnClickListener() {
            public void onClick(View v) {
                graph.trace ();
                Button trace = (Button) findViewById (R.id.graph_trace);
                if (graph.graphMode == graph.TRACE) {
                    trace.setBackgroundResource (R.drawable.btn_blue);
                } else {
                    trace.setBackgroundResource (android.R.drawable.btn_default);
                }
            }
        };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.graph_menu, menu);
        return true;
    }

    private void showHelp () {
        final Dialog dialog = new Dialog(graphCalc.this);
        dialog.setContentView(R.layout.calc_help);
        dialog.setTitle("Graph Help");
        ((TextView)dialog.findViewById (R.id.calchelp_text)).setText (help_text);
        dialog.setCancelable(true);
        Button n = (Button) dialog.findViewById(R.id.calchelp_ret);
        n.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel ();
                }
            });
        dialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        if(item.getItemId() == R.id.graph_help) {
            showHelp ();
            return true;
	} else {
            return super.onOptionsItemSelected(item);
        }
    }

}