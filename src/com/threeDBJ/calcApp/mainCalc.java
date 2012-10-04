package com.threeDBJ.calcAppLib;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.EditText;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.content.Context;
import java.lang.StringBuffer;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.DialogInterface;
import android.view.WindowManager;
import android.content.pm.ActivityInfo;
import android.app.Dialog;
import android.view.MenuInflater;
import android.view.Menu;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.view.ViewGroup.LayoutParams;
//import calc.*;
import cliCalc.*;
import android.util.Log;

import android.widget.TableRow;

public class mainCalc extends Activity {

    final int PREV_ANSWER = 0;
    final int PREV_ENTRY = 1;
    final int EXTRA_FNS = 2;

    private NoImeEditText io;
    Calculator calc;
    private int state;
    private String copy;
    private Dialog d;

    static final String[] extra_fns = { "sinh", "cosh", "tanh", "arcsinh", "arccosh",
                                        "arctanh", "csc", "sec", "cot"};

    private static final String help_text = "-Use the tabs at the top to switch between calculator, unit conversion, and graphing modes.\n-Press shift for more functions.\n-1/x and % functions evaluate the current expression and then operate on the result.\n-copy/paste works for numbers and expressions across all tabs.\n-last/ans contain previous expressions and answers, respectively\n-5E6 is equivalent to 5*10^6\n\n-Please submit bugs and feature requests to 3dbj.dev@gmail.com";

