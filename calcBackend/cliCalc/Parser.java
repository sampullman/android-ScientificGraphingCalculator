package cliCalc;
//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 10 "calc.y"
  import java.lang.Math;
  import java.io.*;
  import java.util.regex.Pattern;;
  import java.util.HashMap;
  import java.util.StringTokenizer;
  
//#line 24 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short NUM=257;
public final static short FNCT=258;
public final static short VAR=259;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    1,    2,    2,    2,    2,    2,    2,    2,
    2,    2,    2,    2,
};
final static short yylen[] = {                            2,
    0,    2,    1,    1,    4,    3,    1,    3,    3,    3,
    3,    3,    3,    3,
};
final static short yydefred[] = {                         1,
    0,    4,    0,    0,    0,    2,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   14,    0,
    0,    0,    0,    0,    0,    5,
};
final static short yydgoto[] = {                          1,
    6,    7,
};
final static short yysindex[] = {                         0,
  -40,    0,  -38,  -57,  -40,    0,  -15,  -40,  -40,  -31,
  -40,  -40,  -40,  -40,  -40,  -40,  -23,  -15,    0,   -7,
   -7,  -91,  -91,  -91,  -91,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    1,    0,    0,   29,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   61,    0,   45,
   53,    9,   17,   25,   33,    0,
};
final static short yygindex[] = {                         0,
    0,  105,
};
final static int YYTABLESIZE=320;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                          5,
    7,    8,   15,    9,    0,    0,    0,    0,   10,   19,
   13,   12,    0,   11,    0,   14,   11,   26,   13,   12,
    0,   11,   16,   14,   13,    0,   13,   12,    3,   11,
    0,   14,   12,    0,   13,    0,    0,    0,    0,   14,
    7,    7,    7,    7,    9,    7,    0,    7,   10,   10,
   10,   10,    8,   10,    0,   10,   11,   11,   11,   11,
    6,   11,   15,   11,   13,   13,   13,   13,    3,   13,
   15,   13,   12,   12,   12,   12,    0,   12,   15,   12,
    0,    0,   16,    0,    9,    9,   15,    9,    0,    9,
   16,    0,    8,    8,    7,    8,    0,    8,   16,    0,
    6,    6,    0,    0,    0,    0,   16,    0,    0,   10,
    0,    0,   17,   18,    7,   20,   21,   22,   23,   24,
   25,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    2,    3,    4,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    7,    7,    7,
    0,    0,    0,    0,    0,   10,   10,   10,    0,    0,
    0,    0,    0,   11,   11,   11,    0,    0,    0,    0,
    0,   13,   13,   13,    0,    3,    3,    3,    0,   12,
   12,   12,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    9,    9,    9,    0,    0,    0,    0,    0,    8,
    8,    8,    0,    0,    0,    0,    0,    6,    6,    6,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
    0,   40,   94,   61,   -1,   -1,   -1,   -1,    0,   41,
   42,   43,   -1,   45,   -1,   47,    0,   41,   42,   43,
   -1,   45,  114,   47,    0,   -1,   42,   43,    0,   45,
   -1,   47,    0,   -1,   42,   -1,   -1,   -1,   -1,   47,
   40,   41,   42,   43,    0,   45,   -1,   47,   40,   41,
   42,   43,    0,   45,   -1,   47,   40,   41,   42,   43,
    0,   45,   94,   47,   40,   41,   42,   43,   40,   45,
   94,   47,   40,   41,   42,   43,   -1,   45,   94,   47,
   -1,   -1,  114,   -1,   40,   41,   94,   43,   -1,   45,
  114,   -1,   40,   41,   94,   43,   -1,   45,  114,   -1,
   40,   41,   -1,   -1,   -1,   -1,  114,   -1,   -1,    5,
   -1,   -1,    8,    9,  114,   11,   12,   13,   14,   15,
   16,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  257,  258,  259,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  257,  258,  259,
   -1,   -1,   -1,   -1,   -1,  257,  258,  259,   -1,   -1,
   -1,   -1,   -1,  257,  258,  259,   -1,   -1,   -1,   -1,
   -1,  257,  258,  259,   -1,  257,  258,  259,   -1,  257,
  258,  259,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  257,  258,  259,   -1,   -1,   -1,   -1,   -1,  257,
  258,  259,   -1,   -1,   -1,   -1,   -1,  257,  258,  259,
};
}
final static short YYFINAL=1;
final static short YYMAXTOKEN=259;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"'('","')'","'*'","'+'",null,
"'-'",null,"'/'",null,null,null,null,null,null,null,null,null,null,null,null,
null,"'='",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"'^'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,"'r'",null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,"NUM","FNCT","VAR",
};
final static String yyrule[] = {
"$accept : input",
"input :",
"input : input line",
"line : exp",
"exp : NUM",
"exp : FNCT '(' exp ')'",
"exp : VAR '=' exp",
"exp : VAR",
"exp : exp '+' exp",
"exp : exp '-' exp",
"exp : exp '*' exp",
"exp : exp '/' exp",
"exp : exp 'r' exp",
"exp : exp '^' exp",
"exp : '(' exp ')'",
};

