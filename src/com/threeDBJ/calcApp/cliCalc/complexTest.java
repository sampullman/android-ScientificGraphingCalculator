package cliCalc;

public class complexTest{
    private static ComplexNumber zero = ComplexNumber.newCartesian(0,0);
    private static ComplexNumber one = ComplexNumber.newCartesian(1,0);
    private static ComplexNumber two = ComplexNumber.newCartesian(2,0);
    private static ComplexNumber root2 = ComplexNumber.newCartesian(Math.sqrt(2),0);
    private static ComplexNumber root3 = ComplexNumber.newCartesian(Math.sqrt(3),0);
    private static ComplexNumber i = ComplexNumber.newCartesian(0,1);


    public static void main(String args[]){
	testComplexNumber();
	testComplexMath_fields();
	testComplexMath_Sum();
	testComplexMath_Difference();
	testComplexMath_Product();
	testComplexMath_Quotient();

	testComplexMath_Pow();
	testComplexMath_Log();

	testComplexMath_Sin();
	testComplexMath_Cos();
	testComplexMath_Tan();
	testComplexMath_Csc();
	testComplexMath_Sec();
	testComplexMath_Cot();

	testComplexMath_Sinh();
	testComplexMath_Cosh();
	testComplexMath_Tanh();
	testComplexMath_Csch();
	testComplexMath_Sech();
	testComplexMath_Coth();

	testComplexMath_Arcsin();
	testComplexMath_Arccos();
	testComplexMath_Arctan();
	testComplexMath_Arccsc();
	testComplexMath_Arcsec();
	testComplexMath_Arccot();

	testComplexMath_Arcsinh();
	testComplexMath_Arccosh();
	testComplexMath_Arctanh();
	testComplexMath_Arccsch();
	testComplexMath_Arcsech();
	testComplexMath_Arccoth();
    }

    static void	testComplexMath_Arccoth(){
	ComplexNumber in, out;
	in = zero;
	out = ComplexNumber.newCartesian(0,- Math.PI / 2 );
	assertTrue(ComplexMath.Equal(ComplexMath.Arccoth(in),out),
		   "Arccoth(0) error");

	//Some positive values
	in = one;
	out = ComplexMath.Arccoth(in);
	assertTrue(Double.isNaN(out.Re() ) || Double.isNaN(out.Im() ),
		   "Arccoth(1) error");
	in = ComplexNumber.newCartesian(1.5,0);
	out = ComplexNumber.newCartesian(0.8047189562170501,0);
	assertTrue(ComplexMath.Equal(ComplexMath.Arccoth(in), out),
		   "Arccoth(1.5) error");
	in = ComplexNumber.newCartesian(10,0);
	out = ComplexNumber.newCartesian(0.10033534773107562,0);
	assertTrue(ComplexMath.Equal(ComplexMath.Arccoth(in), out),
		   "Arccoth(10) error");

	//Some negative values
	in = one;
	in = ComplexMath.Neg(in);
	out = ComplexMath.Arccoth(in);
	assertTrue(Double.isNaN(out.Re() ) || Double.isNaN(out.Im() ),
		   "Arccoth(-1) error");
	in = ComplexNumber.newCartesian(1.5,0);
	in = ComplexMath.Neg(in);
	out = ComplexNumber.newCartesian(-0.8047189562170501,0);
	assertTrue(ComplexMath.Equal(ComplexMath.Arccoth(in), out),
		   "Arccoth(-1.5) error");
	in = ComplexNumber.newCartesian(10,0);
	in = ComplexMath.Neg(in);
	out = ComplexNumber.newCartesian(-0.10033534773107555,0);
	assertTrue(ComplexMath.Equal(ComplexMath.Arccoth(in), out),
		   "Arccoth(-10) error");


	//Some complex values
	in = ComplexNumber.newCartesian(1,1);
	out = ComplexNumber.newCartesian(0.4023594781085252, -0.5535743588970452);
	assertTrue(ComplexMath.Equal(ComplexMath.Arccoth(in), out),
		   "Arccoth(1+I) error");
	in = ComplexNumber.newCartesian(-1.5,2);
	out = ComplexNumber.newCartesian(-0.22008968066202292, -0.3255383607222399);
	assertTrue(ComplexMath.Equal(ComplexMath.Arccoth(in), out),
		   "Arccoth(-1.5+2I) error");
	in = ComplexNumber.newCartesian(0,10);
	out = ComplexNumber.newCartesian(0,-0.09966865249116198);
	assertTrue(ComplexMath.Equal(ComplexMath.Arccoth(in), out),
		   "Arccoth(10I) error");
    }

    static void	testComplexMath_Arcsech(){
	ComplexNumber in, out;

	in = zero;
	out = ComplexMath.Arcsech(in);
	assertTrue(Double.isNaN(out.Re() ) && Double.isNaN(out.Re() ), 
		   "Arcsech(0) error");

	//Some positive values
	out = one;
	in  = ComplexNumber.newCartesian(0.64805427366188795,0);
	assertTrue(ComplexMath.Equal(ComplexMath.Arcsech(in), out),
		   "Arcsech(0.64805) error");
	out = ComplexNumber.newCartesian(1.5,0);
	in  = ComplexNumber.newCartesian(0.4250960349862369,0);
	assertTrue(ComplexMath.Equal(ComplexMath.Arcsech(in), out),
		   "Arcsech(0.425) error");
	out = ComplexNumber.newCartesian(10,0);
	in  = ComplexNumber.newCartesian(9.079985933784465e-05,0);
	assertTrue(ComplexMath.Equal(ComplexMath.Arcsech(in), out),
		   "Arcsech(9.0799e-5) error");

	//Some negative values
	out = ComplexMath.Difference(one,ComplexMath.Product(ComplexMath.I,
							     ComplexMath.PI));
	in  = ComplexNumber.newCartesian(-0.64805427366188795,0);
	assertTrue(ComplexMath.Equal(ComplexMath.Arcsech(in), out),
		   "Arcsech(-0.64805) error");
	out = ComplexNumber.newCartesian(1.5,0);
	out = ComplexMath.Difference(out,ComplexMath.Product(ComplexMath.I,
							     ComplexMath.PI));
	in  = ComplexNumber.newCartesian(-0.4250960349862369,0);
	assertTrue(ComplexMath.Equal(ComplexMath.Arcsech(in), out),
		   "Arcsech(-0.425) error");
	out = ComplexNumber.newCartesian(10,0);
	out = ComplexMath.Sum(out,ComplexMath.Product(ComplexMath.I,
							     ComplexMath.PI));
	in  = ComplexNumber.newCartesian(-9.079985933784465e-05,0);
	assertTrue(ComplexMath.Equal(ComplexMath.Arcsech(in), out),
		   "Arcsech(-9.0799e-5) error");

	//Some complex values
	in = ComplexNumber.newCartesian(1,1);
	out = ComplexNumber.newCartesian(0.5306375309525179, -1.1185178796437059);
	assertTrue(ComplexMath.Equal(ComplexMath.Arcsech(in), out),
		   "Arcsech(1+I) error");
	in = ComplexNumber.newCartesian(-1.5,2);
	out = ComplexNumber.newCartesian(0.32301029562228395, -1.800821322745951);
	assertTrue(ComplexMath.Equal(ComplexMath.Arcsech(in), out),
		   "Arcsech(-1.5+2I) error");
	in = ComplexNumber.newCartesian(0,10);
	out = ComplexNumber.newCartesian(0.09983407889920758, -1.5707963267948966);
	assertTrue(ComplexMath.Equal(ComplexMath.Arcsech(in), out),
		   "Arcsech(10I) error");
    }

    static void testComplexMath_Arccsch(){
	ComplexNumber in, out;

	in = zero;
	out = ComplexMath.Arccsch(in);
	assertTrue(Double.isNaN(out.Re() ) && Double.isNaN(out.Im() ),
		   "Arccsch(0) error");

	//Some positive values
	out = ComplexNumber.newCartesian(1.0,0.0);
	in = ComplexNumber.newCartesian(0.85091812798141186,0);
	assertTrue(ComplexMath.Equal(ComplexMath.Arccsch(in), out),
		   "Arccsch(0.8509) error");
	out = ComplexNumber.newCartesian(1.5,0);
	in  = ComplexNumber.newCartesian(0.4696424406161,0);
	assertTrue(ComplexMath.Equal(ComplexMath.Arccsch(in), out),
		   "Arccsch(0.469) error");
	out = ComplexNumber.newCartesian(10,0);
	in = ComplexNumber.newCartesian(9.0799859712122167e-05,0);
	assertTrue(ComplexMath.Equal(ComplexMath.Arccsch(in), out),
		   "Arccsch(9.08e-5) error");

	//Some negative values
	out = ComplexNumber.newCartesian(-0.7084608435503107,0.0);
	in = ComplexNumber.newCartesian(-1.3,0);
	assertTrue(ComplexMath.Equal(ComplexMath.Arccsch(in), out),
		   "Arccsch(-1.3) error");
	out = ComplexNumber.newCartesian(-0.19868971850997133,0);
	in  = ComplexNumber.newCartesian(-5.00000999,0);
	assertTrue(ComplexMath.Equal(ComplexMath.Arccsch(in), out),
		   "Arccsch(-5.00000999) error");
	out = ComplexNumber.newCartesian(-7.706263173771322,0);
	in = ComplexNumber.newCartesian(-0.0009,0);
	assertTrue(ComplexMath.Equal(ComplexMath.Arccsch(in), out),
		   "Arccsch(-0.0009) error");

	//Some complex values
	in = ComplexNumber.newCartesian(1,1);
	out = ComplexNumber.newCartesian(0.5306375309525179, -0.45227844715119053);
	assertTrue(ComplexMath.Equal(ComplexMath.Arccsch(in), out),
		   "Arccsch(1+I) error");
	in = ComplexNumber.newCartesian(-1.5,2);
	out = ComplexNumber.newCartesian(-0.2498511929143176, -0.31547205587001015);
	assertTrue(ComplexMath.Equal(ComplexMath.Arccsch(in), out),
		   "Arccsch(-1.5+2I) error");
	in = ComplexNumber.newCartesian(0,10);
	out = ComplexNumber.newCartesian(0,-0.1001674211615598);
	assertTrue(ComplexMath.Equal(ComplexMath.Arccsch(in), out),
		   "Arccsch(10I) error");

    }

