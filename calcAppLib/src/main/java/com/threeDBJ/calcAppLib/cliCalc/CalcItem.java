package com.threeDBJ.calcAppLib.cliCalc;

public abstract class CalcItem {

    ParserVal pval;

    ParserVal getParserVal () {
        return this.pval;
    }
    
    /* Meant to return token value for me, only implemented
       in Primitive for now */
    public char getVal () {
        return '\0';
    }

    public CalcItem toComplexNumber () {
        return null;
    }

    public int getType () {
        return -1;
    }

    public void setRe (Double newval) {
    }

    public double getRe () {
        return 0.0;
    }

    public boolean isVar () {
        return false;
    }

    public boolean isNumStr () {
        return false;
    }

    public boolean isNum () {
        return false;
    }

    public boolean isPrimitive () {
        return false;
    }

    public boolean isFn () {
        return false;
    }

    public boolean isConstant () {
        return false;
    }

    public void append (String s) {
    }

    public CalcItem copy () {
        return this;
    }

}