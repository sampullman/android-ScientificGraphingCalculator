package com.threeDBJ.calcAppLib.view.page;

import android.app.Activity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.threeDBJ.calcAppLib.R;
import com.threedbj.viewbuilder.ButtonBuilder;
import com.threedbj.viewbuilder.EditTextBuilder;
import com.threedbj.viewbuilder.LinearLayoutBuilder;
import com.threedbj.viewbuilder.ListViewBuilder;
import com.threedbj.viewbuilder.RadioButtonBuilder;
import com.threedbj.viewbuilder.RadioGroupBuilder;
import com.threedbj.viewbuilder.TextViewBuilder;
import com.threedbj.viewbuilder.style.Style;

import static android.view.Gravity.BOTTOM;
import static android.view.Gravity.CENTER;
import static android.view.Gravity.CENTER_HORIZONTAL;

public class ConvPage {
    public static final int FROM = 0, TO = 1;
    private ConvPageInterface pageInterface;
    private EditText input;
    private TextView output, unit;
    private RadioGroup radio;
    private RadioButton to, from;
    private ListView convChoice, convType;
    private ListAdapter choiceAdapter, typeAdapter;

    public interface ConvPageInterface {
        void inputChanged();
        void paste();
        void copy();
        void radio(int ind);
        void convChoice(int pos);
        void convType(int pos);
    }

    public ConvPage(ConvPageInterface pageInterface, ListAdapter choiceAdapter, ListAdapter typeAdapter) {
        this.pageInterface = pageInterface;
        setAdapters(choiceAdapter, typeAdapter);
    }

    public void setAdapters(ListAdapter choiceAdapter, ListAdapter typeAdapter) {
        this.choiceAdapter = choiceAdapter;
        this.typeAdapter = typeAdapter;
        if(convChoice != null) {
            convChoice.setAdapter(choiceAdapter);
        }
        if(convType != null) {
            convType.setAdapter(typeAdapter);
        }
    }

    public String getInput() {
        return input.getText().toString();
    }

    public void setInput(String text) {
        input.setText(text);
    }

    public void setInputSelection(int selection) {
        input.setSelection(selection);
    }

    public void setOutput(String text) {
        output.setText(text);
    }

    public String getOutput() {
        return output.getText().toString();
    }

    public void setUnit(String text) {
        unit.setText(text);
    }

    public void showRadio() {
        AlphaAnimation fadeIn = new AlphaAnimation(0f, 1f);
        fadeIn.setDuration(500);
        fadeIn.setFillAfter(true);
        convChoice.startAnimation(fadeIn);
        radio.startAnimation(fadeIn);
    }

    public void setRadio(int ind) {
        ((ind == TO) ? to : from).toggle();
    }

    private TextWatcher inputChanged = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            pageInterface.inputChanged();
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
    };

    public View getView(Activity context) {
        LinearLayout root = new LinearLayoutBuilder()
            .style(Style.MATCH)
            .vertical()
            .weightSum(1f)
            .build(context);

        LinearLayoutBuilder rowBuilder = new LinearLayoutBuilder()
            .inLinear()
            .horizontal()
            .style(Style.WIDE)
            .weight(0.1f);

        LinearLayout row = rowBuilder.build(root);
        input = new EditTextBuilder(Style.TALL)
            .inLinear()
            .textSizeSp(22)
            .singleLine()
            .weight(0.76f)
            .inputType(InputType.TYPE_CLASS_PHONE)
            .imeOptions(EditorInfo.IME_ACTION_DONE)
            .hint("Tap to input value")
            .build(row);
        input.addTextChangedListener(inputChanged);
        new ButtonBuilder(Style.TALL)
            .inLinear()
            .weight(0.24f)
            .textSizeSp(15)
            .background(R.drawable.btn_transparent)
            .text("Paste")
            .click(v -> pageInterface.paste())
            .build(row);

        row = rowBuilder.build(root);
        new TextViewBuilder(Style.TALL)
            .inLinear()
            .weight(0.5f)
            .gravity(CENTER_HORIZONTAL | BOTTOM)
            .textSizeSp(20)
            .bold()
            .text("Conversion")
            .build(row);
        radio = new RadioGroupBuilder(Style.TALL)
            .inLinear()
            .horizontal()
            .weight(0.5f)
            .visibility(View.INVISIBLE)
            .build(row);
        from = new RadioButtonBuilder(Style.TALL)
            .inLinear()
            .color(R.color.from_color)
            .text("From")
            .checked(true)
            .click(v -> pageInterface.radio(FROM))
            .build(radio);
        to = new RadioButtonBuilder(Style.TALL)
            .inLinear()
            .color(R.color.to_color)
            .text("To")
            .click(v -> pageInterface.radio(TO))
            .build(radio);

        row = new LinearLayoutBuilder(Style.WIDE)
            .inLinear()
            .horizontal()
            .weight(0.62f)
            .weightSum(1f)
            .paddingDp(8, 8, 8, 8)
            .build(root);
        convType = new ListViewBuilder(Style.TALL)
            .inLinear()
            .weight(0.5f)
            .marginDp(0, 0, 4, 0)
            .choiceMode(AbsListView.CHOICE_MODE_SINGLE)
            .adapter(typeAdapter)
            .itemClick((adapter, v, pos, id) -> pageInterface.convType(pos))
            .build(row);
        convChoice = new ListViewBuilder(Style.TALL)
            .inLinear()
            .weight(0.5f)
            .marginDp(4, 0, 0, 0)
            .choiceMode(AbsListView.CHOICE_MODE_SINGLE)
            .visibility(View.INVISIBLE)
            .adapter(choiceAdapter)
            .itemClick((adapter, v, pos, id) -> pageInterface.convChoice(pos))
            .build(row);

        row = rowBuilder.weight(0.08f).build(root);
        unit = new TextViewBuilder(Style.MATCH)
            .inLinear()
            .gravity(CENTER)
            .textSizeSp(16)
            .build(row);

        row = rowBuilder.weight(0.1f).build(root);
        output = new TextViewBuilder(Style.TALL)
            .inLinear()
            .textSizeSp(28)
            .singleLine()
            .weight(0.76f)
            .background(R.drawable.btn_transparent)
            .paddingDp(8, 0, 0 ,0)
            .text("Result")
            .build(row);
        new ButtonBuilder(Style.TALL)
            .inLinear()
            .weight(0.24f)
            .textSizeSp(16)
            .background(R.drawable.btn_transparent)
            .text("Copy")
            .click(v -> pageInterface.copy())
            .build(row);

        return root;
    }
}
