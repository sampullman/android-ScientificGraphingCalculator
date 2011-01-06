package com.android.calcApp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.EditText;
import android.view.View.OnClickListener;
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
import calc.*;

import android.widget.TableRow;

public class mainCalc extends Activity {

    private String inputStr,viewStr;
    private EditText io;
    Calculator calc;
    private int state,index;
    private String copy;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	Configuration config = this.getResources().getConfiguration();
	
	if(config.orientation == 1)
	    setContentView(R.layout.calc);
	else if(config.orientation == 2)
	    setContentView(R.layout.calc2);
	
	this.io = (EditText) findViewById(R.id.io);
	this.calc = new Calculator();
	this.state = 0; this.index = 0;

	//InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
	//imm.hideSoftInputFromWindow(io.getWindowToken(), 0);
	//Toast.makeText(mainCalc.this, "Created!",Toast.LENGTH_LONG).show();
	
	//Button nada = (Button) findViewById(R.id.one);
	//one.setOnClickListener(oneBtn);
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
	n.setOnClickListener(makeClickListener("neg","-"));
	n = (Button) findViewById(R.id.div);
	n.setOnClickListener(makeClickListener("/","/"));
	n = (Button) findViewById(R.id.ln);
	n.setOnClickListener(makeClickListener("log(","ln("));
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
	n.setOnClickListener(makeClickListener("sin(","sin("));
	n = (Button) findViewById(R.id.cos);
	n.setOnClickListener(makeClickListener("cos(","cos("));
	n = (Button) findViewById(R.id.tan);
	n.setOnClickListener(makeClickListener("tan(","tan("));
	n = (Button) findViewById(R.id.euler);
	n.setOnClickListener(makeClickListener("e","e"));
	n = (Button) findViewById(R.id.pie);
	n.setOnClickListener(makeClickListener("PI","pi"));
	n = (Button) findViewById(R.id.eye);
	n.setOnClickListener(makeClickListener("I","i"));
	n = (Button) findViewById(R.id.E);
	n.setOnClickListener(makeClickListener("E","E"));
	n = (Button) findViewById(R.id.last);
	n.setOnClickListener(lastBtn);
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

    public void onPause() {
	super.onStop();
	mainTab.shared = copy;
    }

    public void onResume() {
	super.onResume();
	copy = mainTab.shared;
    }
    
    private void updateView(int ind,String ins) {
	StringBuffer st = new StringBuffer(calc.viewStr);
	st.insert(index,ins);
	calc.viewStr = st.toString();
	index += ind;
	if(index > calc.viewStr.length()) index = 0;
	if(index < 0) index = calc.viewStr.length();
	io.setText(calc.viewStr);
	io.setSelection(index);
    }



