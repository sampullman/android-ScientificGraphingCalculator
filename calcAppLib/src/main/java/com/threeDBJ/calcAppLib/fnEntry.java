package com.threeDBJ.calcAppLib;

import android.app.Activity;
import android.os.Bundle;
import android.content.Context;
import androidx.core.content.ContextCompat;
import android.view.MenuItem;
import android.widget.Button;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnLongClickListener;
import android.widget.TextView.OnEditorActionListener;
import android.view.View;
import android.widget.EditText;
import android.view.WindowManager;
import android.content.res.Configuration;
import android.content.pm.ActivityInfo;
import android.widget.TextView;
import android.app.Dialog;

public class fnEntry extends Activity {

    int NUM_FNS;

    int state, fnState;
    private static final String help_text = "-The graph or \"back\" button will both take you back to the graph screen.\n-copy/paste works across tabs.\n\n-Please submit bugs and feature requests to 3dbj.dev@gmail.com";

    CalcApp appState;
    Button shift;

    private EditText[] fns;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fnentry);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        appState = (CalcApp) getApplicationContext();
        this.NUM_FNS = appState.getNumFns();
        this.fns = new EditText[NUM_FNS];
        fns[0] = findViewById(R.id.fn1);
        fns[1] = findViewById(R.id.fn2);
        fns[2] = findViewById(R.id.fn3);
        shift = findViewById(R.id.shift);
        shift.setOnClickListener(shiftButtonClick);

        for(int i = 0; i < NUM_FNS; i += 1) {
            fns[i].setText(getCalcs()[i].viewStr);
            fns[i].setOnFocusChangeListener(fnSelect);
            fns[i].setOnLongClickListener(defaultLongClick);
            fns[i].setOnClickListener(ioSelect);
            fns[i].setOnEditorActionListener(ioEdit);
            //fns[i].setTextIsSelectable(true);
        }
        this.state = 0;
        this.fnState = appState.fnState;

        fns[fnState].requestFocus();
        fns[fnState].setSelection(getIndex());

        Button n = findViewById(R.id.fns_graph);
        n.setOnClickListener(graphBtn);

        n = findViewById(R.id.left);
        n.setOnClickListener(leftBtn);
        n = findViewById(R.id.right);
        n.setOnClickListener(rightBtn);
        n = findViewById(R.id.del);
        n.setOnClickListener(bspcBtn);
        n = findViewById(R.id.clr);
        n.setOnClickListener(clrBtn);
        n = findViewById(R.id.one);
        n.setOnClickListener(makeClickListener("1", "1"));
        n = findViewById(R.id.two);
        n.setOnClickListener(makeClickListener("2", "2"));
        n = findViewById(R.id.three);
        n.setOnClickListener(makeClickListener("3", "3"));
        n = findViewById(R.id.four);
        n.setOnClickListener(makeClickListener("4", "4"));
        n = findViewById(R.id.five);
        n.setOnClickListener(makeClickListener("5", "5"));
        n = findViewById(R.id.six);
        n.setOnClickListener(makeClickListener("6", "6"));
        n = findViewById(R.id.seven);
        n.setOnClickListener(makeClickListener("7", "7"));
        n = findViewById(R.id.eight);
        n.setOnClickListener(makeClickListener("8", "8"));
        n = findViewById(R.id.nine);
        n.setOnClickListener(makeClickListener("9", "9"));
        n = findViewById(R.id.zero);
        n.setOnClickListener(makeClickListener("0", "0"));

        n = findViewById(R.id.point);
        n.setOnClickListener(makeClickListener(".", "."));
        n = findViewById(R.id.plus);
        n.setOnClickListener(makeClickListener("+", "+"));
        n = findViewById(R.id.minus);
        n.setOnClickListener(makeClickListener("-", "-"));
        n = findViewById(R.id.mult);
        n.setOnClickListener(makeClickListener("*", "*"));
        n = findViewById(R.id.sign);
        n.setOnClickListener(makeClickListener("-", "-"));
        n = findViewById(R.id.div);
        n.setOnClickListener(makeClickListener("/", "/"));
        n = findViewById(R.id.ln);
        n.setOnClickListener(makeFnClickListener("Log", "ln"));
        n = findViewById(R.id.sqr);
        n.setOnClickListener(sqrBtn);
        n = findViewById(R.id.pwr);
        n.setOnClickListener(makeClickListener("^", "^"));
        n = findViewById(R.id.lParen);
        n.setOnClickListener(makeClickListener("(", "("));
        n = findViewById(R.id.rParen);
        n.setOnClickListener(makeClickListener(")", ")"));
        n = findViewById(R.id.sin);
        n.setOnClickListener(makeFnClickListener("Sin", "sin"));
        n = findViewById(R.id.cos);
        n.setOnClickListener(makeFnClickListener("Cos", "cos"));
        n = findViewById(R.id.tan);
        n.setOnClickListener(makeFnClickListener("Tan", "tan"));
        n = findViewById(R.id.euler);
        n.setOnClickListener(makeClickListener("e", "e"));
        n = findViewById(R.id.pie);
        n.setOnClickListener(makeClickListener("PI", "pi"));
        n = findViewById(R.id.xVar);
        n.setOnClickListener(makeClickListener("x", "x"));
        n = findViewById(R.id.copy);
        n.setOnClickListener(pasteBtn);
        n.setText("paste");
        n = findViewById(R.id.E);
        n.setOnClickListener(makeClickListener("E", "E"));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    Calculator getCalc() {
        return appState.getGraphCalc(fnState);
    }

    Calculator[] getCalcs() {
        return appState.getGraphCalcs();
    }

    int getIndex() {
        //return appState.getGraphIndex (fnState);
        return getCalc().getViewIndex();
    }

    void setIndex(int ind) {
        //appState.setGraphIndex (fnState, ind);
        getCalc().setViewIndex(ind);
    }

    void setFnState(int vid) {
        for(int i = 0; i < NUM_FNS; i += 1) {
            if(fns[i].getId() == vid) {
                fnState = i;
                appState.fnState = i;
                break;
            }
        }
    }

    private void updateView(int ind, String ins) {
        StringBuilder st = new StringBuilder(getCalc().viewStr);
        st.insert(getIndex(), ins);
        getCalc().viewStr = st.toString();
        setIndex(getIndex() + ind);
        if(getIndex() > getCalc().viewStr.length()) {
            setIndex(0);
        }
        if(getIndex() < 0) {
            setIndex(getCalc().viewStr.length());
        }
        fns[fnState].setText(getCalc().viewStr);
        fns[fnState].setSelection(getIndex());
    }

    private OnClickListener makeClickListener(final String token, final String viewString) {
        return v -> {
            getCalc().addToken(token, viewString.length(), getIndex());
            updateView(viewString.length(), viewString);
        };
    }

    private OnClickListener makeFnClickListener(final String token, final String viewString) {
        return v -> {
            getCalc().addToken(token, viewString.length(), getIndex());
            updateView(viewString.length(), viewString);
            getCalc().addToken("(", 1, getIndex());
            updateView(1, "(");
        };
    }

    private OnLongClickListener defaultLongClick = v -> true;

    private OnEditorActionListener ioEdit = (v, actionId, event) -> {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
        }
        return false;
    };

    private OnClickListener ioSelect = new OnClickListener() {
        public void onClick(View v) {
            TextView io = (TextView) v;
            setIndex(io.getSelectionStart());
            InputMethodManager imm = (InputMethodManager) fnEntry.this.getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(io.getWindowToken(), 0);
        }
    };

    private OnClickListener graphBtn = new OnClickListener() {
        public void onClick(View v) {
            appState.initFns();
            //Debug.startMethodTracing("plotFns");
            appState.plotFns();
            //Debug.stopMethodTracing ();
            finish();
        }
    };

    private OnFocusChangeListener fnSelect = new OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if(hasFocus) {
                setFnState(v.getId());
            }
        }
    };

    private OnClickListener leftBtn = new OnClickListener() {
        public void onClick(View v) {
            setIndex(getIndex() - getCalc().bspcHelper(getIndex(), false));
            if(getIndex() < 0) setIndex(getCalc().viewStr.length());
            fns[fnState].setSelection(getIndex());
        }
    };

    private OnClickListener rightBtn = new OnClickListener() {
        public void onClick(View v) {
            setIndex(getIndex() + getCalc().delHelper(getIndex(), false));
            if(getIndex() > getCalc().viewStr.length()) setIndex(0);
            fns[fnState].setSelection(getIndex());
        }
    };

    private OnClickListener delBtn = new OnClickListener() {
        public void onClick(View v) {
            if(getIndex() < getCalc().viewStr.length()) {
                int delNum = getCalc().delHelper(getIndex(), true);
                getCalc().viewStr = getCalc().viewStr.substring(0, getIndex()) +
                        getCalc().viewStr.substring(getIndex() + delNum, getCalc().viewStr.length());
                fns[fnState].setText(getCalc().viewStr);
                fns[fnState].setSelection(getIndex());
            }
        }
    };

    private OnClickListener bspcBtn = new OnClickListener() {
        public void onClick(View v) {
            if(getIndex() > 0) {
                int bspcNum = getCalc().bspcHelper(getIndex(), true);
                getCalc().viewStr = getCalc().viewStr.substring(0, getIndex() - bspcNum) +
                        getCalc().viewStr.substring(getIndex(), getCalc().viewStr.length());
                setIndex(getIndex() - bspcNum);
                fns[fnState].setText(getCalc().viewStr);
                fns[fnState].setSelection(getIndex());
            }
        }
    };

    private OnClickListener clrBtn = v -> {
        getCalc().tokens.clear();
        getCalc().tokenLens.clear();
        getCalc().viewStr = "";
        setIndex(0);
        fns[fnState].setText(getCalc().viewStr);
        fns[fnState].setSelection(getIndex());
    };

    private OnClickListener sqrBtn = v -> {
        getCalc().addToken("^", 1, getIndex());
        getCalc().addToken("2", 1, getIndex() + 1);
        updateView(2, "^2");
    };

    private OnClickListener copyBtn = new OnClickListener() {
        public void onClick(View v) {
            appState.setCopy(getCalc());
        }
    };

    private OnClickListener pasteBtn = new OnClickListener() {
        public void onClick(View v) {
            appState.getCopy(getCalc());
            fns[fnState].setText(getCalc().viewStr);
            fns[fnState].setSelection(getIndex());
        }
    };

    private OnClickListener shiftButtonClick = v -> {
        if(state == 0) {
            Button n = findViewById(R.id.del);
            n.setOnClickListener(delBtn);
            n.setText("del");
            n = findViewById(R.id.sin);
            n.setOnClickListener(makeFnClickListener("Arcsin", "arcsin"));
            n.setText("arcsin");
            n.setTextSize(14);
            n = findViewById(R.id.cos);
            n.setOnClickListener(makeFnClickListener("Arccos", "arccos"));
            n.setTextSize(14);
            n.setText("arccos");
            n = findViewById(R.id.tan);
            n.setOnClickListener(makeFnClickListener("Arctan", "arctan"));
            n.setText("arctan");
            n.setTextSize(14);
            n = findViewById(R.id.copy);
            n.setOnClickListener(copyBtn);
            n.setText("copy");
            shift.setBackgroundResource(R.drawable.btn_shift_pressed);
            shift.setTextColor(ContextCompat.getColor(this, R.color.dark));
            state = 1;
        } else {
            Button n = findViewById(R.id.del);
            n.setText("bspc");
            n.setOnClickListener(bspcBtn);
            n = findViewById(R.id.sin);
            n.setOnClickListener(makeFnClickListener("Sin", "sin"));
            n.setText("sin");
            n.setTextSize(18);
            n = findViewById(R.id.cos);
            n.setOnClickListener(makeFnClickListener("Cos", "cos"));
            n.setText("cos");
            n.setTextSize(18);
            n = findViewById(R.id.tan);
            n.setOnClickListener(makeFnClickListener("Tan", "tan"));
            n.setText("tan");
            n.setTextSize(18);
            n = findViewById(R.id.copy);
            n.setOnClickListener(pasteBtn);
            n.setText("paste");
            shift.setBackgroundResource(R.drawable.btn_shift_normal);
            shift.setTextColor(ContextCompat.getColor(this, R.color.light));
            state = 0;
        }
    };

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.fnentry_menu, menu);
        return true;
    }
    */

    private void showHelp() {
        final Dialog dialog = new Dialog(fnEntry.this);
        dialog.setContentView(R.layout.calc_help);
        dialog.setTitle("Function Entry Help");
        ((TextView) dialog.findViewById(R.id.calchelp_text)).setText(help_text);
        dialog.setCancelable(true);
        Button n = dialog.findViewById(R.id.calchelp_ret);
        n.setOnClickListener(v -> dialog.cancel());
        dialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        if(item.getItemId() == R.id.fnentry_help) {
            showHelp();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}