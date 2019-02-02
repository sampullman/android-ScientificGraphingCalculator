package com.threeDBJ.calcAppLib.view.page;

import android.app.Activity;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;

import com.threeDBJ.calcAppLib.R;
import com.threeDBJ.calcAppLib.Settings;
import com.threeDBJ.calcAppLib.view.AutoRepeatButton.AutoRepeatButtonBuilder;
import com.threeDBJ.calcAppLib.view.CalcEditText;
import com.threeDBJ.calcAppLib.view.CalcEditText.CalcEditTextBuilder;
import com.threedbj.viewbuilder.ButtonBuilder;
import com.threedbj.viewbuilder.LinearLayoutBuilder;
import com.threedbj.viewbuilder.TextViewBuilder;
import com.threedbj.viewbuilder.generic.GenericTextViewBuilder;
import com.threedbj.viewbuilder.generic.GenericViewBuilder;
import com.threedbj.viewbuilder.style.Style;

import java.util.ArrayList;

import static android.view.Gravity.CENTER;
import static android.view.Gravity.CENTER_VERTICAL;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class CalcPage {
    private static String PREF_KEY_SHIFT = "main_shifted";
    private SharedPreferences prefs;
    private CalcPageInterface page;
    private CalcEditText calcInput;
    private TextView resultText;
    private Button shiftButton;
    private boolean shifted;
    private ArrayList<Shiftable> shiftables = new ArrayList<>();

    public static int ANSWER_COUNT = 3;
    private LinearLayout[] answerRows = new LinearLayout[ANSWER_COUNT];

    public interface CalcPageInterface {
        void answerClick(int row);
        void answerInputClick(int row);
        void calcInputClick(CalcEditText calcInput);
        void calcInputSelection(int start, int end);
        void calcInputAfterTextChanged();

        void token(String token);
        void function(String token, String viewString);
        void left();
        void right();
        void backspace();
        void delete();
        void clear();
        void square();
        void reciprocal();
        void percent();
        void equals();
        void copy();
        void paste();
        void answer(View v);
    }

    public CalcPage(SharedPreferences prefs, CalcPageInterface page) {
        this.prefs = prefs;
        this.shifted = prefs.getBoolean(PREF_KEY_SHIFT, false);
        this.page = page;
    }

    static class AnswerStyle extends Style {
        public void apply(GenericViewBuilder builder) {
            GenericTextViewBuilder t = (GenericTextViewBuilder)builder;
            t.color(R.color.light)
                .textSizeSp(18)
                .gravity(CENTER_VERTICAL)
                .inLinear()
                .width(WRAP_CONTENT)
                .height(MATCH_PARENT)
                .background(R.color.white);
        }
    }
    private static AnswerStyle Answer = new AnswerStyle();

    private static class Shiftable {
        private Button button;
        private String main, alt;
        private OnClickListener mainClick, altClick;
        Shiftable(Button button, String main, OnClickListener mainClick, String alt, OnClickListener altClick) {
            this.button = button;
            this.main = main;
            this.alt = alt;
            this.mainClick = mainClick;
            this.altClick = altClick;
        }
        void shift(boolean shifted) {
            button.setText(shifted ? alt : main);
            button.setOnClickListener(shifted ? altClick : mainClick);
        }
    }

    public void setAnswer(int index, String input, String answer) {
        LinearLayout row = answerRows[index];
        ((Button)row.getChildAt(0)).setText(input);
        row.getChildAt(1).setVisibility(View.VISIBLE);
        ((Button)row.getChildAt(2)).setText(answer);
    }

    public void setCalc(String value, int selection) {
        calcInput.setText(value);
        calcInput.setSelection(selection);
    }

    public void setCalcSelection(int selection) {
        calcInput.setSelection(selection);
    }

    public void setCalcText(String value) {
        calcInput.setText(value);
    }

    public void setResult(String value) {
        resultText.setText(value);
    }

    public boolean shifted() {
        return shifted;
    }

    private void setShifted(boolean shifted) {
        this.shifted = shifted;
        Settings.save(prefs, PREF_KEY_SHIFT, shifted);

        int shiftRes = shifted ? R.drawable.btn_shift_pressed : R.drawable.btn_shift_normal;
        int shiftColor = shifted ? R.color.dark : R.color.light;
        shiftButton.setBackgroundResource(shiftRes);
        shiftButton.setTextColor(ContextCompat.getColor(shiftButton.getContext(), shiftColor));
        for(Shiftable s : shiftables) {
            s.shift(shifted);
        }
    }

    private LinearLayout LL(ViewGroup parent, float weight) {
        return new LinearLayoutBuilder()
            .horizontal()
            .inLinear()
            .style(Style.WIDE)
            .weight(weight)
            .backgroundColor(R.color.white)
            .build(parent);
    }

    private void answerRow(ViewGroup root, int index) {

        LinearLayout row1 = LL(root, 1f);
        answerRows[index] = row1;

        new ButtonBuilder(Answer)
            .paddingDp(6, 0, 0, 0)
            .click(v -> page.answerInputClick(index))
            .build(row1);
        new TextViewBuilder(Answer)
            .text(" = ")
            .visibility(View.INVISIBLE)
            .build(row1);
        new ButtonBuilder(Answer)
            .click(v -> page.answerClick(index))
            .build(row1);

        new TextViewBuilder()
            .width(MATCH_PARENT)
            .height(2)
            .background(R.color.dark_gray)
            .build(root);
    }

    private static ButtonBuilder buttonGen = new ButtonBuilder(Style.TALL)
        .inLinear()
        .color(R.color.light)
        .background(R.drawable.btn_shift)
        .marginDp(1, 1, 1, 1);

    private Button button(ViewGroup parent, String text, OnClickListener listener) {
        return button(parent, text, 0.22f, listener);
    }

    private Button button(ViewGroup parent, String text, float weight, OnClickListener listener) {
        return buttonGen
            .text(text)
            .weight(weight)
            .click(listener)
            .build(parent);
    }

    private void token(ViewGroup parent, String token) {
        button(parent, token, 0.22f, v -> page.token(token));
    }

    private void token(ViewGroup parent, String token, float weight) {
        button(parent, token, weight, v -> page.token(token));
    }

    private Button token(ViewGroup parent, String text, float weight,
                          int mLeft, int mTop, int mRight, int mBottom) {
        return new ButtonBuilder()
            .load(buttonGen)
            .text(text)
            .weight(weight)
            .marginDp(mLeft, mTop, mRight, mBottom)
            .click(v -> page.token(text))
            .build(parent);
    }

    private void fnButton(ViewGroup parent, float weight, String text, String token) {
        buttonGen
            .text(text)
            .weight(weight)
            .click(v -> page.function(token, text))
            .build(parent);
    }

    private void shiftable(ViewGroup parent, String main, OnClickListener mainClick, String alt, OnClickListener altClick) {
        shiftable(parent, 0.22f, main, mainClick, alt, altClick);
    }

    private void shiftable(ViewGroup parent, float weight, String main, OnClickListener mainClick, String alt, OnClickListener altClick) {
        Button b = button(parent, main, weight, mainClick);
        shiftables.add(new Shiftable(b, main, mainClick, alt, altClick));
    }

    private void autoButton(ViewGroup parent, @DrawableRes int res, OnClickListener listener) {
        new AutoRepeatButtonBuilder()
            .load(buttonGen)
            .text("")
            .click(listener)
            .weight(0.22f)
            .background(res)
            .build(parent);
    }

    public View getView(Activity context) {
        LinearLayoutBuilder rowBuilder = new LinearLayoutBuilder(Style.WIDE)
            .inLinear()
            .horizontal()
            .weight(1f)
            .weightSum(1f)
            .background(R.color.dark_gray);

        LinearLayout root = new LinearLayoutBuilder()
            .vertical()
            .style(Style.MATCH)
            .weightSum(12.4f)
            .build(context);

        for(int i=0; i < answerRows.length; i += 1) {
            answerRow(root, i);
        }

        calcInput = new CalcEditTextBuilder()
            .listener((start, end) -> page.calcInputSelection(start, end))
            .click(v -> page.calcInputClick(calcInput))
            .layoutGravity(CENTER)
            .inLinear()
            .style(Style.MATCH)
            .paddingDp(8, 0, 0, 0)
            .marginDp(0, 0, 0, 0)
            .inputType(InputType.TYPE_CLASS_NUMBER)
            .textSizeSp(26)
            .build(LL(root, 1.2f));
        calcInput.requestFocus();
        calcInput.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                page.calcInputAfterTextChanged();
            }
        });

        resultText = new TextViewBuilder()
            .inLinear()
            .style(Style.MATCH)
            .textSizeSp(26)
            .color(R.color.light)
            .gravity(CENTER)
            .backgroundColor(R.color.white)
            .build(LL(root, 1.2f));

        LinearLayout row = rowBuilder.build(root);
        shiftButton = button(row, "shift", 0.17f, v -> setShifted(!shifted));
        autoButton(row, R.drawable.left, v -> page.left());
        autoButton(row, R.drawable.right, v -> page.right());
        shiftable(row, "bspc", v -> page.backspace(), "del", v -> page.delete());
        button(row, "clr", 0.17f, v -> page.clear());

        row = rowBuilder.build(root);
        fnButton(row, 0.17f,"ln", "Log");
        token(row, "7", 0.22f, 3, 3, 1, 1);
        token(row, "8", 0.22f, 0, 3, 1, 1);
        token(row, "9", 0.22f, 0, 3, 3, 1);
        token(row, "+", 0.17f);

        row = rowBuilder.build(root);
        token(row, "^", 0.17f);
        token(row, "4", 0.22f, 3, 0, 1, 1);
        token(row, "5", 0.22f, 0, 0, 1, 1);
        token(row, "6", 0.22f, 0, 0, 3, 1);
        token(row, "-", 0.17f);

        row = rowBuilder.build(root);
        shiftable(row, 0.17f, "sqr", v -> page.square(), "Sqrt", v -> page.token("Sqrt"));
        token(row, "1", 0.22f, 3, 0, 0, 3);
        token(row, "2", 0.22f, 1, 0, 1, 1);
        token(row, "3", 0.22f, 0, 0, 3, 3);
        token(row, "*", 0.17f);

        row = rowBuilder.build(root);
        shiftable(row, 0.17f, "E", v -> page.token("E"), "e", v -> page.token("e"));
        token(row, ".");
        token(row, "0", 0.22f, 3, 0, 3, 3);
        button(row, "(-)", v -> page.token("-"));
        token(row, "/", 0.195f);

        row = rowBuilder.build(root);
        shiftable(row, 0.17f, "1/x", v -> page.reciprocal(), "%", v -> page.percent());
        token(row, "(", 0.22f);
        token(row, ")", 0.22f);
        button(row, "=", 0.4f, v -> page.equals());

        row = rowBuilder.build(root);
        shiftable(row, 0.17f, "PI", v -> page.token("PI"), "i", v -> page.token("i"));
        shiftable(row, "Sin", v -> page.function("Sin", "Sin"),
            "Arcsin", v -> page.function("Arcsin", "Arcsin"));
        shiftable(row, "Cos", v -> page.function("Cos", "Cos"),
            "Arccos", v -> page.function("Arccos", "Arccos"));
        shiftable(row, "Tan", v -> page.function("Tan", "Tan"),
            "Arctan", v -> page.function("Arctan", "Arctan"));
        shiftable(row, "copy", v -> page.copy(), "paste", v -> page.paste());
        shiftable(row, 0.17f, "ans", v -> page.answer(v), "last", v -> page.answer(v));

        setShifted(shifted);

        return root;
    }
}
