package cliCalc;

import java.lang.Math;

public class FnctObj extends CalcItem {
    private String fnct;

    // true = Rad, false = Deg
    private boolean angleMode;

    public ComplexNumber t1 = new ComplexNumber (0,0);
    public ComplexNumber t2 = new ComplexNumber (0,0);
    public ComplexNumber t3 = new ComplexNumber (0,0);

    public FnctObj (String fnct){
        this.fnct = fnct;
        this.pval = new ParserVal (this);
        this.angleMode = true;
    }

    public FnctObj (String fnct, boolean angleMode) {
        this.fnct = fnct;
        this.pval = new ParserVal (this);
        this.angleMode = angleMode;
    }

    public int getType () {
        return 2;
    }

    public boolean isFn () {
        return true;
    }

    public CalcItem copy () {
        return new FnctObj (fnct);
    }

    void operate (ComplexNumber z, ComplexNumber ret) {
        if (z.im == 0) {
            ret.im = 0;
            if (fnct.equals("Log") )
                ret.re = Math.log (z.re);
            else if (fnct.equals("Sin") ) {
                if (angleMode)
                    ret.re = Math.sin (z.re);
                else
                    ret.re = Math.sin (z.re * (Math.PI / 180.0));
            } else if (fnct.equals("csc") ) {
                if (angleMode)
                    ret.re = 1.0 / Math.sin (z.re);
                else
                    ret.re = 1.0 / Math.sin (z.re * (Math.PI / 180.0));
            } else if (fnct.equals("Arcsin") ) {
                if (angleMode)
                    ret.re = Math.asin (z.re);
                else
                    ret.re = Math.asin (z.re * (Math.PI / 180.0));
            } else if (fnct.equals("Cos") ) {
                if (angleMode)
                    ret.re = Math.cos (z.re);
                else
                    ret.re = Math.cos (z.re * (Math.PI / 180.0));
            } else if (fnct.equals("sec") ) {
                if (angleMode)
                    ret.re = 1.0 / Math.cos (z.re);
                else 
                    ret.re = 1.0 / Math.cos (z.re * (Math.PI / 180.0));
            } else if (fnct.equals("Arccos") ) {
                if (angleMode)
                    ret.re = Math.acos (z.re);
                else 
                    ret.re = Math.acos (z.re * (Math.PI / 180.0));
            } else if (fnct.equals("Tan") ) {
                if (angleMode) 
                    ret.re = Math.tan (z.re);
                else
                    ret.re = Math.tan (z.re * (Math.PI / 180.0));
            } else if (fnct.equals("cot")) {
                if (angleMode) 
                    ret.re = 1.0 / Math.tan (z.re);
                else 
                    ret.re = 1.0 / Math.tan (z.re * (Math.PI / 180.0));
            } else if (fnct.equals("Arctan")) {
                if (angleMode) 
                    ret.re = Math.atan (z.re);
                else
                    ret.re = Math.atan (z.re * (Math.PI / 180.0));
            } else if (fnct.equals("Exp") )
                ret.re = Math.exp (z.re);
            else if (fnct.equals ("Sqrt")) {
                if (z.re >= 0)
                    ret.re = Math.sqrt (z.re);
                else {
                    ret.re = 0;
                    ret.im = Math.sqrt (z.re*-1);
                }
            } else {
                ComplexNumber temp = operate (z);
                ret.re = temp.re;
                ret.im = temp.im;
            }
        } else {
            ComplexNumber temp = operate (z);
            ret.re = temp.re;
            ret.im = temp.im;
        }
    }

    public void setAngleMode (boolean angleMode) {
        this.angleMode = angleMode;
    }

