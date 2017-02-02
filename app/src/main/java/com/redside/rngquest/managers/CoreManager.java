package com.redside.rngquest.managers;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.redside.rngquest.utils.Assets;

public class CoreManager{
    public static int width;
    public static int height;
    private int tick = 0;
    private int xoff = 0;
    boolean left = true;
    private HUDManager hud;
    private Background background;
    private Assets assets;
    public static ScreenState state;
    public CoreManager(){
        init();
    }
    public void init(){
        state = ScreenState.TITLE;
        hud = new HUDManager();
        background = new Background();
    }
    public void touchEvent(MotionEvent e){
        hud.touchEvent(e);
    }
    public void tick(){
        background.tick();
        hud.tick();
    }
    public void render(Canvas canvas, Paint paint){
        background.render(canvas, paint);
        hud.render(canvas, paint);
    }
}
