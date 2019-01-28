package com.threeDBJ.calcAppLib;

import android.app.Dialog;
import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import androidx.fragment.app.DialogFragment;

public class ListDialogFragment extends DialogFragment {

    private String title;
    private ListDialogCallback callback;
    private String[] items;

    public interface ListDialogCallback {
        void reportListDialogResult(int index);
    }

    static ListDialogFragment newInstance(String title, String[] items, ListDialogCallback callback) {
        ListDialogFragment f = new ListDialogFragment();
        f.callback = callback;
        Bundle args = new Bundle();
        args.putString("title", title);
	    args.putStringArray("items", items);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title = getArguments().getString("title");
	    items = getArguments().getStringArray("items");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
	final ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, items);
	return new AlertDialog.Builder(getActivity())
            .setCancelable(true)
            .setTitle(title)
            .setAdapter(adapter, (dialog, which) -> {
                callback.reportListDialogResult(which);
            })
            .setNegativeButton(android.R.string.cancel, (dialog, which) -> {
			dialog.dismiss();
		    })
            .create();
    }

}