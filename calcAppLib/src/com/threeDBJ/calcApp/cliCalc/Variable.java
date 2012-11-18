package cliCalc;

public class Variable extends CalcItem {

    String name;
    public ComplexNumber val;

    public Variable (String s) {
        name = s;
        val = new ComplexNumber (0,0);
        pval = new ParserVal (val);
    }

    public int getType () {
        return 3;
    }

    public String toString () {
        return "x";
    }

    public void setRe (Double newval) {
        val.setRe (newval);
    }

    public double getRe () {
        return val.re;
    }

    public boolean isVar () {
        return true;
    }

    public CalcItem copy () {
        return this;
    }

}