    static void	testComplexMath_Arctanh(){
	ComplexNumber in, out;
	in = zero;
	out = zero;
	assertTrue(ComplexMath.Equal(ComplexMath.Arctanh(in), out),
		   "Arctanh(0) error");

	//Some positive values
	out = one;
	in = ComplexNumber.newCartesian(0.761594156,0);
	assertTrue(ComplexMath.Equal(ComplexMath.Arctanh(in), out),
		   "Arctanh(0.76159) error");
	out = ComplexNumber.newCartesian(1.5,0);
	in = ComplexNumber.newCartesian(0.9051482536,0);
	assertTrue(ComplexMath.Equal(ComplexMath.Arctanh(in), out),
		   "Arctanh(0.905148) error");
	in = ComplexNumber.newCartesian(1,0);
	out = ComplexMath.Arctanh(in);
	assertTrue(Double.isNaN(out.Re() ) && Double.isNaN(out.Im() ),
		   "Arctanh(1) error");

	//Some negative values
	out = one;
	out = ComplexMath.Neg(in);
	in  = ComplexNumber.newCartesian(-0.761594156,0);
	assertTrue(ComplexMath.Equal(ComplexMath.Arctanh(in), out),
		   "Arctanh(-0.7615) error");
	out = ComplexNumber.newCartesian(-1.5,0);
	in  = ComplexNumber.newCartesian(-0.9051482536,0);
	assertTrue(ComplexMath.Equal(ComplexMath.Arctanh(in), out),
		   "Arctanh(-0.90514) error");
	in  = ComplexNumber.newCartesian(-1,0);
	out = ComplexMath.Arctanh(in);
	assertTrue(Double.isNaN(out.Re() ) && Double.isNaN(out.Im() ),
		   "Arctanh(1) error");


	//Some complex values
	in = ComplexNumber.newCartesian(1,1);
	out = ComplexNumber.newCartesian(0.4023594781085251,1.0172219678978514);
	assertTrue(ComplexMath.Equal(ComplexMath.Arctanh(in), out),
		   "Arctanh(1+I) error");
	in = ComplexNumber.newCartesian(-1.5,2);
	out = ComplexNumber.newCartesian(-0.22008968066202309, 1.2452579660726568);
	assertTrue(ComplexMath.Equal(ComplexMath.Arctanh(in), out),
		   "Arctanh(-1.5+2I) error");
	in = ComplexNumber.newCartesian(0,10);
	out = ComplexNumber.newCartesian(0,1.4711276743037347);
	assertTrue(ComplexMath.Equal(ComplexMath.Arctanh(in), out),
		   "Arctanh(10I) error");
    }


    static void	testComplexMath_Arccosh(){
	ComplexNumber in, out;

	out = zero;
	in  = one;
	assertTrue(ComplexMath.Equal(ComplexMath.Arccosh(in), out),
		   "Arccosh(0) error");

	//Some positive values
	out = one;
	in  = ComplexNumber.newCartesian(1.54308063482,0);
	assertTrue(ComplexMath.Equal(ComplexMath.Arccosh(in), out),
		   "Arccosh(1.54) error");
	out = ComplexNumber.newCartesian(1.5,0);
	in  = ComplexNumber.newCartesian(2.352409615,0);
	assertTrue(ComplexMath.Equal(ComplexMath.Arccosh(in), out),
		   "Arccosh(2.35) error");
	out = ComplexNumber.newCartesian(10,0);
	in = ComplexNumber.newCartesian(11013.2329201,0);
	assertTrue(ComplexMath.Equal(ComplexMath.Arccosh(in), out),
		   "Arccosh(11013) error");

	//Some negative values
	out = ComplexMath.Sum(one, ComplexMath.Product(i,ComplexMath.PI));;
	in  = ComplexNumber.newCartesian(-1.54308063482,0);
	assertTrue(ComplexMath.Equal(ComplexMath.Arccosh(in), out),
		   "Arccosh(-1.54308) error");
	out = ComplexNumber.newCartesian(1.5,Math.PI);
	in  = ComplexNumber.newCartesian(-2.352409615,0);
	assertTrue(ComplexMath.Equal(ComplexMath.Arccosh(in), out),
		   "Arccosh(-2.3524) error");
	out = ComplexNumber.newCartesian(7.5999017087076535, 3.141592653589793);
	in = ComplexNumber.newCartesian(-999,0);
	assertTrue(ComplexMath.Equal(ComplexMath.Arccosh(in), out),
		   "Arccosh(-999) error");

	//Some complex values
	in = ComplexNumber.newCartesian(1,1);
	out = ComplexNumber.newCartesian(1.0612750619050355, 0.9045568943023813);
	assertTrue(ComplexMath.Equal(ComplexMath.Arccosh(in), out),
		   "Arccosh(1+I) error");
	in = ComplexNumber.newCartesian(-1.5,2);
	out = ComplexNumber.newCartesian(1.6224941488715938,2.1773078449946515);
	assertTrue(ComplexMath.Equal(ComplexMath.Arccosh(in), out),
		   "Arccosh(-1.5+2I) error");
	in = ComplexNumber.newCartesian(0,10);
	out = ComplexNumber.newCartesian(2.99822295029797,1.5707963267948968);
	assertTrue(ComplexMath.Equal(ComplexMath.Arccosh(in), out),
		   "Arccosh(10I) error");
    }
    
    static void testComplexMath_Arcsinh(){
	ComplexNumber in, out;

	in = zero;
	out = zero;
	assertTrue(ComplexMath.Equal(ComplexMath.Arcsinh(in), out),
		   "Arcsinh(0) error");

	//Some positive values
	out = ComplexNumber.newCartesian(1.0,0.0);
	in = ComplexNumber.newCartesian(1.175201194,0);
	assertTrue(ComplexMath.Equal(ComplexMath.Arcsinh(in), out),
		   "Arcsinh(1.17) error");
	out = ComplexNumber.newCartesian(1.5,0);
	in = ComplexNumber.newCartesian(2.129279455,0);
	assertTrue(ComplexMath.Equal(ComplexMath.Arcsinh(in), out),
		   "Arcsinh(2.12) error");
	out = ComplexNumber.newCartesian(10,0);
	in = ComplexNumber.newCartesian(11013.232874703393,0);
	assertTrue(ComplexMath.Equal(ComplexMath.Arcsinh(in), out),
		   "Arcsinh(11013) error");

	//Some negative values
	out = ComplexNumber.newCartesian(-1,0);
	in = ComplexNumber.newCartesian(-1.175201194,0);
	assertTrue(ComplexMath.Equal(ComplexMath.Arcsinh(in), out),
		   "Arcsinh(-1.17) error");
	out = ComplexNumber.newCartesian(-1.5,0);
	in = ComplexNumber.newCartesian(-2.129279455,0);
	assertTrue(ComplexMath.Equal(ComplexMath.Arcsinh(in), out),
		   "Arcsinh(-2.12) error");

	//Some complex values
	out = ComplexNumber.newCartesian(1,1);
	in = ComplexNumber.newCartesian(0.6349639148,1.298457581);
	assertTrue(ComplexMath.Equal(ComplexMath.Arcsinh(in), out),
		   "Arcsinh(0.63+1.29I) error");
	in = ComplexNumber.newCartesian(-1.5,2);
	out = ComplexNumber.newCartesian(-1.60041005523461,0.8877651461839);
	assertTrue(ComplexMath.Equal(ComplexMath.Arcsinh(in), out),
		   "Arcsinh(-1.5+2I) error");
	in = ComplexNumber.newCartesian(0,10);
	out = ComplexNumber.newCartesian(-2.99322284612637,1.5707963267948);
	assertTrue(ComplexMath.Equal(ComplexMath.Arcsinh(in), out),
		   "Arcsinh(10I) error");	
    }

    static void testComplexMath_Arccot(){
	ComplexNumber in , out;
	
	in = zero;
	out = ComplexMath.Quotient(ComplexMath.PI,two);
	assertTrue(ComplexMath.Equal(ComplexMath.Arccot(in), out),
		   "Arccot(0) error");
	in = ComplexMath.Quotient(one,root3);
	out = ComplexMath.Quotient(ComplexMath.PI,ComplexNumber.newCartesian(3,0)); 
	assertTrue(ComplexMath.Equal(ComplexMath.Arccot(in), out),
		   "Arccot(1/RootThree) error");
	in = one;
	out = ComplexMath.Quotient(ComplexMath.PI,ComplexNumber.newCartesian(4,0)); 
	assertTrue(ComplexMath.Equal(ComplexMath.Arccot(in), out),
		   "Arccot(1) error");
	in = root3;
	out = ComplexMath.Quotient(ComplexMath.PI,ComplexNumber.newCartesian(6,0)); 
	assertTrue(ComplexMath.Equal(ComplexMath.Arccot(in), out),
		   "Arccot(RootThree) error");
	in = zero;
	out = ComplexMath.Quotient(ComplexMath.PI,two);
	assertTrue(ComplexMath.Equal(ComplexMath.Arccot(in), out),
		   "Arccot(0) error");
	in = ComplexMath.Neg(one);
	out = ComplexMath.Neg(ComplexMath.Quotient( ComplexMath.PI, 
						    ComplexNumber.newCartesian(4,0)));
	assertTrue(ComplexMath.Equal(ComplexMath.Arccot(in), out),
		   "Arccot(-1) error");

	//complex stuff...weird due to multi-value nature of the function
	in  = ComplexNumber.newCartesian(1.5,-2);
	out = ComplexNumber.newCartesian(0.25957305712326145 , 0.31042828307719583);
	assertTrue(ComplexMath.Equal(ComplexMath.Arccot(in), out),
		   "Arccot(a+bI) error1");
	in  = ComplexNumber.newCartesian(1,1);
	out = ComplexNumber.newCartesian(0.5535743588970451 , -0.4023594781085253);
	assertTrue(ComplexMath.Equal(ComplexMath.Arccot(in), out),
		   "Arccot(a+bI) error2");
    }



