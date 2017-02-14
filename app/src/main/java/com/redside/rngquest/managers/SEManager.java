package com.redside.rngquest.managers;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class SEManager {
    private static int tick = 0;
    private static int opacity = 0;
    private static boolean running = false;
    private static boolean fadein = false;
    private static Effect currentEffect = Effect.NOTHING;
    private static ScreenState nextState = null;

    public void tick(){
        switch(currentEffect){
            // Check for current playing effects
            case FADE_TRANSITION:
                // Check for black screen fading in
                if (tick < 32 && fadein){
                    tick++;
                    // Check if fully faded (at 31 ticks)
                    if (tick == 31){
                        // Set states, and prepare to fade out
                        fadein = false;
                        CoreManager.state = nextState;
                        HUDManager.onStateChange(nextState);
                        Background.onStateChange(nextState);
                        GameManager.onStateChange(nextState);
                        HUDManager.selection = 0;
                    }
                    // Check if fading out
                }else if (tick < 32 && !fadein){
                    tick--;
                    // Check if fully faded out (31 ticks)
                    if (tick == 0){
                        // Reset everything
                        running = false;
                        currentEffect = Effect.NOTHING;
                        CoreManager.setAllowTouch(true);
                    }
                }
                // When opacity is 255, it is fully visible, when it is 0 it is fully transparent
                opacity = tick * 8;
                break;
        }
    }
    public void render(Canvas canvas, Paint paint){
        if (running){
            switch(currentEffect){
                case FADE_TRANSITION:
                    // For fade transition, create a black rectangle that covers the whole screen
                    Rect rect = new Rect(0, 0, CoreManager.width, CoreManager.height);
                    paint.setColor(Color.BLACK);
                    paint.setAlpha(opacity);
                    canvas.drawRect(rect, paint);
                    break;
            }
        }
    }
    public static void playEffect(Effect effect, ScreenState newState){
        if (!running){
            running = true;
            currentEffect = effect;
            nextState = newState;
            tick = 0;
            switch(effect){
                case FADE_TRANSITION:
                    fadein = true;
                    // Temporarily disable user input while transitioning
                    CoreManager.setAllowTouch(false);
                    break;
            }
        }
    }
    public static void onStateChange(ScreenState newState){

    }
    public enum Effect {
        NOTHING, FADE_TRANSITION, SHAKE, RED_FLASH, YELLOW_FLASH
    }
}
