package cliCalc;
class FnctObj{
    private String fnct;

    FnctObj(String fnct){
	this.fnct = fnct;
    }

    ComplexNumber operate(ComplexNumber z){
	if (fnct.equals("Sin") )
	    return ComplexMath.Sin(z);
	if (fnct.equals("Cos") )
	    return ComplexMath.Cos(z);
	if (fnct.equals("Tan") )
	    return ComplexMath.Tan(z);
	if (fnct.equals("Csc") )
	    return ComplexMath.Csc(z);
	if (fnct.equals("Sec") )
	    return ComplexMath.Sec(z);
	if (fnct.equals("Cot") )
	    return ComplexMath.Cot(z);

	if (fnct.equals("Sinh") )
	    return ComplexMath.Sinh(z);
	if (fnct.equals("Cosh") )
	    return ComplexMath.Cosh(z);
	if (fnct.equals("Tanh") )
	    return ComplexMath.Tanh(z);
	if (fnct.equals("Csch") )
	    return ComplexMath.Csch(z);
	if (fnct.equals("Sech") )
	    return ComplexMath.Sech(z);
	if (fnct.equals("Coth") )
	    return ComplexMath.Coth(z);


	if (fnct.equals("Arcsin") )
	    return ComplexMath.Arcsin(z);
	if (fnct.equals("Arccos") )
	    return ComplexMath.Arccos(z);
	if (fnct.equals("Arctan") )
	    return ComplexMath.Arctan(z);
	if (fnct.equals("Arccsc") )
	    return ComplexMath.Arccsc(z);
	if (fnct.equals("Arcsec") )
	    return ComplexMath.Arcsec(z);
	if (fnct.equals("Arccot") )
	    return ComplexMath.Arccot(z);

	if (fnct.equals("Arcsinh") )
	    return ComplexMath.Arcsinh(z);
	if (fnct.equals("Arccosh") )
	    return ComplexMath.Arccosh(z);
	if (fnct.equals("Arctanh") )
	    return ComplexMath.Arctanh(z);
	if (fnct.equals("Arccsch") )
	    return ComplexMath.Arccsch(z);
	if (fnct.equals("Arcsech") )
	    return ComplexMath.Arcsech(z);
	if (fnct.equals("Arccoth") )
	    return ComplexMath.Arccoth(z);


	if (fnct.equals("Exp") )
	    return ComplexMath.Exp(z);
	if(fnct.equals("Conj") )
	    return ComplexMath.Conjugate(z);
	if (fnct.equals("Log") )
	    return ComplexMath.Log(z);
	if (fnct.equals("Neg") )
	    return ComplexMath.Neg(z);

	System.err.println("NO SPECIFIED FUNCTION: " + fnct); 
	return ComplexNumber.newCartesian(0,0);
    }

    static boolean isFnct(String s){
	return (s.equals("Sin") ||
		s.equals("Cos") ||
		s.equals("Tan") ||
		s.equals("Csc") ||
		s.equals("Sec") ||
		s.equals("Cot") ||

		s.equals("Sinh") ||
		s.equals("Cosh") ||
		s.equals("Tanh") ||
		s.equals("Csch") ||
		s.equals("Sech") ||
		s.equals("Coth") ||

		s.equals("Arcsin") ||
		s.equals("Arcsos") ||
		s.equals("Arctan") ||
		s.equals("Arccsc") ||
		s.equals("Arcsec") ||
		s.equals("Arccot") ||

		s.equals("Arcsinh") ||
		s.equals("Arccosh") ||
		s.equals("Arctanh") ||
		s.equals("Arccsch") ||
		s.equals("Arcsech") ||
		s.equals("Arccoth") ||

		s.equals("Exp") ||
		s.equals("Conj") ||
		s.equals("Log") ||
		s.equals("Neg")  );

    }

}