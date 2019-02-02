package com.threeDBJ.calcAppLib;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.View;
import android.widget.Toast;
import android.view.ContextMenu;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.content.Context;
import android.view.WindowManager;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.threeDBJ.calcAppLib.cliCalc.*;
import com.threeDBJ.calcAppLib.view.CalcEditText;
import com.threeDBJ.calcAppLib.view.ListDialogFragment.ListDialogCallback;
import com.threeDBJ.calcAppLib.view.page.CalcPage;
import com.threeDBJ.calcAppLib.view.page.CalcPage.CalcPageInterface;

import timber.log.Timber;

import static com.threeDBJ.calcAppLib.view.page.CalcPage.ANSWER_COUNT;

public class mainCalc extends Fragment implements ListDialogCallback, CalcPageInterface {;

    private final int PREV_ANSWER = 0;
    private final int PREV_ENTRY = 1;
    private final int EXTRA_FNS = 2;

    private SharedPreferences prefs;
    private OnSharedPreferenceChangeListener listener;

    private CalcPage calcPage;
    private Calculator calc;

    private static final String[] extra_fns = { "sinh", "cosh", "tanh", "arcsinh", "arccosh",
            "arctanh", "csc", "sec", "cot"};

    private static final String help_text = "-Use the tabs at the top to switch between calculator, unit conversion, and graphing modes.\n-Press shift for more functions.\n-1/x and % functions evaluate the current expression and then operate on the result.\n-copy/paste works for numbers and expressions across all tabs.\n-last/ans contain previous expressions and answers, respectively\n-5E6 is equivalent to 5*10^6\n-Tap a previous result to insert it, or previous equation to replace the current input.\n\n-Please submit bugs and feature requests to 3dbj.dev@gmail.com";

    private CalcApp appState;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        registerPreferenceListener();

        calcPage = new CalcPage(prefs,this);
        return calcPage.getView(getActivity());
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        appState = (CalcApp)getActivity().getApplicationContext();
        this.calc = appState.getMainCalc();
        if(calc == null) {
            Timber.e("null main calc");
        }
        calcPage.setCalcText(calc.viewStr);

        try {
            appState.setGlobalRounding(Integer.parseInt(prefs.getString("rounding", "6")));
        } catch(Exception e) {
        }
        appState.setGlobalAngleMode(prefs.getBoolean("angle_rad", true));

        if(savedInstanceState != null) {
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
            calcPage.setResult(calc.calcOnTheFly());
        }
    }

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
            calcPage.setResult(calc.calcOnTheFly());
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

    private void updateView(int ind, String ins) {
        StringBuilder st = new StringBuilder(calc.viewStr);
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

    private void addCalcFn(String token, String viewString) {
        calc.addToken(token, viewString.length(), getIndex());
        updateView(viewString.length (), viewString);
        calc.addToken("(", 1, getIndex());
        updateView(1,"(");
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        if(v.getId() == R.id.ans && calcPage.shifted()) {
            menu.setHeaderTitle("Answers");
            for(int i=calc.answers.size()-1;i>=0;i-=1) {
                menu.add(PREV_ANSWER,i,0,calc.answers.get(i));
            }
        } else if(v.getId() == R.id.ans && !calcPage.shifted()) {
            menu.setHeaderTitle("Previous Entries");
            for(int i=calc.oldViews.size()-1;i>=0;i-=1) {
                menu.add(PREV_ENTRY,i,0,calc.oldViews.get(i));
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

    @Override
    public void calcInputAfterTextChanged() {
        String fly = calc.calcOnTheFly();
        if(fly.length() > 0 && calc.viewStr.length() != 0) {
            calcPage.setResult(fly);
        }
    }

    @Override
    public void token(String token) {
        calc.addToken(token, token.length(), getIndex());
        updateView(token.length(), token);
    }

    @Override
    public void function(String token, String viewString) {
        addCalcFn(token, viewString);
    }

    @Override
    public void left() {
        setIndex(getIndex() - calc.bspcHelper(getIndex(),false));
        if(getIndex() < 0) {
            setIndex (calc.viewStr.length());
        }
        calcPage.setCalcSelection(getIndex());
    }

    @Override
    public void right() {
        setIndex (getIndex() + calc.delHelper(getIndex(),false));
        if(getIndex() > calc.viewStr.length()) setIndex (0);
        calcPage.setCalcSelection(getIndex());
    }

    @Override
    public void delete() {
        if (getIndex() < calc.viewStr.length()) {
            int delNum = calc.delHelper(getIndex(),true);
            int ind = getIndex();
            calc.viewStr = calc.viewStr.substring(0,getIndex()) +
                calc.viewStr.substring(getIndex() + delNum);
            calcPage.setCalc(calc.viewStr, ind);
        }
    }

    @Override
    public void backspace() {
        if (getIndex() > 0) {
            int bspcNum = calc.bspcHelper(getIndex(), true);
            calc.viewStr = calc.viewStr.substring(0,getIndex()-bspcNum) +
                calc.viewStr.substring(getIndex());
            int newInd = getIndex() - bspcNum;
            setIndex(newInd);
            calcPage.setCalc(calc.viewStr, getIndex());
        }    }

    @Override
    public void clear() {
        calc.tokens.clear();
        calc.tokenLens.clear();
        calc.viewStr = "";
        setIndex(0);
        calcPage.setCalc(calc.viewStr, getIndex());
    }

    @Override
    public void square() {
        calc.addToken("^", 1, getIndex());
        calc.addToken("2", 1, getIndex()+1);
        updateView(2,"^2");
    }

    @Override
    public void equals() {
        // send to parser
        if(calc.viewStr != null && calc.viewStr.length() > 0) {
            if(calc.execute()) {
                setIndex (0);
                calcPage.setCalc(calc.viewStr, getIndex());
                calcPage.setResult(calc.result);
                updatePrevResults();
            } else {
                Toast.makeText(getActivity(), "Error: Could not calculate", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void updatePrevResults() {
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

    @Override
    public void answer(View v) {
        getActivity().openContextMenu(v);
    }

    @Override
    public void copy() {
        appState.setCopy(calc);
    }

    @Override
    public void paste() {
        //calc.tokenize(copy);
        appState.getCopy(calc);
        setIndex(calc.viewStr.length());
        calcPage.setCalc(calc.viewStr, getIndex());
    }

    @Override
    public void reciprocal() {
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
        calcPage.setResult(calc.result);
        updatePrevResults();
    }

    @Override
    public void percent() {
        calc.tokens.add(0, new Primitive("("));
        calc.tokenLens.add(0, 1);
        calc.tokens.add(new Primitive(")"));
        calc.tokenLens.add(1);
        calc.tokens.add(new Primitive("/"));
        calc.tokenLens.add(1);
        calc.tokens.add(new ComplexNumber(100.0, 0));
        calc.tokenLens.add(3);
        calc.viewStr = "(" + calc.viewStr + ")/100.0";
        calc.execute();
        setIndex(calc.viewStr.length());
        calcPage.setCalc(calc.viewStr, getIndex());
        calcPage.setResult(calc.result);
        updatePrevResults();
    }

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
        Intent intent = new Intent(getContext(), Settings.class);
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
