package com.android.calcApp;

import android.view.View;
import android.content.Context;
import android.util.Log;

// Graphics stuff
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.graphics.Color;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.MotionEvent;
import android.view.View.OnTouchListener;
import graph.*;
import java.util.ArrayList;

public class GraphView extends SurfaceView implements SurfaceHolder.Callback {
    class GraphThread extends Thread {

	SurfaceHolder sHolder;
	Graph graph;
	Handler hand;
	Context cont;
	int canvWidth=1,canvHeight=1;
	private boolean run=false,ticks=true,init=true,xaxis,yaxis;
	Paint ticksPaint,axisPaint,fnPaint,textPaint;
	private ArrayList<Integer> fnCols;
	float[] xticks,yticks;
	float x1=0,y1=0,x2,y2,dx=0,dy=0,xdist1=0,ydist1=0,xdist2=0,ydist2=0;
	int NONE=0,DRAG=1,ZOOM=2;
	// ztype: 0 = none, 1 = x, 2 = y, 3 = both
	int ztype=0,mode=NONE;

	public GraphThread(SurfaceHolder surfaceHolder, Context context,
                Handler handler) {
            // get handles to some important objects
            sHolder = surfaceHolder;
            hand = handler;
            cont = context;
	    graph = new Graph();
	    ticksPaint = new Paint();
	    ticksPaint.setColor(Color.BLACK);
	    axisPaint = new Paint();
	    axisPaint.setColor(Color.BLACK);
	    textPaint = new Paint();
	    textPaint.setColor(Color.BLACK);
	    fnPaint = new Paint();
	    fnCols = new ArrayList<Integer>();
	}

	/* Callback invoked when the surface dimensions change. */
        public void setSurfaceSize(int width, int height) {
            // synchronized to make sure these all change atomically
            synchronized (sHolder) {
		//Log.v("size =",Integer.toString(width)+" "+Integer.toString(height));
                canvWidth = width;
                canvHeight = height;
		
		graph.setBounds(width,height);
		if(init) {
		    graph.setOrigin(graph.xmid,graph.ymid);
		    init = false;
		}
                // don't forget to resize the background image
		bground = bground.createScaledBitmap(bground,width,height,false);
	    }
        }

	@Override
        public void run() {
            while (run) {
                Canvas c = null;
                try {
                    c = sHolder.lockCanvas(null);
                    synchronized (sHolder) {
                        doDraw(c);
                    }
                } finally {
                    // do this in a finally so that if an exception is thrown
                    // during the above, we don't leave the Surface in an
                    // inconsistent state
                    if (c != null) {
                        sHolder.unlockCanvasAndPost(c);
                    }
                }
            }
        }

	private void doDraw(Canvas c) {
	    c.drawBitmap(bground, 0, 0, null);
	   
	    //graph.setOrigin(graph.xorig+1,graph.yorig+1);
	    c.drawText(Float.toString(graph.xmin),5,canvHeight/2,textPaint);
	    c.drawText(Float.toString(graph.xmax),canvWidth-50,canvHeight/2,textPaint);
	    c.drawText(Float.toString(graph.ymax),canvWidth/2,13,textPaint);
	    c.drawText(Float.toString(graph.ymin),canvWidth/2,canvHeight-8,textPaint);
	    
	    xaxis = graph.xAxis();
	    yaxis = graph.yAxis();
	    if(xaxis) {
		c.drawLine(0,graph.yorig,canvWidth,graph.yorig,axisPaint);
		c.drawLines(graph.getXTicks(),ticksPaint);
	    }
	    if(yaxis) {
		c.drawLine(graph.xorig,0,graph.xorig,canvHeight,axisPaint);
		c.drawLines(graph.getYTicks(),ticksPaint);
	    }
	}

	/**
         * Used to signal the thread whether it should be running or not.
         * Passing true allows the thread to run; passing false will shut it
         * down if it's already running. Calling start() after this was most
         * recently called with false will result in an immediate shutdown.
         * 
         * @param b true to run, false to shut down
         */
        public void setRunning(boolean b) {
            run = b;
        }

	public void handleTouch(MotionEvent e) {
	    switch(e.getAction()) {
	    case MotionEvent.ACTION_DOWN:
		x1 = e.getX();
		y1 = e.getY();
		mode=DRAG;
		//Log.v("sizzy =",Float.toString(x1)+" "+Float.toString(y1));
		break;
	    case MotionEvent.ACTION_POINTER_DOWN:
		xdist1 = graph.abs(e.getX(0)-e.getX(1));
		ydist1 = graph.abs(e.getY(0)-e.getY(1));
		if(xdist1 > 10f && ydist1 > 10f) mode=ZOOM;
		Log.v("yee","2nd finger down");
	    case MotionEvent.ACTION_MOVE:
		if(mode == DRAG) {
		    x2 = e.getX();
		    y2 = e.getY();
		    dx = x2-x1;
		    dy = y2-y1;
		    Log.v("move","translate");
		    graph.move((int)dx,(int)dy);
		    
		    x1 = x2;
		    y1 = y2;
		} else if(mode == ZOOM) {
		    Log.v("move","scale");
		    xdist2 = graph.abs(e.getX(0)-e.getX(1));
		    ydist2 = graph.abs(e.getY(0)-e.getY(1));
		    graph.scale(xdist2/xdist1,ydist2/ydist1);
		}
		break;
	    }
	}
    }

