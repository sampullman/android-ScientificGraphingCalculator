package com.threeDBJ.calcAppLib;

import cliCalc.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.lang.StringBuilder;
import java.util.Collections;

import android.util.Log;

public class Calculator {
    public int oldVal,round=6,viewIndex;
    public boolean converted=false, inf=false, angleMode=true; // True = rad, False = deg
    public String viewStr="", lastAdd="", result="";
    public ArrayList<String> oldViews,answers;
    public ArrayList<CalcItem> tokens, fnToks;
    public ArrayList<ArrayList<CalcItem>> oldTokens;
    public ArrayList<ArrayList<Integer>> oldTokenLens;
    public ArrayList<Integer> tokenLens, fnTokLens;
    private Parser par;
    private final String[] fns = { "Sin","Cos","Tan","Arcsin","Arccos","Arctan",
                                   "Ln","Log","Sqrt", "sinh", "cosh", "tanh", "arcsinh",
                                   "arccosh", "arctanh", "csc", "sec", "cot" };
    private final String[] ops = { "+","-","*","/","^" };
    private final String[] constants = { "PI","e" };

    private static CalcItem zero = new ComplexNumber (0,0);
    private static CalcItem mult = new Primitive ("*");
    private static CalcItem rparen = new Primitive (")");

    int[] locs = new int[100];

    public Calculator() {
        this.par = new Parser(false);
        this.tokens = new ArrayList<CalcItem>();
        this.oldTokens = new ArrayList<ArrayList<CalcItem>>();
        this.oldTokenLens = new ArrayList<ArrayList<Integer>>();
        this.tokenLens = new ArrayList<Integer>();
        this.oldViews = new ArrayList<String>();
        this.answers = new ArrayList<String>();
        Arrays.sort(fns);
        Arrays.sort(ops);
        Arrays.sort(constants);
    }

    public String getAngleMode () {
        if (angleMode) return "Rad";
        return "Deg";
    }

    public int getViewIndex () {
        return viewIndex;
    }

    public void setViewIndex (int ind) {
        viewIndex = ind;
    }

    public boolean empty () {
        return this.tokens.size () == 0;
    }

    float abs (float x) {
        if (x < 0.0) {
            return x * (float)-1.0;
        } else return x;
    }

    static int realToGraph (float r, float rmin, float rmax,
                            float gmin, float gmax) {
        return (int)(((r - rmin) / (rmax - rmin)) * (gmax - gmin));
    }

    void placePoint (float[] fnPts, int i, int max, int ind, int val) {
        if (i == 0) {
            fnPts[0] = ind;
            fnPts[1] = val;
        } else if (i == max) {
            fnPts[(4*((int)i-1))+2] = ind;
            fnPts[(4*((int)i-1))+3] = val;
        } else {
            fnPts[4*((int)i)] = ind;
            fnPts[(4*((int)i))+1] = val;
            fnPts[(4*((int)i-1))+2] = ind;
            fnPts[(4*((int)i-1))+3] = val;
        }
    }

    public void initForGraph () {
        if (!converted) {
            fnToks = deepCopy (tokens);
            fnTokLens = new ArrayList<Integer>(tokenLens);
            convertStrings ();
            converted = true;
        }
    }

    public void initForFnEntry () {
        tokens = fnToks;
        tokenLens = fnTokLens;
        converted = false;
    }

    public void graphFn (float[] fnPts, float xleft, float xright,
                         float ybot, float ytop, float xmin, float xmax,
                         float ymin, float ymax, float unitLen) {
        int nvars = findVarLocs (locs), ind, val;
        double xVal = (double)xleft, incr=(double)unitLen;
        for (int i=0;i<(int)xmax;i+=1) {
            xVal += unitLen;
            for (int j=0;j<nvars;j+=1) {
                tokens.get (locs[j]).setRe (xVal);
            }
            ind = (int)(realToGraph ((float)xVal, xleft, xright, xmin, xmax));
            val = (int)(ymax - realToGraph (executeGraph (),
                                            ybot, ytop, ymin, ymax));
            placePoint (fnPts, i, (int)xmax-1, ind, val);
        }
    }

    public float getTracePt (float xval) {
        int nvars = findVarLocs (locs), ind, val;
        for (int j=0;j<nvars;j+=1) {
            tokens.get (locs[j]).setRe ((double)xval);
        }
        return executeGraph ();
    }