    static void testComplexMath_Arcsec(){
	ComplexNumber in , out;
	in  = zero;
	out = ComplexMath.Arcsec(in);
	assertTrue(Double.isNaN(out.Re() ) && Double.isNaN(out.Im() ),
		   "Arcsec(0) error");

	in = two;
	out = ComplexMath.Quotient(ComplexMath.PI,ComplexNumber.newCartesian(3,0)); 
	assertTrue(ComplexMath.Equal(ComplexMath.Arcsec(in), out),
		   "Arcsec(2) error");
	in = ComplexMath.Quotient(two,root2);
	out = ComplexMath.Quotient(ComplexMath.PI,ComplexNumber.newCartesian(4,0)); 
	assertTrue(ComplexMath.Equal(ComplexMath.Arcsec(in), out),
		   "Arcsec(2/RootTwo) error");
	in = ComplexMath.Quotient(two,root3);
	out = ComplexMath.Quotient(ComplexMath.PI,ComplexNumber.newCartesian(6,0)); 
	assertTrue(ComplexMath.Equal(ComplexMath.Arcsec(in), out),
		   "Arcsec(2/RootThree) error");
	in = one;
	out = zero;
	assertTrue(ComplexMath.Equal(ComplexMath.Arcsec(in), out),
		   "Arcsec(1) error");
	in = ComplexMath.Neg(one);
	out = ComplexMath.PI;
	assertTrue(ComplexMath.Equal(ComplexMath.Arcsec(in), out),
		   "Arcsec(-1) error");

	//complex stuff...weird due to multi-value nature of the function
	in  = ComplexNumber.newCartesian(1.5,-2);
	out = ComplexNumber.newCartesian(1.3407713308438423, -0.32301029562228434);
	assertTrue(ComplexMath.Equal(ComplexMath.Arcsec(in), out),
		   "Arcsec(a+bI) error1");
	in  = ComplexNumber.newCartesian(1,1);
	out = ComplexNumber.newCartesian(1.1185178796437059 , 0.5306375309525174);
	assertTrue(ComplexMath.Equal(ComplexMath.Arcsec(in), out),
		   "Arcsec(a+bI) error2");
    }



    static void testComplexMath_Arccsc(){
	ComplexNumber in , out;

	in  = zero;
	out = ComplexMath.Arccsc(in);
	assertTrue(Double.isNaN(out.Re() ) && Double.isNaN(out.Im() ),
		   "Arccsc(0) error");
	in = two;
	out = ComplexMath.Quotient(ComplexMath.PI,ComplexNumber.newCartesian(6,0)); 
	assertTrue(ComplexMath.Equal(ComplexMath.Arccsc(in), out),
		   "Arccsc(2) error");
	in = ComplexMath.Quotient(two,root2);
	out = ComplexMath.Quotient(ComplexMath.PI,ComplexNumber.newCartesian(4,0)); 
	assertTrue(ComplexMath.Equal(ComplexMath.Arccsc(in), out),
		   "Arccsc(2/RootTwo) error");
	in = ComplexMath.Quotient(two,root3);
	out = ComplexMath.Quotient(ComplexMath.PI,ComplexNumber.newCartesian(3,0)); 
	assertTrue(ComplexMath.Equal(ComplexMath.Arccsc(in), out),
		   "Arccsc(2/RootThree) error");
	in = one;
	out = ComplexMath.Quotient(ComplexMath.PI,two); 
	assertTrue(ComplexMath.Equal(ComplexMath.Arccsc(in), out),
		   "Arccsc(1) error");
	in = ComplexMath.Neg(one);
	out = ComplexMath.Quotient(ComplexMath.PI,ComplexMath.Neg(two));
	assertTrue(ComplexMath.Equal(ComplexMath.Arccsc(in), out),
		   "Arccsc(-1) error");

	//complex stuff...weird due to multi-value nature of the function
	in  = ComplexNumber.newCartesian(3,-4.5);
	out = ComplexNumber.newCartesian(0.10153365662812651 , 0.15403275873684946);
	assertTrue(ComplexMath.Equal(ComplexMath.Arccsc(in), out),
		   "Arccsc(a+bI) error1");
	in  = ComplexNumber.newCartesian(1,1);
	out = ComplexNumber.newCartesian(0.4522784471511909 , -0.5306375309525174);
	assertTrue(ComplexMath.Equal(ComplexMath.Arccsc(in), out),
		   "Arccsc(a+bI) error2");
    }


    static void testComplexMath_Arctan(){
	ComplexNumber in , out;
	
	in = zero;
	out = zero;
	assertTrue(ComplexMath.Equal(ComplexMath.Arctan(in), out),
		   "Arctan(0) error");
	in = root3;
	out = ComplexMath.Quotient(ComplexMath.PI,ComplexNumber.newCartesian(3,0)); 
	assertTrue(ComplexMath.Equal(ComplexMath.Arctan(in), out),
		   "Arctan(RootThree) error");
	in = one;
	out = ComplexMath.Quotient(ComplexMath.PI,ComplexNumber.newCartesian(4,0)); 
	assertTrue(ComplexMath.Equal(ComplexMath.Arctan(in), out),
		   "Arctan(1) error");
	in = ComplexMath.Quotient(one,root3);
	out = ComplexMath.Quotient(ComplexMath.PI,ComplexNumber.newCartesian(6,0)); 
	assertTrue(ComplexMath.Equal(ComplexMath.Arctan(in), out),
		   "Arctan(1/RootThree) error");
	in = zero;
	out = zero;
	assertTrue(ComplexMath.Equal(ComplexMath.Arctan(in), out),
		   "Arctan(0) error");
	in = ComplexMath.Neg(one);
	out = ComplexMath.Neg(ComplexMath.Quotient( ComplexMath.PI, 
						    ComplexNumber.newCartesian(4,0)));
	assertTrue(ComplexMath.Equal(ComplexMath.Arctan(in), out),
		   "Arctan(-1) error");

	//complex stuff...weird due to multi-value nature of the function
	in  = ComplexNumber.newCartesian(1.5,-2);
	out = ComplexNumber.newCartesian(1.31122327,-0.31042828);
	assertTrue(ComplexMath.Equal(ComplexMath.Arctan(in), out),
		   "Arctan(a+bI) error1");
	in  = ComplexNumber.newCartesian(1,1);
	out = ComplexNumber.newCartesian(1.01722197 , 0.40235948);
	assertTrue(ComplexMath.Equal(ComplexMath.Arctan(in), out),
		   "Arctan(a+bI) error2");
    }

    static void testComplexMath_Arccos(){
	ComplexNumber in , out;
	
	in = zero;
	out = ComplexMath.Quotient(ComplexMath.PI,two);
	assertTrue(ComplexMath.Equal(ComplexMath.Arccos(in), out),
		   "Arccos(0) error");
	in = ComplexMath.Quotient(one,two);
	out = ComplexMath.Quotient(ComplexMath.PI,ComplexNumber.newCartesian(3,0)); 
	assertTrue(ComplexMath.Equal(ComplexMath.Arccos(in), out),
		   "Arccos(0.5) error");
	in = ComplexMath.Quotient(root2,two);
	out = ComplexMath.Quotient(ComplexMath.PI,ComplexNumber.newCartesian(4,0)); 
	assertTrue(ComplexMath.Equal(ComplexMath.Arccos(in), out),
		   "Arccos(RootTwo/2) error");
	in = ComplexMath.Quotient(root3,two);
	out = ComplexMath.Quotient(ComplexMath.PI,ComplexNumber.newCartesian(6,0)); 
	assertTrue(ComplexMath.Equal(ComplexMath.Arccos(in), out),
		   "Arccos(RootThree/2) error");
	in = one;
	out = zero;
	assertTrue(ComplexMath.Equal(ComplexMath.Arccos(in), out),
		   "Arccos(1) error");
	in = ComplexMath.Neg(one);
	out = ComplexMath.PI;
	assertTrue(ComplexMath.Equal(ComplexMath.Arccos(in), out),
		   "Arccos(-1) error");

	//complex stuff...weird due to multi-value nature of the function
	in  = ComplexNumber.newCartesian(1.5,-2);
	out = ComplexNumber.newCartesian(0.96428481, 1.62249415);
	assertTrue(ComplexMath.Equal(ComplexMath.Arccos(in), out),
		   "Arccos(a+bI) error1");
	in  = ComplexNumber.newCartesian(1,1);
	out = ComplexNumber.newCartesian(0.90455689, -1.06127506);
	assertTrue(ComplexMath.Equal(ComplexMath.Arccos(in), out),
		   "Arccos(a+bI) error2");
    }


    static void testComplexMath_Arcsin(){
	ComplexNumber in , out;
	
	in = zero;
	out = zero;
	assertTrue(ComplexMath.Equal(ComplexMath.Arcsin(in), out),
		   "Arcsin(0) error");
	in = ComplexMath.Quotient(one,two);
	out = ComplexMath.Quotient(ComplexMath.PI,ComplexNumber.newCartesian(6,0)); 
	assertTrue(ComplexMath.Equal(ComplexMath.Arcsin(in), out),
		   "Arcsin(0.5) error");
	in = ComplexMath.Quotient(root2,two);
	out = ComplexMath.Quotient(ComplexMath.PI,ComplexNumber.newCartesian(4,0)); 
	assertTrue(ComplexMath.Equal(ComplexMath.Arcsin(in), out),
		   "Arcsin(RootTwo/2) error");
	in = ComplexMath.Quotient(root3,two);
	out = ComplexMath.Quotient(ComplexMath.PI,ComplexNumber.newCartesian(3,0)); 
	assertTrue(ComplexMath.Equal(ComplexMath.Arcsin(in), out),
		   "Arcsin(RootThree/2) error");
	in = one;
	out = ComplexMath.Quotient(ComplexMath.PI,two); 
	assertTrue(ComplexMath.Equal(ComplexMath.Arcsin(in), out),
		   "Arcsin(1) error");
	in = ComplexMath.Neg(one);
	out = ComplexMath.Quotient(ComplexMath.PI,ComplexMath.Neg(two));
	assertTrue(ComplexMath.Equal(ComplexMath.Arcsin(in), out),
		   "Arcsin(-1) error");

	//complex stuff...weird due to multi-value nature of the function
	in  = ComplexNumber.newCartesian(9.15449915, -4.16890696);
	out = ComplexNumber.newCartesian(1.14159265, -3);
	assertTrue(ComplexMath.Equal(ComplexMath.Arcsin(in), out),
		   "Arcsin(a+bI) error1");
	in  = ComplexNumber.newCartesian(1,1);
	out = ComplexNumber.newCartesian(0.66623943, 1.06127506);
	assertTrue(ComplexMath.Equal(ComplexMath.Arcsin(in), out),
		   "Arcsin(a+bI) error2");
    }

