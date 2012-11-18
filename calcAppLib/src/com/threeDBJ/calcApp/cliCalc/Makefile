#Makefile for the calculator

.PHONY : clean new parser test archive main
BYACCJ=calc.y
COMPMATH_java=ComplexMath.java ComplexNumber.java
COMPMATH_class=ComplexMath.class ComplexNumber.class
PARSER_java=Parser.java ParserVal.java 
PARSER_class=Parser.class ParserVal.class 
TEST=complexTest

main : parser	
	javac Main.java

parser : $(COMPMATH_java) new FnctObj.java
	javac $(COMPMATH_java) $(PARSER_java)  FnctObj.java

new : $(BYACCJ)
	byaccj -J $(BYACCJ)

check : $(TEST).java
	javac $(TEST).java
	java $(TEST)

$(COMPMATH_class) : $(COMPMATH_java)
	javac $(COMPMATH_java)

clean:
	rm -f *.class *~ $(PARSER_java) TAGS

etags:
	find . -name '*.java' | xargs etags

archive: clean
	rm -f ../calc.tar 2>/dev/null
	tar cf ../calc.tar .