    private Paint   mPaint = new Paint();
    private float[] axisPts,tickPts;
    private int xmax,ymax,xticks,yticks; 
    private float xleft,xright,ybot,ytop,xaxis,yaxis;
    private GraphThread thread;
    private Bitmap bground;
    
    public GraphView(Context context, AttributeSet attrs) {
        super(context, attrs);
	// register our interest in hearing about changes to our surface
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
	
	int[] colors = { Color.parseColor("#BBBBBB") };
	bground = Bitmap.createBitmap(colors,1,1,Bitmap.Config.valueOf("RGB_565"));

	// create thread only; it's started in surfaceCreated()
        thread = new GraphThread(holder, context, new Handler() {
	    @Override
	    public void handleMessage(Message m) {
		
	    }
        });

	this.setOnTouchListener(touchList);

	init();
    }

    /* Callback invoked when the surface dimensions change. */
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
            int height) {
        thread.setSurfaceSize(width, height);
    }
    public void surfaceCreated(SurfaceHolder holder) {
        // start the thread here so that we don't busy-wait in run()
        // waiting for the surface to be created
        thread.setRunning(true);
        thread.start();
    }
    /* Don't touch surface after this is called. */
    public void surfaceDestroyed(SurfaceHolder holder) {
        // we have to tell thread to shut down & wait for it to finish, or else
        // it might touch the Surface after we return and explode
        boolean retry = true;
        thread.setRunning(false);
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }

    private OnTouchListener touchList = new OnTouchListener() {
	    public boolean onTouch(View v,MotionEvent event) {
		thread.handleTouch(event);
		return true;
	    }
	};

    public float setBounds(int xleft, int xright, int ybot, int ytop, 
			 int xticks, int yticks) {
	this.xleft = xleft;
	this.xright = xright;
	this.ybot = ybot;
	this.ytop = ytop;
	this.xticks = xticks;
	this.yticks = yticks;
	if(xleft > 0) {
	    this.yaxis = 0;
	} else if(xright < 0) {
	    this.yaxis = xmax;
	} else {
	    this.yaxis = (float)xmax * ((float)((xleft * -1.0) / (xright - xleft)));
	}
	if(ytop < 0) {
	    this.xaxis = 0;
	} else if(ybot > 0) {
	    this.xaxis = ymax;
	} else {
	    this.xaxis = (float)ymax * ((float)((ybot * -1.0) / (ytop - ybot)));
	}
	// Draw y axis
	int count = 0;
	for(int i=0;i<ymax;i+=1) {
	    axisPts[count] = yaxis;
	    axisPts[count+1] = i;
	    count += 2;
	}
	// Draw x axis
	for(int i=0;i<xmax;i+=1) {
	    axisPts[count] = i;
	    axisPts[count+1] = xaxis;
	    count += 2;
	}
	// Draw y ticks
	count = 0;
	for(int y=10;y<=ymax-10;y+=((ymax-20)/yticks)) {
	    for(int x=((int)yaxis-10);x<(yaxis+10.0);x+=1) {
		tickPts[count] = x;
		tickPts[count+1] = y;
		count += 2;
	    }
	}
	// Draw x ticks
	for(int x=10;x<=xmax-10;x+=((xmax-20)/xticks)) {
	    for(int y=((int)xaxis-10);y<(xaxis+10.0);y+=1) {
		tickPts[count] = x;
		tickPts[count+1] = y;
		count += 2;
	    }
	}
	return yaxis;
    }

    private void init() {
	axisPts = new float[4096];
	tickPts = new float[4096];
	xmax = 300;
	ymax = 300;
	setBounds(-10,10,-10,10,10,10);
    }

    @Override protected void onDraw(Canvas canvas) {
	Paint paint = mPaint;

	canvas.translate(10, 10);

	canvas.drawColor(Color.WHITE);

	// Draw ticks
	paint.setColor(Color.RED);
	paint.setStrokeWidth(1);
	canvas.drawPoints(tickPts, paint);

	// Draw axis
	paint.setColor(Color.BLUE);
	paint.setStrokeWidth(3);
	canvas.drawPoints(axisPts, paint);

	// Draw min/max values
	paint.setColor(Color.GREEN);
	paint.setStrokeWidth(2);
	float yloc,xloc;
	if(xaxis > (ymax / 2)) xloc = xaxis - 13;
	else xloc = xaxis + 23;
	if(yaxis > (xmax / 2)) yloc = yaxis - 38 ;
	else yloc = yaxis + 13;
	canvas.drawText(Float.toString(xleft),-5,xloc,paint);
	canvas.drawText(Float.toString(xright),xmax-20,xloc,paint);
	canvas.drawText(Float.toString(ytop),yloc,14,paint);
	canvas.drawText(Float.toString(ybot),yloc,ymax-5,paint);

	// Cover up point drawn by null values in axis/ticks arrays
	paint.setColor(Color.WHITE);
	paint.setStrokeWidth(3);
	float[] hack = new float[2];
	hack[0] = 0; hack[1] = 0;
	canvas.drawPoints(hack,paint);
    }
}
