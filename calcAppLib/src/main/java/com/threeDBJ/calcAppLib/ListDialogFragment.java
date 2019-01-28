package com.threeDBJ.calcAppLib;

import android.app.Dialog;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import androidx.fragment.app.DialogFragment;

public class ListDialogFragment extends DialogFragment {

    String title;
    int callerId;
    String[] items;

    static ListDialogFragment newInstance(int callerId, String title, String[] items) {
        ListDialogFragment f = new ListDialogFragment();

        Bundle args = new Bundle();
        args.putInt("caller_id", callerId);
        args.putString("title", title);
	args.putStringArray("items", items);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
	callerId = getArguments().getInt("caller_id");
        title = getArguments().getString("title");
	items = getArguments().getStringArray("items");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
	final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
	return new AlertDialog.Builder(getActivity())
            .setCancelable(true)
            .setTitle(title)
            .setAdapter(adapter, new DialogInterface.OnClickListener() {
		    @Override
		    public void onClick(DialogInterface dialog, int which) {
			((CalcTabsActivity)getActivity()).reportListDialogResult(callerId, which);
		    }
		})
            .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
		    @Override
		    public void onClick(DialogInterface dialog, int which) {
			dialog.dismiss();
		    }
		})
            .create();
    }

}