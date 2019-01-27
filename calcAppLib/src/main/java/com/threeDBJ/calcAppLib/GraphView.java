package com.threeDBJ.calcAppLib;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ArrayAdapter;

import com.threeDBJ.calcAppLib.graph.*;

// Graphics stuff

public class GraphView extends SurfaceView implements SurfaceHolder.Callback {
    class GraphThread extends Thread {

        SurfaceHolder sHolder;
        public Graph graph;
        Handler hand;
        Context cont;
        int canvWidth = 1, canvHeight = 1;
        private boolean run = false, ticks = true, init = true, xaxis, yaxis;
        Paint ticksPaint, axisPaint, textPaint, tracePaint;
        Paint[] fnPaint = new Paint[NUM_FNS];
        float[] xticks, yticks;
        float x1 = 0, y1 = 0, x2, y2, dx = 0, dy = 0, xdist1 = 0, ydist1 = 0, xdist2 = 0, ydist2 = 0;
        int NONE = 0, DRAG = 1, ZOOM = 2;
        // ztype: 0 = none, 1 = x, 2 = y, 3 = both
        int ztype = 0, mode = NONE, activePtrId = -1;
        boolean zoomflag = true;

        public GraphThread(SurfaceHolder surfaceHolder, Handler handler) {
            // get handles to some important objects
            sHolder = surfaceHolder;
            hand = handler;
            graph = new Graph();
            // Unused: fnPaints used for trace instead due to coloring
            tracePaint = new Paint();
            tracePaint.setColor(Color.BLACK);
            ticksPaint = new Paint();
            ticksPaint.setColor(Color.GRAY);
            axisPaint = new Paint();
            axisPaint.setColor(Color.LTGRAY);
            axisPaint.setStrokeWidth(2);
            textPaint = new Paint();
            textPaint.setTextSize(26);
            textPaint.setColor(Color.WHITE);
            textPaint.setAntiAlias(true);
            for(int i = 0; i < NUM_FNS; i += 1) {
                fnPaint[i] = new Paint();
                fnPaint[i].setStyle(Paint.Style.FILL_AND_STROKE);
                fnPaint[i].setStrokeWidth(2);
                fnPaint[i].setAntiAlias(true);
            }
            fnPaint[0].setColor(Color.MAGENTA);
            fnPaint[1].setColor(Color.CYAN);
            fnPaint[2].setColor(Color.GREEN);
        }

        /* Callback invoked when the surface dimensions change. */
        public void setSurfaceSize(int width, int height) {
            // synchronized to make sure these all change atomically
            synchronized(sHolder) {
                canvWidth = width;
                canvHeight = height;
                graph.setBounds(width, height);
                if(init) {
                    graph.initOrigin();
                    init = false;
                }
                // don't forget to resize the background image
                bground = bground.createScaledBitmap(bground, width, height, false);
            }
        }

        @Override
        public void run() {
            while(run) {
                Canvas c = null;
                try {
                    c = sHolder.lockCanvas(null);
                    synchronized(sHolder) {
                        doDraw(c);
                    }
                } finally {
                    // do this in a finally so that if an exception is thrown
                    // during the above, we don't leave the Surface in an
                    // inconsistent state
                    if(c != null) {
                        sHolder.unlockCanvasAndPost(c);
                    }
                }
            }
        }

        public int getCanvHeight() {
            return canvHeight;
        }

        public int getCanvWidth() {
            return canvWidth;
        }

