package com.threeDBJ.calcAppLib;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
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
import android.view.MenuInflater;
import android.view.Menu;
import android.util.Log;

public class convCalc extends Activity {

    private EditText input;
    private TextView output, unitDisplay;
    private int menFlag,from, to;
    private String[] curNames;
    private double[] curConvs;
    private View type;
    private double result;
    private String copy;
    private static final String help_text = "-Enter a number at the top by tapping the field, and then select the type of unit conversion. A list will pop up with available input currencies. After selecting one the list of target currencies will appear. -copy/paste buttons works between tabs, and copy evaluates the expression if necessary.\n\n-Please submit bugs and feature requests to 3dbj.dev@gmail.com";

    private CalcApp appState;

    private static String[] massNames = { "Microgram","Milligram","Gram","Kilogram",
				   "Metric ton/Megagram","Grain","Dram",
				   "Ounce","Pound","US ton" };
    private static double[] massConvs = { 0.000001,0.001,1.0,1000.0,1000000.0,0.06479891,
				1.7718451953,28.349523125,453.59237,907184.74 };
    private static String[] tempNames = { "Celsius","Kelvin","Fahrenheit","Rankine" };
    private static double[] tempConvs = { 274.15,1,255.9277778,0.5555556 };
    private static String[] volNames = { "Microliter","Milliliter", "Centiliter", "Liter","Kiloliter","Megaliter",
				  "Fifth","Ounce (US)","Gallon (US)","Pint (US)","Quart (US)" };
    // Uses Liter as base
    private static double[] volConvs = { 0.000001,0.001, 0.01, 1.0,1000.0,1000000.0,0.7570823568,0.029573529563,
				  3.785411784,0.473176473,0.946352946 };
    private static String[] distNames = { "Nanometer","Micrometer","Millimeter","Centimeter","Meter",
				   "Kilometer","Inch","Feet","Yard","Mile","League"};
    private double[] distConvs = { 0.000000001,0.000001,0.001,0.01,1.0,1000.0,0.0254,
				   0.3048,0.9144,1609.344,4828.0417 };
    private String[] cookNames = { "Teaspoon (US)","Tablespoon (US)","Ounce (US)","Cup (Metric)",
				   "Pint (US)","Quart (US)","Gallon (US)","Barrel (US)",
				   "Milliliter","Centiliter","Liter" };
    private static double[] cookConvs = { 0.0049289215938,0.014786764781,0.029573529562,0.25,
				   0.473176473,0.946352946,3.785411784,119.2404712,
				   0.001,0.01,1.0 };
    private static String[] speedNames = { "Mile/hour","Feet/second","Meter/second","Kilometer/hour",
				    "Kilometer/second","Mile/second","Speed of Light",
				    "Centimeter/second","Inch/second","Knot","Mach",};
    private static double[] speedConvs = { 0.44704,0.3048,1.0,0.27777777778,1000.0,1609.344,
				    299792458.0,0.01,0.0254,0.51444444444,340.29 };
    // TODO: add hex conversion
    private static String[] baseNames = { "Base 2","Base 3","Base 4","Base 5","Base 6","Base 7","Base 8",
					  "Base 9","Base 10", };
    private static double[] baseConvs = { 0,0,0,0,0,0,0,0,0 };
    private static String[] energyNames = { "Millijoule","Joule","Kilojoule","calorie (chem)",
				       "Calorie (nutr)","Watt/hour","Kilowatt/hour","Electronvolt" };
    private static double[] energyConvs = { 0.001,1.0,1000.0,4.184,4186.8,3600.0,3600000.0,1.6021773e-19 };

    String unitString="", inputString="", outputString="";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

