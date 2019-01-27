package com.threeDBJ.calcAppLib.cliCalc;

import java.lang.Math;

import android.util.Log;

public class ComplexMath{

    static ComplexNumber E = ComplexNumber.newCartesian(Math.E, 0);
    static ComplexNumber PI = ComplexNumber.newCartesian(Math.PI, 0);
    static ComplexNumber I = ComplexNumber.newCartesian(0, 1);
    public static ComplexNumber INFINITY = ComplexNumber.newCartesian(Double.POSITIVE_INFINITY, 
							       Double.POSITIVE_INFINITY);

    public static ComplexNumber Zero = ComplexNumber.newCartesian(0,0);
    private static ComplexNumber half = ComplexNumber.newCartesian(0.5,0);
    private static ComplexNumber One = ComplexNumber.newCartesian(1,0);
    private static ComplexNumber Two = ComplexNumber.newCartesian(2,0);
    private static double tolerance = 1e-7;
    // 510-290-1704
    public ComplexMath () {
    }

    static boolean Equal(ComplexNumber z1, ComplexNumber z2){
	return ( ( Math.abs(z1.Re() - z2.Re()) <= tolerance ) &&
		 ( Math.abs(z1.Im() - z2.Im()) <= tolerance ) );
    }

    static ComplexNumber Sum(ComplexNumber z1, ComplexNumber z2){
	return ComplexNumber.newCartesian( z1.Re() + z2.Re() , z1.Im() + z2.Im() );
    }

    static void Add(ComplexNumber z1, ComplexNumber z2, ComplexNumber ret) {
	if(z1 == null || z2 == null) return;
        ret.re = z1.re + z2.re;
        ret.im = z1.im + z2.im;
    }

    static ComplexNumber Difference(ComplexNumber z1, ComplexNumber z2){
	return ComplexNumber.newCartesian( z1.Re() - z2.Re() , z1.Im() - z2.Im() );
    }

    static void Sub(ComplexNumber z1, ComplexNumber z2, ComplexNumber ret) {
	if(z1 == null || z2 == null) return;
        ret.re = z1.re - z2.re;
        ret.im = z1.im - z2.im;
    }

    static ComplexNumber Product(ComplexNumber z1, ComplexNumber z2){
	return ComplexNumber.newPolar(z1.R()*z2.R() , z1.Theta() + z2.Theta() );
    }

    static void Mult(ComplexNumber z1, ComplexNumber z2, ComplexNumber ret) {
	if(z1 == null || z2 == null) return;
        if (z1.im == 0 && z2.im == 0) {
            ret.re = z1.re * z2.re;
            ret.im = 0;
        } else if (z1.re == 0 && z2.im == 0) {
            ret.re = 0;
            ret.im = z1.im * z2.re;
        } else if (z1.im == 0 && z2.re == 0) {
            ret.re = 0;
            ret.im = z1.re * z2.im;
        } else if (z1.re == 0 && z2.re == 0) {
            ret.re = -1 * z1.im * z2.im;
            ret.im = 0;
        } else {
            double R = z1.R()*z2.R();
            double theta = z1.Theta() + z2.Theta();
            ret.re = R*Math.cos(theta);
            ret.im = R*Math.sin(theta);
        }
    }

    static ComplexNumber Quotient(ComplexNumber z1, ComplexNumber z2){
	if ( Equal(z2,Zero) )
	    return INFINITY;
	return ComplexNumber.newPolar(z1.R()/z2.R() , z1.Theta() - z2.Theta() );
    }

    static void Div(ComplexNumber z1, ComplexNumber z2, ComplexNumber ret){
	if(z1 == null || z2 == null) return;
        if ( Equal(z2, Zero) ) {
            ret.re = Double.POSITIVE_INFINITY;
            ret.im = Double.POSITIVE_INFINITY;
        } else if (z1.im == 0 && z2.im == 0) {
            ret.re = z1.re / z2.re;
            ret.im = 0;
        } else {
            double R = z1.R()/z2.R();
            double theta = z1.Theta() - z2.Theta();
            ret.re = R*Math.cos(theta);
            ret.im = R*Math.sin(theta);
        }
    }

    static ComplexNumber Sqrt (ComplexNumber z) {
        return Pow (z, half);
    }

    static ComplexNumber Pow(ComplexNumber z1, ComplexNumber z2){
	if (Equal(z1,Zero) && Equal(z2,Zero)  )
	    return ComplexNumber.newCartesian(Double.NaN, Double.NaN);
	if (Equal(z1,Zero) )
	    return ComplexNumber.newCartesian(0, 0);
	ComplexNumber tmp1 = Product(Log(z1) , z2);
	return ComplexNumber.newPolar(Math.exp(tmp1.Re() ), tmp1.Im() );
    }

