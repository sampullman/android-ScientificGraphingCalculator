package com.threeDBJ.calcAppLib;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.widget.TextView;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.view.ContextMenu;
import android.text.Editable;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.content.Context;
import java.lang.StringBuffer;
import android.content.res.Configuration;
import android.view.WindowManager;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.threeDBJ.calcAppLib.cliCalc.*;
import com.threeDBJ.calcAppLib.view.CalcEditText;
import com.threeDBJ.calcAppLib.view.ListDialogFragment.ListDialogCallback;
import com.threeDBJ.calcAppLib.view.calc.CalcPage;
import com.threeDBJ.calcAppLib.view.calc.CalcPage.CalcPageInterface;

import timber.log.Timber;

import static com.threeDBJ.calcAppLib.view.calc.CalcPage.ANSWER_COUNT;

public class mainCalc extends Fragment implements ListDialogCallback, CalcPageInterface {

    public static final int UNIQUE_ID=1;

    private final int PREV_ANSWER = 0;
    private final int PREV_ENTRY = 1;
    private final int EXTRA_FNS = 2;

    private SharedPreferences prefs;
    private OnSharedPreferenceChangeListener listener;

    private CalcPage calcPage;
    private TextView onTheFly;
    private Calculator calc;
    private int state = 0;
    private Button shift;

    private static final String[] extra_fns = { "sinh", "cosh", "tanh", "arcsinh", "arccosh",
            "arctanh", "csc", "sec", "cot"};

    private static final String help_text = "-Use the tabs at the top to switch between calculator, unit conversion, and graphing modes.\n-Press shift for more functions.\n-1/x and % functions evaluate the current expression and then operate on the result.\n-copy/paste works for numbers and expressions across all tabs.\n-last/ans contain previous expressions and answers, respectively\n-5E6 is equivalent to 5*10^6\n-Tap a previous result to insert it, or previous equation to replace the current input.\n\n-Please submit bugs and feature requests to 3dbj.dev@gmail.com";

    private CalcApp appState;
    private View v;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Configuration config = getResources().getConfiguration();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        registerPreferenceListener();

        if(config.orientation == 1) {
            calcPage = new CalcPage(this);
            v = calcPage.getView(getActivity());

        } else if(config.orientation == 2) {
            v = inflater.inflate(R.layout.calc2, container, false);
        }
        return v;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setup();
        try {
            appState.setGlobalRounding(Integer.parseInt(prefs.getString("rounding", "6")));
        } catch(Exception e) {
        }
        appState.setGlobalAngleMode(prefs.getBoolean("angle_rad", true));

        if(savedInstanceState != null) {
            state = savedInstanceState.getInt("state");
            calc.viewStr = savedInstanceState.getString("input_string");
            calc.result = savedInstanceState.getString("result_string");
            if(calc.viewStr == null) calc.viewStr = "";
            if(calc.result == null) calc.result = "";
            setIndex(savedInstanceState.getInt("selection_index"));
            calcPage.setCalc(calc.viewStr, getIndex());
            // if(savedInstanceState.getStringArrayList("old_inputs") != null) {
            // 	calc.oldViews = savedInstanceState.getStringArrayList("old_inputs");
            // }
            // if(calc.answers = savedInstanceState.getStringArrayList("old_answers") != null) {
            // 	calc.answers = savedInstanceState.getStringArrayList("old_answers");
            // }
            updatePrevResults();
            onTheFly.setText(calc.calcOnTheFly());
        }
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(calc != null) {
            updatePrevResults();
            calcPage.setCalc(calc.viewStr, getIndex());
            if(onTheFly != null)
                onTheFly.setText(calc.calcOnTheFly());
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        if(calc != null) {
            if(calc.viewStr != null) {
                savedInstanceState.putString("input_string", calc.viewStr);
                savedInstanceState.putString("result_string", calc.result);
                savedInstanceState.putInt("selection_index", calc.getViewIndex());
                savedInstanceState.putInt("state", state);
            }
            if(calc.oldViews != null && calc.answers != null) {
                savedInstanceState.putStringArrayList("old_inputs", calc.oldViews);
                savedInstanceState.putStringArrayList("old_answers", calc.answers);
            }
        }
    }