    public int calcZeros (float[] zeros, float xleft, float xright,
                           float ybot, float ytop, float xmin, float xmax,
                           float ymin, float ymax, float unitLen) {
        int nvars = findVarLocs (locs), nzeros=0;
        double xVal = (double)xleft, incr=(double)unitLen, prev=-99999, cur, xint;
        for (int i=0;i<(int)xmax;i+=1) {
            xVal += unitLen;
            setVars (nvars, xVal);
            cur = executeGraph ();
            if ((prev < 0 && prev > -10 && cur > 0 && cur < 10) ||
                (cur < 0 && cur > -10 && prev > 0 && prev < 10)) {
                xint = calcZerosHelp (locs, nvars, xVal-unitLen, prev, xVal, cur);
                zeros[nzeros] = (float)xint;
                nzeros += 1;
            } else if (cur == 0.0) {
                zeros[nzeros] = (float)xVal;
            } else if (cur < 0.1 && cur > 0 && prev < 0.1 && prev > 0) {
                setVars (nvars, xVal + unitLen);
                double next = executeGraph ();
                if (next > cur && prev > cur) {
                    xint = calcZerosHelp (locs, nvars, xVal-unitLen, prev, xVal+unitLen, next);
                    zeros[nzeros] = (float)xint;
                    nzeros += 1;
                }
            } else if (cur > -0.1 && cur < 0 && prev > -0.1 && prev < 0) {
		setVars (nvars, xVal + unitLen);
		double next = executeGraph ();
                if (next < cur && prev < cur) {
                    xint = calcZerosHelp (locs, nvars, xVal-unitLen, prev, xVal+unitLen, next);
                    zeros[nzeros] = (float)xint;
                    nzeros += 1;
                }
            }
            prev = cur;
        }
        return nzeros;
    }

    private void setVars (int nvars, double xVal) {
        for (int j=0;j<nvars;j+=1) {
            tokens.get (locs[j]).setRe (xVal);
        }
    }

    private double calcZerosHelp (int[] locs, int nvars, double x1, double y1,
                                  double x2, double y2) {
        double mid=x2, cur;
        for (int i=0;i<1000;i+=1) {
            mid = (x1 + x2) / 2.0;
            for (int j=0;j<nvars;j+=1) {
                tokens.get (locs[j]).setRe (mid);
            }
            cur = executeGraph ();
            if (Math.abs (cur) < 0.0001) {
                break;
            } else if ((y1 > 0 && cur < 0) || (y1 < 0 && cur > 0)) {
                x2 = mid;
                y2 = cur;
            } else {
                x1 = mid;
                y1 = cur;
            }
        }
        return mid;
    }

    public int findVarLocs (int[] locs) {
        int ret = 0;
        for (int i=0;i<tokens.size ();i+=1) {
            if (tokens.get (i).isVar ()) {
                locs[ret] = i;
                ret += 1;
            }
        }
        return ret;
    }

    public void saveState () {
        if (!oldViews.isEmpty() && !oldTokens.isEmpty() && !oldViews.isEmpty() &&
	    !answers.isEmpty() && oldViews.get(oldViews.size()-1).equals(viewStr)) {
	    oldTokens.remove(oldTokens.size()-1);
	    oldTokenLens.remove(oldTokenLens.size()-1);
	    oldViews.remove(oldViews.size()-1);
	    answers.remove(answers.size()-1);
        }
	this.oldTokens.add(deepCopy (tokens));
	this.oldTokenLens.add (new ArrayList<Integer>(tokenLens));
	this.oldViews.add(this.viewStr);
    }

    public void undoSave() {
	if(!oldViews.isEmpty() && !oldTokens.isEmpty() && !oldViews.isEmpty()) {
	    tokens = oldTokens.remove(oldTokens.size()-1);
	    tokenLens = oldTokenLens.remove(oldTokenLens.size()-1);
	    oldViews.remove(oldViews.size()-1);
	}
    }

    public static ArrayList<CalcItem> deepCopy (ArrayList<CalcItem> arr) {
        ArrayList<CalcItem> ret = new ArrayList<CalcItem>();
        for (int i=0;i<arr.size ();i+=1) {
            ret.add (arr.get (i).copy () );
        }
        return ret;
    }