    //(a2+b2)^(c/2) * e^(-d*atan2(b,a)) * cos(c*atan2(b,a)+0.5*d*ln(a2+b2) ) +
    //(a2+b2)^(c/2) * e^(-d*atan2(b,a)) * sin(c*atan2(b,a)+0.5*d*ln(a2+b2) ) i
    static void Pow(ComplexNumber z1, ComplexNumber z2, ComplexNumber ret) {
	if(z1 == null || z2 == null) return;
        if (z1.re >= 0 && z1.im == 0 && z2.im == 0) {
	    ret.re = Math.pow(z1.re, z2.re);
        } else {
	    double r2 = z1.re*z1.re + z1.im*z1.im;
	    double p = ComplexNumber.clean(Math.pow(r2, z2.re / 2.0));
	    double theta = z1.arg();
	    double m = -1.0 * z2.im * theta;
	    double angle = (z2.re * theta + 0.5 * z2.im * Math.log(r2));
	    double exp = ComplexNumber.clean(Math.pow(Math.E, m));

	    ret.re = p * exp;
	    ret.im = p * exp;
	    ret.clean();
	    ret.re *= ComplexNumber.clean(Math.cos(angle));
	    ret.im *= ComplexNumber.clean(Math.sin(angle));
        }
    }

    static ComplexNumber Log(ComplexNumber z){
	if (Equal(z,Zero) )
	    return ComplexNumber.newCartesian(Double.NaN, Double.NaN);

	return ComplexNumber.newCartesian(Math.log(z.R() ) , z.Theta() );
    }

    static void Log(ComplexNumber z, ComplexNumber ret){
	if(z == null) return;
        if (Equal(z,Zero) ) {
            ret.re = Double.NaN;
            ret.im = Double.NaN;
        } else if (z.im == 0) {
            ret.re = Math.log (z.re);
            ret.im = 0;
        } else {
            ret.re = Math.log(z.R());
            ret.im = z.Theta();
        }
    }

    static ComplexNumber Exp(ComplexNumber z){
        return Pow(E,z);
    }

    static void Exp(ComplexNumber z, ComplexNumber ret){
	if(z == null) return;
        Pow(E,z,ret);
    }

    static ComplexNumber Neg(ComplexNumber z){
        return ComplexNumber.newCartesian(-1*z.Re() , -1*z.Im() );
    }

    static void Neg(ComplexNumber z, ComplexNumber ret){
	if(z == null) return;
        ret.re = -1.0*z.re;
        ret.im = -1.0*z.im;
    }

    static ComplexNumber Sin(ComplexNumber z){
        return Quotient(Difference( Exp(Product(I,z)) , Exp(Product(Neg(I),z)) ) ,
                        Product(I, Two ));
    }

    static ComplexNumber Csc(ComplexNumber z){
        return Quotient(One, Sin(z) );
    }

    static ComplexNumber Cos(ComplexNumber z){
        return Quotient(Sum( Exp(Product(I,z)) , Exp(Product(Neg(I),z)) ) , Two );
    }

    static ComplexNumber Sec(ComplexNumber z){
	return Quotient(One,Cos(z) );
    }

    static ComplexNumber Tan(ComplexNumber z){
	return Quotient(Sin(z) , Cos(z) );
    }

    static ComplexNumber Cot(ComplexNumber z){
	return Quotient(One,Tan(z) );
    }

    static ComplexNumber Conjugate(ComplexNumber z){
	return z.conjugate();
    }

    static ComplexNumber Arcsin(ComplexNumber z){
	ComplexNumber t1 = Product(I, z);
	ComplexNumber t2 = Pow(Difference(One , Pow(z,Two) ),
			       Quotient(One,Two) );
	ComplexNumber t3 = Log(Sum(t1,t2) );
	return Neg(Product(I,t3));
    }

    static ComplexNumber Arccos(ComplexNumber z){
	ComplexNumber t1 = Pow(Difference(One , Pow(z,Two) ),
			       Quotient(One,Two) );
	ComplexNumber t2 = Product(t1,I);
	ComplexNumber t3 = Log(Sum(z,t2) );
	return Neg(Product(I,t3) );
    }

    static ComplexNumber Arctan(ComplexNumber z){
	ComplexNumber t1 = Product(I,z);
	ComplexNumber t2 = Difference(Log(Difference(One, t1)),
				      Log(Sum(One,t1)));
	return Quotient(Product(I,t2),Two);
    }

    static ComplexNumber Arccsc(ComplexNumber z){
	if (Equal(z,Zero) )
	    return ComplexNumber.newCartesian(Double.NaN,Double.NaN);
	return Arcsin(Quotient(One,z));
    }

    static ComplexNumber Arcsec(ComplexNumber z){
	if (Equal(z,Zero) )
	    return ComplexNumber.newCartesian(Double.NaN,Double.NaN);

	return Arccos(Quotient(One,z));
    }

