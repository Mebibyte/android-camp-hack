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
    private Paint mPaint, rPaint, ePaint;
    private ArrayList<DrawPath> paths = new ArrayList<DrawPath>();
    private State state = State.DRAW;
    private float highlightX;

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

    public DrawPanel(Context context) {
        super(context);
        rPaint = new Paint();
        rPaint.setColor(Color.BLACK);
        rPaint.setStyle(Paint.Style.STROKE);
        rPaint.setStrokeWidth(6);
        ePaint = new Paint();
        ePaint.setColor(Color.WHITE);
        ePaint.setStrokeWidth(10);
        ePaint.setStyle(Paint.Style.STROKE);
        mPaint = new Paint();
        mPaint.setDither(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(3);
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
        ePaint.setStrokeWidth(10);
        ePaint.setStyle(Paint.Style.STROKE);
        mPaint = new Paint();
        mPaint.setDither(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(3);
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
        ePaint.setStrokeWidth(10);
        ePaint.setStyle(Paint.Style.STROKE);
        mPaint = new Paint();
        mPaint.setDither(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(3);
        highlightX = 23;
        setFocusable(true);
        setWillNotDraw(false);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        canvas.drawRect(highlightX,995,highlightX + 110,1105, mPaint);
        for(DrawPath path: paths)
        {
            canvas.drawPath(path.getPath(), path.getPaint());
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
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