    CalcApp appState;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration config = this.getResources().getConfiguration();

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);

        if(config.orientation == 1)
            setContentView(R.layout.calc);
        else if(config.orientation == 2)
            setContentView(R.layout.calc2);

        setup ();
    }

    @Override
    public void onResume() {
	super.onResume();
	Configuration config = this.getResources().getConfiguration();
	if(config.orientation == 1)
            setContentView(R.layout.calc);
        else if(config.orientation == 2)
            setContentView(R.layout.calc2);

	setup();
	io.requestFocus ();
        io.setSelection(getIndex ());
    }

    @Override
    public void onConfigurationChanged(Configuration config) {
        super.onConfigurationChanged(config);
        if(config.orientation == 1)
            setContentView(R.layout.calc);
        else if(config.orientation == 2)
            setContentView(R.layout.calc2);
        setup ();
        io.requestFocus ();
        io.setSelection(getIndex ());
    }

    private void setup () {
        appState = (CalcApp) getApplicationContext ();
        this.calc = appState.getMainCalc ();

        this.io = (NoImeEditText) findViewById(R.id.io);
        io.setText(calc.viewStr);
        //io.setOnLongClickListener (defaultLongClick);
        io.setOnClickListener (ioSelect);

        this.state = 0;
        setupButtons ();
    }

    private void setupButtons () {
        Button n = (Button) findViewById(R.id.shift);
        n.setOnClickListener(shiftBtn);
        n = (Button) findViewById(R.id.left);
        n.setOnClickListener(leftBtn);
        n = (Button) findViewById(R.id.right);
        n.setOnClickListener(rightBtn);
        n = (Button) findViewById(R.id.del);
        n.setOnClickListener(bspcBtn);
        n = (Button) findViewById(R.id.clr);
        n.setOnClickListener(clrBtn);
        n = (Button) findViewById(R.id.one);
        n.setOnClickListener(makeClickListener("1","1"));
        n = (Button) findViewById(R.id.two);
        n.setOnClickListener(makeClickListener("2","2"));
        n = (Button) findViewById(R.id.three);
        n.setOnClickListener(makeClickListener("3","3"));
        n = (Button) findViewById(R.id.four);
        n.setOnClickListener(makeClickListener("4","4"));
        n = (Button) findViewById(R.id.five);
        n.setOnClickListener(makeClickListener("5","5"));
        n = (Button) findViewById(R.id.six);
        n.setOnClickListener(makeClickListener("6","6"));
        n = (Button) findViewById(R.id.seven);
        n.setOnClickListener(makeClickListener("7","7"));
        n = (Button) findViewById(R.id.eight);
        n.setOnClickListener(makeClickListener("8","8"));
        n  = (Button) findViewById(R.id.nine);
        n.setOnClickListener(makeClickListener("9","9"));
        n = (Button) findViewById(R.id.zero);
        n.setOnClickListener(makeClickListener("0","0"));
        n = (Button) findViewById(R.id.equals);
        n.setOnClickListener(equalsBtn);
        n = (Button) findViewById(R.id.point);
        n.setOnClickListener(makeClickListener(".","."));
        n = (Button) findViewById(R.id.plus);
        n.setOnClickListener(makeClickListener("+","+"));
        n = (Button) findViewById(R.id.minus);
        n.setOnClickListener(makeClickListener("-","-"));
        n = (Button) findViewById(R.id.mult);
        n.setOnClickListener(makeClickListener("*","*"));
        n = (Button) findViewById(R.id.sign);
        n.setOnClickListener(makeClickListener("-","-"));
        n = (Button) findViewById(R.id.div);
        n.setOnClickListener(makeClickListener("/","/"));
        n = (Button) findViewById(R.id.ln);
        n.setOnClickListener(makeFnClickListener("Log","ln"));
        //Button sqrt = (Button) findViewById(R.id.sqrt);
        //sqrt.setOnClickListener(makeClickListener("sqrt(","sqrt("));
        n = (Button) findViewById(R.id.sqr);
        n.setOnClickListener(sqrBtn);
        n = (Button) findViewById(R.id.pwr);
        n.setOnClickListener(makeClickListener("^","^"));
        n = (Button) findViewById(R.id.lParen);
        n.setOnClickListener(makeClickListener("(","("));
        n = (Button) findViewById(R.id.rParen);
        n.setOnClickListener(makeClickListener(")",")"));
        n = (Button) findViewById(R.id.sin);
        n.setOnClickListener(makeFnClickListener("Sin","sin"));
        n = (Button) findViewById(R.id.cos);
        n.setOnClickListener(makeFnClickListener("Cos","cos"));
        n = (Button) findViewById(R.id.tan);
        n.setOnClickListener(makeFnClickListener("Tan","tan"));
        n = (Button) findViewById(R.id.euler);
        n.setOnClickListener(makeClickListener("e","e"));
        n = (Button) findViewById(R.id.pie);
        n.setOnClickListener(makeClickListener("PI","pi"));
        n = (Button) findViewById(R.id.eye);
        n.setOnClickListener(makeClickListener("i","i"));
        n = (Button) findViewById(R.id.E);
        n.setOnClickListener(makeClickListener("E","E"));
        n = (Button) findViewById(R.id.last);
        n.setOnClickListener(lastBtn);
        registerForContextMenu(n);
        n = (Button) findViewById(R.id.ans);
        n.setOnClickListener(ansBtn);
        registerForContextMenu(n);
        n = (Button) findViewById(R.id.copy);
        n.setOnClickListener(copyBtn);
        n = (Button) findViewById(R.id.recip);
        n.setOnClickListener(recipBtn);
        n = (Button) findViewById(R.id.percent);
        n.setOnClickListener(percentBtn);

    }

    private void updateView(int ind, String ins) {
        StringBuffer st = new StringBuffer(calc.viewStr);
        st.insert(getIndex (),ins);
        calc.viewStr = st.toString();
        Log.v ("update",Integer.toString(getIndex())+" "+Integer.toString(ind));
        setIndex (getIndex() + ind);
        if(getIndex() > calc.viewStr.length()) setIndex (0);
        if(getIndex () < 0) setIndex (calc.viewStr.length());
        io.setText(calc.viewStr);
        io.setSelection(getIndex ());
    }

    private void setIndex (int ind) {
        calc.setViewIndex (ind);
    }

    private int getIndex () {
        return calc.getViewIndex ();
    }

    private OnClickListener makeClickListener(final String token,final String viewString) {
        return new OnClickListener() {
            public void onClick(View v) {
                calc.addToken(token, viewString.length(), getIndex());
                updateView(viewString.length(),viewString);
            }
        };
    }

    private void addCalcFn (String token, String viewString) {
        calc.addToken(token, viewString.length(), getIndex());
        updateView (viewString.length (), viewString);
        calc.addToken("(", 1, getIndex());
        updateView(1,"(");
    }

    private OnClickListener makeFnClickListener(final String token,final String viewString) {
        return new OnClickListener() {
            public void onClick(View v) {
                addCalcFn (token, viewString);
            }
        };
    }

    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenu.ContextMenuInfo menuInfo) {
        Log.v ("cmenu",Integer.toString (R.id.last)+", "+Integer.toString (v.getId ()));
        MenuItem item;
        if(v.getId() == R.id.ans) {
            menu.setHeaderTitle("Answers");
            for(int i=calc.answers.size()-1;i>=0;i-=1) {
                item = menu.add(PREV_ANSWER,i,0,calc.answers.get(i));
            }
	} else if(v.getId() == R.id.last) {
            Log.v ("made it", "useless");
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
            updateView(ans.length(),ans);
            break;
        case PREV_ENTRY:
            calc.lastInp (item.getItemId ());
            io.setText(calc.viewStr);
            setIndex (calc.viewStr.length());
            io.setSelection(getIndex());
            break;
        }
        return true;
    }

    private OnClickListener shiftBtn = new OnClickListener() {
	    public void onClick(View v) {
		if(state == 0) {
		    Button n = (Button) findViewById(R.id.del);
		    n.setOnClickListener(delBtn);
		    n.setText("del");
		    n = (Button) findViewById(R.id.sin);
		    n.setOnClickListener(makeFnClickListener("Arcsin","arcsin"));
		    n.setText("arcsin");
		    n.setTextSize(14);
		    n = (Button) findViewById(R.id.cos);
		    n.setOnClickListener(makeFnClickListener("Arccos","arccos"));
		    n.setTextSize(14);
		    n.setText("arccos");
		    n = (Button) findViewById(R.id.tan);
		    n.setOnClickListener(makeFnClickListener("Arctan","arctan"));
		    n.setText("arctan");
		    n.setTextSize(14);
		    n = (Button) findViewById(R.id.copy);
		    n.setOnClickListener(pasteBtn);
		    n.setText("paste");
            n = (Button) findViewById (R.id.sqr);
            n.setOnClickListener(makeFnClickListener("Sqrt","sqrt"));
            n.setText ("sqrt");
		    state = 1;
		} else {
		    Button n = (Button) findViewById(R.id.del);
		    n.setText("bspc");
		    n.setOnClickListener(bspcBtn);
		    n = (Button) findViewById(R.id.sin);
		    n.setOnClickListener(makeFnClickListener("Sin","sin"));
		    n.setText("sin");
		    n.setTextSize(18);
		    n = (Button) findViewById(R.id.cos);
		    n.setOnClickListener(makeFnClickListener("Cos","cos"));
		    n.setText("cos");
		    n.setTextSize(18);
		    n = (Button) findViewById(R.id.tan);
		    n.setOnClickListener(makeFnClickListener("Tan","tan"));
		    n.setText("tan");
		    n.setTextSize(18);
		    n = (Button) findViewById(R.id.copy);
		    n.setOnClickListener(copyBtn);
		    n.setText("copy");
            n = (Button) findViewById (R.id.sqr);
            n.setOnClickListener(sqrBtn);
            n.setText ("sqr");
		    state = 0;
		}
        }
        };

    private OnLongClickListener defaultLongClick = new OnLongClickListener () {
            public boolean onLongClick (View v) {
                return true;
            }
        };

    private OnClickListener ioSelect = new OnClickListener() {
            public void onClick(View v) {
                setIndex (((TextView)v).getSelectionStart ());
            }
        };

    private OnClickListener leftBtn = new OnClickListener() {
            public void onClick(View v) {
                //Log.v("help",calc.tokens+"\n"+calc.tokenLens);
                setIndex (getIndex() - calc.bspcHelper(getIndex(),false));
                if(getIndex() < 0) setIndex (calc.viewStr.length());
                io.setSelection(getIndex());
            }
        };

    private OnClickListener rightBtn = new OnClickListener() {
            public void onClick(View v) {
                setIndex (getIndex() + calc.delHelper(getIndex(),false));
                if(getIndex() > calc.viewStr.length()) setIndex (0);
                io.setSelection(getIndex());
       	    }
        };

    private OnClickListener delBtn = new OnClickListener() {
            public void onClick(View v) {
                if (getIndex() < calc.viewStr.length ()) {
                    int delNum = calc.delHelper(getIndex(),true);
                    calc.viewStr = calc.viewStr.substring(0,getIndex()) +
                        calc.viewStr.substring(getIndex()+delNum,calc.viewStr.length());
                    io.setText(calc.viewStr);
                    //Log.v("del",calc.tokens+"\n"+calc.tokenLens);
                    io.setSelection(getIndex());
                }
            }
        };

    private OnClickListener bspcBtn = new OnClickListener() {
            public void onClick(View v) {
                if (getIndex() > 0) {
                    int bspcNum = calc.bspcHelper(getIndex(),true);
                    /*
                      Log.v ("bspcButton","nback: "+Integer.toString(bspcNum)+
                      ", getIndex(): "+Integer.toString(getIndex())+
                      ", totLen: "+Integer.toString(calc.viewStr.length()));
                    */
                    calc.viewStr = calc.viewStr.substring(0,getIndex()-bspcNum) +
                        calc.viewStr.substring(getIndex(),calc.viewStr.length());
                    setIndex (getIndex()-bspcNum);
                    io.setText(calc.viewStr);
                    io.setSelection(getIndex());
                }
       	    }
        };

    private OnClickListener clrBtn = new OnClickListener() {
            public void onClick(View v) {
                calc.tokens.clear();
                calc.tokenLens.clear();
                calc.viewStr = "";
                setIndex (0);
                io.setText(calc.viewStr);
                io.setSelection(getIndex());
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
                calc.execute();
                setIndex (calc.viewStr.length());
                io.setText(calc.viewStr);
                io.setSelection(getIndex());
            }
        };

    private OnClickListener lastBtn = new OnClickListener() {
            public void onClick(View v) {
                openContextMenu (findViewById (R.id.last));
            }
        };

    private OnClickListener ansBtn = new OnClickListener() {
            public void onClick(View v) {
                openContextMenu (findViewById( R.id.ans));
            }
        };

    private OnClickListener copyBtn = new OnClickListener() {
            public void onClick(View v) {
                appState.setCopy (calc);
            }
        };

    private OnClickListener pasteBtn = new OnClickListener() {
            public void onClick(View v) {
                //calc.tokenize(copy);
                appState.getCopy (calc);
                io.setText (calc.viewStr);
                io.setSelection (getIndex ());
            }
        };

    private OnClickListener recipBtn = new OnClickListener() {
            public void onClick(View v) {
                calc.execute();
                calc.tokens.add(0, new Primitive ("/"));
                calc.tokens.add(0,new ComplexNumber (1.0,0));
                calc.tokenLens.add(0,1);
                calc.tokenLens.add(0,1);
                calc.execute(false);
                setIndex (calc.viewStr.length());
                io.setText(calc.viewStr);
                io.setSelection(getIndex());
            }
        };

    private OnClickListener percentBtn = new OnClickListener() {
            public void onClick(View v) {
                calc.execute();
                calc.tokens.add(new Primitive ("/"));
                calc.tokenLens.add(1);
                calc.tokens.add(new ComplexNumber (100.0,0));
                calc.tokenLens.add(3);
                calc.execute(false);
                setIndex (calc.viewStr.length());
                io.setText(calc.viewStr);
                io.setSelection(getIndex());
            }
        };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.calc_menu, menu);
        return true;
    }

    private void showHelp () {
        d = new Dialog(mainCalc.this);
        d.setContentView(R.layout.calc_help);
        d.setTitle("Calculator Help");
        ((TextView)d.findViewById (R.id.calchelp_text)).setText (help_text);
        d.setCancelable(true);
        Button n = (Button) d.findViewById(R.id.calchelp_ret);
        n.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                d.cancel ();
            }
            });
        d.show();
    }

    private void showExtraFnsMenu () {
        d = new Dialog(mainCalc.this);
        d.setContentView(R.layout.calc_extrafns);
        d.setTitle ("Extra Functions");
        d.setCancelable(true);
        ListView opts = (ListView) d.findViewById (R.id.extrafns);
        opts.setAdapter(new ArrayAdapter<String>(this, R.layout.list_item, extra_fns));
        opts.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(AdapterView parent, View view,
                                        int position, long id) {
                    String fn = ((TextView) view).getText().toString ();
                    addCalcFn (fn, fn);
                    d.cancel ();
                }
            });
        d.show ();
    }

    private OnFocusChangeListener roundingFcs = new OnFocusChangeListener() {
            public void onFocusChange (View v, boolean hasFocus) {
                if (!hasFocus) {
                    try {
                        String newRnd = ((EditText)d.findViewById (R.id.options_round3)).getText().toString();
                        int newInt = Integer.parseInt (newRnd);
                        if (!newRnd.equals ("") && newInt > 0 && newInt <= 12)
                            calc.round = newInt;
                        Log.v("input","update round");
                    } catch (Exception e) {
                    }
                } else {
                }
            }
        };

    private OnClickListener roundingBtn = new OnClickListener() {
            public void onClick(View v) {
                d.findViewById (R.id.options_round3).performClick ();
            }
        };

    private OnClickListener roundingBtn2 = new OnClickListener() {
            public void onClick(View v) {
                //((EditText)d.findViewById (R.id.options_round3)).setText("");
                Log.v("input","sanity");
            }
        };

    private DialogInterface.OnDismissListener optionsDismiss = new DialogInterface.OnDismissListener () {
            public void onDismiss (DialogInterface dialog) {
                try {
                    String newRnd = ((EditText)d.findViewById (R.id.options_round3)).getText().toString();
                    int newInt = Integer.parseInt (newRnd);
                    if (!newRnd.equals ("") && newInt > 0 && newInt <= 12)
                        calc.round = newInt;
                    Log.v("input","update round");
                } catch (Exception e) {
                }
            }
        };

    private OnClickListener angleBtn = new OnClickListener() {
            public void onClick(View v) {
                if (calc.angleMode)
                    calc.angleMode = false;
                else
                    calc.angleMode = true;
                TextView t = (TextView)d.findViewById (R.id.options_angle3);
                t.setText (calc.getAngleMode ());
            }
        };

    public void showDialog(String title, String text) {
	Dialog dialog = new Dialog(this);
	dialog.setContentView(R.layout.text_alt);
	dialog.setTitle(title);
	TextView t = (TextView) dialog.findViewById(R.id.alt_text);
	t.setText(text);
	dialog.setCancelable(true);
	dialog.show();
    }

    private void showOptionsMenu () {
        d = new Dialog (mainCalc.this);
        d.setContentView (R.layout.calc_options);
        d.setTitle ("Calculator Settings");
        d.setCancelable (true);
        d.setOnDismissListener (optionsDismiss);
        // Rounding option
        TextView t = (TextView)d.findViewById (R.id.options_round1);
        t.setOnClickListener (roundingBtn);
        t = (TextView)d.findViewById (R.id.options_round2);
        t.setOnClickListener (roundingBtn);
        EditText e = (EditText)d.findViewById (R.id.options_round3);
        e.setOnFocusChangeListener (roundingFcs);
        e.setOnClickListener (roundingBtn2);
        e.setText (Integer.toString (calc.round));
        e.setSelection (e.getText().toString().length()-1);
        // Deg/Rad option
        t = (TextView)d.findViewById (R.id.options_angle1);
        t.setOnClickListener (angleBtn);
        t = (TextView)d.findViewById (R.id.options_angle2);
        t.setOnClickListener (angleBtn);
        t = (TextView)d.findViewById (R.id.options_angle3);
        t.setOnClickListener (angleBtn);
        t.setText (calc.getAngleMode ());
        d.show ();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        if(item.getItemId() == R.id.calc_help) {
            showHelp ();
	} else if(item.getItemId() == R.id.extra_fns) {
            showExtraFnsMenu ();
	} else if(item.getItemId() == R.id.options) {
            showOptionsMenu ();
	} else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
