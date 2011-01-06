package com.android.calcApp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.Toast;
import android.widget.TableRow;

public class graphCalc extends Activity {

    private GraphView graph;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.graph);
	graph = (GraphView) findViewById(R.id.graph);
	//float err = graph.setBounds(-10,10,-10,10,10,10);
	//Toast.makeText(graphCalc.this, Float.toString(err),
	//	       Toast.LENGTH_LONG).show();

    }

}