package com.redside.rngquest.managers;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.redside.rngquest.gameobjects.CoreView;
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
    public static Context context;
    public static ScreenState state;
    public static boolean allowTouch = true;
    public CoreManager(Context context, int width, int height){
        this.context = context;
        this.width = width;
        this.height = height;
        init();
    }

    /**
     * Called when this manager is initialized.
     */
    public void init(){
        // Initialize HUD manager, background manager, and SE manager
        // Load sound effects
        state = ScreenState.TITLE;
        hud = new HUDManager();
        background = new Background();
        se = new SEManager();
        gm = new GameManager();
        Sound.loadSounds();
        // Call first time onStateChange for HUD and Background
        hud.onStateChange(state, state);
        background.onStateChange(state ,state);
        gm.onStateChange(state, state);
    }

    /**
     * Called when the user lets go on the screen.
     * @param e The {@link MotionEvent} to listen to
     */
    public void touchEvent(MotionEvent e){
        if (allowTouch){
            hud.touchEvent(e);
        }
    }
    /**
     * Called when the user first touches the screen.
     * @param e The {@link MotionEvent} to listen to
     */
    public void preTouchEvent(MotionEvent e){
        if (allowTouch){
            hud.preTouchEvent(e);
        }
    }

    /**
     * Called when the game ticks.
     * Ticks all managers in order.
     */
    public void tick(){
        // Tick all managers in order
        background.tick();
        hud.tick();
        se.tick();
        gm.tick();
    }

    /**
     * Sets if the game should detect touches or not.
     * @param touch Touch
     */
    public static void setAllowTouch(boolean touch){
        allowTouch = touch;
    }

    /**
     * Called when the game renders.
     * Renders all managers in order.
     * @param canvas The {@link Canvas} to draw on
     * @param paint The {@link Paint} object to draw with
     */
    public void render(Canvas canvas, Paint paint){
        // Render all managers in order. This determines the layering
        background.render(canvas, paint);
        hud.render(canvas, paint);
        se.render(canvas, paint);
    }
}
