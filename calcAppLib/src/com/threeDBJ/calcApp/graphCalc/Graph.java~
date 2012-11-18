package graph;

import android.util.Log;
import java.lang.Math;
import java.util.Arrays;
import java.util.ArrayList;

public class Graph {

    public int xpix,ypix,xorig,yorig,numTicks,tickDist,
        nxLabels,nyLabels,xoff,yoff,zero=(int)'0',xcheck1,ycheck1,xcheck2,ycheck2;
    private float[] xticks,yticks;
    public char[] xLabels,yLabels,tempLabel;
    public int[] xLabelIndex, yLabelIndex, xLabelLocs, yLabelLocs;
    public float xMinInit,yMinInit,convx,convy,zoomx,zoomy,rTickDist,
        xUnitLen,yUnitLen,xl,xr,yb,yt,dist;
    //public String xMinStr, xMaxStr, yMinStr, yMaxStr;
    static float labelTol = (float)0.01;
    private boolean initx,inity;

    public ArrayList<String> xlabs,ylabs;
    
    int i,n,x,y;
    
    public Graph() {
        xorig = 0;
        yorig = 0;
        yoff = 0;
        xoff = 0;
        xcheck1 = 0;
        ycheck1 = 0;
        xcheck2 = 0;
        ycheck2 = 0;
        numTicks = 8;
        xl = 0;
        xr = 0;
        yb = 0;
        yt = 0;
        xticks = new float[(20)*4];
        yticks = new float[(20)*4];
        xLabelIndex = new int[(20)*2];
        yLabelIndex = new int[(20)*2];
        xLabels = new char[(20)*10];
        yLabels = new char[(20)*10];
        xLabelLocs = new int[(20)*2];
        yLabelLocs = new int[(20)*2];
        tempLabel = new char[100];
        initx = true;
        inity = true;
        xlabs = new ArrayList<String>();
        ylabs = new ArrayList<String>();
        dist = 0;
    }

    public float getXMin () {
        return xMinInit + ((float)xoff * xUnitLen);
    }

    public float getXMax () {
        return xMinInit + ((float)(xoff+xpix) * xUnitLen);
    }

    public float getYMin () {
        return yMinInit + ((float)yoff * yUnitLen);
    }

    public float getYMax () {
        return yMinInit + ((float)(yoff+ypix) * yUnitLen);
    }

    public void reset () {
        xoff = 0;
        yoff = 0;
        setOrigin (xpix/2, ypix/2);
        rTickDist = 2;
        xUnitLen = rTickDist / (float)tickDist;
        yUnitLen = rTickDist / (float)tickDist;
        xMinInit = -1* ((float)(xpix / 2) * xUnitLen);
        yMinInit = -1* ((float)(ypix / 2) * yUnitLen);
        //xcheck1 = xorig % tickDist;
        //ycheck1 = yorig % tickDist;
        //xcheck2 = (xpix-xcheck1-1) % tickDist;
        //ycheck2 = (ypix-ycheck1-1) % tickDist;
        xcheck1 = 0;
        xcheck2 = 0;
        ycheck1 = yorig % tickDist;
        ycheck2 = (ypix-ycheck1) % tickDist;
        initx = true;
        inity = true;
        buildXLabels ();
        buildYLabels ();
        Log.v("graph",xlabs.toString());
        Log.v ("graph", Arrays.toString(xLabelLocs));
    }

    public void setBounds(int x, int y) {
	if(x % 2 == 0) {
	    xpix = x;
	} else {
	    xpix = x-1;
	}
	if(y % 2 == 0) {
	    ypix = y;
	} else {
	    ypix = y-1;
	}
	tickDist = xpix / numTicks;
        rTickDist = 2;
        xUnitLen = rTickDist / (float)tickDist;
        yUnitLen = rTickDist / (float)tickDist;
        xMinInit = -1* ((float)(x / 2) * xUnitLen);
        yMinInit = -1* ((float)(y / 2) * yUnitLen);
        yoff = 0;
        //xcheck1 = xorig % tickDist;
        xcheck1 = 0;
        ycheck1 = yorig % tickDist;
        //ycheck1 = ypix % tickDist;
        //xcheck2 = (xpix-xcheck1-1) % tickDist;
        xcheck2 = 0;
        //ycheck2 = (ypix-ycheck1-1) % tickDist;
        ycheck2 = (ypix-ycheck1) % tickDist;
        initx = true;
        inity = true;
        buildXLabels ();
        buildYLabels ();
    }

