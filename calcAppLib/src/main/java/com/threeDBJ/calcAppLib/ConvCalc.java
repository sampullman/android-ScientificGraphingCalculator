package com.threeDBJ.calcAppLib;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;

import android.preference.PreferenceManager;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.threeDBJ.calcAppLib.CalcTabsActivity.CalcTab;
import com.threeDBJ.calcAppLib.cliCalc.ComplexNumber;
import com.threeDBJ.calcAppLib.view.page.ConvPage;
import com.threeDBJ.calcAppLib.view.page.ConvPage.ConvPageInterface;
import static com.threeDBJ.calcAppLib.view.page.ConvPage.*;

public class ConvCalc extends CalcTab implements ConvPageInterface {
    private Activity activity;

    private ConvPage page;
    private SelectedAdapter convTypeAdapter, convChoiceAdapter;
    private int fromInd = -1, toInd, choiceType = FROM, convChoiceInd = -1, convInd = -1;
    private String[] curNames;
    private double[] curConvs;
    private double result;
    private String copy;
    private static final String help_text = "-Enter a number at the top by tapping the field, and then select the type of unit conversion from the list on the left. A list of possible conversions will appear, and you can select what you want to convert from/to.\n  -From/to units are color coded with the toggle button above the conversions, which is used to determine what your next selection corresponds to.\n  -copy/paste buttons work between tabs, and pasting evaluates the expression if necessary.\n\n-Please submit bugs and feature requests to 3dbj.dev@gmail.com";

    private CalcApp appState;

    private static final String[] convTypes = {"Mass", "Temperature", "Pressure", "Volume", "Distance", "Cooking", "Speed",
            "Base", "Energy"};
    private static final String[][] convNames = {{"Microgram", "Milligram", "Gram", "Kilogram",
            "Metric ton/Megagram", "Grain", "Dram",
            "Ounce", "Pound", "US ton"},
            {"Celsius", "Kelvin", "Fahrenheit", "Rankine"},
            {"Pascal (N/m^2)", "Bar", "Technical Atmosphere", "Standard Atmosphere",
                    "Torr", "Pound/sq. inch"},
            {"Microliter", "Milliliter", "Centiliter", "Liter", "Kiloliter", "Megaliter",
                    "Fifth", "Ounce (US)", "Gallon (US)", "Pint (US)", "Quart (US)"},
            {"Nanometer", "Micrometer", "Millimeter", "Centimeter", "Meter",
                    "Kilometer", "Inch", "Feet", "Yard", "Mile", "League"},
            {"Teaspoon (US)", "Tablespoon (US)", "Ounce (US)", "Cup (Metric)",
                    "Pint (US)", "Quart (US)", "Gallon (US)", "Barrel (US)",
                    "Milliliter", "Centiliter", "Liter"},
            {"Mile/hour", "Feet/second", "Meter/second", "Kilometer/hour",
                    "Kilometer/second", "Mile/second", "Speed of Light",
                    "Centimeter/second", "Inch/second", "Knot", "Mach"},
            {"Base 2", "Base 3", "Base 4", "Base 5", "Base 6", "Base 7", "Base 8",
                    "Base 9", "Base 10", "Base 16 (Hex)"},
            {"Millijoule", "Joule", "Kilojoule", "calorie (chem)",
                    "Calorie (nutr)", "Watt/hour", "Kilowatt/hour", "Electronvolt"}};
    private static final double[][] convValues = {{0.000001, 0.001, 1.0, 1000.0, 1000000.0, 0.06479891,
            1.7718451953, 28.349523125, 453.59237, 907184.74},
            {274.15, 1, 255.9277778, 0.5555556},
            {1.0, 100000.0, 98066.5, 101325.01, 133.32237, 6894.75728},
            {0.000001, 0.001, 0.01, 1.0, 1000.0, 1000000.0, 0.7570823568, 0.029573529563,
                    3.785411784, 0.473176473, 0.946352946},
            {0.000000001, 0.000001, 0.001, 0.01, 1.0, 1000.0, 0.0254,
                    0.3048, 0.9144, 1609.344, 4828.0417},
            {0.0049289215938, 0.014786764781, 0.029573529562, 0.25,
                    0.473176473, 0.946352946, 3.785411784, 119.2404712,
                    0.001, 0.01, 1.0},
            {0.44704, 0.3048, 1.0, 0.27777777778, 1000.0, 1609.344,
                    299792458.0, 0.01, 0.0254, 0.51444444444, 340.29},
            {2, 3, 4, 5, 6, 7, 8, 9, 10, 16},
            {0.001, 1.0, 1000.0, 4.184, 4186.8, 3600.0, 3600000.0, 1.6021773e-19}};

    private String unitString = "", inputString = "", outputString = "";

    public ConvCalc(Activity activity) {
        this.activity = activity;
    }

    public View getView() {
        appState = (CalcApp) activity.getApplicationContext();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);

        page = new ConvPage(this, convChoiceAdapter, convTypeAdapter);
        View v = page.getView(activity);

        result = 0;

        convTypeAdapter = new SelectedAdapter(activity, 0, convTypes, Color.GRAY);
        convTypeAdapter.setNotifyOnChange(true);

        int[] colors = {Color.argb(225, 0, 200, 225), Color.argb(225, 245, 0, 190)};
        String[] temp = {};
        convChoiceAdapter = new SelectedAdapter(activity, 0, temp, colors);
        convChoiceAdapter.setNotifyOnChange(true);
        page.setAdapters(convChoiceAdapter, convTypeAdapter);