    private OnClickListener makeClickListener(final String token,final String viewString) {
	return new OnClickListener() {
	    public void onClick(View v) {
		//for(int i=0;i<tokens.length;i+=1)
		calc.addToken(token,viewString.length());
		updateView(viewString.length(),viewString);
	    }
	};
    }

    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenu.ContextMenuInfo menuInfo) {
	if (v.getId() == R.id.ans) {
	    menu.setHeaderTitle("Answers");
	    MenuItem item;
	    for(int i=0;i<calc.answers.size();i+=1) {
		item = menu.add(calc.answers.get(i));
	    }
	}
    }
    
    public boolean onContextItemSelected(MenuItem item) {
	String ans = item.getTitle().toString();
	calc.tokenize(ans);
	updateView(ans.length(),ans);
	return true;
    }

    private OnClickListener shiftBtn = new OnClickListener() {
	    public void onClick(View v) {
		if(state == 0) {
		    Button n = (Button) findViewById(R.id.del);
		    n.setOnClickListener(delBtn);
		    n.setText("del");
		    n = (Button) findViewById(R.id.sin);
		    n.setOnClickListener(makeClickListener("arcsin(","arcsin("));
		    n.setText("arcsin");
		    n.setTextSize(14);
		    n = (Button) findViewById(R.id.cos);
		    n.setOnClickListener(makeClickListener("arccos(","arccos("));
		    n.setTextSize(14);
		    n.setText("arccos");
		    n = (Button) findViewById(R.id.tan);
		    n.setOnClickListener(makeClickListener("arctan(","arctan("));
		    n.setText("arctan");
		    n.setTextSize(14);
		    n = (Button) findViewById(R.id.copy);
		    n.setOnClickListener(pasteBtn);
		    n.setText("paste");
		    state = 1;
		} else {
		    Button n = (Button) findViewById(R.id.del);
		    n.setText("bspc");
		    n.setOnClickListener(bspcBtn);
		    n = (Button) findViewById(R.id.sin);
		    n.setOnClickListener(makeClickListener("sin(","sin("));
		    n.setText("sin");
		    n.setTextSize(18);
		    n = (Button) findViewById(R.id.cos);
		    n.setOnClickListener(makeClickListener("cos(","cos("));
		    n.setText("cos");
		    n.setTextSize(18);
		    n = (Button) findViewById(R.id.tan);
		    n.setOnClickListener(makeClickListener("tan(","tan("));
		    n.setText("tan");
		    n.setTextSize(18);
		    n = (Button) findViewById(R.id.copy);
		    n.setOnClickListener(copyBtn);
		    n.setText("copy");
		    state = 0;
		}
       	    }
	};

    private OnClickListener leftBtn = new OnClickListener() {
	    public void onClick(View v) {
		index -= calc.bspcHelper(index);
		if(index == 0) index = calc.viewStr.length()-1;
		io.setSelection(index);
	    }
	};

    private OnClickListener rightBtn = new OnClickListener() {
	    public void onClick(View v) {
		index += calc.delHelper(index);
		io.setSelection(index);
       	    }
	};

    private OnClickListener delBtn = new OnClickListener() {
	    public void onClick(View v) {
		int delNum = calc.delHelper(index);
		calc.viewStr = calc.viewStr.substring(0,index) +
		    calc.viewStr.substring(index+delNum,calc.viewStr.length());
		io.setText(calc.viewStr);
		io.setSelection(index);
       	    }
	};

    private OnClickListener bspcBtn = new OnClickListener() {
	    public void onClick(View v) {
		int bspcNum = calc.bspcHelper(index);
		//Toast.makeText(mainCalc.this, Integer.toString(index),Toast.LENGTH_SHORT).show();
		calc.viewStr = calc.viewStr.substring(0,index-bspcNum) +
		    calc.viewStr.substring(index,calc.viewStr.length());
		index -= bspcNum;
		io.setText(calc.viewStr);
		io.setSelection(index);
       	    }
	};
    
    private OnClickListener clrBtn = new OnClickListener() {
	    public void onClick(View v) {
		calc.tokens.clear();
		calc.tokenLens.clear();
		calc.viewStr = "";
		index = 0;
		io.setText(calc.viewStr);
		io.setSelection(index);
       	    }
	};

    private OnClickListener sqrBtn = new OnClickListener() {
	    public void onClick(View v) {
		calc.addToken("^",1);
		calc.addToken("2",1);
		updateView(2,"^2");
	    }
	};

    private OnClickListener equalsBtn = new OnClickListener() {
	    public void onClick(View v) {
		// send to parser
		String debug = calc.execute();
		//Toast.makeText(mainCalc.this, calc.tokens.toString(),
		//	   Toast.LENGTH_LONG).show();
		//Toast.makeText(mainCalc.this, calc.tokenLens.toString(),
		//	   Toast.LENGTH_LONG).show();
		index = calc.viewStr.length();
		io.setText(calc.viewStr);
		io.setSelection(index);
	    }
	};
    
    private OnClickListener lastBtn = new OnClickListener() {
	    public void onClick(View v) {
		calc.lastInp();
		io.setText(calc.viewStr);
		index = calc.viewStr.length();
		io.setSelection(index);
	    }
	};

    private OnClickListener ansBtn = new OnClickListener() {
	    public void onClick(View v) {
		openContextMenu(findViewById(R.id.ans));
	    }
	};
    
    private OnClickListener copyBtn = new OnClickListener() {
	    public void onClick(View v) {
		String debug = calc.execute();
		Toast.makeText(mainCalc.this, debug,Toast.LENGTH_SHORT).show();
		index = calc.viewStr.length();
		io.setText(calc.viewStr);
		io.setSelection(index);
		copy = calc.viewStr;
	    }
	};
    
    private OnClickListener pasteBtn = new OnClickListener() {
	    public void onClick(View v) {
		calc.tokenize(copy);
		updateView(copy.length(),copy);
	    }
	};

    private OnClickListener recipBtn = new OnClickListener() {
	    public void onClick(View v) {
		calc.execute();
		calc.tokens.add(0,"/");
		calc.tokens.add(0,"1");
		calc.tokenLens.add(0,1);
		calc.tokenLens.add(0,1);
		calc.execute();
		index = calc.viewStr.length();
		io.setText(calc.viewStr);
		io.setSelection(index);
	    }
	};

    private OnClickListener percentBtn = new OnClickListener() {
	    public void onClick(View v) {
		calc.execute();
		calc.tokens.add("/");
		calc.tokenLens.add(1);
		calc.tokenize("100.0");
		calc.execute();
		index = calc.viewStr.length();
		io.setText(calc.viewStr);
		io.setSelection(index);
	    }
	};

}