    public void initOrigin () {
        setOrigin (xpix/2, ypix/2);
    } 

    public void setOrigin(int newx,int newy) {
        xorig = newx;
        yorig = newy;
    }

    public void move(int newx, int newy) {
        xorig += newx;
        yorig += newy;
        xoff -= newx;
        yoff += newy;
        xcheck1 += newx;
        xcheck2 += newx;
        ycheck1 += newy;
        ycheck2 -= newy;
        //Log.v ("graph",Integer.toString(ycheck1)+" "+Integer.toString(ycheck2));
        while (xcheck1 >= tickDist) {
            xl -= rTickDist;
            addLabelLeft (xlabs, xl);
            nxLabels += 2;
            xcheck1 -= tickDist;
        }
        while (xcheck1 < 0) {
            xl += rTickDist;
            remLabelLeft (xlabs, xl);
            nxLabels -= 2;
            xcheck1 += tickDist;
        }
        while (xcheck2 < 0) {
            xr += rTickDist;
            addLabelRight (xlabs, xr);
            nxLabels += 2;
            xcheck2 += tickDist;
        }
        while (xcheck2 >= tickDist) {
            nxLabels -= 2;
            xr -= rTickDist;
            remLabelRight (xlabs, xr);
            xcheck2 -= tickDist;
        }
        while (ycheck1 < 0) {
            yt -= rTickDist;
            remLabelLeft (ylabs, yt);
            nyLabels += 2;
            ycheck1 += tickDist;
        }
        while (ycheck1 >= tickDist) {
            Log.v("uhoh","yadd1");
            yt += rTickDist;
            addLabelLeft (ylabs, yt);
            nyLabels += 2;
            ycheck1 -= tickDist;
        }
        while (ycheck2 < 0) {
            yb += rTickDist;
            remLabelRight (ylabs, yb);
            nyLabels -= 2;
            ycheck2 += tickDist;
        }
        while (ycheck2 >= tickDist) {
            Log.v("uhoh","yadd2");
            yb -= rTickDist;
            addLabelRight (ylabs, yb);
            nyLabels += 2;
            ycheck2 -= tickDist;
        }
    }

    public static float abs(float a) {
        if(a < 0) a *= -1.0;
        return a;
    }
    
    public void setDist (float x1, float x2, float y1, float y2) {
        dist = (float) Math.sqrt (Math.pow (x2 - x1, 2) + Math.pow (y2 - y1, 2));
    }
    
    public void zoom(float x1, float x2, float y1, float y2) {
        float dx = x2 - x1, dy = y2 - y1;
        int mode;
        float newdist = (float) Math.sqrt (Math.pow (x2 - x1, 2) + Math.pow (y2 - y1, 2));
        if (newdist > dist)
            mode = 0;
        else mode = 1; 
        dist = newdist;
        /*
        if (dx > 1) mode = 0;
        else if (dx < 1) mode = 1;
        else if (dy > 1) mode = 0;
        else if (dy < 1) mode = 1;
        else mode = -1;
        */
        if (mode == 0) {
            xMinInit *= 0.95;
            yMinInit *= 0.95;
            xUnitLen *= 0.95;
            yUnitLen *= 0.95;
            rTickDist *= 0.95;
        } else if (mode == 1) {
            xMinInit *= 1.05;
            yMinInit *= 1.05;
            xUnitLen *= 1.05;
            yUnitLen *= 1.05;
            rTickDist *= 1.05;
        }
        initx = true;
        inity = true;
        //buildXLabels ();
        //buildYLabels ();
    }

    public boolean xAxis() {
        if(yorig >= 0 && yorig < ypix) {
            return true;
        } else {
            return false;
        }
    }

    public boolean yAxis() {
        if(xorig >= 0 && xorig < xpix) {
            return true;
        } else {
            return false;
        }
    }
    