        private void doDraw(Canvas c) {
            int i;
            if(c == null || fnPts == null) return;
            c.drawBitmap(bground, 0, 0, null);

            xaxis = graph.xAxis();
            yaxis = graph.yAxis();
            float[] xticks, yticks;
            if(zoomflag) {
                xticks = graph.buildXLabels();
                yticks = graph.buildYLabels();
                zoomflag = false;
            } else {
                xticks = graph.getXTicks();
                yticks = graph.getYTicks();
            }
            c.drawLines(xticks, ticksPaint);
            c.drawLines(yticks, ticksPaint);
            if(xaxis) {
                for(i = 0; i < graph.xlabs.size(); i += 1) {
                    c.drawText(graph.xlabs.get(i), graph.xLabelLocs[2 * i],
                            graph.xLabelLocs[(2 * i) + 1] + 8, textPaint);
                }
                c.drawLine(0, graph.yorig, canvWidth, graph.yorig, axisPaint);
            }
            if(c == null || fnPts == null) return;
            if(yaxis) {
                int size = graph.ylabs.size();
                for(i = 0; i < graph.ylabs.size(); i += 1) {
                    if(graph.ylabs.size() < size) {
                        break;
                    }
                    c.drawText(graph.ylabs.get(i), graph.yLabelLocs[2 * i],
                            graph.yLabelLocs[(2 * i) + 1], textPaint);
                }
                c.drawLine(graph.xorig, 0, graph.xorig, canvHeight, axisPaint);
                //}
            }
            if(c == null || fnPts == null) return;
            for(i = 0; i < NUM_FNS; i += 1) {
                c.drawLines(fnPts[i], fnPaint[i]);
            }
            if(graphMode == TRACE) {
                int ygraph, textLeft = (int) ((0.35) * getXMax()),
                        textBot = (int) ((0.05) * getYMax());
                int yTextCur = textBot + 10;
                float xloc, yloc;
                xloc = graph.graphToReal(appState.traceLoc, getXLeft(), getXRight(),
                        (int) getXMax());
                c.drawText("x = " + Float.toString(xloc), getXMax() - textLeft + 15,
                        yTextCur, textPaint);
                yTextCur += textBot;
                for(i = 0; i < NUM_FNS; i += 1) {
                    if(!appState.isCalcEmpty(i)) {
                        yloc = appState.getYTracePt(i, xloc);
                        ygraph = appState.getYGraphPt(yloc);
                        c.drawCircle(appState.traceLoc, ygraph, 4, fnPaint[i]);
                        c.drawText("Fn" + Integer.toString(i + 1) + " = " + Float.toString(yloc),
                                getXMax() - textLeft, yTextCur, textPaint);
                        yTextCur += textBot;
                    }
                }
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

        public void handleTrace(MotionEvent e) {
            final int action = e.getAction();
            switch(action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN: {
                x1 = e.getX();
                activePtrId = e.getPointerId(0);
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                final int ptrInd = e.findPointerIndex(activePtrId);
                x2 = e.getX(ptrInd);
                if(x2 > x1) {
                    dx = 1;
                } else {
                    dx = -1;
                }
                appState.updateTrace((int) dx);
                x1 = x2;
                break;
            }
            }
        }

        public void handleTouch(MotionEvent e) {
            final int action = e.getAction();
            switch(action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN: {
                x1 = e.getX();
                y1 = e.getY();
                mode = DRAG;
                activePtrId = e.getPointerId(0);
                break;
            }
            case MotionEvent.ACTION_POINTER_DOWN: {
                xdist1 = graph.abs(e.getX(0) - e.getX(1));
                ydist1 = graph.abs(e.getY(0) - e.getY(1));
                graph.setDist(e.getX(0), e.getX(1), e.getY(0), e.getY(1));
                if(xdist1 > 5f && ydist1 > 5f) mode = ZOOM;
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                if(mode == DRAG) {
                    // Translate graph
                    final int ptrInd = e.findPointerIndex(activePtrId);
                    x2 = e.getX(ptrInd);
                    y2 = e.getY(ptrInd);
                    dx = x2 - x1;
                    dy = y2 - y1;
                    graph.move((int) dx, (int) dy);
                    x1 = x2;
                    y1 = y2;
                } else if(mode == ZOOM) {
                    // Zoom graph
                    //xdist2 = graph.abs(e.getX(0)-e.getX(1));
                    //ydist2 = graph.abs(e.getY(0)-e.getY(1));
                    //graph.zoom(xdist1, xdist2, ydist1, ydist2);
                    //if (zoomflag) {
                    graph.zoom(e.getX(0), e.getX(1), e.getY(0), e.getY(1));
                    zoomflag = true;
                    //zoomflag = false;
                    // } else zoomflag = true;
                    //xdist1 = xdist2;
                    //ydist1 = ydist2;
                }
                break;
            }
            case MotionEvent.ACTION_UP: {
                activePtrId = -1;
                break;
            }

            case MotionEvent.ACTION_CANCEL: {
                activePtrId = -1;
                break;
            }
            case MotionEvent.ACTION_POINTER_UP: {
                // Back to translate
                final int ptrInd = (action & MotionEvent.ACTION_POINTER_INDEX_MASK)
                        >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
                final int ptrId = e.getPointerId(ptrInd);
                final int nPtrInd = ptrInd == 0 ? 1 : 0;
                x1 = e.getX(nPtrInd);
                y1 = e.getY(nPtrInd);
                mode = DRAG;
                activePtrId = e.getPointerId(nPtrInd);
                break;
            }
            }
        }
    }

    private Paint mPaint = new Paint();
    public float[][] fnPts;
    public int xticks, yticks, graphMode;
    private GraphThread thread;
    private Bitmap bground;
    private CalcApp appState;
    public final int NUM_FNS = 3, GRAPH = 0, TRACE = 1;

    public GraphView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // register our interest in hearing about changes to our surface
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        appState = (CalcApp) context.getApplicationContext();

        int[] color = {Color.BLACK};
        bground = Bitmap.createBitmap(color, 1, 1, Bitmap.Config.valueOf("RGB_565"));

        this.setOnTouchListener(touchList);
        graphMode = GRAPH;
    }

