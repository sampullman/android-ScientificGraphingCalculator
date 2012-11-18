package com.threeDBJ.calcAppLib;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

public class SelectedAdapter extends ArrayAdapter {

    int[] selPos;
    int[] colors;
    Context context;

    public SelectedAdapter(Context context, int tvResource, String[] items, int color) {
	super(context, tvResource, new ArrayList<String>(Arrays.asList(items)));
	this.context = context;
	colors = new int[1];
	colors[0] = color;
	selPos = new int[1];
	selPos[0] = -1;
    }

    public SelectedAdapter(Context context, int tvResource, String[] items, int[] colors) {
	super(context, tvResource, new ArrayList<String>(Arrays.asList(items)));
	this.context = context;
	this.colors = colors;
	selPos = new int[colors.length];
	clearSelected();
    }

    public void setSelectedPosition(int pos) {
	selPos[0] = pos;
	// inform the view of this change
	notifyDataSetChanged();
    }

    public void setSelectedPosition(int pos, int colorInd) {
	for(int i=0;i<selPos.length;i+=1) {
	    if(selPos[i] == pos) {
		selPos[i] = -1;
		break;
	    }
	}
	selPos[colorInd] = pos;
	// inform the view of this change
	notifyDataSetChanged();
    }

    public void clearSelection() {
	for(int i=0;i<selPos.length;i+=1) {
	    selPos[i] = -1;
	}
    }

    /* Returns true if as many items in the adapter are selected as allowed */
    public boolean selectionSet() {
	for(int i=0;i<selPos.length;i+=1) {
	    if(selPos[i] == -1) return false;
	}
	return true;
    }

    public int getSelectedPosition() {
	return selPos[0];
    }

    public int getSelectedPosition(int colorInd) {
	return selPos[colorInd];
    }

    public void clearSelected() {
	for(int i=0;i<selPos.length;i+=1) {
	    selPos[i] = -1;
	}
    }

    boolean inArray(int[] arr, int n) {
	for(int i=0;i<arr.length;i+=1) {
	    if(arr[i] == n) return true;
	}
	return false;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
	// only inflate the view if it's null
	if (v == null) {
	    LayoutInflater vi
		= (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    v = vi.inflate(R.layout.list_item, null);
	}

	TextView label = (TextView)v;
	// change row colors based on selected state
	for(int i=0;i<colors.length;i+=1) {
	}

	for(int i=0;i<colors.length;i+=1) {
	    if(selPos[i] == position) {
		Log.e("calc", position+" "+colors[i]);
		//label.setBackgroundColor(colors[i]);
		label.setBackgroundColor(colors[i]);
		label.setTextColor(Color.BLACK);
	    } else if(!inArray(selPos, position)) {
		label.setBackgroundColor(Color.BLACK);
		label.setTextColor(Color.LTGRAY);
	    }
	}

	label.setText(this.getItem(position).toString());
	return v;
    }
}