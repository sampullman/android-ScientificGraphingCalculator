/* Get BYACC/J from: http://byaccj.sourceforge.net/
 * Compile with: yaccJ -J calc.y && javac Parser.java
 * To use:
 * Parser par = new Parser(false)
 * par.run("expression") returns the result of the expression
 */


%{
  package cliCalc;
  import java.lang.Math;
  import java.io.*;
  import java.util.regex.Pattern;;
  import java.util.HashMap;
  import java.util.StringTokenizer;
  import cliCalc.*;
  import java.util.ArrayList;
  %}

/* YACC Declarations */
%token NUM
%left FNCT
%left  '-' '+'
%left '*' '/'
%left NEG
%right VAR '^' 'r'

 /* Grammar follows */
%%
input: /* empty string */
| input line
;

line: exp { ans = (ComplexNumber)$1.obj; }
;

exp:    
  NUM     { $$ = $1; }
  | FNCT '(' exp ')' 
  { ((FnctObj) $1.obj).operate((ComplexNumber)$3.obj,
                               (ComplexNumber)pvals.newObj ()); $$ = pvals.get (); }
  | VAR '=' exp 
            { vars.put($1.sval,(ComplexNumber)$3.obj); 
              $$ = new ParserVal((ComplexNumber)$3.obj); }
  | VAR     { $$ = ((CalcItem)($1.obj)).getParserVal (); }
  | exp '+' exp 
  { ComplexMath.Add ((ComplexNumber)$1.obj,(ComplexNumber)$3.obj,
                     (ComplexNumber)pvals.newObj ()); $$ = pvals.get (); }
  | exp '-' exp 
  { ComplexMath.Sub ((ComplexNumber)$1.obj,(ComplexNumber)$3.obj,
                     (ComplexNumber)pvals.newObj ()); $$ = pvals.get (); }
  | exp '*' exp 
  { ComplexMath.Mult ((ComplexNumber)$1.obj,(ComplexNumber)$3.obj,
                      (ComplexNumber)pvals.newObj ()); $$ = pvals.get (); }
  | exp '/' exp 
  { ComplexMath.Div ((ComplexNumber)$1.obj,(ComplexNumber)$3.obj,
                     (ComplexNumber)pvals.newObj ()); $$ = pvals.get (); }
  | exp 'r' exp 
            { $$ = new ParserVal(ComplexMath.Pow((ComplexNumber)$1.obj,  
                                      ComplexMath.Quotient(ComplexNumber.newCartesian(1,0),
                                                           (ComplexNumber)$3.obj))); }
  | exp '^' exp 
  { ComplexMath.Pow ((ComplexNumber)$1.obj,(ComplexNumber)$3.obj,
                     (ComplexNumber)pvals.newObj ()); $$ = pvals.get (); }
  | '(' exp ')' { $$ = $2; }
  | '-' exp %prec NEG 
  { ComplexMath.Neg ((ComplexNumber)$2.obj,(ComplexNumber)pvals.newObj ()); $$ = pvals.get (); }
;
%%


ArrayList<CalcItem> input;
int cur;
CalcItem curItem;
ComplexNumber ans = new ComplexNumber (0,0);
PvalBox pvals = new PvalBox ();
HashMap<String,ComplexNumber> vars = new HashMap<String,ComplexNumber>();

public ComplexNumber getAns(){
  return ans;
}

public boolean getError(){
  if (this.yynerrs == 0)
    return false;
  return true;
}

void yyerror(String s) {
  /*FAIL SILENTLY */
  //System.err.println("FAILURE");
}

int yylex(){  
  if (cur == input.size () )//done
    return 0;

  curItem = input.get (cur);
  cur += 1;

  switch (curItem.getType ()) {
  case 0:
    return curItem.getVal ();
  case 1:
    yylval = curItem.getParserVal ();
    return NUM;
  case 2:
    yylval = curItem.getParserVal ();
    return FNCT;
  case 3:
    yylval = curItem.getParserVal ();
    return VAR;
  default:
    return curItem.getVal ();

  }

}

public void run (ArrayList<CalcItem> arg){
  input = arg;
  cur = 0;
  yyparse();
}