    static void	testComplexMath_Coth(){
	ComplexNumber in, out;
	in = zero;
	out = ComplexMath.Coth(in);
	assertTrue(Double.isInfinite(out.Re() ) && Double.isInfinite(out.Im() ),
		   "Coth(0) error");

	//Some positive values
	in = one;
	out = ComplexNumber.newCartesian(1.3130352854230671,0);
	assertTrue(ComplexMath.Equal(ComplexMath.Coth(in), out),
		   "Coth(1) error");
	in = ComplexNumber.newCartesian(1.5,0);
	out = ComplexNumber.newCartesian(1.1047913930372744,0);
	assertTrue(ComplexMath.Equal(ComplexMath.Coth(in), out),
		   "Coth(1.5) error");
	in = ComplexNumber.newCartesian(10,0);
	out = ComplexNumber.newCartesian(1.0000000041000001,0);
	assertTrue(ComplexMath.Equal(ComplexMath.Coth(in), out),
		   "Coth(10) error");

	//Some negative values
	in = one;
	in = ComplexMath.Neg(in);
	out = ComplexNumber.newCartesian(-1.3130352854230671,0);
	assertTrue(ComplexMath.Equal(ComplexMath.Coth(in), out),
		   "Coth(-1) error");
	in = ComplexNumber.newCartesian(1.5,0);
	in = ComplexMath.Neg(in);
	out = ComplexNumber.newCartesian(-1.1047913930372744,0);
	assertTrue(ComplexMath.Equal(ComplexMath.Coth(in), out),
		   "Coth(-1.5) error");
	in = ComplexNumber.newCartesian(10,0);
	in = ComplexMath.Neg(in);
	out = ComplexNumber.newCartesian(-1.0000000041000001,0);
	assertTrue(ComplexMath.Equal(ComplexMath.Coth(in), out),
		   "Coth(-10) error");


	//Some complex values
	in = ComplexNumber.newCartesian(1,1);
	out = ComplexNumber.newCartesian(0.8680141429,-0.2176215619);
	assertTrue(ComplexMath.Equal(ComplexMath.Coth(in), out),
		   "Coth(1+I) error");
	in = ComplexNumber.newCartesian(-1.5,2);
	out = ComplexNumber.newCartesian(-0.9343894564,0.0705886505);
	assertTrue(ComplexMath.Equal(ComplexMath.Coth(in), out),
		   "Coth(-1.5+2I) error");
	in = ComplexNumber.newCartesian(0,10);
	out = ComplexNumber.newCartesian(0,-1.542351045);
	assertTrue(ComplexMath.Equal(ComplexMath.Coth(in), out),
		   "Coth(10I) error");
    }


    static void	testComplexMath_Sech(){
	ComplexNumber in, out;

	in = zero;
	out = one;
	assertTrue(ComplexMath.Equal(ComplexMath.Sech(in), out),
		   "Sech(0) error");

	//Some positive values
	in = one;
	out = ComplexNumber.newCartesian(0.64805427366188795,0);
	assertTrue(ComplexMath.Equal(ComplexMath.Sech(in), out),
		   "Sech(1) error");
	in = ComplexNumber.newCartesian(1.5,0);
	out = ComplexNumber.newCartesian(0.4250960349862369,0);
	assertTrue(ComplexMath.Equal(ComplexMath.Sech(in), out),
		   "Sech(1.5) error");
	in = ComplexNumber.newCartesian(10,0);
	out = ComplexNumber.newCartesian(9.079985933784465e-05,0);
	assertTrue(ComplexMath.Equal(ComplexMath.Sech(in), out),
		   "Sech(10) error");

	//Some negative values
	in = one;
	in = ComplexMath.Neg(in);
	out = ComplexNumber.newCartesian(0.64805427366188795,0);
	assertTrue(ComplexMath.Equal(ComplexMath.Sech(in), out),
		   "Sech(-1) error");
	in = ComplexNumber.newCartesian(1.5,0);
	in = ComplexMath.Neg(in);
	out = ComplexNumber.newCartesian(0.4250960349862369,0);
	assertTrue(ComplexMath.Equal(ComplexMath.Sech(in), out),
		   "Sech(-1.5) error");
	in = ComplexNumber.newCartesian(10,0);
	in = ComplexMath.Neg(in);
	out = ComplexNumber.newCartesian(9.079985933784465e-05,0);
	assertTrue(ComplexMath.Equal(ComplexMath.Sech(in), out),
		   "Sech(-10) error");

	//Some complex values
	in = ComplexNumber.newCartesian(1,1);
	out = ComplexNumber.newCartesian(0.4983370306,-0.5910838417);
	assertTrue(ComplexMath.Equal(ComplexMath.Sech(in), out),
		   "Sech(1+I) error");
	in = ComplexNumber.newCartesian(-1.5,2);
	out = ComplexNumber.newCartesian(-0.2079766112,0.4113330254);
	assertTrue(ComplexMath.Equal(ComplexMath.Sech(in), out),
		   "Sech(-1.5+2I) error");
	in = ComplexNumber.newCartesian(0,10);
	out = ComplexNumber.newCartesian(-1.191793507,0);
	assertTrue(ComplexMath.Equal(ComplexMath.Sech(in), out),
		   "Sech(10I) error");
    }

    static void	testComplexMath_Csch(){
	ComplexNumber in, out;

	in = zero;
	out = ComplexMath.Csch(in);
	assertTrue(Double.isInfinite(out.Re() ) && Double.isInfinite(out.Im() ),
		   "Csch(0) error");

	//Some positive values
	in = ComplexNumber.newCartesian(1.0,0.0);
	out = ComplexNumber.newCartesian(0.85091812798141186,0);
	assertTrue(ComplexMath.Equal(ComplexMath.Csch(in), out),
		   "Csch(1) error");
	in = ComplexNumber.newCartesian(1.5,0);
	out = ComplexNumber.newCartesian(0.4696424406161,0);
	assertTrue(ComplexMath.Equal(ComplexMath.Csch(in), out),
		   "Csch(1.5) error");
	in = ComplexNumber.newCartesian(10,0);
	out = ComplexNumber.newCartesian(9.0799859712122167e-05,0);
	assertTrue(ComplexMath.Equal(ComplexMath.Csch(in), out),
		   "Csch(10) error");

	//Some negative values
	in = ComplexNumber.newCartesian(1.0,0.0);
	in = ComplexMath.Neg(in);
	out = ComplexNumber.newCartesian(-0.85091812798141186,0);
	assertTrue(ComplexMath.Equal(ComplexMath.Csch(in), out),
		   "Csch(-1) error");
	in = ComplexNumber.newCartesian(1.5,0);
	in = ComplexMath.Neg(in);
	out = ComplexNumber.newCartesian(-0.4696424406161,0);
	assertTrue(ComplexMath.Equal(ComplexMath.Csch(in), out),
		   "Csch(-1.5) error");
	in = ComplexNumber.newCartesian(10,0);
	in = ComplexMath.Neg(in);
	out = ComplexNumber.newCartesian(-9.0799859712122167e-05,0);
	assertTrue(ComplexMath.Equal(ComplexMath.Csch(in), out),
		   "Csch(-10) error");

	//Some complex values
	in = ComplexNumber.newCartesian(1,1);
	out = ComplexNumber.newCartesian(0.3039310016,-0.6215180172);
	assertTrue(ComplexMath.Equal(ComplexMath.Csch(in), out),
		   "Csch(1+I) error");
	in = ComplexNumber.newCartesian(-1.5,2);
	out = ComplexNumber.newCartesian(0.1652957095,-0.3990260303);
	assertTrue(ComplexMath.Equal(ComplexMath.Csch(in), out),
		   "Csch(-1.5+2I) error");
	in = ComplexNumber.newCartesian(0,10);
	out = ComplexNumber.newCartesian(0,1.838163961);
	assertTrue(ComplexMath.Equal(ComplexMath.Csch(in), out),
		   "Csch(10I) error");
    }


    static void	testComplexMath_Tanh(){
	ComplexNumber in, out;
	in = zero;
	out = zero;
	assertTrue(ComplexMath.Equal(ComplexMath.Tanh(in), out),
		   "Tanh(0) error");

	//Some positive values
	in = one;
	out = ComplexNumber.newCartesian(0.761594156,0);
	assertTrue(ComplexMath.Equal(ComplexMath.Tanh(in), out),
		   "Tanh(1) error");
	in = ComplexNumber.newCartesian(1.5,0);
	out = ComplexNumber.newCartesian(0.9051482536,0);
	assertTrue(ComplexMath.Equal(ComplexMath.Tanh(in), out),
		   "Tanh(1.5) error");
	in = ComplexNumber.newCartesian(10,0);
	out = ComplexNumber.newCartesian(0.9999999959,0);
	assertTrue(ComplexMath.Equal(ComplexMath.Tanh(in), out),
		   "Tanh(10) error");

	//Some negative values
	in = one;
	in = ComplexMath.Neg(in);
	out = ComplexNumber.newCartesian(-0.761594156,0);
	assertTrue(ComplexMath.Equal(ComplexMath.Tanh(in), out),
		   "Tanh(-1) error");
	in = ComplexNumber.newCartesian(1.5,0);
	in = ComplexMath.Neg(in);
	out = ComplexNumber.newCartesian(-0.9051482536,0);
	assertTrue(ComplexMath.Equal(ComplexMath.Tanh(in), out),
		   "Tanh(1.5) error");
	in = ComplexNumber.newCartesian(10,0);
	in = ComplexMath.Neg(in);
	out = ComplexNumber.newCartesian(-0.9999999959,0);
	assertTrue(ComplexMath.Equal(ComplexMath.Tanh(in), out),
		   "Tanh(10) error");


	//Some complex values
	in = ComplexNumber.newCartesian(1,1);
	out = ComplexNumber.newCartesian(1.083923327, 0.2717525853);
	assertTrue(ComplexMath.Equal(ComplexMath.Tanh(in), out),
		   "Tanh(1+I) error");
	in = ComplexNumber.newCartesian(-1.5,2);
	out = ComplexNumber.newCartesian(-1.064144399,-0.0803910153);
	assertTrue(ComplexMath.Equal(ComplexMath.Tanh(in), out),
		   "Tanh(-1.5+2I) error");
	in = ComplexNumber.newCartesian(0,10);
	out = ComplexNumber.newCartesian(0,0.6483608275);
	assertTrue(ComplexMath.Equal(ComplexMath.Tanh(in), out),
		   "Tanh(10I) error");
    }