    @Override
    public void answerInputClick(int row) {
        if (calc.oldViews.size() > row) {
            calc.lastInp(calc.oldViews.size() - (row + 1));
            setIndex(calc.viewStr.length());
            calcPage.setCalc(calc.viewStr, getIndex());
        }
    }

    @Override
    public void answerClick(int row) {
        if (calc.answers.size() > row) {
            String ans = calc.lastAns(calc.answers.size() - (row + 1));
            updateView(ans.length(), ans);
        }
    }

    private void setup () {
        appState = (CalcApp)getActivity().getApplicationContext();
        this.calc = appState.getMainCalc();
        if(calc == null) {
            Timber.e("null main calc");
        }
        calcPage.setCalcText(calc.viewStr);
        /*

        onTheFly = v.findViewById(R.id.on_the_fly);
        setupButtons ();
        */
    }

    private void setupButtons () {
        shift = v.findViewById(R.id.shift);
        shift.setOnClickListener(shiftBtn);
        Button n = v.findViewById(R.id.left);
        n.setOnClickListener(leftBtn);
        n = v.findViewById(R.id.right);
        n.setOnClickListener(rightBtn);
        n = v.findViewById(R.id.del);
        n.setOnClickListener(bspcBtn);
        n = v.findViewById(R.id.clr);
        n.setOnClickListener(clrBtn);
        n = v.findViewById(R.id.one);
        n.setOnClickListener(makeClickListener("1","1"));
        n = v.findViewById(R.id.two);
        n.setOnClickListener(makeClickListener("2","2"));
        n = v.findViewById(R.id.three);
        n.setOnClickListener(makeClickListener("3","3"));
        n = v.findViewById(R.id.four);
        n.setOnClickListener(makeClickListener("4","4"));
        n = v.findViewById(R.id.five);
        n.setOnClickListener(makeClickListener("5","5"));
        n = v.findViewById(R.id.six);
        n.setOnClickListener(makeClickListener("6","6"));
        n = v.findViewById(R.id.seven);
        n.setOnClickListener(makeClickListener("7","7"));
        n = v.findViewById(R.id.eight);
        n.setOnClickListener(makeClickListener("8","8"));
        n  = v.findViewById(R.id.nine);
        n.setOnClickListener(makeClickListener("9","9"));
        n = v.findViewById(R.id.zero);
        n.setOnClickListener(makeClickListener("0","0"));
        n = v.findViewById(R.id.equals);
        n.setOnClickListener(equalsBtn);
        n = v.findViewById(R.id.point);
        n.setOnClickListener(makeClickListener(".","."));
        n = v.findViewById(R.id.plus);
        n.setOnClickListener(makeClickListener("+","+"));
        n = v.findViewById(R.id.minus);
        n.setOnClickListener(makeClickListener("-","-"));
        n = v.findViewById(R.id.mult);
        n.setOnClickListener(makeClickListener("*","*"));
        n = v.findViewById(R.id.sign);
        n.setOnClickListener(makeClickListener("-","-"));
        n = v.findViewById(R.id.div);
        n.setOnClickListener(makeClickListener("/","/"));
        n = v.findViewById(R.id.ln);
        n.setOnClickListener(makeFnClickListener("Log","ln"));
        n = v.findViewById(R.id.sqr);
        n.setOnClickListener(sqrBtn);
        n = v.findViewById(R.id.pwr);
        n.setOnClickListener(makeClickListener("^","^"));
        n = v.findViewById(R.id.lParen);
        n.setOnClickListener(makeClickListener("(","("));
        n = v.findViewById(R.id.rParen);
        n.setOnClickListener(makeClickListener(")",")"));
        n = v.findViewById(R.id.sin);
        n.setOnClickListener(makeFnClickListener("Sin","sin"));
        n = v.findViewById(R.id.cos);
        n.setOnClickListener(makeFnClickListener("Cos","cos"));
        n = v.findViewById(R.id.tan);
        n.setOnClickListener(makeFnClickListener("Tan","tan"));
        n = v.findViewById(R.id.pie);
        n.setOnClickListener(makeClickListener("PI","pi"));
        n = v.findViewById(R.id.E);
        n.setOnClickListener(makeClickListener("E","E"));
        n = v.findViewById(R.id.ans);
        n.setOnClickListener(ansBtn);
        registerForContextMenu(n);
        n = v.findViewById(R.id.copy);
        n.setOnClickListener(copyBtn);
        n = v.findViewById(R.id.recip);
        n.setOnClickListener(recipBtn);

    }

