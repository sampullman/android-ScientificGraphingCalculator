package cliCalc;
import java.lang.Math;
final class ComplexMath{

    static ComplexNumber E = ComplexNumber.newCartesian(Math.E, 0);
    static ComplexNumber PI = ComplexNumber.newCartesian(Math.PI, 0);
    static ComplexNumber I = ComplexNumber.newCartesian(0, 1);
    static ComplexNumber INFINITY = ComplexNumber.newCartesian(Double.POSITIVE_INFINITY, 
							       Double.POSITIVE_INFINITY);

    private static ComplexNumber Zero = ComplexNumber.newCartesian(0,0);
    private static ComplexNumber One = ComplexNumber.newCartesian(1,0);
    private static ComplexNumber Two = ComplexNumber.newCartesian(2,0);
    private static double tolerance = 1e-7;

    static boolean Equal(ComplexNumber z1, ComplexNumber z2){
	return ( ( Math.abs(z1.Re() - z2.Re()) <= tolerance ) &&
		 ( Math.abs(z1.Im() - z2.Im()) <= tolerance ) );
    }

    static ComplexNumber Sum(ComplexNumber z1, ComplexNumber z2){
	return ComplexNumber.newCartesian( z1.Re() + z2.Re() , z1.Im() + z2.Im() );
    }

    static ComplexNumber Difference(ComplexNumber z1, ComplexNumber z2){
	return ComplexNumber.newCartesian( z1.Re() - z2.Re() , z1.Im() - z2.Im() );
    }

    static ComplexNumber Product(ComplexNumber z1, ComplexNumber z2){
	return ComplexNumber.newPolar(z1.R()*z2.R() , z1.Theta() + z2.Theta() );
    }

    static ComplexNumber Quotient(ComplexNumber z1, ComplexNumber z2){
	if ( Equal(z2,Zero) )
	    return INFINITY;
	return ComplexNumber.newPolar(z1.R()/z2.R() , z1.Theta() - z2.Theta() );
    }
	
    static ComplexNumber Pow(ComplexNumber z1, ComplexNumber z2){
	if (Equal(z1,Zero) && Equal(z2,Zero)  )
	    return ComplexNumber.newCartesian(Double.NaN, Double.NaN);
	if (Equal(z1,Zero) )
	    return ComplexNumber.newCartesian(0, 0);
	    
	ComplexNumber tmp1 = Product(Log(z1) , z2);
	return ComplexNumber.newPolar(Math.exp(tmp1.Re() ), tmp1.Im() );
    }

    static ComplexNumber Log(ComplexNumber z){
	if (Equal(z,Zero) )
	    return ComplexNumber.newCartesian(Double.NaN, Double.NaN);

	return ComplexNumber.newCartesian(Math.log(z.R() ) , z.Theta() );
    }

    static ComplexNumber Exp(ComplexNumber z){
	return Pow(E,z);
    }

    static ComplexNumber Neg(ComplexNumber z){
	return ComplexNumber.newCartesian(-1*z.Re() , -1*z.Im() );
    }

    static ComplexNumber Sin(ComplexNumber z){
	return Quotient(Difference( Exp(Product(I,z)) , Exp(Product(Neg(I),z)) ) , Product(I, Two ));
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
}