    static void	testComplexMath_Cosh(){
	ComplexNumber in, out;

	in = zero;
	out = one;
	assertTrue(ComplexMath.Equal(ComplexMath.Cosh(in), out),
		   "Cosh(0) error");

	//Some positive values
	in = one;
	out = ComplexNumber.newCartesian(1.54308063482,0);
	assertTrue(ComplexMath.Equal(ComplexMath.Cosh(in), out),
		   "Cosh(1) error");
	in = ComplexNumber.newCartesian(1.5,0);
	out = ComplexNumber.newCartesian(2.352409615,0);
	assertTrue(ComplexMath.Equal(ComplexMath.Cosh(in), out),
		   "Cosh(1.5) error");
	in = ComplexNumber.newCartesian(10,0);
	out = ComplexNumber.newCartesian(11013.2329201,0);
	assertTrue(ComplexMath.Equal(ComplexMath.Cosh(in), out),
		   "Cosh(10) error");

	//Some negative values
	in = one;
	in = ComplexMath.Neg(in);
	out = ComplexNumber.newCartesian(1.54308063482,0);
	assertTrue(ComplexMath.Equal(ComplexMath.Cosh(in), out),
		   "Cosh(-1) error");
	in = ComplexNumber.newCartesian(1.5,0);
	in = ComplexMath.Neg(in);
	out = ComplexNumber.newCartesian(2.352409615,0);
	assertTrue(ComplexMath.Equal(ComplexMath.Cosh(in), out),
		   "Cosh(-1.5) error");
	in = ComplexNumber.newCartesian(10,0);
	in = ComplexMath.Neg(in);
	out = ComplexNumber.newCartesian(11013.2329201,0);
	assertTrue(ComplexMath.Equal(ComplexMath.Cosh(in), out),
		   "Cosh(-10) error");

	//Some complex values
	in = ComplexNumber.newCartesian(1,1);
	out = ComplexNumber.newCartesian(0.8337300251,0.9888977058);
	assertTrue(ComplexMath.Equal(ComplexMath.Cosh(in), out),
		   "Cosh(1+I) error");
	in = ComplexNumber.newCartesian(-1.5,2);
	out = ComplexNumber.newCartesian(-0.9789478196,-1.93614833);
	assertTrue(ComplexMath.Equal(ComplexMath.Cosh(in), out),
		   "Cosh(-1.5+2I) error");
	in = ComplexNumber.newCartesian(0,10);
	out = ComplexNumber.newCartesian(-0.8390715291,0);
	assertTrue(ComplexMath.Equal(ComplexMath.Cosh(in), out),
		   "Cosh(10I) error");
    }


    static void	testComplexMath_Sinh(){
	ComplexNumber in, out;

	in = zero;
	out = zero;
	assertTrue(ComplexMath.Equal(ComplexMath.Sinh(in), out),
		   "Sinh(0) error");

	//Some positive values
	in = ComplexNumber.newCartesian(1.0,0.0);
	out = ComplexNumber.newCartesian(1.175201194,0);
	assertTrue(ComplexMath.Equal(ComplexMath.Sinh(in), out),
		   "Sinh(1) error");
	in = ComplexNumber.newCartesian(1.5,0);
	out = ComplexNumber.newCartesian(2.129279455,0);
	assertTrue(ComplexMath.Equal(ComplexMath.Sinh(in), out),
		   "Sinh(1.5) error");
	in = ComplexNumber.newCartesian(10,0);
	out = ComplexNumber.newCartesian(11013.232874703393,0);
	assertTrue(ComplexMath.Equal(ComplexMath.Sinh(in), out),
		   "Sinh(10) error");

	//Some negative values
	in = ComplexNumber.newCartesian(-1,0);
	out = ComplexNumber.newCartesian(-1.175201194,0);
	assertTrue(ComplexMath.Equal(ComplexMath.Sinh(in), out),
		   "Sinh(-1) error");
	in = ComplexNumber.newCartesian(-1.5,0);
	out = ComplexNumber.newCartesian(-2.129279455,0);
	assertTrue(ComplexMath.Equal(ComplexMath.Sinh(in), out),
		   "Sinh(-1.5) error");
	in = ComplexNumber.newCartesian(-10,0);
	out = ComplexNumber.newCartesian(-11013.232874703393,0);
	assertTrue(ComplexMath.Equal(ComplexMath.Sinh(in), out),
		   "Sinh(-10) error");

	//Some complex values
	in = ComplexNumber.newCartesian(1,1);
	out = ComplexNumber.newCartesian(0.6349639148,1.298457581);
	assertTrue(ComplexMath.Equal(ComplexMath.Sinh(in), out),
		   "Sinh(1+I) error");
	in = ComplexNumber.newCartesian(-1.5,2);
	out = ComplexNumber.newCartesian(0.8860929094, 2.13904001);
	assertTrue(ComplexMath.Equal(ComplexMath.Sinh(in), out),
		   "Sinh(-1.5+2I) error");
	in = ComplexNumber.newCartesian(0,10);
	out = ComplexNumber.newCartesian(0,-0.5440211109);
	assertTrue(ComplexMath.Equal(ComplexMath.Sinh(in), out),
		   "Sinh(10I) error");
    }

    static void testComplexMath_Cot(){
	ComplexNumber in;

	//Common positive values
	assertTrue(Double.isInfinite(ComplexMath.Cot(zero).Re() ) &&
		   Double.isInfinite(ComplexMath.Cot(zero).Im() ), 
		   "Cot(0) error");
	in = ComplexMath.Quotient(ComplexMath.PI,ComplexNumber.newCartesian(6,0));
	assertTrue(ComplexMath.Equal(ComplexMath.Cot(in) , 
				     ComplexMath.Quotient(root3,one)) , 
		   "Cot(PI/6) error");
	in = ComplexMath.Quotient(ComplexMath.PI,ComplexNumber.newCartesian(4,0));
	assertTrue(ComplexMath.Equal(ComplexMath.Cot(in) , one ),
		   "Cot(PI/4) error");
	in = ComplexMath.Quotient(ComplexMath.PI,ComplexNumber.newCartesian(3,0));
	assertTrue(ComplexMath.Equal(ComplexMath.Cot(in) , 
				     ComplexMath.Quotient(one,root3) ), 
		   "Cot(PI/3) error");
	in = ComplexMath.Quotient(ComplexMath.PI,two);
	assertTrue(ComplexMath.Equal(ComplexMath.Cot(in), zero),
		   "Cot(PI/2) error");
	in = ComplexMath.PI;
	assertTrue(Double.isInfinite(ComplexMath.Cot(in).Re()) &&
		   Double.isInfinite(ComplexMath.Cot(in).Im()),
		   "Cot(PI) error");
	
	//Common negative values
	in = ComplexMath.Quotient(ComplexMath.PI,ComplexNumber.newCartesian(6,0));
	in = ComplexMath.Neg(in);
	assertTrue(ComplexMath.Equal(ComplexMath.Cot(in) , 
				     ComplexMath.Neg(ComplexMath.Quotient(root3,one))),
		   "Cot(-PI/6) error");
	in = ComplexMath.Quotient(ComplexMath.PI,ComplexNumber.newCartesian(4,0));
	in = ComplexMath.Neg(in);
	assertTrue(ComplexMath.Equal(ComplexMath.Cot(in) , ComplexMath.Neg(one) ),
		   "Cot(-PI/4) error");
	in = ComplexMath.Quotient(ComplexMath.PI,ComplexNumber.newCartesian(3,0));
	in = ComplexMath.Neg(in);
	assertTrue(ComplexMath.Equal(ComplexMath.Cot(in) , 
				     ComplexMath.Neg(ComplexMath.Quotient(one,root3))),
		   "Cot(-PI/3) error");
	in = ComplexMath.Quotient(ComplexMath.PI,two);
	in = ComplexMath.Neg(in);
	assertTrue(ComplexMath.Equal(ComplexMath.Cot(in), ComplexMath.Neg(zero)),
		   "Cot(-PI/2) error");
	in = ComplexMath.PI;
	in = ComplexMath.Neg(in);
	assertTrue(Double.isInfinite(ComplexMath.Cot(in).Re()) &&
		   Double.isInfinite(ComplexMath.Cot(in).Im()),
		   "Cot(-PI) error");
	
	//Complex numbers
	in = ComplexMath.I;
	assertTrue(ComplexMath.Equal(ComplexMath.Cot(in) , 
				     ComplexNumber.newCartesian(0,-1.31303528549933)) ,
		   "Cot(I) error");
	in = ComplexMath.Neg(ComplexMath.I);
	assertTrue(ComplexMath.Equal(ComplexMath.Cot(in) , 
				     ComplexNumber.newCartesian(0,1.31303528549933)) ,
		   "Cot(-I) error");
	in = ComplexNumber.newCartesian(1,1);
	assertTrue(ComplexMath.Equal(ComplexMath.Cot(in) , 
				     ComplexNumber.newCartesian(0.217621561854405, -0.868014142895928)),
		   "Cot(1+I) error");
	in = ComplexNumber.newCartesian(-1,-1);
	assertTrue(ComplexMath.Equal(ComplexMath.Cot(in) , 
				     ComplexNumber.newCartesian(-0.217621561854405, 0.868014142895928)),
		   "Cot(-1-I) error");
    }