    public static float graphToReal (int g, float rmin, float rmax, int gmax) {
        return (((float)g*(rmax-rmin))/(float)gmax)+rmin;
    }

    public void justifyRight (char[] arr, int n) {
        for (int i=arr.length-1;i>=n;i-=1) {
            arr[i] = arr[i-n];
        }
    }
    
    public void justifyRight (int[] arr, int n) {
        for (int i=arr.length-1;i>=n;i-=1) {
            arr[i] = arr[i-n];
        }
    }

    public void justifyLeft (char[] arr, int n) {
        for (int i=0;i<arr.length-n;i+=1) {
            arr[i] = arr[i+n];
        }
    }

    public void justifyLeft (int[] arr, int n) {
        for (int i=0;i<arr.length-n;i+=1) {
            arr[i] = arr[i+n];
        }
    }

    public void printLabels (char[] labs, int[] ind) {
        String print = "[ ";
        for (int i=0;i<ind.length;i+=2) {
            for (int k=0;k<ind[i+1];k+=1) {
                print += Character.toString(labs[ind[i]+k]);
            }
            print += ", ";
        }
        print += "]";
        Log.v("graph",print);
    }

    private void remLabelLeft (char[] labs, int[] ind, int end) {
        justifyLeft (labs, ind[1]);
        Log.v ("graph",Arrays.toString(ind));
        for (int i=0;i<end;i+=2) {
            ind[i] -= ind[1];
            //if (ind[i] < 0)
            //    ind[i] = 0;
        }
        justifyLeft (ind, 2);
        Log.v ("graph",Arrays.toString(ind));
    }

    private void remLabelLeft (ArrayList<String> labs, float num) {
        labs.remove (0);
    }

    private void addLabelLeft (ArrayList<String> labs, float num) {
        labs.add (0, getLabStr (num));
    }
    
    private void addLabelRight (ArrayList<String> labs, float num) {
        labs.add (getLabStr (num));
    }

    private void remLabelRight (ArrayList<String> labs, float num) {
        labs.remove (labs.size ()-1);
    }

    private void addLabelLeft (char[] labs, int[] ind, float num) {
        int len = updateLabel (tempLabel, 0, num);
        justifyRight (labs, len);
        justifyRight (ind, 2);
        ind[0] = 0;
        ind[1] = len;
        for (int i=2;i<ind.length;i+=2)
            ind[i] += len;
        for (int i=0;i<len;i+=1) {
            labs[i] = tempLabel[i];
        }
    } 

    private void addLabelRight (char[] labs, int[] ind, int stop, float num) {
        int end = 0;
        for (int i=0;i<stop;i+=2)
            end += ind[i+1];
        int len = updateLabel (tempLabel, 0, num);
        Log.v ("graph",Arrays.toString(ind));
        ind[stop] = end;
        ind[stop+1] = len;
        Log.v ("graph",Arrays.toString(ind));
        for (int i=0;i<len;i+=1)
            labs[end+i] = tempLabel[i];
    }

    private String getLabStr (float num) {
        String ret = "";
        float weight;
        int m, digit;
        if (num < 0) {
            num *= -1.0;
            ret = "-";
        }
        m = (int)Math.log10 (num);
        while (num > labelTol) {
            weight = (float)Math.pow (10.0, m);
            digit = (int)Math.floor (num / weight);
            num -= digit*weight;
            ret += Integer.toString(digit);
            if (m == 0) {
                ret += ".";
             } else if (num == 0) {
                ret += "0";
            }
            m -= 1;
        }
        if (ret.length() != 0) {
            if (ret.charAt (ret.length ()-1) == '.') 
                ret = ret.substring (0, ret.length()-1);
            else if (ret.length() == 1 && ret.charAt (0) == '-')
                ret = "";
        }
        return ret;
    }

    private int updateLabel (char[] labs, int ind, float num) {
        float weight;
        int m, digit;
        if (num < 0) {
            num *= -1.0;
            labs[ind] = '-';
            ind += 1;
        }
        m = (int)Math.log10 (num);
        while (num > labelTol) {
            weight = (float)Math.pow (10.0, m);
            digit = (int)Math.floor (num / weight);
            num -= digit*weight;
            labs[ind] = (char)(zero + digit);
            ind += 1;
            if (m == 0) {
                labs[ind] = '.';
                ind += 1;
            } else if (num == 0) {
                labs[ind] = (char)zero;
                ind += 1;
            }
            m -= 1;
        }
        if (ind != 0)
            if (labs[ind-1] == '.') ind -= 1;
        return ind;
    }

