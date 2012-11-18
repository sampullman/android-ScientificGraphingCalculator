#ifndef lint
static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";
#endif
#define YYBYACC 1
#line 10 "calc.y"
  package cliCalc;
  import java.lang.Math;
  import java.io.*;
  import java.util.regex.Pattern;;
  import java.util.HashMap;
  import java.util.StringTokenizer;
  import cliCalc.*;
  import java.util.ArrayList;
  
#line 16 "y.tab.c"
#define NUM 257
#define FNCT 258
#define NEG 259
#define VAR 260
#define YYERRCODE 256
short yylhs[] = {                                        -1,
    0,    0,    1,    2,    2,    2,    2,    2,    2,    2,
    2,    2,    2,    2,    2,
};
short yylen[] = {                                         2,
    0,    2,    1,    1,    4,    3,    1,    3,    3,    3,
    3,    3,    3,    3,    2,
};
short yydefred[] = {                                      1,
    0,    4,    0,    0,    0,    0,    2,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   14,    0,    0,    0,    0,    0,    0,    5,
};
short yydgoto[] = {                                       1,
    7,    8,
};
short yysindex[] = {                                      0,
  -40,    0,  -38,  -40,  -57,  -40,    0,  -15,  -40,  -91,
  -40,  -31,  -40,  -40,  -40,  -40,  -40,  -40,  -23,  -15,
    0,   42,   42,  -91,  -91,  -91,  -91,    0,
};
short yyrindex[] = {                                      0,
    0,    0,    0,    0,    1,    0,    0,   37,    0,    9,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   69,
    0,   53,   61,   17,   25,   33,   45,    0,
};
short yygindex[] = {                                      0,
    0,  107,
};
#define YYTABLESIZE 329
short yytable[] = {                                       6,
    7,    9,   17,   11,    4,    0,    0,    0,   15,   21,
   15,   14,    0,   13,    0,   16,   10,   28,   15,   14,
    0,   13,   18,   16,   11,    0,   15,   14,    0,   13,
    0,   16,   13,    0,    0,    0,    3,    0,    0,    0,
    7,    7,    7,    7,   12,    7,    0,    7,   15,   15,
   15,   15,    9,   15,    0,   15,   10,   10,   10,   10,
    8,   10,   17,   10,   11,   11,   11,   11,    6,   11,
   17,   11,   13,   13,   13,   13,    3,   13,   17,   13,
    0,    0,   18,   15,   12,   12,   12,   12,   16,   12,
   18,   12,    9,    9,    7,    9,    0,    9,   18,    0,
    8,    8,    0,    8,    0,    8,    0,    0,    6,    6,
   10,    0,   12,    0,    7,   19,    0,   20,    0,   22,
   23,   24,   25,   26,   27,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   17,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   18,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    2,    3,    0,    5,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    7,    7,    0,
    7,    0,    0,    0,    0,   15,   15,    0,   15,    0,
    0,    0,    0,   10,   10,    0,   10,    0,    0,    0,
    0,   11,   11,    0,   11,    0,    0,    0,    0,   13,
   13,    0,   13,    3,    3,    0,    3,    0,    0,    0,
    0,   12,   12,    0,   12,    0,    0,    0,    0,    9,
    9,    0,    9,    0,    0,    0,    0,    8,    8,    0,
    8,    0,    0,    0,    0,    6,    6,    0,    6,
};
short yycheck[] = {                                      40,
    0,   40,   94,   61,   45,   -1,   -1,   -1,    0,   41,
   42,   43,   -1,   45,   -1,   47,    0,   41,   42,   43,
   -1,   45,  114,   47,    0,   -1,   42,   43,   -1,   45,
   -1,   47,    0,   -1,   -1,   -1,    0,   -1,   -1,   -1,
   40,   41,   42,   43,    0,   45,   -1,   47,   40,   41,
   42,   43,    0,   45,   -1,   47,   40,   41,   42,   43,
    0,   45,   94,   47,   40,   41,   42,   43,    0,   45,
   94,   47,   40,   41,   42,   43,   40,   45,   94,   47,
   -1,   -1,  114,   42,   40,   41,   42,   43,   47,   45,
  114,   47,   40,   41,   94,   43,   -1,   45,  114,   -1,
   40,   41,   -1,   43,   -1,   45,   -1,   -1,   40,   41,
    4,   -1,    6,   -1,  114,    9,   -1,   11,   -1,   13,
   14,   15,   16,   17,   18,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   94,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  114,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  257,  258,   -1,  260,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  257,  258,   -1,
  260,   -1,   -1,   -1,   -1,  257,  258,   -1,  260,   -1,
   -1,   -1,   -1,  257,  258,   -1,  260,   -1,   -1,   -1,
   -1,  257,  258,   -1,  260,   -1,   -1,   -1,   -1,  257,
  258,   -1,  260,  257,  258,   -1,  260,   -1,   -1,   -1,
   -1,  257,  258,   -1,  260,   -1,   -1,   -1,   -1,  257,
  258,   -1,  260,   -1,   -1,   -1,   -1,  257,  258,   -1,
  260,   -1,   -1,   -1,   -1,  257,  258,   -1,  260,
};
#define YYFINAL 1
#ifndef YYDEBUG
#define YYDEBUG 0
#endif
#define YYMAXTOKEN 260
#if YYDEBUG
char *yyname[] = {
"end-of-file",0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
0,0,0,0,0,0,"'('","')'","'*'","'+'",0,"'-'",0,"'/'",0,0,0,0,0,0,0,0,0,0,0,0,0,
"'='",0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,"'^'",0,0,
0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,"'r'",0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
0,0,"NUM","FNCT","NEG","VAR",
};
char *yyrule[] = {
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
"exp : '-' exp",
};
#endif
#ifndef YYSTYPE
typedef int YYSTYPE;
#endif
#define yyclearin (yychar=(-1))
#define yyerrok (yyerrflag=0)
#ifdef YYSTACKSIZE
#ifndef YYMAXDEPTH
#define YYMAXDEPTH YYSTACKSIZE
#endif
#else
#ifdef YYMAXDEPTH
#define YYSTACKSIZE YYMAXDEPTH
#else
#define YYSTACKSIZE 500
#define YYMAXDEPTH 500
#endif
#endif
int yydebug;
int yynerrs;
int yyerrflag;
int yychar;
short *yyssp;
YYSTYPE *yyvsp;
YYSTYPE yyval;
YYSTYPE yylval;
short yyss[YYSTACKSIZE];
YYSTYPE yyvs[YYSTACKSIZE];
#define yystacksize YYSTACKSIZE
#line 70 "calc.y"