	Configuration config = this.getResources().getConfiguration();

	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);

	if(config.orientation == 1)
	    setContentView(R.layout.conv);
	else if(config.orientation == 2)
	    setContentView(R.layout.conv2);

	appState = (CalcApp) getApplicationContext ();

	menFlag = 0;
	from = -1;
	result = 0;

	setup();
    }

    public void setup() {
	input = (EditText) findViewById(R.id.convInput);
	output = (TextView) findViewById(R.id.convResult);
	unitDisplay = (TextView) findViewById(R.id.unitDisplay);

	input.setText(inputString);
	output.setText(outputString);
	// Currently does not display in landscape mode
	if(unitDisplay != null)
	    unitDisplay.setText(unitString);

	Button n = (Button) findViewById(R.id.mass);
	n.setOnClickListener(makeClickListener(massNames,massConvs,n));
	registerForContextMenu(n);
	n = (Button) findViewById(R.id.temp);
	n.setOnClickListener(makeClickListener(tempNames,tempConvs,n));
	registerForContextMenu(n);
	n = (Button) findViewById(R.id.volume);
	n.setOnClickListener(makeClickListener(volNames,volConvs,n));
	registerForContextMenu(n);
	n = (Button) findViewById(R.id.dist);
	n.setOnClickListener(makeClickListener(distNames,distConvs,n));
	registerForContextMenu(n);
	n = (Button) findViewById(R.id.speed);
	n.setOnClickListener(makeClickListener(speedNames,speedConvs,n));
	registerForContextMenu(n);
	n = (Button) findViewById(R.id.cooking);
	n.setOnClickListener(makeClickListener(cookNames,cookConvs,n));
	registerForContextMenu(n);
	n = (Button) findViewById(R.id.base);
	n.setOnClickListener(makeClickListener(baseNames,baseConvs,n));
	registerForContextMenu(n);
	n = (Button) findViewById(R.id.energy);
	n.setOnClickListener(makeClickListener(energyNames,energyConvs,n));
	registerForContextMenu(n);
	n = (Button) findViewById(R.id.copyConv);
	n.setOnClickListener(copyBtn);
	n = (Button) findViewById(R.id.pasteConv);
	n.setOnClickListener(pasteBtn);
	n = (Button) findViewById(R.id.refreshBtn);
	n.setOnClickListener(refreshBtn);
    }

    @Override
    public void onResume() {
	super.onResume();
	Configuration config = this.getResources().getConfiguration();
	if(config.orientation == 1)
            setContentView(R.layout.conv);
        else if(config.orientation == 2)
            setContentView(R.layout.conv2);
	copy = mainTab.shared;

	setup();
    }

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

    public void onPause() {
	super.onPause();
	mainTab.shared = copy;
    }

    private OnClickListener makeClickListener(final String[] names,final double[] convs,
                                              final Button b) {
        return new OnClickListener() {
            public void onClick(View v) {
                curNames = names;
                curConvs = convs;
                menFlag = 2;
                openContextMenu(b);
                menFlag = 1;
                openContextMenu(b);
            }
        };
    }

    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenu.ContextMenuInfo menuInfo) {
        if(menFlag == 1) {
            menu.setHeaderTitle("Convert from...");
        } else if(menFlag == 2) {
	    menu.close();
	    menu.setHeaderTitle("to...");
	}
	for(int i=0;i<curNames.length;i+=1) {
	    menu.add(0,i,0,curNames[i]);
	}
	type = v;
    }

    /* Grabs input, converts it appropriately, and displays conversion.
       Returns a boolean for calls from onContextItemSelected */
    public boolean doConversion() {
	try {
	    inputString = input.getText().toString();
	    if(inputString.length() == 0) inputString = "0";
	    double inp = Double.parseDouble(inputString);
	    if(curNames[0].compareTo("Celsius") == 0) {
		// Temp conversion is special :/
		result = convertTemp(curNames[from],curNames[to],inp);
	    } else if(curNames[0].compareTo("2") == 0) {
		result = Double.parseDouble(convertBase((int)inp,from+2,to+2));
		result = ComplexNumber.round(result,10);
		outputString = Integer.toString((int)result);
		output.setText(outputString);
		return true;
	    } else {
		result = (inp * curConvs[from]) / curConvs[to];
	    }
	    result = ComplexNumber.round(result,10);
	    outputString = Double.toString(result);
	    output.setText(outputString);
	    menFlag = 0;
	} catch (Exception e) {
	    Toast.makeText(convCalc.this, "Could not convert.",Toast.LENGTH_LONG).show();
	}
	return true;
    }

    public boolean onContextItemSelected(MenuItem item) {
	if(menFlag == 1) {
	    from = item.getItemId();
	    menFlag = 2;
	} else {
	    to = item.getItemId();
	    unitString = curNames[from]+" -> "+curNames[to];
	    unitDisplay.setText(unitString);
	    return doConversion();
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

    private String convertBase(int n,int b1, int b2) {
	int med = 0;
	char[] num = Integer.toString(n).toCharArray();
	int mult = 1;
	for(int i=num.length-1;i>=0;i-=1) {
	    med += (Integer.parseInt(Character.toString(num[i]))*mult);
	    mult *= b1;
	}
	String res = "";
	while(med > b2) {
	    res = Integer.toString(mod(med,b2)) + res;
	    med = med / b2;
	}
	return Integer.toString(med) + res;
    }

    private int mod(int x, int n) {
	while(x >= n) x -= n;
	return x;
    }


    // Res better be big enough!
    /*private void addBase(short res[],int y[], int b) {
	short carry=0;
	for(int i=0;i<y.length;i+=1) {
	    res[i] += carry;
	    carry = res[i] + y[i];
	    if(carry > b=) {
		res[i] = carry - b;
		carry = 1;
	    } else {
		res[i] = carry;
		carry = 0;
	    }
	}
    }*/

    private int indexOf(String s, String[] a) {
	for(int i=0;i<a.length;i+=1) {
	    if(s.compareTo(a[i]) == 0) {
		return i;
	    }
	}
	return -1;
    }

    private OnClickListener refreshBtn = new OnClickListener() {
	    public void onClick(View v) {
		doConversion();
	    }
	};

    private OnClickListener pasteBtn = new OnClickListener() {
            public void onClick(View v) {
                String newInp = Double.toString (appState.getCopyVal ());
                //String newInp = appState.getCopyVal ();
                input.setText(newInp);
                input.setSelection (newInp.length ());
            }
        };

    private OnClickListener copyBtn = new OnClickListener() {
	    public void onClick(View v) {
            appState.setCopy (output.getText().toString());
	    }
	};

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.conv_menu, menu);
        return true;
    }

    private void showHelp () {
        final Dialog dialog = new Dialog(convCalc.this);
        dialog.setContentView(R.layout.calc_help);
        dialog.setTitle("Conversion Calculator Help");
        ((TextView)dialog.findViewById (R.id.calchelp_text)).setText (help_text);
        dialog.setCancelable(true);
        Button n = (Button) dialog.findViewById(R.id.calchelp_ret);
        n.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel ();
            }
            });
        dialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        if(item.getItemId() == R.id.conv_help) {
            showHelp ();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

}