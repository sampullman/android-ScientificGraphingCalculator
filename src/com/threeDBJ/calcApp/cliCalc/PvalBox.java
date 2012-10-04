package cliCalc;

public class PvalBox {

    ParserVal[] pvals;
    int ind;

    public PvalBox () {
        pvals = new ParserVal[100];
        ind = 0;
        for (int i=0;i<pvals.length;i+=1) {
            pvals[i] = new ParserVal (new ComplexNumber (0, 0));
        }
    }

    public Object newObj () {
        ind += 1;
        if (ind >= pvals.length)
            ind = 0;
        return pvals[ind].obj;
    }

    public ParserVal get () {
        return pvals[ind];
    }

}