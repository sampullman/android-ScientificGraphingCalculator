package com.threeDBJ.calcAppLib;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.content.Context;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.Toast;
import java.lang.Exception;
import cliCalc.ComplexNumber;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;
import android.content.res.Configuration;
import android.content.pm.ActivityInfo;
import android.app.Dialog;
import android.view.animation.Animation;
import android.view.animation.AlphaAnimation;
import android.widget.TextView.OnEditorActionListener;
import android.text.Editable;
import android.view.inputmethod.EditorInfo;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.Menu;
import android.view.ViewGroup;
import android.util.Log;

import android.support.v4.app.Fragment;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class convCalc extends Fragment {

    static final int FROM=0, TO=1;

    public static final int UNIQUE_ID=2;

    private EditText input;
    private TextView output, unitDisplay;
    ListView convType, convChoice;
    SelectedAdapter convTypeAdapter, convChoiceAdapter;
    private int fromInd=-1, toInd, choiceType=FROM, convChoiceInd=-1, convInd=-1;
    boolean exists=false;
    private String[] curNames;
    private double[] curConvs;
    private double result;
    private String copy;
    private static final String help_text = "-Enter a number at the top by tapping the field, and then select the type of unit conversion from the list on the left. A list of possible conversions will appear, and you can select what you want to convert from/to.\n  -From/to units are color coded with the toggle button above the conversions, which is used to determine what your next selection corresponds to.\n  -copy/paste buttons work between tabs, and pasting evaluates the expression if necessary.\n\n-Please submit bugs and feature requests to 3dbj.dev@gmail.com";

    private CalcApp appState;

    static final String[] convTypes = { "Mass", "Temperature", "Pressure", "Volume", "Distance", "Cooking", "Speed",
					"Base", "Energy" };
    static final String[][] convNames = { { "Microgram","Milligram","Gram","Kilogram",
					    "Metric ton/Megagram","Grain","Dram",
					    "Ounce","Pound","US ton" },
					  { "Celsius","Kelvin","Fahrenheit","Rankine" },
					  { "Pascal (N/m^2)", "Bar", "Technical Atmosphere", "Standard Atmosphere",
					    "Torr", "Pound/sq. inch" },
					  { "Microliter","Milliliter", "Centiliter", "Liter","Kiloliter","Megaliter",
					    "Fifth","Ounce (US)","Gallon (US)","Pint (US)","Quart (US)" },
					  { "Nanometer","Micrometer","Millimeter","Centimeter","Meter",
					    "Kilometer","Inch","Feet","Yard","Mile","League"},
					  { "Teaspoon (US)","Tablespoon (US)","Ounce (US)","Cup (Metric)",
					    "Pint (US)","Quart (US)","Gallon (US)","Barrel (US)",
					    "Milliliter","Centiliter","Liter" },
					  { "Mile/hour","Feet/second","Meter/second","Kilometer/hour",
					    "Kilometer/second","Mile/second","Speed of Light",
					    "Centimeter/second","Inch/second","Knot","Mach" },
					  { "Base 2","Base 3","Base 4","Base 5","Base 6","Base 7","Base 8",
					    "Base 9","Base 10", "Base 16 (Hex)" },
					  { "Millijoule","Joule","Kilojoule","calorie (chem)",
					    "Calorie (nutr)","Watt/hour","Kilowatt/hour","Electronvolt" } };
    static final double[][] convValues = { { 0.000001,0.001,1.0,1000.0,1000000.0,0.06479891,
					     1.7718451953,28.349523125,453.59237,907184.74 },
					   { 274.15,1,255.9277778,0.5555556 },
					   { 1.0, 100000.0, 98066.5, 101325.01, 133.32237, 6894.75728},
					   { 0.000001,0.001, 0.01, 1.0,1000.0,1000000.0,0.7570823568,0.029573529563,
					     3.785411784,0.473176473,0.946352946 },
					   { 0.000000001,0.000001,0.001,0.01,1.0,1000.0,0.0254,
					     0.3048,0.9144,1609.344,4828.0417 },
					   { 0.0049289215938,0.014786764781,0.029573529562,0.25,
					     0.473176473,0.946352946,3.785411784,119.2404712,
					     0.001,0.01,1.0 },
					   { 0.44704,0.3048,1.0,0.27777777778,1000.0,1609.344,
					     299792458.0,0.01,0.0254,0.51444444444,340.29 },
					   { 2,3,4,5,6,7,8,9,10,16 },
					   { 0.001,1.0,1000.0,4.184,4186.8,3600.0,3600000.0,1.6021773e-19 } };

    String unitString="", inputString="", outputString="";

    View v;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	Configuration config = getActivity().getResources().getConfiguration();
	if(config.orientation == 1)
	    v = inflater.inflate(R.layout.conv, container, false);
	else if(config.orientation == 2)
	    v = inflater.inflate(R.layout.conv2, container, false);

	appState = (CalcApp) getActivity().getApplicationContext ();

	this.exists = true;
	return v;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
	super.onActivityCreated(savedInstanceState);
	result = 0;

	convTypeAdapter = new SelectedAdapter(getActivity(), 0, convTypes, Color.GRAY);
	convTypeAdapter.setNotifyOnChange(true);

        convType = (ListView) v.findViewById(R.id.conv_types);
        convType.setAdapter(convTypeAdapter);

        convType.setOnItemClickListener(new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView arg0, View view,
					int position, long id) {
		    // user clicked a list item, make it "selected"
		    convInd = position;
		    convTypeAdapter.setSelectedPosition(convInd);
		    showConvChoice(convInd);
		    convChoiceAdapter.clearSelection();
		    if(unitDisplay != null) {
			unitString = "";
			unitDisplay.setText("");
		    }
		}
	    });

	int[] colors = { Color.argb(225, 0, 200, 225), Color.argb(225, 245, 0, 190) };
	String[] temp = {};
	convChoiceAdapter = new SelectedAdapter(getActivity(), 0, temp, colors);
	convChoiceAdapter.setNotifyOnChange(true);

	convChoice = (ListView) v.findViewById(R.id.conv_choice);
        convChoice.setAdapter(convChoiceAdapter);

	convChoice.setOnItemClickListener(new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView arg0, View view,
					int position, long id) {
		    // user clicked a list item, make it "selected"
		    convChoiceAdapter.setSelectedPosition(position, choiceType);
		    if(choiceType == FROM) {
			choiceType = TO;
			fromInd = position;
			((RadioButton)v.findViewById(R.id.radio_to)).toggle();
		    } else {
			toInd = position;
			choiceType = FROM;
			((RadioButton)v.findViewById(R.id.radio_from)).toggle();
		    }
		    doConversion();
		}
	    });
	RadioButton r = (RadioButton) v.findViewById(R.id.radio_from);
	r.setOnClickListener(makeRadioListener(FROM));
	r = (RadioButton) v.findViewById(R.id.radio_to);
	r.setOnClickListener(makeRadioListener(TO));

	setup();

	if(savedInstanceState != null) {
	    inputString = savedInstanceState.getString("input_string");
	    outputString = savedInstanceState.getString("output_string");
	    input.setText(inputString);
	    output.setText(outputString);
	    convInd = savedInstanceState.getInt("conv_ind");
	    if(convInd != -1) {
		convTypeAdapter.setSelectedPosition(convInd);
		showConvChoice(convInd);
		fromInd = savedInstanceState.getInt("from_ind");
		toInd = savedInstanceState.getInt("to_ind");
		convChoiceAdapter.setSelectedPosition(fromInd, FROM);
		convChoiceAdapter.setSelectedPosition(toInd, TO);
		choiceType = savedInstanceState.getInt("choice_type");
		if(choiceType == TO) {
		    ((RadioButton)v.findViewById(R.id.radio_to)).toggle();
		} else {
		    ((RadioButton)v.findViewById(R.id.radio_from)).toggle();
		}
	    }
	}
    }

    @Override
    public void onDestroyView() {
	super.onDestroyView();
	this.exists = false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
	super.onSaveInstanceState(savedInstanceState);
	savedInstanceState.putString("input_string", input.getText().toString());
	savedInstanceState.putString("output_string", output.getText().toString());
	savedInstanceState.putInt("from_ind", fromInd);
	savedInstanceState.putInt("to_ind", toInd);
	savedInstanceState.putInt("choice_type", choiceType);
	savedInstanceState.putInt("conv_ind", convInd);
    }

    public void setup() {
	input = (EditText) v.findViewById(R.id.conv_input);
	output = (TextView) v.findViewById(R.id.conv_result);
	unitDisplay = (TextView) v.findViewById(R.id.unit_display);

	// Currently does not display in landscape mode
	if(unitDisplay != null)
	    unitDisplay.setText(unitString);

	Button n = (Button) v.findViewById(R.id.copy_conv);
	n.setOnClickListener(copyBtn);
	n = (Button) v.findViewById(R.id.paste_conv);
	n.setOnClickListener(pasteBtn);
    }

    public void showConvChoice(int ind) {
	// Setup up the list view adapter
	if(convChoiceInd != ind) {
	    curNames = convNames[ind];
	    curConvs = convValues[ind];
	    convChoiceAdapter.clear();
	    for(int i=0;i<curNames.length;i+=1) {
		convChoiceAdapter.add(curNames[i]);
	    }
	}
	// Show the list view if it is currently invisible
	if(convChoiceInd == -1) {
	    if(android.os.Build.VERSION.SDK_INT > 13) {
		AlphaAnimation fadeIn = new AlphaAnimation(0f, 1f);
		fadeIn.setDuration(500);
		fadeIn.setFillAfter(true);
		convChoice.startAnimation(fadeIn);
		RadioGroup rg = (RadioGroup) v.findViewById(R.id.radio);
		rg.startAnimation(fadeIn);
	    } else {
		convChoice.setVisibility(View.VISIBLE);
	    }
	}
	convChoiceInd = ind;
    }

    public OnClickListener makeRadioListener(final int ind) {
	return new OnClickListener() {
	    public void onClick(View v) {
		choiceType = ind;
	    }
	};
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
	super.setUserVisibleHint(isVisibleToUser);
	if(this.exists) {
	    copy = appState.shared;
	    if(isVisibleToUser) {
		input.addTextChangedListener(inputChanged);
	    } else {
		input.removeTextChangedListener(inputChanged);
	    }
	}
    }

    @Override
    public void onResume() {
	super.onResume();
	copy = appState.shared;
    }

    /*
    @Override
    public void onConfigurationChanged(Configuration config) {
        super.onConfigurationChanged(config);
	Log.v("config_change", Integer.toString(config.orientation));
        if(config.orientation == 1) {
            setContentView(R.layout.conv);
	} else if(config.orientation == 2) {
            setContentView(R.layout.conv2);
	}
	setup();
    }
    */
    public void onPause() {
	super.onPause();
	appState.shared = copy;
    }

    /* Grabs input, converts it appropriately, and displays conversion.
       Returns a boolean for calls from onContextItemSelected */
    public boolean doConversion() {
	if(!convTypeAdapter.selectionSet() || !convChoiceAdapter.selectionSet())
	    return false;
	try {
	    unitString = curNames[fromInd]+" -> "+curNames[toInd];
	    if(unitDisplay != null) {
		unitDisplay.setText(unitString);
	    }
	    inputString = input.getText().toString();
	    if(inputString.length() == 0) {
		outputString = "";
		output.setText(outputString);
		return false;
	    }
	    double inp = Double.parseDouble(inputString);
	    if(convInd == 1) {
		// Temp conversion is special :/
		result = convertTemp(curNames[fromInd],curNames[toInd],inp);
	    } else if(convInd == 6) {
		// So is base conversion
		outputString = convertBase(inp, (int)curConvs[fromInd], (int)curConvs[toInd]);
		output.setText(outputString);
		return true;
	    } else {
		result = (inp * curConvs[fromInd]) / curConvs[toInd];
	    }
	    result = ComplexNumber.round(result,10);
	    outputString = Double.toString(result);
	    output.setText(outputString);
	} catch (Exception e) {
	    Toast.makeText(getActivity(), "Could not convert.",Toast.LENGTH_LONG).show();
	    outputString = "";
	    output.setText(outputString);
	}
	return true;
    }

    private double convertTemp(String l1, String l2, double x) {
	double n=0;
	if(l1.compareTo("Celsius") == 0)
	    n = x + 273.15;
	else if(l1.compareTo("Kelvin") == 0)
	    n = x;
	else if(l1.compareTo("Fahrenheit") == 0)
	    n = (x + 459.67) * (5.0 / 9.0);
	else if(l1.compareTo("Rankine") == 0)
	    n = x * (5.0 / 9.0);
	if(l2.compareTo("Celsius") == 0)
	    return n - 273.15;
	else if(l2.compareTo("Kelvin") == 0)
	    return n;
	else if(l2.compareTo("Fahrenheit") == 0)
	    return (n * (9.0 / 5.0)) - 459.67;
	else if(l2.compareTo("Rankine") == 0)
	    return n * (9.0 / 5.0);
	return -1;
    }

    private String convertBase(double n, int b1, int b2) {
	String s = (int)n+"";
	int med = Integer.parseInt(s, b1);
	return Integer.toString(med, b2);
    }

    private int indexOf(String s, String[] a) {
	for(int i=0;i<a.length;i+=1) {
	    if(s.compareTo(a[i]) == 0) {
		return i;
	    }
	}
	return -1;
    }

    private OnClickListener pasteBtn = new OnClickListener() {
            public void onClick(View v) {
                String newInp = appState.getConvCopyString();
                input.setText(newInp);
                input.setSelection (newInp.length ());
            }
        };

    private OnClickListener copyBtn = new OnClickListener() {
	    public void onClick(View v) {
		String text = output.getText().toString();
		if(text.length() > 0 && !text.equals("Result")) {
		    appState.setCopy (text);
		}
	    }
	};

    private void showHelp (FragmentManager fm) {
	CalcApp.showTextDialog(fm, "Conversion Help", help_text);
    }

    public void handleOptionsItemSelected(FragmentManager fm, int itemId) {
        if(itemId == R.id.conv_help) {
            showHelp(fm);
        }
    }

    private OnEditorActionListener calcAction = new OnEditorActionListener() {

	    @Override
	    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		if (actionId == EditorInfo.IME_ACTION_DONE) {
		    doConversion();
		}
		return false;
	    }
        };

    private TextWatcher inputChanged = new TextWatcher() {
	    @Override
	    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
	    }

	    @Override
	    public void afterTextChanged(Editable s) {
		doConversion();
	    }

	    @Override
	    public void onTextChanged(CharSequence s, int start, int before, int count) {
	    }
	};

}