        page.setUnit(unitString);

        inputString = prefs.getString("input_string", "");
        outputString = prefs.getString("output_string", "");
        page.setInput(inputString);
        page.setOutput(outputString);
        convInd = prefs.getInt("conv_ind", 0);
        if(convInd != -1) {
            convTypeAdapter.setSelectedPosition(convInd);
            showConvChoice(convInd);
            fromInd = prefs.getInt("from_ind", 0);
            toInd = prefs.getInt("to_ind", 0);
            convChoiceAdapter.setSelectedPosition(fromInd, FROM);
            convChoiceAdapter.setSelectedPosition(toInd, TO);
            choiceType = prefs.getInt("choice_type", 0);
            page.setRadio(choiceType);
        }
        return v;
    }

    private void setUnit(String text) {
        unitString = text;
        page.setUnit(text);
    }

    @Override
    public void pause(SharedPreferences.Editor edit) {
        edit.putString("input_string", page.getInput());
        edit.putString("output_string", page.getOutput());
        edit.putInt("from_ind", fromInd);
        edit.putInt("to_ind", toInd);
        edit.putInt("choice_type", choiceType);
        edit.putInt("conv_ind", convInd);
        appState.shared = copy;
    }

    @Override
    public void resume() {
        copy = appState.shared;
    }

    @Override
    public void convChoice(int pos) {
        // user clicked a list item, make it "selected"
        convChoiceAdapter.setSelectedPosition(pos, choiceType);
        page.setRadio(choiceType);
        if (choiceType == FROM) {
            choiceType = TO;
            fromInd = pos;
        } else {
            toInd = pos;
            choiceType = FROM;
        }
        doConversion();
    }

    @Override
    public void convType(int pos) {
        // user clicked a list item, make it "selected"
        convInd = pos;
        convTypeAdapter.setSelectedPosition(convInd);
        showConvChoice(convInd);
        convChoiceAdapter.clearSelection();
        setUnit("");
    }

    private void showConvChoice(int ind) {
        // Setup up the list view adapter
        if(convChoiceInd != ind) {
            curNames = convNames[ind];
            curConvs = convValues[ind];
            convChoiceAdapter.clear();

            for(String curName : curNames) {
                convChoiceAdapter.add(curName);
            }
        }
        // Show the list view if it is currently invisible

        if(convChoiceInd == -1) {
            page.showRadio();
        }
        convChoiceInd = ind;
    }

    @Override
    public void radio(int ind) {
        choiceType = ind;
    }

    /* Grabs input, converts it appropriately, and displays conversion.
       Returns a boolean for calls from onContextItemSelected */
    private void doConversion() {
        if(!convTypeAdapter.selectionSet() || !convChoiceAdapter.selectionSet()) {
            return;
        }
        try {
            setUnit(curNames[fromInd] + " -> " + curNames[toInd]);

            inputString = page.getInput();
            if(inputString.length() == 0) {
                outputString = "";
                page.setOutput(outputString);
            }
            double inp = Double.parseDouble(inputString);
            if(convInd == 1) {
                // Temp conversion is special :/
                result = convertTemp(curNames[fromInd], curNames[toInd], inp);
            } else if(convInd == 6) {
                // So is base conversion
                outputString = convertBase(inp, (int) curConvs[fromInd], (int) curConvs[toInd]);
                page.setOutput(outputString);
            } else {
                result = (inp * curConvs[fromInd]) / curConvs[toInd];
            }
            result = ComplexNumber.round(result, 10);
            outputString = Double.toString(result);
            page.setOutput(outputString);
        } catch(Exception e) {
            Toast.makeText(activity, "Could not convert.", Toast.LENGTH_LONG).show();
            outputString = "";
            page.setOutput(outputString);
        }
    }

    private double convertTemp(String l1, String l2, double x) {
        double n = 0;
        if(l1.compareTo("Celsius") == 0) {
            n = x + 273.15;
        } else if(l1.compareTo("Kelvin") == 0) {
            n = x;
        } else if(l1.compareTo("Fahrenheit") == 0) {
            n = (x + 459.67) * (5.0 / 9.0);
        } else if(l1.compareTo("Rankine") == 0) {
            n = x * (5.0 / 9.0);
        }
        if(l2.compareTo("Celsius") == 0) {
            return n - 273.15;
        } else if(l2.compareTo("Kelvin") == 0) {
            return n;
        } else if(l2.compareTo("Fahrenheit") == 0) {
            return (n * (9.0 / 5.0)) - 459.67;
        } else if(l2.compareTo("Rankine") == 0) {
            return n * (9.0 / 5.0);
        }
        return -1;
    }

    private String convertBase(double n, int b1, int b2) {
        String s = (int) n + "";
        int med = Integer.parseInt(s, b1);
        return Integer.toString(med, b2);
    }

    @Override
    public void paste() {
        String newInp = appState.getConvCopyString();
        page.setInput(newInp);
        page.setInputSelection(newInp.length());
    }

    @Override
    public void copy() {
        String text = page.getOutput();
        if(text.length() > 0 && !text.equals("Result")) {
            appState.setCopy(text);
        }
    }

    private void showHelp() {
        CalcApp.showTextDialog(activity, "Conversion Help", help_text);
    }

    @Override
    public void handleOptionsItemSelected(int itemId) {
        if(itemId == R.id.conv_help) {
            showHelp();
        }
    }

    private OnEditorActionListener calcAction = (v, actionId, event) -> {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            doConversion();
        }
        return false;
    };

    @Override
    public void inputChanged() {
        doConversion();
    }

}