/* Interface to android */
import java.io.*;
import cliCalc.*;

public class Main {

    public static string parse(String input){
	Parser par = new Parser(false);
	try {
	    par.run(input);
	    if(par.getError())
		return "BAD INPUT TO PARSER";
	    else {
		return num.toString();
	    }
	} catch (Exception e) {
	    return "ERROR UNDETECTED BY PARSER";
	}
    }

}
