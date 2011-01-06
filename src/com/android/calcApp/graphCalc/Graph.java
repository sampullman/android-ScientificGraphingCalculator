package graph;

import java.util.ArrayList;
import android.util.Log;

public class Graph {

    public int xpix,ypix,xorig,yorig,xmid,ymid,numxticks,numyticks;
    private float[] xticks,yticks;
    private ArrayList<String> fns;
    public float xmin,xmax,ymin,ymax,convx,convy,zoomx,zoomy;
    
    public Graph() {
	fns = new ArrayList<String>();
	xorig = 0;
	yorig = 0;
	xmin = -10;
	ymin = -10;
	xmax = 10;
	ymax = 10;
	numxticks = 10;
	numyticks = 10;
	xticks = new float[numxticks*4];
	yticks = new float[numyticks*4];
    }

    public void setBounds(int x, int y) {
	xpix = x;
	ypix = y;
	xmid = x / 2;
	ymid = y / 2;
    }

    public void setOrigin(int newx,int newy) {
	xorig = newx;
	yorig = newy;
    }

    public void move(int newx,int newy) {
	xorig += newx;
	yorig += newy;
	convx = ((float)newx / (float)xpix) * (xmax-xmin);
	convy = ((float)newy / (float)ypix) * (ymax-ymin);
	xmax -= convx;
	xmin -= convx;
	ymax += convy;
	ymin += convy;
    }

    public static float abs(float a) {
	if(a < 0) a *= -1.0;
	return a;
    }
    float test = 0;
    public float scale(float xscale,float yscale) {
	xmin *= xscale;
	xmax *= xscale;
	ymin *= yscale;
	ymax *= yscale;
	return 0;
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

    public float[] getXTicks() {
	float dx = (float)xpix / (float)numxticks;
	float x=0;
	for(int i=0;i<xticks.length;i+=4) {
	    //Log.v("getXTicks()",Float.toString(x));
	    xticks[i] = x;
	    xticks[i+1] = yorig+5;
	    xticks[i+2] = x;
	    xticks[i+3] = yorig-5;
	    x += dx;
	}
	return xticks;
    }

    public float[] getYTicks() {
	float dy = (float)ypix / (float)numyticks;
	float y=0;
	for(int i=0;i<yticks.length;i+=4) {
	    yticks[i] = xorig+5;
	    yticks[i+1] = y;
	    yticks[i+2] = xorig-5;
	    yticks[i+3] = y;
	    y += dy;
	}
	return yticks;
    }    

}