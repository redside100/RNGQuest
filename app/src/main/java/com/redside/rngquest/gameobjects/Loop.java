package com.redside.rngquest.gameobjects;

import android.os.Handler;


public class Loop {
    public static double FPS = 60;
    private Handler handler;
    private Runnable runnable;
    private final CoreView c;
    public Loop(CoreView coreView){
        c = coreView;
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                c.render();
                c.tick();
                handler.postDelayed(runnable, (long) (1000 / FPS));
            }
        };
        handler.post(runnable);
    }
}
