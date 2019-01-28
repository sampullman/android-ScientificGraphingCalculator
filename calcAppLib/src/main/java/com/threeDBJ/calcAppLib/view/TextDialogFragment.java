package com.threeDBJ.calcAppLib.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.threeDBJ.calcAppLib.R;

public class TextDialogFragment extends DialogFragment {
    private String title="", text;

    public static TextDialogFragment newInstance(String title, String text) {
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
        if(getArguments() != null) {
            title = getArguments().getString("title", "");
            text = getArguments().getString("text");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.text_fragment_dialog, container, false);

        TextView tv = v.findViewById(R.id.text);
        tv.setText(text);

        getDialog().setTitle(title);

        // Watch for button clicks.
        Button b = v.findViewById(R.id.cancel);
        b.setOnClickListener(view -> dismiss());

        return v;
    }
}