    private void updateView(int ind, String ins) {
        StringBuffer st = new StringBuffer(calc.viewStr);
        st.insert(getIndex (),ins);
        calc.viewStr = st.toString();
        int newInd = getIndex() + ind;
        Timber.e("calc updateView: %d %d", getIndex(), calc.viewStr.length());
        if(newInd > calc.viewStr.length()) {
            newInd = 0;
        }
        if(newInd < 0) {
            newInd = calc.viewStr.length();
        }
        setIndex(newInd);
        Timber.e("calc updateView: %d %d", getIndex(), calc.viewStr.length());
        calcPage.setCalc(calc.viewStr, getIndex());
    }

    private void setIndex (int ind) {
        calc.setViewIndex (ind);
    }

    private int getIndex() {
        return calc.getViewIndex ();
    }

    private OnClickListener makeClickListener(final String token,final String viewString) {
        return v -> {
            calc.addToken(token, viewString.length(), getIndex());
            updateView(viewString.length(), viewString);
        };
    }

    private void addCalcFn (String token, String viewString) {
        calc.addToken(token, viewString.length(), getIndex());
        updateView (viewString.length (), viewString);
        calc.addToken("(", 1, getIndex());
        updateView(1,"(");
    }

    private OnClickListener makeFnClickListener(final String token,final String viewString) {
        return v -> addCalcFn (token, viewString);
    }

    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem item;
        if(v.getId() == R.id.ans && state == 0) {
            menu.setHeaderTitle("Answers");
            for(int i=calc.answers.size()-1;i>=0;i-=1) {
                item = menu.add(PREV_ANSWER,i,0,calc.answers.get(i));
            }
        } else if(v.getId() == R.id.ans && state == 1) {
            menu.setHeaderTitle("Previous Entries");
            for(int i=calc.oldViews.size()-1;i>=0;i-=1) {
                item = menu.add(PREV_ENTRY,i,0,calc.oldViews.get(i));
            }
        }
    }

    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getGroupId ()) {
        case PREV_ANSWER:
            String ans = calc.lastAns (item.getItemId());
            updateView(ans.length(), ans);
            break;
        case PREV_ENTRY:
            calc.lastInp (item.getItemId ());
            setIndex(calc.viewStr.length());
            calcPage.setCalc(calc.viewStr, getIndex());
            break;
        }
        return true;
    }

    private void updateCalcState() {
        if(state == 1) {
            Button n = v.findViewById(R.id.del);
            n.setOnClickListener(delBtn);
            n.setText("del");
            n = v.findViewById(R.id.sin);
            n.setOnClickListener(makeFnClickListener("Arcsin","arcsin"));
            n.setText("arcsin");
            n.setTextSize(14);
            n = v.findViewById(R.id.cos);
            n.setOnClickListener(makeFnClickListener("Arccos","arccos"));
            n.setTextSize(14);
            n.setText("arccos");
            n = v.findViewById(R.id.tan);
            n.setOnClickListener(makeFnClickListener("Arctan","arctan"));
            n.setText("arctan");
            n.setTextSize(14);
            n = v.findViewById(R.id.copy);
            n.setOnClickListener(pasteBtn);
            n.setText("paste");
            n = v.findViewById (R.id.sqr);
            n.setOnClickListener(makeFnClickListener("Sqrt","sqrt"));
            n.setText ("sqrt");
            n = v.findViewById(R.id.pie);
            n.setOnClickListener(makeClickListener("i","i"));
            n.setText("i");
            n = v.findViewById(R.id.ans);
            n.setOnClickListener(ansBtn);
            n.setText("last");
            n = v.findViewById(R.id.E);
            n.setOnClickListener(makeClickListener("e","e"));
            n.setText("e");
            n = v.findViewById(R.id.recip);
            n.setOnClickListener(percentBtn);
            n.setText("%");
            shift.setBackgroundResource(R.drawable.btn_shift_pressed);
            shift.setTextColor(getColor(R.color.dark));
        } else {
            Button n = v.findViewById(R.id.del);
            n.setText("bspc");
            n.setOnClickListener(bspcBtn);
            n = v.findViewById(R.id.sin);
            n.setOnClickListener(makeFnClickListener("Sin","sin"));
            n.setText("sin");
            n.setTextSize(18);
            n = v.findViewById(R.id.cos);
            n.setOnClickListener(makeFnClickListener("Cos","cos"));
            n.setText("cos");
            n.setTextSize(18);
            n = v.findViewById(R.id.tan);
            n.setOnClickListener(makeFnClickListener("Tan","tan"));
            n.setText("tan");
            n.setTextSize(18);
            n = v.findViewById(R.id.copy);
            n.setOnClickListener(copyBtn);
            n.setText("copy");
            n = v.findViewById (R.id.sqr);
            n.setOnClickListener(sqrBtn);
            n.setText ("sqr");
            n = v.findViewById(R.id.pie);
            n.setOnClickListener(makeClickListener("PI","pi"));
            n.setText("pi");
            n = v.findViewById(R.id.ans);
            n.setOnClickListener(ansBtn);
            n.setText("ans");
            n = v.findViewById(R.id.E);
            n.setOnClickListener(makeClickListener("E","E"));
            n.setText("E");
            n = v.findViewById(R.id.recip);
            n.setOnClickListener(recipBtn);
            n.setText("1/x");
            shift.setBackgroundResource(R.drawable.btn_shift_normal);
            shift.setTextColor(getColor(R.color.light));
        }
    }

    // TODO -- abstract (+ FnEntry => CalcApp or singleton)
    private int getColor(int res) {
        return getActivity().getResources().getColor(res);
    }

    private OnClickListener shiftBtn = v -> {
        state = (state == 0) ? 1 : 0;
        updateCalcState();
    };

    private OnLongClickListener defaultLongClick = v -> true;

    @Override
    public void calcInputClick(CalcEditText calcInput) {
        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(calcInput.getWindowToken(), 0);
    }

    @Override
    public void calcInputSelection(int start, int end) {
        Timber.e("calc %d %d",start, end);
        if(start == end) {
            setIndex(start);
        }
    }

    private OnClickListener leftBtn = new OnClickListener() {
        public void onClick(View v) {
            setIndex(getIndex() - calc.bspcHelper(getIndex(),false));
            if(getIndex() < 0) {
                setIndex (calc.viewStr.length());
            }
            calcPage.setCalcSelection(getIndex());
        }
    };

    private OnClickListener rightBtn = new OnClickListener() {
        public void onClick(View v) {
            setIndex (getIndex() + calc.delHelper(getIndex(),false));
            if(getIndex() > calc.viewStr.length()) setIndex (0);
            calcPage.setCalcSelection(getIndex());
        }
    };

    private OnClickListener delBtn = new OnClickListener() {
        public void onClick(View v) {
            if (getIndex() < calc.viewStr.length ()) {
                int delNum = calc.delHelper(getIndex(),true);
                int ind = getIndex();
                calc.viewStr = calc.viewStr.substring(0,getIndex()) +
                        calc.viewStr.substring(getIndex() + delNum);
                calcPage.setCalc(calc.viewStr, ind);
            }
        }
    };

    private OnClickListener bspcBtn = new OnClickListener() {
        public void onClick(View v) {
            if (getIndex() > 0) {
                int bspcNum = calc.bspcHelper(getIndex(), true);
                calc.viewStr = calc.viewStr.substring(0,getIndex()-bspcNum) +
                        calc.viewStr.substring(getIndex());
                int newInd = getIndex() - bspcNum;
                setIndex(newInd);
                calcPage.setCalc(calc.viewStr, getIndex());
            }
        }
    };

    private OnClickListener clrBtn = new OnClickListener() {
        public void onClick(View v) {
            calc.tokens.clear();
            calc.tokenLens.clear();
            calc.viewStr = "";
            setIndex(0);
            calcPage.setCalc(calc.viewStr, getIndex());
        }
    };

    private OnClickListener sqrBtn = new OnClickListener() {
        public void onClick(View v) {
            calc.addToken("^", 1, getIndex());
            calc.addToken("2", 1, getIndex()+1);
            updateView(2,"^2");
        }
    };

    private OnClickListener equalsBtn = new OnClickListener() {
        public void onClick(View v) {
            // send to parser
            if(calc.viewStr != null && calc.viewStr.length() > 0) {
                if(calc.execute()) {
                    setIndex (0);
                    calcPage.setCalc(calc.viewStr, getIndex());
                    onTheFly.setText(calc.result);
                    updatePrevResults();
                } else {
                    Toast.makeText(getActivity(), "Error: Could not calculate", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    public void updatePrevResults() {
        int n;
        if(calc.answers.size() > calc.oldViews.size()) {
            n = calc.oldViews.size() - 1;
        } else {
            n = calc.answers.size() - 1;
        }
        for(int i=0; i < ANSWER_COUNT; i += 1) {
            if(n < 0) break;
            calcPage.setAnswer(i, calc.oldViews.get(n), calc.answers.get(n));
            n -= 1;
        }
    }

    private OnClickListener ansBtn = v ->
        getActivity().openContextMenu (v.findViewById( R.id.ans));

    private OnClickListener copyBtn = new OnClickListener() {
        public void onClick(View v) {
            appState.setCopy (calc);
        }
    };

    private OnClickListener pasteBtn = new OnClickListener() {
        public void onClick(View v) {
            //calc.tokenize(copy);
            appState.getCopy (calc);
            setIndex(calc.viewStr.length());
            calcPage.setCalc(calc.viewStr, getIndex());
        }
    };

    private OnClickListener recipBtn = new OnClickListener() {
        public void onClick(View v) {
            calc.tokens.add(0, new Primitive("("));
            calc.tokenLens.add(0, 1);
            calc.tokens.add(0, new Primitive ("/"));
            calc.tokens.add(0,new ComplexNumber (1.0,0));
            calc.tokenLens.add(0,1);
            calc.tokenLens.add(0,1);
            calc.tokens.add(new Primitive (")"));
            calc.tokenLens.add(1);
            calc.viewStr = "1/("+calc.viewStr+")";
            calc.execute();
            setIndex (calc.viewStr.length());
            calcPage.setCalc(calc.viewStr, getIndex());
            onTheFly.setText(calc.result);
            updatePrevResults();
        }
    };

    private OnClickListener percentBtn = new OnClickListener() {
        public void onClick(View v) {
            calc.tokens.add(0, new Primitive ("("));
            calc.tokenLens.add(0, 1);
            calc.tokens.add(new Primitive (")"));
            calc.tokenLens.add(1);
            calc.tokens.add(new Primitive ("/"));
            calc.tokenLens.add(1);
            calc.tokens.add(new ComplexNumber (100.0,0));
            calc.tokenLens.add(3);
            calc.viewStr = "("+calc.viewStr+")/100.0";
            calc.execute();
            setIndex (calc.viewStr.length());
            calcPage.setCalc(calc.viewStr, getIndex());
            onTheFly.setText(calc.result);
            updatePrevResults();
        }
    };

    private TextWatcher ioChanged = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            String fly = calc.calcOnTheFly();
            if(fly.length() > 0 && calc.viewStr.length() != 0) {
                onTheFly.setText(fly);
            }
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
    };

    @Override
    public void reportListDialogResult(int ind) {
        addCalcFn(extra_fns[ind], extra_fns[ind]);
    }

    private void showHelp(FragmentManager fm) {
        CalcApp.showTextDialog(fm, "Calculator Help", help_text);
    }

    private void showExtraFnsMenu(FragmentManager fm) {
        CalcApp.showListDialog(fm, "Extra Functions", extra_fns, this);
    }

    private void registerPreferenceListener() {
        listener = (prefs, key) -> {
            if ("rounding".equals(key)) {
                try {
                    int newRnd = Integer.parseInt(prefs.getString(key, "6"));
                    if (newRnd > 0 && newRnd <= 12)
                        appState.setGlobalRounding(newRnd);
                } catch (Exception e) {
                }
            } else if ("angle_rad".equals(key)) {
                appState.setGlobalAngleMode(prefs.getBoolean(key, true));
            }
        };
        prefs.registerOnSharedPreferenceChangeListener(listener);
    }

    private void showOptionsMenu() {
        Intent intent = new Intent(v.getContext(), Settings.class);
        startActivityForResult(intent, 0);
    }

    void handleOptionsItemSelected(FragmentManager fm, int itemId) {
        if(itemId == R.id.calc_help) {
            showHelp(fm);
        } else if(itemId == R.id.extra_fns) {
            showExtraFnsMenu(fm);
        } else if(itemId == R.id.settings) {
            showOptionsMenu ();
        }
    }
}