    public float executeGraph () {
        ComplexNumber res;
        //try {
            par.run(tokens);
            if(par.getError()) {
                return 0;
            } else res = par.getAns();
            //} catch (Exception e) {
            //return 0;
            //}
        if (res == null) return 0;
        return (float) res.Re ();
    }

    public boolean execute () {
        return execute(true);
    }

    public boolean execute(boolean save) {
        ComplexNumber res;
        if (save)
            saveState ();
        convertStrings ();
        try {
            if(tokens.size () == 0) tokens.add (zero);
            par.run(this.tokens);
            if(par.getError()) {
		if(save) undoSave();
		Log.e("calc", "error: "+par.error);
                return false;
            } else res = par.getAns().clean();
        } catch (Exception e) {
	    Log.e("calc", "exception: "+e.getMessage());
	    if(save) undoSave();
            return false;
        }
        if (res == null) {
	    Log.e("calc", "sharror");
	    if(save) undoSave();
            return false;
        }
        res = res.round(this.round);
        // Can probably replace with .clear()
        this.tokens = new ArrayList<CalcItem>();
        this.tokenLens = new ArrayList<Integer>();
        this.result = res.toString ();
	this.viewStr = "";
        if (this.result.equals ("INFINITY") || this.result.equals ("NaN")) {
            this.inf = true;
        } else {
            //tokenize (this.viewStr);
            this.answers.add(result);
        }
        lastAdd = "";
	return true;
    }

    public String calcOnTheFly() {
        ComplexNumber res;
	ArrayList<CalcItem> flyToks = deepCopy(this.tokens);
	ArrayList<Integer> tokLens = new ArrayList<Integer>(this.tokenLens);
        convertStrings (flyToks, tokLens);
        try {
            if(flyToks.size() == 0) return "";
            par.run(flyToks);
            if(par.getError()) {
                return "";
            } else res = par.getAns().clean();
        } catch (Exception e) {
            return "";
        }
        res = res.round(this.round);
	return res.toString();
    }

    public double getValue () {
        ComplexNumber res;
        convertStrings ();
        try {
            if(tokens.size () == 0) tokens.add (zero);
            par.run(this.tokens);
            if(par.getError()) {
                return 0;
            } else res = par.getAns();
        } catch (Exception e) {
            return 0;
        }
        if (res == null) {
            return 0;
        }
        return res.re;
    }

    public void convertStrings () {
	convertStrings(this.tokens, this.tokenLens);
    }

    public void convertStrings (ArrayList<CalcItem> tokens, ArrayList<Integer> tokenLens) {
        int nparens = 0;
        CalcItem cur;
        for (int i=0;i<tokens.size ();i+=1) {
            cur = tokens.get (i);
            if (cur.isNumStr () && i != 0) {
                if (tokens.get (i-1).isNumStr ()) {
                    ((ComplexNumberStr)tokens.get (i-1)).me +=
                        ((ComplexNumberStr)cur).me;
                    tokens.remove (i);
                    i -= 1;
                }
            } else if (cur.getVal () == '(') {
                nparens += 1;
		if(i != 0 && tokens.get(i-1).getVal() == ')' && cur.getVal() == '(') {
		    tokens.add (i, mult);
                    tokenLens.add (i,1);
		}
		i += 1;
            } else if (cur.getVal () == ')') {
                nparens -= 1;
            } else if (i != 0) {
                if (tokens.get (i-1).isNum () && (cur.isFn () || cur.isVar () || cur.isConstant ())) {
                    tokens.add (i, mult);
                    tokenLens.add (i,1);
                }
            } else if (cur.isFn ()) {
                ((FnctObj)cur).setAngleMode (angleMode);
            }
        }
        for (int i=0;i<tokens.size ();i+=1) {
            cur = tokens.get (i);
            if (cur.isNumStr ())
                tokens.set (i, cur.toComplexNumber ());
        }
        for (int i=0;i<nparens;i+=1) {
            tokens.add (rparen);
        }
    }

    public void tokenize (String tok) {
        String t;
        for (int i=0;i<tok.length ();i+=1) {
            t = tok.substring (i,i+1);
            addToken (t, 1, i);
        }
    }

