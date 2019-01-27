package com.threeDBJ.calcAppLib.cliCalc;

public class Primitive extends CalcItem {

    char me;

    public Primitive (String tok) {
        this.me = tok.charAt (0);
    }

    public int getType () {
        return 0;
    }
    
    public String toString () {
        return Character.toString (me);
    }

    public boolean isPrimitive () {
        return true;
    }

    public char getVal () {
        return me;
    }

    public CalcItem copy () {
        return new Primitive (Character.toString (me));
    }

}