    /*
    public void buildXLabels () {
        getXTicks ();
        float start = getXMin()+((float)xticks[0]*xUnitLen),cur, add = rTickDist;
        xl = start;
        xr = start + (rTickDist * ((nxLabels / 2)-1));
        Log.v("graph",Float.toString (xr)+" "+Integer.toString (nxLabels));
        int cNum=0,prev=0;
        for (i=0;i<nxLabels;i+=2) {
            cur = start;
            cNum = updateLabel (xLabels, cNum, cur);
            xLabelIndex[i] = prev;
            xLabelIndex[i+1] = cNum-prev;
            prev = cNum;
            start += add;
        }
    }
    
    public void buildYLabels () {
        getYTicks ();
        float start = getYMin()+(((float)(ypix-yLabelLocs[nyLabels-1]))*yUnitLen),
            add = rTickDist,cur;
        yb = start;
        yt = start + (rTickDist * ((nyLabels / 2)-1));
        int cNum=0,prev=0;
        for (i=nyLabels-2;i>=0;i-=2) {
            cur = start;
            cNum = updateLabel (yLabels, cNum, cur);
            yLabelIndex[i] = prev;
            yLabelIndex[i+1] = cNum-prev;
            prev = cNum;
            start += add;
        }
    }
    */
    
    public float[] buildXLabels () {
        xlabs.clear ();
        float[] ret = getXTicks ();
        float start = getXMin()+((float)xticks[0]*xUnitLen),cur, add = rTickDist;
        xl = start;
        int cNum=0,prev=0;
        for (i=0;i<nxLabels;i+=2) {
            xlabs.add (getLabStr (start));
            start += add;
        }
        xr = start - add;
        return ret;
    }

    public float[] buildYLabels () {
        ylabs.clear ();
        float[] ret = getYTicks ();
        float start = getYMin()+(((float)(ypix-yLabelLocs[nyLabels-1]))*yUnitLen),
	    add = rTickDist;
        //Log.v ("gb",ylabs.toString());
        yb = start;
        int cNum=0,prev=0;
        for (i=nyLabels-2;i>=0;i-=2) {
            ylabs.add (0,getLabStr (start));
            start += add;
        }
        yt = start - add;
        return ret;
    }

    public float[] getXTicks() {
        i=0;
        if (initx)
            nxLabels=0;
        if (xorig < 0)
            x = (xorig % tickDist) + tickDist;
        else x = xorig % tickDist;
        for(;x<xpix;x+=tickDist) {
            if (i >= xticks.length) Log.v ("xticks", Float.toString (tickDist)+" "+Integer.toString(i));
            xticks[i] = x;
            xticks[i+1] = 0;
            xticks[i+2] = x;
            xticks[i+3] = ypix;
            xLabelLocs[i/2] = x-5;
            xLabelLocs[(i/2)+1] = yorig + 15;
            i+=4;
            if (initx)
                nxLabels += 2;
        }
        for (i=i;i<xticks.length;i+=1) {
            xticks[i] = 0;
        }
        initx = false;
        return xticks;
    }

    public float[] getYTicks() {
        i=0;
        if (inity)
            nyLabels=0;
        if (yorig < 0)
            y = (yorig % tickDist) + tickDist;
        else y = yorig % tickDist;
        for (;y<ypix;y+=tickDist) {
            if (i >= yticks.length) Log.v ("yticks", Float.toString (tickDist)+" "+Integer.toString(i));
            yticks[i] = 0;
            yticks[i+1] = y;
            yticks[i+2] = xpix;
            yticks[i+3] = y;
            yLabelLocs[i/2] = xorig + 5;
            yLabelLocs[(i/2)+1] = y;
            i+=4;
            if (inity)
                nyLabels += 2;
        }
        
        for (;i<yticks.length;i+=1) {
            yticks[i] = 0;
        }
        inity = false;
        return yticks;
    }

}