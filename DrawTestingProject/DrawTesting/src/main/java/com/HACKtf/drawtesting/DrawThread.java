package com.HACKtf.drawtesting;

import android.graphics.Canvas;
import android.view.SurfaceHolder;
/**
 * Created by demouser on 6/11/13.
 */
public class DrawThread extends Thread {
    private SurfaceHolder sh;
    private DrawPanel panel;
    private boolean run = false;

    public DrawThread(SurfaceHolder surfaceHolder, DrawPanel panel)
    {
        sh = surfaceHolder;
        this.panel = panel;
    }

    public void setRunning(boolean running)
    {
        run = running;
    }

    public SurfaceHolder getSurfaceHolder()
    {
        return sh;
    }

    @Override
    public void run()
    {
        Canvas c;
        while(run)
        {
            c = null;
            try
            {
                c = sh.lockCanvas(null);
                synchronized (sh)
                {
                    panel.onDraw(c);
                }
            }
            finally
            {
                //put this in our finally block so that if an exception is thrown, don't leave
                //the surface in a bad state
                if(c != null)
                {
                    sh.unlockCanvasAndPost(c);
                }
            }
        }
    }
}
