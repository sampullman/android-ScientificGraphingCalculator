package com.threeDBJ.calcAppLib.view.calc;

import android.app.Activity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.threeDBJ.calcAppLib.R;
import com.threeDBJ.calcAppLib.view.CalcEditText;
import com.threeDBJ.calcAppLib.view.CalcEditText.CalcEditTextBuilder;
import com.threedbj.viewbuilder.ButtonBuilder;
import com.threedbj.viewbuilder.LinearLayoutBuilder;
import com.threedbj.viewbuilder.TextViewBuilder;
import com.threedbj.viewbuilder.generic.GenericTextViewBuilder;
import com.threedbj.viewbuilder.generic.GenericViewBuilder;
import com.threedbj.viewbuilder.style.Style;

import static android.view.Gravity.CENTER;
import static android.view.Gravity.CENTER_VERTICAL;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class CalcPage {
    private CalcPageInterface page;
    private CalcEditText calcInput;
    private TextView resultText;

    public static int ANSWER_COUNT = 3;
    private LinearLayout[] answerRows = new LinearLayout[ANSWER_COUNT];

    public interface CalcPageInterface {
        void answerClick(int row);
        void answerInputClick(int row);
        void calcInputClick(CalcEditText calcInput);
        void calcInputSelection(int start, int end);
        void calcInputAfterTextChanged();
    }

    public CalcPage(CalcPageInterface page) {
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
                .background(R.drawable.btn_black_normal);
        }
    }
    public static AnswerStyle Answer = new AnswerStyle();

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

    private void answerRow(ViewGroup root, int index) {

        LinearLayout row1 = new LinearLayoutBuilder()
            .horizontal()
            .inLinear()
            .style(Style.WIDE)
            .weight(0.7f)
            .build(root);
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

    public View getView(Activity context) {

        LinearLayout root = new LinearLayoutBuilder()
            .vertical()
            .style(Style.MATCH)
            .weightSum(10.9f)
            .build(context);

        for(int i=0; i < answerRows.length; i += 1) {
            answerRow(root, i);
        }

        calcInput = new CalcEditTextBuilder()
            .listener((start, end) -> page.calcInputSelection(start, end))
            .click(v -> page.calcInputClick(calcInput))
            .layoutGravity(CENTER)
            .inLinear()
            .style(Style.WIDE)
            .paddingDp(8, 0, 0, 0)
            .marginDp(0, 0, 0, 6)
            .inputType(InputType.TYPE_CLASS_NUMBER)
            .textSizeSp(26)
            .build(root);
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
            .build(new LinearLayoutBuilder(Style.WIDE)
                .inLinear()
                .weight(0.8f)
                .horizontal()
                .build(root));

        return root;
    }
}