    ComplexNumber operate (ComplexNumber z){
        String fnCheck;
        if (!angleMode) 
            fnCheck = fnct + "_d";
        else fnCheck = fnct;
        if (fnCheck.equals("Sin") )
            return ComplexMath.Sin(z);
        if (fnCheck.equals("Cos") )
            return ComplexMath.Cos(z);
        if (fnCheck.equals("Tan") )
            return ComplexMath.Tan(z);
        if (fnCheck.equals("csc") )
            return ComplexMath.Csc(z);
        if (fnCheck.equals("sec") )
            return ComplexMath.Sec(z);
        if (fnCheck.equals("cot") )
            return ComplexMath.Cot(z);

        if (fnCheck.equals("Sin_d") )
            return ComplexMath.Sin_d(z);
        if (fnCheck.equals("Cos_d") )
            return ComplexMath.Cos_d(z);
        if (fnCheck.equals("Tan_d") )
            return ComplexMath.Tan_d(z);
        if (fnCheck.equals("Csc_d") )
            return ComplexMath.Csc_d(z);
        if (fnCheck.equals("Sec_d") )
            return ComplexMath.Sec_d(z);
        if (fnCheck.equals("Cot_d") )
            return ComplexMath.Cot_d(z);

        if (fnCheck.equals("sinh") || fnCheck.equals ("sinh_d"))
            return ComplexMath.Sinh(z);
        if (fnCheck.equals("cosh") || fnCheck.equals ("cosh_d"))
            return ComplexMath.Cosh(z);
        if (fnCheck.equals("tanh") || fnCheck.equals ("tanh_d"))
            return ComplexMath.Tanh(z);
        if (fnCheck.equals("csch") || fnCheck.equals ("csch_d"))
            return ComplexMath.Csch(z);
        if (fnCheck.equals("sech") || fnCheck.equals ("sech_d"))
            return ComplexMath.Sech(z);
        if (fnCheck.equals("coth") || fnCheck.equals ("coth_d"))
            return ComplexMath.Coth(z);


        if (fnCheck.equals("Arcsin") )
            return ComplexMath.Arcsin(z);
        if (fnCheck.equals("Arccos") )
            return ComplexMath.Arccos(z);
        if (fnCheck.equals("Arctan") )
            return ComplexMath.Arctan(z);
        if (fnCheck.equals("arccsc") )
            return ComplexMath.Arccsc(z);
        if (fnCheck.equals("arcsec") )
            return ComplexMath.Arcsec(z);
        if (fnCheck.equals("arccot") )
            return ComplexMath.Arccot(z);

        if (fnCheck.equals("Arcsin_d") )
            return ComplexMath.Arcsin_d(z);
        if (fnCheck.equals("Arccos_d") )
            return ComplexMath.Arccos_d(z);
        if (fnCheck.equals("Arctan_d") )
            return ComplexMath.Arctan_d(z);
        if (fnCheck.equals("arccsc_d") )
            return ComplexMath.Arccsc_d(z);
        if (fnCheck.equals("arcsec_d") )
            return ComplexMath.Arcsec_d(z);
        if (fnCheck.equals("arccot_d") )
            return ComplexMath.Arccot_d(z);


        if (fnCheck.equals("arcsinh") || fnCheck.equals("arcsinh_d"))
            return ComplexMath.Arcsinh(z);
        if (fnCheck.equals("arccosh") || fnCheck.equals("arccosh_d"))
            return ComplexMath.Arccosh(z);
        if (fnCheck.equals("arctanh") || fnCheck.equals("arctanh_d"))
            return ComplexMath.Arctanh(z);
        if (fnCheck.equals("arccsch") || fnCheck.equals("arccsch_d"))
            return ComplexMath.Arccsch(z);
        if (fnCheck.equals("arcsech") || fnCheck.equals("arcsech_d"))
            return ComplexMath.Arcsech(z);
        if (fnCheck.equals("arccoth") || fnCheck.equals("arccoth_d"))
            return ComplexMath.Arccoth(z);

        if (fnCheck.equals ("Sqrt"))
            return ComplexMath.Sqrt (z);
        if (fnCheck.equals("Exp") )
            return ComplexMath.Exp(z);
        if(fnCheck.equals("Conj") )
            return ComplexMath.Conjugate(z);
        if (fnCheck.equals("Log") )
            return ComplexMath.Log(z);

        System.err.println("NO SPECIFIED FUNCTION: " + fnCheck); 
        return ComplexNumber.newCartesian(0,0);
    }

    public String toString () {
        return fnct;
    }

    static boolean isFnct (String s){
        return (s.equals("Sin") ||
                s.equals("Cos") ||
                s.equals("Tan") ||
                s.equals("Csc") ||
                s.equals("Sec") ||
                s.equals("Cot") ||

                s.equals("Sin_d") ||
                s.equals("Cos_d") ||
                s.equals("Tan_d") ||
                s.equals("Csc_d") ||
                s.equals("Sec_d") ||
                s.equals("Cot_d") ||


                s.equals("Sinh") ||
                s.equals("Cosh") ||
                s.equals("Tanh") ||
                s.equals("Csch") ||
                s.equals("Sech") ||
                s.equals("Coth") ||

                s.equals("Arcsin") ||
                s.equals("Arccos") ||
                s.equals("Arctan") ||
                s.equals("Arccsc") ||
                s.equals("Arcsec") ||
                s.equals("Arccot") ||


                s.equals("Arcsin_d") ||
                s.equals("Arccos_d") ||
                s.equals("Arctan_d") ||
                s.equals("Arccsc_d") ||
                s.equals("Arcsec_d") ||
                s.equals("Arccot_d") ||

                s.equals("Arcsinh") ||
                s.equals("Arccosh") ||
                s.equals("Arctanh") ||
                s.equals("Arccsch") ||
                s.equals("Arcsech") ||
                s.equals("Arccoth") ||

                s.equals("Exp") ||
                s.equals("Conj") ||
                s.equals("Log") ||
                s.equals("Neg") ||
                s.equals("Sqrt")
                );

    }

}
