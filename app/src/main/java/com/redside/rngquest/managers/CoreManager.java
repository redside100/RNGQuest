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
    private static SEManager se;
    private static GameManager gm;
    private Assets assets;
    public static ScreenState state;
    public static boolean allowTouch = true;
    public CoreManager(int width, int height){
        this.width = width;
        this.height = height;
        init();
    }
    public void init(){
        // Initialize HUD manager, background manager, and SE manager
        state = ScreenState.TITLE;
        hud = new HUDManager();
        background = new Background();
        se = new SEManager();
        gm = new GameManager();
        // Call first time onStateChange for HUD and Background
        hud.onStateChange(state);
        background.onStateChange(state);
        gm.onStateChange(state);
    }
    public static void stateChange(ScreenState newState){
        // Usually state changes are handled in SE
        se.onStateChange(newState);
    }
    public void touchEvent(MotionEvent e){
        if (allowTouch){
            hud.touchEvent(e);
        }
    }
    public void tick(){
        // Tick all managers in order
        background.tick();
        hud.tick();
        se.tick();
        gm.tick();
    }
    public static void setAllowTouch(boolean touch){
        allowTouch = touch;
    }
    public void render(Canvas canvas, Paint paint){
        // Render all managers in order. This determines the layering
        background.render(canvas, paint);
        hud.render(canvas, paint);
        se.render(canvas, paint);
    }
}
