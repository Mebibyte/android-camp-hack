package com.HACKtf.drawtesting;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

/**
 * Created by demouser on 6/11/13.
 */

enum State
{
    DRAW, ERASE, RECTANGLE;
}

public class DrawPanel extends SurfaceView implements SurfaceHolder.Callback {
    private DrawThread drawThread;
    private Path path;
    private Paint mPaint;
    private ArrayList<Path> paths = new ArrayList<Path>();
    private State state = State.DRAW;
    public DrawPanel(Context context)
    {
        super(context);
        getHolder().addCallback(this);
        drawThread = new DrawThread(getHolder(), this);
        mPaint = new Paint();
        mPaint.setDither(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(3);
        setFocusable(true);
    }

    public DrawPanel(Context context, AttributeSet attrbs)
    {
        this(context);
    }

    public DrawPanel(Context context, AttributeSet attrbs, int defStyle)
    {
        this(context);
    }

    public DrawThread getThread()
    {
        return drawThread;
    }

    public void surfaceCreated(SurfaceHolder holder)
    {
        drawThread.setRunning(true);
        drawThread.start();
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {

    }

    public void surfaceDestroyed(SurfaceHolder holder)
    {
        boolean retry = true;
        drawThread.setRunning(false);
        while(retry)
        {
            try
            {
                drawThread.join();
                retry = false;
            }   catch(InterruptedException e)
            {

            }
        }
    }

    @Override
    public void onDraw(Canvas canvas)
    {
        canvas.drawColor(Color.WHITE);
        for(Path path : paths) {
            canvas.drawPath(path, mPaint);
        }
    }

    public boolean onTouchEvent(MotionEvent event)
    {
        synchronized(drawThread.getSurfaceHolder())
        {
            if(event.getAction() == MotionEvent.ACTION_DOWN)
            {
                path = new Path();
                path.moveTo(event.getX(), event.getY());
                path.lineTo(event.getX(), event.getY());
                paths.add(path);
            }else if(event.getAction() == MotionEvent.ACTION_MOVE ||
                    event.getAction() == MotionEvent.ACTION_UP)
            {
                path.lineTo(event.getX(), event.getY());
            }

            return true;
        }
    }
}
