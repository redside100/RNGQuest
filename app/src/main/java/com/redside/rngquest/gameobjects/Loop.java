package com.redside.rngquest.gameobjects;

import android.os.Handler;


public class Loop {
    public static int FPS = 60;
    private Handler handler;
    private Runnable runnable;
    private final CoreView c;
    private long beginTime;
    private long timeDiff;
    private int sleepTime;
    private int framesSkipped;
    private int framePeriod = 1000 / FPS;
    private int maxFrameSkips = 10;
    public Loop(CoreView coreView){
        sleepTime = 0;
        c = coreView;
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                beginTime = System.currentTimeMillis();
                framesSkipped = 0;
                c.tick();
                c.render();
                timeDiff = System.currentTimeMillis() - beginTime;
                sleepTime = (int) (framePeriod - timeDiff);
                if (sleepTime > 0){
                    handler.postDelayed(runnable, sleepTime);
                }
                while (sleepTime < 0 && framesSkipped < maxFrameSkips) {
                    c.tick();
                    sleepTime += framePeriod;
                    framesSkipped++;
                    System.out.println("Can't keep up! Skipped " + framesSkipped + " frames");
                }

            }
        };
        handler.post(runnable);
    }
}
