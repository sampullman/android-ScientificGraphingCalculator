package com.threeDBJ.calcAppLib;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;

import androidx.fragment.app.DialogFragment;

public class TextDialogFragment extends DialogFragment {
    String title="", text;

    static TextDialogFragment newInstance(String title, String text) {
        TextDialogFragment f = new TextDialogFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("text", text);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title = getArguments().getString("title");
        text = getArguments().getString("text");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.text_fragment_dialog, container, false);

        TextView tv = (TextView) v.findViewById(R.id.text);
        tv.setText(text);

        getDialog().setTitle(title);

        // Watch for button clicks.
        Button b = (Button)v.findViewById(R.id.cancel);
        b.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                dismiss();
            }
        });

        return v;
    }
}