    static ComplexNumber Arccot(ComplexNumber z){
        if (Equal(z,Zero) )
            return ComplexMath.Quotient(PI,Two);

        return Arctan(Quotient(One,z));
    }

    static ComplexNumber Sinh(ComplexNumber z){
        return Neg(Product(I,Sin(Product(I,z))));
    }

    static ComplexNumber Cosh(ComplexNumber z){
        return Cos(Product(I,z));
    }

    static ComplexNumber Tanh(ComplexNumber z){
        return Quotient(Sinh(z),Cosh(z));
    }

    static ComplexNumber Csch(ComplexNumber z){
        return Quotient(One,Sinh(z));
    }

    static ComplexNumber Sech(ComplexNumber z){
	return Quotient(One,Cosh(z));
    }

    static ComplexNumber Coth(ComplexNumber z){
	return Quotient(One, Tanh(z));
    }

    static ComplexNumber Arcsinh(ComplexNumber z){
	ComplexNumber result = Sum(One, Pow(z,Two) );
	result = Pow(result, Quotient(One,Two) );
	result = Log( Sum(result,z) );
	return result;
    }

    static ComplexNumber Arccosh(ComplexNumber z){
	ComplexNumber result = Pow(Sum(z,One) , Quotient(One, Two) );
	result = Product(Pow(Difference(z,One) , Quotient(One, Two) ) , result);
	result = Log( Sum(result, z) );
	return result;
    }

    static ComplexNumber Arctanh(ComplexNumber z){
	ComplexNumber result = Log(Sum(One,z));
	result = Difference(result,
			    Log(Difference(One,z)));
	result = Quotient(result,Two);
	return result;
    }

    static ComplexNumber Arccsch(ComplexNumber z){
	if (Equal(z,Zero) )
	    return ComplexNumber.newCartesian(Double.NaN, Double.NaN);
	return Arcsinh(Quotient(One,z));
    }

    static ComplexNumber Arcsech(ComplexNumber z){
	if (Equal(z,Zero) )
	    return ComplexNumber.newCartesian(Double.NaN, Double.NaN);
	return Arccosh(Quotient(One,z));
    }

    static ComplexNumber Arccoth(ComplexNumber z){
	ComplexNumber result = Log(Quotient(Sum(z,One),
					    Difference(z,One)));
	result = Quotient(result,Two);
	return result;

    }

    /* CIRCULAR TRIG FUNCTIONS WITH DEGREES */
    static ComplexNumber Sin_d(ComplexNumber z){
	return
	    Sin( ComplexNumber.newCartesian( z.R() * (Math.PI / 180.0), 0) );
    }
    static ComplexNumber Cos_d(ComplexNumber z){
	return
	    Cos( ComplexNumber.newCartesian( z.R() * (Math.PI / 180.0), 0) );
    }
    static ComplexNumber Tan_d(ComplexNumber z){
	return
	    Tan( ComplexNumber.newCartesian( z.R() * (Math.PI / 180.0), 0) );
    }
    static ComplexNumber Csc_d(ComplexNumber z){
	return
	    Csc( ComplexNumber.newCartesian( z.R() * (Math.PI / 180.0), 0) );
    }
    static ComplexNumber Sec_d(ComplexNumber z){
	return
	    Sec( ComplexNumber.newCartesian( z.R() * (Math.PI / 180.0), 0) );
    }
    static ComplexNumber Cot_d(ComplexNumber z){
	return
	    Cot( ComplexNumber.newCartesian( z.R() * (Math.PI / 180.0), 0) );
    }

    static ComplexNumber Arcsin_d(ComplexNumber z){
	return
	    Product( ComplexNumber.newCartesian( 180.0 / Math.PI , 0),
		     Arcsin(z));
    }
    static ComplexNumber Arccos_d(ComplexNumber z){
	return
	    Product( ComplexNumber.newCartesian( 180.0 / Math.PI , 0),
		     Arccos(z));
    }
    static ComplexNumber Arctan_d(ComplexNumber z){
	return
	    Product( ComplexNumber.newCartesian( 180.0 / Math.PI , 0),
		     Arctan(z));
    }
    static ComplexNumber Arccsc_d(ComplexNumber z){
	return
	    Product( ComplexNumber.newCartesian( 180.0 / Math.PI , 0),
		     Arccsc(z));
    }
    static ComplexNumber Arcsec_d(ComplexNumber z){
	return
	    Product( ComplexNumber.newCartesian( 180.0 / Math.PI , 0),
		     Arcsec(z));
    }
    static ComplexNumber Arccot_d(ComplexNumber z){
	return
	    Product( ComplexNumber.newCartesian( 180.0 / Math.PI , 0),
		     Arccot(z));
    }
}