ArrayList<CalcItem> input;
int cur;
CalcItem curItem;
ComplexNumber ans = new ComplexNumber (0,0);
PvalBox pvals = new PvalBox (ans);
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


#line 243 "y.tab.c"
#define YYABORT goto yyabort
#define YYACCEPT goto yyaccept
#define YYERROR goto yyerrlab
int
yyparse()
{
    register int yym, yyn, yystate;
#if YYDEBUG
    register char *yys;
    extern char *getenv();

    if (yys = getenv("YYDEBUG"))
    {
        yyn = *yys;
        if (yyn >= '0' && yyn <= '9')
            yydebug = yyn - '0';
    }
#endif

    yynerrs = 0;
    yyerrflag = 0;
    yychar = (-1);

    yyssp = yyss;
    yyvsp = yyvs;
    *yyssp = yystate = 0;

yyloop:
    if (yyn = yydefred[yystate]) goto yyreduce;
    if (yychar < 0)
    {
        if ((yychar = yylex()) < 0) yychar = 0;
#if YYDEBUG
        if (yydebug)
        {
            yys = 0;
            if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
            if (!yys) yys = "illegal-symbol";
            printf("yydebug: state %d, reading %d (%s)\n", yystate,
                    yychar, yys);
        }
#endif
    }
    if ((yyn = yysindex[yystate]) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
    {
#if YYDEBUG
        if (yydebug)
            printf("yydebug: state %d, shifting to state %d (%s)\n",
                    yystate, yytable[yyn],yyrule[yyn]);
#endif
        if (yyssp >= yyss + yystacksize - 1)
        {
            goto yyoverflow;
        }
        *++yyssp = yystate = yytable[yyn];
        *++yyvsp = yylval;
        yychar = (-1);
        if (yyerrflag > 0)  --yyerrflag;
        goto yyloop;
    }
    if ((yyn = yyrindex[yystate]) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
    {
        yyn = yytable[yyn];
        goto yyreduce;
    }
    if (yyerrflag) goto yyinrecovery;
#ifdef lint
    goto yynewerror;
#endif
yynewerror:
    yyerror("syntax error");
#ifdef lint
    goto yyerrlab;
#endif
yyerrlab:
    ++yynerrs;
yyinrecovery:
    if (yyerrflag < 3)
    {
        yyerrflag = 3;
        for (;;)
        {
            if ((yyn = yysindex[*yyssp]) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
#if YYDEBUG
                if (yydebug)
                    printf("yydebug: state %d, error recovery shifting\
 to state %d\n", *yyssp, yytable[yyn]);
#endif
                if (yyssp >= yyss + yystacksize - 1)
                {
                    goto yyoverflow;
                }
                *++yyssp = yystate = yytable[yyn];
                *++yyvsp = yylval;
                goto yyloop;
            }
            else
            {
#if YYDEBUG
                if (yydebug)
                    printf("yydebug: error recovery discarding state %d\n",
                            *yyssp);
#endif
                if (yyssp <= yyss) goto yyabort;
                --yyssp;
                --yyvsp;
            }
        }
    }
    else
    {
        if (yychar == 0) goto yyabort;
#if YYDEBUG
        if (yydebug)
        {
            yys = 0;
            if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
            if (!yys) yys = "illegal-symbol";
            printf("yydebug: state %d, error recovery discards token %d (%s)\n",
                    yystate, yychar, yys);
        }
#endif
        yychar = (-1);
        goto yyloop;
    }
yyreduce:
#if YYDEBUG
    if (yydebug)
        printf("yydebug: state %d, reducing by rule %d (%s)\n",
                yystate, yyn, yyrule[yyn]);
#endif
    yym = yylen[yyn];
    yyval = yyvsp[1-yym];
    switch (yyn)
    {
case 3:
#line 34 "calc.y"
{ ans = (ComplexNumber)yyvsp[0].obj; }
break;
case 4:
#line 38 "calc.y"
{ yyval = yyvsp[0]; }
break;
case 5:
#line 40 "calc.y"
{ ((FnctObj) yyvsp[-3].obj).operate((ComplexNumber)yyvsp[-1].obj,
                               (ComplexNumber)pvals.newObj ()); yyval = pvals.get (); }
break;
case 6:
#line 43 "calc.y"
{ vars.put(yyvsp[-2].sval,(ComplexNumber)yyvsp[0].obj); 
              yyval = new ParserVal((ComplexNumber)yyvsp[0].obj); }
break;
case 7:
#line 45 "calc.y"
{ yyval = ((CalcItem)(yyvsp[0].obj)).getParserVal (); }
break;
case 8:
#line 47 "calc.y"
{ ComplexMath.Add ((ComplexNumber)yyvsp[-2].obj,(ComplexNumber)yyvsp[0].obj,
                     (ComplexNumber)pvals.newObj ()); yyval = pvals.get (); }
break;
case 9:
#line 50 "calc.y"
{ ComplexMath.Sub ((ComplexNumber)yyvsp[-2].obj,(ComplexNumber)yyvsp[0].obj,
                     (ComplexNumber)pvals.newObj ()); yyval = pvals.get (); }
break;
case 10:
#line 53 "calc.y"
{ ComplexMath.Mult ((ComplexNumber)yyvsp[-2].obj,(ComplexNumber)yyvsp[0].obj,
                      (ComplexNumber)pvals.newObj ()); yyval = pvals.get (); }
break;
case 11:
#line 56 "calc.y"
{ ComplexMath.Div ((ComplexNumber)yyvsp[-2].obj,(ComplexNumber)yyvsp[0].obj,
                     (ComplexNumber)pvals.newObj ()); yyval = pvals.get (); }
break;
case 12:
#line 59 "calc.y"
{ yyval = new ParserVal(ComplexMath.Pow((ComplexNumber)yyvsp[-2].obj,  
                                      ComplexMath.Quotient(ComplexNumber.newCartesian(1,0),
                                                           (ComplexNumber)yyvsp[0].obj))); }
break;
case 13:
#line 63 "calc.y"
{ ComplexMath.Pow ((ComplexNumber)yyvsp[-2].obj,(ComplexNumber)yyvsp[0].obj,
                     (ComplexNumber)pvals.newObj ()); yyval = pvals.get (); }
break;
case 14:
#line 65 "calc.y"
{ yyval = yyvsp[-1]; }
break;
case 15:
#line 67 "calc.y"
{ ComplexMath.Neg ((ComplexNumber)yyvsp[0].obj,(ComplexNumber)pvals.newObj ()); yyval = pvals.get (); }
break;
#line 444 "y.tab.c"
    }
    yyssp -= yym;
    yystate = *yyssp;
    yyvsp -= yym;
    yym = yylhs[yyn];
    if (yystate == 0 && yym == 0)
    {
#if YYDEBUG
        if (yydebug)
            printf("yydebug: after reduction, shifting from state 0 to\
 state %d\n", YYFINAL);
#endif
        yystate = YYFINAL;
        *++yyssp = YYFINAL;
        *++yyvsp = yyval;
        if (yychar < 0)
        {
            if ((yychar = yylex()) < 0) yychar = 0;
#if YYDEBUG
            if (yydebug)
            {
                yys = 0;
                if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
                if (!yys) yys = "illegal-symbol";
                printf("yydebug: state %d, reading %d (%s)\n",
                        YYFINAL, yychar, yys);
            }
#endif
        }
        if (yychar == 0) goto yyaccept;
        goto yyloop;
    }
    if ((yyn = yygindex[yym]) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn];
    else
        yystate = yydgoto[yym];
#if YYDEBUG
    if (yydebug)
        printf("yydebug: after reduction, shifting from state %d \
to state %d\n", *yyssp, yystate);
#endif
    if (yyssp >= yyss + yystacksize - 1)
    {
        goto yyoverflow;
    }
    *++yyssp = yystate;
    *++yyvsp = yyval;
    goto yyloop;
yyoverflow:
    yyerror("yacc stack overflow");
yyabort:
    return (1);
yyaccept:
    return (0);
}
