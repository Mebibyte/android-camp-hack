package com.HACK.codersbestfriend;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

enum State {
    DRAW, ERASE, RECTANGLE;
}

public class DrawPanel extends View {
    private Path path;
    private Paint mPaint;
    private ArrayList<Path> paths = new ArrayList<Path>();
    private State state = State.DRAW;

    public void toDraw()
    {
        state = State.DRAW;
    }

    public void toErase()
    {
        state = State.ERASE;
    }

    public void toRect()
    {
        state = State.RECTANGLE;
    }

    public DrawPanel(Context context) {
        super(context);
        mPaint = new Paint();
        mPaint.setDither(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(3);
        setFocusable(true);
        setWillNotDraw(false);
    }

    public DrawPanel(Context context, AttributeSet attrbs) {
        super(context, attrbs);
        mPaint = new Paint();
        mPaint.setDither(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(3);
        setFocusable(true);
        setWillNotDraw(false);
    }

    public DrawPanel(Context context, AttributeSet attrbs, int defStyle) {
        super(context, attrbs, defStyle);
        mPaint = new Paint();
        mPaint.setDither(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(3);
        setFocusable(true);
        setWillNotDraw(false);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        Log.d("AMIDRAWING", "" + paths.size());
        for (Path path : paths) {
            canvas.drawPath(path, mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("AMITOUCHING", "IAMTOUCHING");
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            path = new Path();
            path.moveTo(event.getX(), event.getY());
            path.lineTo(event.getX(), event.getY());
            paths.add(path);
        } else if(event.getAction() == MotionEvent.ACTION_MOVE ||
                event.getAction() == MotionEvent.ACTION_UP) {
            path.lineTo(event.getX(), event.getY());
        }
        invalidate();
        return true;
    }
}
