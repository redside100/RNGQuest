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
    private static HUDManager hud;
    private static Background background;
    private Assets assets;
    public static ScreenState state;
    public CoreManager(int width, int height){
        this.width = width;
        this.height = height;
        init();
    }
    public void init(){
        state = ScreenState.TITLE;
        hud = new HUDManager();
        background = new Background();
        stateChange(ScreenState.TITLE);
    }
    public static void stateChange(ScreenState newState){
        hud.onStateChange(newState);
        background.onStateChange(newState);
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