    static void testComplexMath_Sec(){
	ComplexNumber in;

	//Common positive values
	assertTrue(ComplexMath.Equal(ComplexMath.Sec(zero) , one),
		   "Sec(0) error");
	in = ComplexMath.Quotient(ComplexMath.PI,ComplexNumber.newCartesian(6,0));
	assertTrue(ComplexMath.Equal(ComplexMath.Sec(in) , ComplexMath.Quotient(two,root3)) , 
		   "Sec(PI/6) error");
	in = ComplexMath.Quotient(ComplexMath.PI,ComplexNumber.newCartesian(4,0));
	assertTrue(ComplexMath.Equal(ComplexMath.Sec(in) , ComplexMath.Quotient(two,root2)) , 
		   "Sec(PI/4) error");
	in = ComplexMath.Quotient(ComplexMath.PI,ComplexNumber.newCartesian(3,0));
	assertTrue(ComplexMath.Equal(ComplexMath.Sec(in) , two) , 
		   "Sec(PI/3) error");
	in = ComplexMath.Quotient(ComplexMath.PI,two);
	assertTrue(Double.isInfinite(ComplexMath.Sec(in).Re() ) &&
		   Double.isInfinite(ComplexMath.Sec(in).Re() ) ,
		   "Sec(PI/2) error");
	in = ComplexMath.PI;
	assertTrue(ComplexMath.Equal(ComplexMath.Sec(in) , ComplexMath.Neg(one)) , 
		   "Sec(PI) error");

	//Common negative values
	in = ComplexMath.Quotient(ComplexMath.PI,ComplexNumber.newCartesian(6,0));
	in = ComplexMath.Neg(in);
	assertTrue(ComplexMath.Equal(ComplexMath.Sec(in) , 
				     ComplexMath.Quotient(two,root3)) , 
		   "Sec(-PI/6) error");
	in = ComplexMath.Quotient(ComplexMath.PI,ComplexNumber.newCartesian(4,0));
	in = ComplexMath.Neg(in);
	assertTrue(ComplexMath.Equal(ComplexMath.Sec(in) , ComplexMath.Quotient(two,root2)) , 
		   "Sec(-PI/4) error");
	in = ComplexMath.Quotient(ComplexMath.PI,ComplexNumber.newCartesian(3,0));
	in = ComplexMath.Neg(in);
	assertTrue(ComplexMath.Equal(ComplexMath.Sec(in) , two ), 
		   "Sec(-PI/3) error");
	in = ComplexMath.Quotient(ComplexMath.PI,two);
	in = ComplexMath.Neg(in);
	assertTrue(Double.isInfinite(ComplexMath.Sec(in).Re() ) &&
		   Double.isInfinite(ComplexMath.Sec(in).Re() ) ,
		   "Sec(-PI/2) error");
	in = ComplexMath.PI;
	in = ComplexMath.Neg(in);
	assertTrue(ComplexMath.Equal(ComplexMath.Sec(in) , ComplexMath.Neg(one)) , 
		   "Sec(-PI) error");

	//Complex numbers
	in = ComplexMath.I;
	assertTrue(ComplexMath.Equal(ComplexMath.Sec(in) , 
				     ComplexNumber.newCartesian(0.648054273663887,0)) ,
		   "Sec(I) error");
	in = ComplexMath.Neg(ComplexMath.I);
	assertTrue(ComplexMath.Equal(ComplexMath.Sec(in) , 
				     ComplexNumber.newCartesian(0.648054273663887,0)) ,
		   "Sec(-I) error");
	in = ComplexNumber.newCartesian(1,1);
	assertTrue(ComplexMath.Equal(ComplexMath.Sec(in) , 
				     ComplexNumber.newCartesian(0.498337030555187, 0.591083841721045)),
		   "Sec(1+I) error");
	in = ComplexNumber.newCartesian(-1,-1);
	assertTrue(ComplexMath.Equal(ComplexMath.Sec(in) , 
				     ComplexNumber.newCartesian(0.498337030555187, 0.591083841721045)),
		   "Sec(-1-I) error");
    }


    static void testComplexMath_Csc(){
	ComplexNumber in;

	//Common positive values
	assertTrue(Double.isInfinite(ComplexMath.Csc(zero).Re()) &&
		   Double.isInfinite(ComplexMath.Csc(zero).Re()) , 
		   "Csc(0) error");
	in = ComplexMath.Quotient(ComplexMath.PI,ComplexNumber.newCartesian(6,0));
	assertTrue(ComplexMath.Equal(ComplexMath.Csc(in) , two) , 
		   "Csc(PI/6) error");
	in = ComplexMath.Quotient(ComplexMath.PI,ComplexNumber.newCartesian(4,0));
	assertTrue(ComplexMath.Equal(ComplexMath.Csc(in) , ComplexMath.Quotient(two,root2)) , 
		   "Csc(PI/4) error");
	in = ComplexMath.Quotient(ComplexMath.PI,ComplexNumber.newCartesian(3,0));
	assertTrue(ComplexMath.Equal(ComplexMath.Csc(in) , ComplexMath.Quotient(two,root3)) , 
		   "Csc(PI/3) error");
	in = ComplexMath.Quotient(ComplexMath.PI,two);
	assertTrue(ComplexMath.Equal(ComplexMath.Csc(in) , one) , 
		   "Csc(PI/2) error");
	in = ComplexMath.Quotient(ComplexMath.PI,one);
	assertTrue(Double.isInfinite(ComplexMath.Csc(zero).Re()) &&
		   Double.isInfinite(ComplexMath.Csc(zero).Re()) , 
		   "Csc(PI) error");


	//Common negative values
	in = ComplexMath.Neg(ComplexMath.Quotient(ComplexMath.PI,ComplexNumber.newCartesian(6,0)) );
	assertTrue(ComplexMath.Equal(ComplexMath.Csc(in) , ComplexMath.Neg(two)) , 
		   "Csc(-PI/6) error");
	in = ComplexMath.Neg(ComplexMath.Quotient(ComplexMath.PI,ComplexNumber.newCartesian(4,0)) );
	assertTrue(ComplexMath.Equal(ComplexMath.Csc(in) , 
				     ComplexMath.Neg(ComplexMath.Quotient(two,root2)) ), 
		   "Csc(-PI/4) error");
	in = ComplexMath.Neg(ComplexMath.Quotient(ComplexMath.PI,ComplexNumber.newCartesian(3,0)) );
	assertTrue(ComplexMath.Equal(ComplexMath.Csc(in) , ComplexMath.Neg(ComplexMath.Quotient(two,root3)) ), 
		   "Csc(-PI/3) error");
	in = ComplexMath.Neg(ComplexMath.Quotient(ComplexMath.PI,two) );
	assertTrue(ComplexMath.Equal(ComplexMath.Csc(in) , ComplexMath.Neg(one) ), 
		   "Csc(-PI/2) error");
	in = ComplexMath.Neg(ComplexMath.Quotient(ComplexMath.PI,one) );
	assertTrue(Double.isInfinite(ComplexMath.Csc(zero).Re()) &&
		   Double.isInfinite(ComplexMath.Csc(zero).Re()) , 
		   "Csc(-PI) error");
	
	//Complex numbers
	in = ComplexMath.I;
	assertTrue(ComplexMath.Equal(ComplexMath.Csc(in) , 
				     ComplexNumber.newCartesian(0,-0.850918128239323)) ,
		   "Csc(I) error");
	in = ComplexMath.Neg(ComplexMath.I);
	assertTrue(ComplexMath.Equal(ComplexMath.Csc(in) , 
				     ComplexNumber.newCartesian(0,0.850918128239323)) ,
		   "Csc(-I) error");
	in = ComplexNumber.newCartesian(1,1);
	assertTrue(ComplexMath.Equal(ComplexMath.Csc(in) , 
				     ComplexNumber.newCartesian(0.621518017170428, -0.303931001628425)),
		   "Csc(1+I) error");
	in = ComplexNumber.newCartesian(-1,-1);
	assertTrue(ComplexMath.Equal(ComplexMath.Csc(in) , 
				     ComplexNumber.newCartesian(-0.621518017170428, 0.303931001628425)),
		   "Csc(-1-I) error");
    }


    static void testComplexMath_Tan(){
	ComplexNumber in;

	//Common positive values
	assertTrue(ComplexMath.Equal(ComplexMath.Tan(zero) , zero) , 
		   "Tan(0) error");
	in = ComplexMath.Quotient(ComplexMath.PI,ComplexNumber.newCartesian(6,0));
	assertTrue(ComplexMath.Equal(ComplexMath.Tan(in) , ComplexMath.Quotient(one,root3)) , 
		   "Tan(PI/6) error");
	in = ComplexMath.Quotient(ComplexMath.PI,ComplexNumber.newCartesian(4,0));
	assertTrue(ComplexMath.Equal(ComplexMath.Tan(in) , one ),
		   "Tan(PI/4) error");
	in = ComplexMath.Quotient(ComplexMath.PI,ComplexNumber.newCartesian(3,0));
	assertTrue(ComplexMath.Equal(ComplexMath.Tan(in) , root3) , 
		   "Tan(PI/3) error");
	in = ComplexMath.Quotient(ComplexMath.PI,two);
	assertTrue(Double.isInfinite(ComplexMath.Tan(in).Re()) && Double.isInfinite(ComplexMath.Tan(in).Re()) ,
		   "Tan(PI/2) error");
	in = ComplexMath.PI;
	assertTrue(ComplexMath.Equal(ComplexMath.Tan(in) , zero) , 
		   "Tan(PI) error");
	
	//Common negative values
	assertTrue(ComplexMath.Equal(ComplexMath.Tan(ComplexMath.Neg(zero)) , ComplexMath.Neg(zero)) , 
		   "Tan(-0) error");
	in = ComplexMath.Quotient(ComplexMath.PI,ComplexNumber.newCartesian(6,0));
	assertTrue(ComplexMath.Equal(ComplexMath.Tan(ComplexMath.Neg(in)) , 
				     ComplexMath.Neg(ComplexMath.Quotient(one,root3))) , 
		   "Tan(-PI/6) error");
	in = ComplexMath.Quotient(ComplexMath.PI,ComplexNumber.newCartesian(4,0));
	assertTrue(ComplexMath.Equal(ComplexMath.Tan(ComplexMath.Neg(in)) , ComplexMath.Neg(one) ),
		   "Tan(-PI/4) error");
	in = ComplexMath.Quotient(ComplexMath.PI,ComplexNumber.newCartesian(3,0));
	assertTrue(ComplexMath.Equal(ComplexMath.Tan(ComplexMath.Neg(in)) , ComplexMath.Neg(root3)),
		   "Tan(-PI/3) error");
	in = ComplexMath.Quotient(ComplexMath.PI,two);
	assertTrue(Double.isInfinite(ComplexMath.Tan(ComplexMath.Neg(in)).Re()) &&
		   Double.isInfinite(ComplexMath.Tan(ComplexMath.Neg(in)).Im()),
		   "Tan(-PI/2) error");
	in = ComplexMath.PI;
	assertTrue(ComplexMath.Equal(ComplexMath.Tan(ComplexMath.Neg(in)) , zero) , 
		   "Tan(-PI) error");
	
	//Complex numbers
	in = ComplexMath.I;
	assertTrue(ComplexMath.Equal(ComplexMath.Tan(in) , 
				     ComplexNumber.newCartesian(0,0.761594155955765)) ,
		   "Tan(I) error");
	in = ComplexMath.Neg(ComplexMath.I);
	assertTrue(ComplexMath.Equal(ComplexMath.Tan(in) , 
				     ComplexNumber.newCartesian(0,-0.761594155955765)) ,
		   "Tan(-I) error");
	in = ComplexNumber.newCartesian(1,1);
	assertTrue(ComplexMath.Equal(ComplexMath.Tan(in) , 
				     ComplexNumber.newCartesian(0.271752585319512, 1.08392332733869)),
		   "Tan(1+I) error");
	in = ComplexNumber.newCartesian(-1,-1);
	assertTrue(ComplexMath.Equal(ComplexMath.Tan(in) , 
				     ComplexNumber.newCartesian(-0.271752585319512, -1.08392332733869)),
		   "Tan(-1-I) error");
    }