//#line 46 "calc.y"


StringTokenizer inputString;
ComplexNumber ans;
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
  if (! inputString.hasMoreTokens() )//done
    return 0;

  String current = inputString.nextToken();

  if ( isDouble(current.substring(0,current.length() - 1) ) && (current.charAt(current.length() - 1) == 'I')){//Imaginary Number
    yylval = new ParserVal(  ComplexNumber.newCartesian(0,Double.valueOf(current.substring(0, current.length() -1) ) )  );
    return NUM;
  }else if (current.equals("PI") ){//PI
    yylval = new ParserVal( ComplexMath.PI );
    return NUM;
  }else if (current.equals("I") ){//Imaginary Unit
    yylval = new ParserVal( ComplexMath.I );
    return NUM;
  }else if (current.equals("E") ){//Euler's Number
    yylval = new ParserVal( ComplexMath.E );
    return NUM;
  }else if (isDouble(current) ){//Real Number
    yylval = new ParserVal(ComplexNumber.newCartesian(Double.valueOf(current).doubleValue(), 0) );
    return NUM;
  }
  
  if (current.length() == 1)//Primitive Operator
    return current.charAt(0);
  else{
    if ( FnctObj.isFnct(current) ){
      yylval = new ParserVal(new FnctObj(current) );//Function
      return FNCT;
    }else{
      yylval = new ParserVal(current);
      return VAR;
    }

  }
}

boolean isDouble(String s){
  try{
    double d = Double.valueOf(s);
    return true;
  }catch (Exception e){  }
  return false;
}

public void run (String arg){
  inputString = new StringTokenizer(arg);
  yyparse();
}


//#line 315 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 3:
//#line 30 "calc.y"
{ ans = (ComplexNumber)val_peek(0).obj; }
break;
case 4:
//#line 33 "calc.y"
{ yyval = val_peek(0); }
break;
case 5:
//#line 34 "calc.y"
{ yyval = new ParserVal( ( (FnctObj) val_peek(3).obj).operate((ComplexNumber)val_peek(1).obj));}
break;
case 6:
//#line 35 "calc.y"
{ vars.put(val_peek(2).sval,(ComplexNumber)val_peek(0).obj); yyval = new ParserVal((ComplexNumber)val_peek(0).obj);}
break;
case 7:
//#line 36 "calc.y"
{ yyval = new ParserVal(vars.get(val_peek(0).sval));}
break;
case 8:
//#line 37 "calc.y"
{ yyval = new ParserVal(ComplexMath.Sum((ComplexNumber)val_peek(2).obj , (ComplexNumber)val_peek(0).obj)); }
break;
case 9:
//#line 38 "calc.y"
{ yyval = new ParserVal(ComplexMath.Difference((ComplexNumber)val_peek(2).obj , (ComplexNumber)val_peek(0).obj)); }
break;
case 10:
//#line 39 "calc.y"
{ yyval = new ParserVal(ComplexMath.Product((ComplexNumber)val_peek(2).obj , (ComplexNumber)val_peek(0).obj)); }
break;
case 11:
//#line 40 "calc.y"
{ yyval = new ParserVal(ComplexMath.Quotient((ComplexNumber)val_peek(2).obj , (ComplexNumber)val_peek(0).obj)); }
break;
case 12:
//#line 41 "calc.y"
{ yyval = new ParserVal(ComplexMath.Pow((ComplexNumber)val_peek(2).obj, ComplexMath.Quotient(ComplexNumber.newCartesian(1,0),(ComplexNumber)val_peek(0).obj)));}
break;
case 13:
//#line 42 "calc.y"
{ yyval = new ParserVal(ComplexMath.Pow((ComplexNumber)val_peek(2).obj, (ComplexNumber)val_peek(0).obj)); }
break;
case 14:
//#line 43 "calc.y"
{ yyval = val_peek(1); }
break;
//#line 512 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