    public int delHelper (int index, boolean remove) {
        return delHelper (index, remove, this.tokens, this.tokenLens);
    }

    // Given an index, returns the length of the next token and deletes it.
    public int delHelper(int index, boolean remove,
                         ArrayList<CalcItem> toks, ArrayList<Integer> tokLens) {
        int totLen=0;
        for(int i=0;i<toks.size();i+=1) {
            if(totLen == index) {
                if(remove) {
                    toks.remove(i);
                    return tokLens.remove(i);
                } else {
                    return tokLens.get(i);
                }
            }
            totLen += tokLens.get(i);
        }
        return 1;
    }

    public int bspcHelper (int index, boolean remove) {
        return bspcHelper (index, remove, this.tokens, this.tokenLens);
    }

    // Same as above, but for previous token
        public int bspcHelper(int index, boolean remove,
                              ArrayList<CalcItem> toks, ArrayList<Integer> tokLens) {
        int totLen=0;
        if(index == 0) return 1;
        for(int i=0;i<toks.size();i+=1) {
            totLen += tokLens.get(i);
            if(totLen == index) {
                if(remove) {
                    toks.remove(i);
                    return tokLens.remove(i);
                } else {
                    return tokLens.get(i);
                }
            }
        }
        toks.remove(tokLens.size()-1);
        return tokLens.remove(tokLens.size()-1);
    }

    CalcItem makeCalcItem (String t) {
        if (isNumeric (t)) {
            return new ComplexNumberStr (t);
        } else if (isPrimitive (t)) {
            return new Primitive (t);
        } else if (isFn (t)) {
            return new FnctObj (t, angleMode);
        } else if (isConstant (t)) {
            return new Constant (t);
        } else if (isVar (t)) {
            return new Variable (t);
        } else {
            return null;
        }
    }

    public void addToken(String t, int len, int index) {
        int totLen=0;
        CalcItem add;
        if (this.inf) {
            viewIndex = 0;
            inf = false;
            index = 0;
            viewStr = "";
        }
        for(int i=0;i<tokens.size()+1;i+=1) {
            if(totLen == index) {
                /*
                if (i != 0) {
                    if (tokens.get (i-1).isNumStr () &&
                        (isNumeric (t) || (t.equals ("-") && lastAdd.equals ("E")))) {
                        tokens.get (i-1).append (t);
                        tokenLens.set (i-1, tokenLens.get (i-1)+1);
                        break;
                    }
                }
                */
                if (t.equals ("-") && lastAdd.equals ("E")) {
                    add = new ComplexNumberStr (t);
                } else {
                    add = makeCalcItem (t);
                }
                tokens.add(i, add);
                tokenLens.add(i, len);
                break;
            }
            totLen += tokenLens.get(i);
        }
        lastAdd = t;
    }

    public void lastInp(int lastNum) {
        this.tokens = deepCopy (this.oldTokens.get(lastNum));
        this.viewStr = this.oldViews.get(lastNum);
        this.tokenLens = new ArrayList<Integer> (this.oldTokenLens.get(lastNum));
    }

    public String lastAns (int lastNum) {
        String ans = answers.get (lastNum);
        String t;
        for (int i=0;i<ans.length ();i+=1) {
            t = ans.substring (i,i+1);
            addToken (t, 1, viewIndex + i);
        }
        return ans;
    }

    public boolean isReg(String s) {
        return (!isFn(s) && !isConstant(s) && !isOp(s));
    }

    public boolean isVar (String s) {
        return s.equals ("x");
    }

    public boolean isFn(String s) {
        if(Arrays.binarySearch(fns,s) >= 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isConstant(String s) {
        if(Arrays.binarySearch(constants,s) >= 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isPrimitive (String s) {
        return isOp (s) ||
            s.equals ("(") ||
            s.equals (")");
    }

    public boolean isOp(String s) {
        if(Arrays.binarySearch(ops,s) >= 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isNumeric (String s) {
        if (s.equals("0") ||
            s.equals("1") ||
            s.equals("2") ||
            s.equals("3") ||
            s.equals("4") ||
            s.equals("5") ||
            s.equals("6") ||
            s.equals("7") ||
            s.equals("8") ||
            s.equals("9") ||
            s.equals("i") ||
            s.equals("E") ||
            s.equals(".") ) {
            return true;
        } else return false;
    }

}