    static void testComplexMath_Sin(){
	ComplexNumber in;

	//Common positive values
	assertTrue(ComplexMath.Equal(ComplexMath.Sin(zero) , zero) , 
		   "Sin(0) error");
	in = ComplexMath.Quotient(ComplexMath.PI,ComplexNumber.newCartesian(6,0));
	assertTrue(ComplexMath.Equal(ComplexMath.Sin(in) , ComplexMath.Quotient(one,two)) , 
		   "Sin(PI/6) error");
	in = ComplexMath.Quotient(ComplexMath.PI,ComplexNumber.newCartesian(4,0));
	assertTrue(ComplexMath.Equal(ComplexMath.Sin(in) , ComplexMath.Quotient(root2,two)) , 
		   "Sin(PI/4) error");
	in = ComplexMath.Quotient(ComplexMath.PI,ComplexNumber.newCartesian(3,0));
	assertTrue(ComplexMath.Equal(ComplexMath.Sin(in) , ComplexMath.Quotient(root3,two)) , 
		   "Sin(PI/3) error");
	in = ComplexMath.Quotient(ComplexMath.PI,two);
	assertTrue(ComplexMath.Equal(ComplexMath.Sin(in) , one) , 
		   "Sin(PI/2) error");
	in = ComplexMath.Quotient(ComplexMath.PI,one);
	assertTrue(ComplexMath.Equal(ComplexMath.Sin(in) , zero) , 
		   "Sin(PI) error");

	//Common negative values
	assertTrue(ComplexMath.Equal(ComplexMath.Sin(ComplexMath.Neg(zero)) , zero) , 
		   "Sin(-0) error");
	in = ComplexMath.Quotient(ComplexMath.PI,ComplexNumber.newCartesian(6,0));
	assertTrue(ComplexMath.Equal(ComplexMath.Sin(ComplexMath.Neg(in)) , 
				     ComplexMath.Neg(ComplexMath.Quotient(one,two))) , 
		   "Sin(-PI/6) error");
	in = ComplexMath.Quotient(ComplexMath.PI,ComplexNumber.newCartesian(4,0));
	assertTrue(ComplexMath.Equal(ComplexMath.Sin(ComplexMath.Neg(in)) , 
				     ComplexMath.Neg(ComplexMath.Quotient(root2,two))) , 
		   "Sin(-PI/4) error");
	in = ComplexMath.Quotient(ComplexMath.PI,ComplexNumber.newCartesian(3,0));
	assertTrue(ComplexMath.Equal(ComplexMath.Sin(ComplexMath.Neg(in)) , 
				     ComplexMath.Neg(ComplexMath.Quotient(root3,two)) ), 
		   "Sin(-PI/3) error");
	in = ComplexMath.Quotient(ComplexMath.PI,two);
	assertTrue(ComplexMath.Equal(ComplexMath.Sin(ComplexMath.Neg(in)) , ComplexMath.Neg(one)) , 
		   "Sin(-PI/2) error");
	in = ComplexMath.Neg(ComplexMath.PI);
	assertTrue(ComplexMath.Equal(ComplexMath.Sin(in) , zero) , 
		   "Sin(-PI) error");
	
	//Complex numbers
	in = ComplexMath.I;
	assertTrue(ComplexMath.Equal(ComplexMath.Sin(in) , 
				     ComplexNumber.newCartesian(0,1.1752011936438)) ,
		   "Sin(I) error");
	in = ComplexMath.Neg(ComplexMath.I);
	assertTrue(ComplexMath.Equal(ComplexMath.Sin(in) , 
				     ComplexNumber.newCartesian(0,-1.1752011936438)) ,
		   "Sin(-I) error");
	in = ComplexNumber.newCartesian(1,1);
	assertTrue(ComplexMath.Equal(ComplexMath.Sin(in) , 
				     ComplexNumber.newCartesian(1.2984575814,0.634963914)),
		   "Sin(1+I) error");
	in = ComplexNumber.newCartesian(-1,-1);
	assertTrue(ComplexMath.Equal(ComplexMath.Sin(in) , 
				     ComplexNumber.newCartesian(-1.298457581,-0.63496391)),
		   "Sin(-1-I) error");
    }
    
    static void testComplexMath_Cos(){
	ComplexNumber in;

	//Common positive values
	assertTrue(ComplexMath.Equal(ComplexMath.Cos(zero) , one) , 
		   "Cos(0) error");
	in = ComplexMath.Quotient(ComplexMath.PI,ComplexNumber.newCartesian(6,0));
	assertTrue(ComplexMath.Equal(ComplexMath.Cos(in) , ComplexMath.Quotient(root3,two)) , 
		   "Cos(PI/6) error");
	in = ComplexMath.Quotient(ComplexMath.PI,ComplexNumber.newCartesian(4,0));
	assertTrue(ComplexMath.Equal(ComplexMath.Cos(in) , ComplexMath.Quotient(root2,two)) , 
		   "Cos(PI/4) error");
	in = ComplexMath.Quotient(ComplexMath.PI,ComplexNumber.newCartesian(3,0));
	assertTrue(ComplexMath.Equal(ComplexMath.Cos(in) , ComplexMath.Quotient(one,two)) , 
		   "Cos(PI/3) error");
	in = ComplexMath.Quotient(ComplexMath.PI,two);
	assertTrue(ComplexMath.Equal(ComplexMath.Cos(in) , zero) , 
		   "Cos(PI/2) error");
	in = ComplexMath.PI;
	assertTrue(ComplexMath.Equal(ComplexMath.Cos(in) , ComplexMath.Neg(one)) , 
		   "Cos(PI) error");

	//Common negative values
	assertTrue(ComplexMath.Equal(ComplexMath.Cos(ComplexMath.Neg(zero)) , one) , 
		   "Cos(0) error");
	in = ComplexMath.Neg(ComplexMath.Quotient(ComplexMath.PI,ComplexNumber.newCartesian(6,0)) );
	assertTrue(ComplexMath.Equal(ComplexMath.Cos(in) , ComplexMath.Quotient(root3,two)) , 
		   "Cos(PI/6) error");
	in = ComplexMath.Neg(ComplexMath.Quotient(ComplexMath.PI,ComplexNumber.newCartesian(4,0)));
	assertTrue(ComplexMath.Equal(ComplexMath.Cos(in) , ComplexMath.Quotient(root2,two)) , 
		   "Cos(PI/4) error");
	in = ComplexMath.Neg(ComplexMath.Quotient(ComplexMath.PI,ComplexNumber.newCartesian(3,0)) );
	assertTrue(ComplexMath.Equal(ComplexMath.Cos(in) , ComplexMath.Quotient(one,two)) , 
		   "Cos(PI/3) error");
	in = ComplexMath.Neg(ComplexMath.Quotient(ComplexMath.PI,two) );;
	assertTrue(ComplexMath.Equal(ComplexMath.Cos(in) , zero) , 
		   "Cos(PI/2) error");
	in = ComplexMath.Neg(ComplexMath.PI );;
	assertTrue(ComplexMath.Equal(ComplexMath.Cos(in) , ComplexMath.Neg(one)) , 
		   "Cos(PI) error");

	
	//Complex numbers
	in = ComplexMath.I;
	assertTrue(ComplexMath.Equal(ComplexMath.Cos(in) , 
				     ComplexNumber.newCartesian(1.54308063481524,0)) ,
		   "Cos(I) error");
	in = ComplexMath.Neg(ComplexMath.I);
	assertTrue(ComplexMath.Equal(ComplexMath.Cos(in) , 
				     ComplexNumber.newCartesian(1.54308063481524,0)) ,
		   "Cos(-I) error");
	in = ComplexNumber.newCartesian(1,1);
	assertTrue(ComplexMath.Equal(ComplexMath.Cos(in) , 
				     ComplexNumber.newCartesian(0.833730025131149,-0.988897705762865)),
		   "Cos(1+I) error");
	in = ComplexNumber.newCartesian(-1,-1);
	assertTrue(ComplexMath.Equal(ComplexMath.Cos(in) , 
				     ComplexNumber.newCartesian(0.833730025131149,-0.988897705762865)),
		   "Cos(-1-I) error");
    }


    static void testComplexMath_Log(){
	ComplexNumber z = ComplexMath.Log(ComplexNumber.newCartesian(0,0) );
	assertTrue( ((Double)z.Re()).isNaN() , "Log(0) error");
	assertTrue( ((Double)z.Im()).isNaN(), "Log(0) error");

	assertTrue(ComplexMath.Equal(ComplexMath.Log(ComplexMath.E) , one) , "Log(E) error");
	assertTrue(ComplexMath.Equal(ComplexMath.Log(ComplexNumber.newCartesian(Math.pow(Math.E,2) , 0))
		      , ComplexNumber.newCartesian(2 , 0))
		   , "Log(E^2) error");
	assertTrue(ComplexMath.Equal(ComplexMath.Log(ComplexMath.PI),
				     ComplexNumber.newCartesian(1.144729886 , 0)),
		   "Log(PI) error");

	double piOVER2 = Math.PI / 2;
	assertTrue(ComplexMath.Equal(ComplexMath.Log(ComplexMath.I),
				     ComplexNumber.newPolar(piOVER2,piOVER2)),
		   "Log(I) error");

	z = ComplexNumber.newCartesian(3.4,-3.01);
	assertTrue(ComplexMath.Equal(ComplexMath.Log(z) , 
				     ComplexNumber.newCartesian(1.513133164 , -0.7246306389)),
		   "Log(z) error");
    }
    
