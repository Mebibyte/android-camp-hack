package com.HACK.codersbestfriend;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

enum State {
    DRAW, ERASE, RECTANGLE;
}

public class DrawPanel extends View {
    private Path path;
    private Bitmap bmp;
    //Mpaint is for drawing, R paint is for rectangles, E paint is for erasing, B paint is for
    //the block of white at the bottom
    private Paint mPaint, rPaint, ePaint, bPaint;
    private ArrayList<DrawPath> paths = new ArrayList<DrawPath>();
    private ArrayList<DrawPath> backupPaths = new ArrayList<DrawPath>();
    private State state = State.DRAW;
    private float highlightX;
    private float startX, startY;

    public void toDraw()
    {
        state = State.DRAW;
        highlightX = 23;
        invalidate();
    }

    public void toErase()
    {
        state = State.ERASE;
        highlightX = 143;
        invalidate();
    }

    public void toRect()
    {
        state = State.RECTANGLE;
        highlightX = 263;
        invalidate();
    }

    public void undo()
    {
        if(paths.size() == 0)
        {
            if(backupPaths.size() > 0)
            {
                paths = backupPaths;
                backupPaths = new ArrayList<DrawPath>();
            }
        }
        else
        {
            paths.remove(paths.size()-1);
        }
        invalidate();
    }

    public void clear()
    {
        backupPaths = paths;
        paths = new ArrayList<DrawPath>();
        invalidate();
    }

    public DrawPanel(Context context) {
        super(context);
        rPaint = new Paint();
        rPaint.setColor(Color.BLACK);
        rPaint.setStyle(Paint.Style.STROKE);
        rPaint.setStrokeWidth(6);
        ePaint = new Paint();
        ePaint.setColor(Color.WHITE);
        ePaint.setStrokeWidth(40);
        ePaint.setStyle(Paint.Style.STROKE);
        mPaint = new Paint();
        mPaint.setDither(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(3);
        bPaint = new Paint();
        bPaint.setColor(Color.WHITE);
        bPaint.setStyle(Paint.Style.FILL);
        highlightX = 23;
        setFocusable(true);
        setWillNotDraw(false);
    }

    public DrawPanel(Context context, AttributeSet attrbs) {
        super(context, attrbs);
        rPaint = new Paint();
        rPaint.setColor(Color.BLACK);
        rPaint.setStyle(Paint.Style.STROKE);
        rPaint.setStrokeWidth(6);
        ePaint = new Paint();
        ePaint.setColor(Color.WHITE);
        ePaint.setStrokeWidth(40);
        ePaint.setStyle(Paint.Style.STROKE);
        mPaint = new Paint();
        mPaint.setDither(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(3);
        bPaint = new Paint();
        bPaint.setColor(Color.WHITE);
        bPaint.setStyle(Paint.Style.FILL);
        highlightX = 23;
        setFocusable(true);
        setWillNotDraw(false);
    }

    public DrawPanel(Context context, AttributeSet attrbs, int defStyle) {
        super(context, attrbs, defStyle);
        rPaint = new Paint();
        rPaint.setColor(Color.BLACK);
        rPaint.setStyle(Paint.Style.STROKE);
        rPaint.setStrokeWidth(6);
        ePaint = new Paint();
        ePaint.setColor(Color.WHITE);
        ePaint.setStrokeWidth(40);
        ePaint.setStyle(Paint.Style.STROKE);
        mPaint = new Paint();
        mPaint.setDither(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(3);
        bPaint = new Paint();
        bPaint.setColor(Color.WHITE);
        bPaint.setStyle(Paint.Style.FILL);
        highlightX = 23;
        setFocusable(true);
        setWillNotDraw(false);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        for(DrawPath path: paths)
        {
            canvas.drawPath(path.getPath(), path.getPaint());
        }
        canvas.drawRect(0, 980, 1500, 1500, bPaint);
        canvas.drawRect(highlightX,980,highlightX + 110,1090, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(state == State.RECTANGLE)
        {
            if(event.getAction() == MotionEvent.ACTION_DOWN)
            {
                path = new Path();
                paths.add(new DrawPath(path, rPaint));
                startX = event.getX();
                startY = event.getY();
            }

            if(event.getAction() == MotionEvent.ACTION_MOVE ||
                    event.getAction() == MotionEvent.ACTION_UP)
            {
                path.rewind();
                path.addRect(Math.min(startX, event.getX()), Math.min(startY, event.getY()),
                        Math.max(startX, event.getX()), Math.max(startY, event.getY()), Path.Direction.CW);
            }
        }
        else
        {
            if(event.getAction() == MotionEvent.ACTION_DOWN) {
                path = new Path();
                path.moveTo(event.getX(), event.getY());
                path.lineTo(event.getX(), event.getY());
                if(state == State.DRAW)
                {
                    paths.add(new DrawPath(path, mPaint));
                }
                else if(state == State.ERASE)
                {
                    paths.add(new DrawPath(path, ePaint));
                }
            } else if(event.getAction() == MotionEvent.ACTION_MOVE ||
                    event.getAction() == MotionEvent.ACTION_UP) {
                path.lineTo(event.getX(), event.getY());
            }
        }
        invalidate();
        return true;
    }
}

class DrawPath
{
    private Paint paint;
    private Path path;

    public DrawPath(Path path, Paint paint)
    {
        this.paint = paint;
        this.path = path;
    }
    public Paint getPaint()
    {
        return paint;
    }

    public Path getPath()
    {
        return path;
    }
}
