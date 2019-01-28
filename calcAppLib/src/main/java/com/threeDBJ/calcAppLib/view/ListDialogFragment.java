package com.threeDBJ.calcAppLib.view;

import android.app.Activity;
import android.app.Dialog;
import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class ListDialogFragment extends DialogFragment {

    private String title;
    private ListDialogCallback callback;
    private String[] items;

    public interface ListDialogCallback {
        void reportListDialogResult(int index);
    }

    public static ListDialogFragment newInstance(String title, String[] items, ListDialogCallback callback) {
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
        if(getArguments() != null) {
            title = getArguments().getString("title");
            items = getArguments().getStringArray("items");
        }
    }

    @Override @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Activity a = getActivity();

        final ArrayAdapter<String> adapter =
            new ArrayAdapter<>(a, android.R.layout.simple_list_item_1, items);

        return new AlertDialog.Builder(a)
            .setCancelable(true)
            .setTitle(title)
            .setAdapter(adapter, (dialog, which) ->
                callback.reportListDialogResult(which)
            )
            .setNegativeButton(android.R.string.cancel, (d, which) -> d.dismiss())
            .create();
    }

}