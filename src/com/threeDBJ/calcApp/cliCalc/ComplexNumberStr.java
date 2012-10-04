package cliCalc;

/* This is just a wrapper class for ComplexNumber that
   makes building a complex number from strings easier */

public class ComplexNumberStr extends CalcItem {

    public String me;

    public ComplexNumberStr (String s) {
        this.me = s;
    }

    public void append (String s) {
        this.me += s;
    }

    public boolean isNumStr () {
        return true;
    }

    public CalcItem copy () {
        return new ComplexNumberStr (me);
    }

    public CalcItem toComplexNumber () {
        try {
            if (me.charAt(me.length() - 1) == 'i') {
                if (me.length () == 1)
                    return new ComplexNumber (0, 1.0);
                return new ComplexNumber(0,Double.valueOf(me.substring(0, me.length() -1)));
            } else {
                return new ComplexNumber(Double.valueOf(me), 0);
            }
        } catch (Exception e) {
            return this;
        }
    }

    public boolean isNum () {
        return true;
    }

}