    static void testComplexMath_Pow(){
	ComplexNumber z1 = ComplexNumber.newCartesian(3.5 , -2.3);
	ComplexNumber z2 = ComplexNumber.newCartesian(3.019, 2.03);
	ComplexNumber z;

	z = ComplexMath.Pow(z1,z2);
	assertTrue( eq(z.Re() , 99.86016614) , "Pow(z1,z2).Re() error");
	assertTrue( eq(z.Im() , 224.499326) , "Pow(z1,z2).Im() error");
	
	z = ComplexMath.Pow(z1,one);
	assertTrue( eq(z.Re() , z1.Re()) , "Pow(z1,one).Re() error");
	assertTrue( eq(z.Im() , z1.Im()) , "Pow(z1,one).Im() error");

	z = ComplexMath.Pow(z1,zero);
	assertTrue( eq(z.Re() , 1) , "Pow(z1,zero).Re() error");
	assertTrue( eq(z.Im() , 0) , "Pow(z1,zero).Im() error");

	z = ComplexMath.Pow(zero,zero);
	assertTrue( eq(z.Re() , Double.NaN) , "Pow(zero,zero).Re() error");
	assertTrue( eq(z.Im() , Double.NaN) , "Pow(zero,zero).Im() error");

	z = ComplexMath.Pow(one,z2);
	assertTrue( eq(z.Re() , 1) , "Pow(one,z2).Re() error");
	assertTrue( eq(z.Im() , 0) , "Pow(one,z2).Im() error");      

	z = ComplexMath.Pow(ComplexMath.INFINITY,ComplexMath.INFINITY);
	assertTrue( eq(z.Re() , Double.NaN) , "Pow(INFINITY,INFINITY).Re() error");
	assertTrue( eq(z.Im() , Double.NaN) , "Pow(INFINITY,INFINITY).Im() error");      

    }

    static void testComplexMath_Sum(){
	ComplexNumber z1 = ComplexNumber.newCartesian(3.5 , -2.3);
	ComplexNumber z2 = ComplexNumber.newPolar(3.019, 2.03);

	ComplexNumber z = ComplexMath.Sum(z1,z2);
	assertTrue( eq(z.Re(), z1.Re()+z2.Re() ) , "Sum(z1,z2).Re() error" );
	assertTrue( eq(z.Im(), z1.Im()+z2.Im() ) , "Sum(z1,z2).Im() error");

	z = ComplexMath.Sum(z1,zero);
	assertTrue( eq(z.Re(), z1.Re() ) , "Sum(z1,zero).Re() error");
	assertTrue( eq(z.Im(), z1.Im() ) , "Sum(z1,zero).Im() error");

	z = ComplexMath.Sum(z1,one);
	assertTrue( eq(z.Re(), z1.Re()+one.Re() ) , "Sum(z1,one).Re() error");
	assertTrue( eq(z.Im(), z1.Im()+one.Im() ) , "Sum(z1,one).Im() error");

	z = ComplexMath.Sum(z1,i);
	assertTrue( eq(z.Re(), z1.Re()+i.Re() ) , "Sum(z1,i).Re() error");
	assertTrue( eq(z.Im(), z1.Im()+i.Im() ) , "Sum(z1,i).Im() error");
    }

    static void testComplexMath_Difference(){
	ComplexNumber z1 = ComplexNumber.newCartesian(3.5 , -2.3);
	ComplexNumber z2 = ComplexNumber.newPolar(3.019, 2.03);

	ComplexNumber z = ComplexMath.Difference(z1,z2);
	assertTrue( eq(z.Re(), z1.Re()-z2.Re() ) , "Difference(z1,z2).Re() error");
	assertTrue( eq(z.Im(), z1.Im()-z2.Im() ) , "Difference(z1,z2).Im() error");

	z = ComplexMath.Difference(z1,zero);
	assertTrue( eq(z.Re(), z1.Re() ) , "Difference(z1,zero).Re() error");
	assertTrue( eq(z.Im(), z1.Im() ) , "Difference(z1,zero).Im() error");

	z = ComplexMath.Difference(z1,one);
	assertTrue( eq(z.Re(), z1.Re()-one.Re() ) , "Difference(z1,one).Re() error");
	assertTrue( eq(z.Im(), z1.Im()-one.Im() ) , "Difference(z1,one).Im() error");

	z = ComplexMath.Difference(z1,i);
	assertTrue( eq(z.Re(), z1.Re()-i.Re() ) , "Difference(z1,i).Re() error");
	assertTrue( eq(z.Im(), z1.Im()-i.Im() ) , "Difference(z1,i).Im() error");
    }


    static void testComplexMath_Product(){
	ComplexNumber z1 = ComplexNumber.newCartesian(3.5 , -2.3);
	ComplexNumber z2 = ComplexNumber.newPolar(3.019, 2.03);

	ComplexNumber z = ComplexMath.Product(z1,z2);
	assertTrue( eq(z.R(), z1.R()*z2.R() ) , "Product(z1,z2).R() error");
	assertTrue( eq(z.Theta(), z1.Theta()+z2.Theta() ) , "Product(z1,z2).Theta() error");

	z = ComplexMath.Product(z1,zero);
	assertTrue( eq(z.R(), 0 ) , "Product(z1,zero).R() error");

	z = ComplexMath.Product(z1,one);
	assertTrue( eq(z.R(), z1.R()*one.R() ) , "Product(z1,one).R() error");
	assertTrue( eq(z.Theta(), z1.Theta()+one.Theta() ) , "Product(z1,one).Theta() error");

	z = ComplexMath.Product(z1,i);
	assertTrue( eq(z.R(), z1.R()*i.R() ) , "Product(z1,i).R() error");
	assertTrue( eq(z.Theta(), z1.Theta()+i.Theta() ) , "Product(z1,i).Theta() error");
    }


    static void testComplexMath_Quotient(){
	ComplexNumber z1 = ComplexNumber.newCartesian(3.5 , -2.3);
	ComplexNumber z2 = ComplexNumber.newPolar(3.019, 2.03);

	ComplexNumber z = ComplexMath.Quotient(z1,z2);
	assertTrue( eq(z.R(), z1.R()/z2.R() ) , "Quotient(z1,z2).R() error");
	assertTrue( eq(z.Theta(), z1.Theta()-z2.Theta() ) , "Quotient(z1,z2).Theta() error");

	z = ComplexMath.Quotient(z1,zero);
	assertTrue( eq(z.R(), Double.POSITIVE_INFINITY ) , "Quotient(z1,zero).R() error");

	z = ComplexMath.Quotient(z1,one);
	assertTrue( eq(z.R(), z1.R()/one.R() ) , "Quotient(z1,one).R() error");
	assertTrue( eq(z.Theta(), z1.Theta()-one.Theta() ) , "Quotient(z1,one).Theta() error");

	z = ComplexMath.Quotient(z1,i);
	assertTrue( eq(z.R(), z1.R()/i.R() ) , "Quotient(z1,i).R() error");
	assertTrue( eq(z.Theta(), z1.Theta()-i.Theta() ) , "Quotient(z1,i).Theta() error");
    }


    static void testComplexMath_fields(){
	assertTrue( eq(ComplexMath.E.Re() , Math.E) , "ComplexMath.E != Math.E");
	assertTrue( eq(ComplexMath.E.Im() , 0) , "ComplexMath.E.Im() != 0.00"); 
	assertTrue( eq(ComplexMath.PI.Re() , Math.PI) , "ComplexMath.PI != Math.PI");
	assertTrue( eq(ComplexMath.PI.Im() , 0) , "ComplexMath.PI.Im() != 0.00"); 
	assertTrue( eq(ComplexMath.I.Re() , 0) , "ComplexMath.I.Re() != 0.00");
	assertTrue( eq(ComplexMath.I.Im() , 1) , "ComplexMath.I.Im() != 1.00"); 

    }

    static void testComplexNumber(){
	ComplexNumber z1 = ComplexNumber.newCartesian(3,4);
	ComplexNumber z2 = ComplexNumber.newPolar(z1.R() , z1.Theta() );
	assertTrue( eq(z1.R() , z2.R() ) , "R() not equal");
	assertTrue( eq(z1.Theta() , z2.Theta() ) , "Theta() not equal");
	assertTrue( eq(z1.Re() , z2.Re() ) , "Re() not equal");
	assertTrue( eq(z1.Im() , z2.Im() ) , "Im() not equal");
	
	z1 = ComplexNumber.newCartesian(3.5,4.3);
	z2 = ComplexNumber.newPolar(z1.R() , z1.Theta() );
	assertTrue( eq(z1.R() , z2.R() ) , "R() not equal");
	assertTrue( eq(z1.Theta() , z2.Theta() ) , "Theta() not equal");
	assertTrue( eq(z1.Re() , z2.Re() ) , "Re() not equal");
	assertTrue( eq(z1.Im() , z2.Im() ) , "Im() not equal");
	
	z2 = z1.conjugate();
	assertTrue( eq(z1.R() , z2.R() ) , "R() not equal");
	assertTrue( eq(z1.Re() , z2.Re() ) , "Re() not equal");
	assertTrue( eq(z1.Im() , - z2.Im() ) , "Im() not opposite");

	z1 = ComplexNumber.newPolar(1,0);
	z2 = ComplexNumber.newPolar(1,2*Math.PI);
	assertTrue(ComplexMath.Equal(z1,z2), "2*PI period error");
    }


    static double round (double num, int places){
	int div = (int) Math.pow(10, places);
	return (double) Math.round(num * div) / div ;
    }

    static boolean eq(double d1 , double d2){
	return (  round(d1,5) == round(d2,5)  );
    }

    static void assertTrue(boolean bool, String msg){
	if (! bool)
	    System.err.println(msg);
    }

}