    /* Callback invoked when the surface dimensions change. */
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        thread.setSurfaceSize(width, height);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        // start the thread here so that we don't busy-wait in run()
        // waiting for the surface to be created
        // create thread only; it's started in surfaceCreated()
        thread = new GraphThread(holder, new Handler() {
            @Override
            public void handleMessage(Message m) {
            }
        });
        thread.setSurfaceSize(getWidth(), getHeight());
        Log.v("size", Integer.toString(getWidth()) + " " + Integer.toString(getHeight()));
        graphMode = GRAPH;
        thread.graph.initOrigin();
        thread.setRunning(true);
        thread.start();
        fnPts = new float[NUM_FNS][(((int) getXMax()) + 1) * 4];
        appState.initFns(NUM_FNS);
        appState.plotFns(fnPts, NUM_FNS);
    }

    /* Don't touch surface after this is called. */
    public void surfaceDestroyed(SurfaceHolder holder) {
        // we have to tell thread to shut down & wait for it to finish, or else
        // it might touch the Surface after we return and explode
        appState.resetCalcs(NUM_FNS);
        boolean retry = true;
        thread.setRunning(false);
        while(retry) {
            try {
                thread.join();
                retry = false;
            } catch(InterruptedException e) {
            }
        }
    }

    public void reset() {
        thread.graph.reset();
        if(graphMode == TRACE) {
            appState.initTrace();
        }
        appState.plotFns(fnPts, NUM_FNS);
    }

    public void zeros(ArrayAdapter<String> zerosArr) {
        appState.calcZeros(zerosArr, NUM_FNS);
    }

    public void trace() {
        if(graphMode == TRACE) {
            graphMode = GRAPH;
        } else {
            graphMode = TRACE;
            appState.initTrace();
        }
    }

    private OnTouchListener touchList = new OnTouchListener() {
        public boolean onTouch(View v, MotionEvent event) {
            if(graphMode == TRACE) {
                thread.handleTrace(event);
            } else {
                thread.handleTouch(event);
                appState.plotFns(fnPts, NUM_FNS);
            }
            return true;
        }
    };

    public float[][] getFnPts() {
        return fnPts;
    }

    float getXLeft() {
        return thread.graph.getXMin();
    }

    float getXRight() {
        return thread.graph.getXMax();
    }

    float getYTop() {
        return thread.graph.getYMax();
    }

    float getYBot() {
        return thread.graph.getYMin();
    }

    float getXMin() {
        return (float) 0;
    }

    float getYMin() {
        return (float) 0;
    }

    float getXMax() {
        return (float) thread.getCanvWidth();
    }

    float getYMax() {
        return (float) thread.getCanvHeight();
    }

    float getXUnitLen() {
        return thread.graph.xUnitLen;
    }

}
