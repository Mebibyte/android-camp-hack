package com.HACK.codersbestfriend;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

enum State {
    DRAW, ERASE, RECTANGLE
}

public class DrawPanel extends View {
    private Path path;
    // mPaint is for drawing, rPaint is for rectangles, ePaint is for erasing, bPaint is for
    // the block of white at the bottom
    private Paint mPaint, rPaint, ePaint, bPaint;
    private List<DrawPath> paths = new ArrayList<DrawPath>();
    private List<DrawPath> backupPaths = new ArrayList<DrawPath>();
    private State state = State.DRAW;
    private Canvas mCanvas = null;
    private Bitmap bmp;
    private float highlightX;
    private float startX, startY;
    private CodersBestFragment fragment;

    public DrawPanel(Context context) {
        super(context);
        construct();
    }

    public DrawPanel(Context context, AttributeSet attrbs) {
        super(context, attrbs);
        construct();
    }

    public DrawPanel(Context context, AttributeSet attrbs, int defStyle) {
        super(context, attrbs, defStyle);
        construct();
    }

    private void construct() {
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
        highlightX = 10;
        setFocusable(true);
        setWillNotDraw(false);
    }

    public void toDraw()
    {
        state = State.DRAW;
        highlightX = 10;
        invalidate();
    }

    public void toErase()
    {
        state = State.ERASE;
        highlightX = 140;
        invalidate();
    }

    public void toRect()
    {
        state = State.RECTANGLE;
        highlightX = 270;
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

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /*if(mCanvas == null)
        {
            mCanvas = canvas;
            canvas.setBitmap(bmp);
        }*/
        canvas.drawColor(Color.WHITE);
        for (DrawPath path : paths)
        {
            canvas.drawPath(path.getPath(), path.getPaint());
        }
        canvas.drawRect(0, MainActivity.screenHeight - 230, 1500, MainActivity.screenHeight - 100, bPaint);
        canvas.drawRect(highlightX, MainActivity.screenHeight - 230, highlightX + 110,
                MainActivity.screenHeight - 120, mPaint);

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

    public void closing(MainActivity mainActivity)
    {
        mainActivity.saveImage(bmp, "design", "Coder's best friend design");
        mainActivity.setPaths(paths);
        mainActivity.setBackupPaths(backupPaths);
    }


    public void setFragment(CodersBestFragment fragment) {
        this.fragment = fragment;
    }

    public void setPath(List<DrawPath> path) {
        this.paths = path;
    }

    public void setBackupPaths(List<DrawPath> paths) {
        this.backupPaths = paths;
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
