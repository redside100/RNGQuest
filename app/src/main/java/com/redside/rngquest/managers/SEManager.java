package com.redside.rngquest.managers;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class SEManager {
    private static int tick = 0;
    private static int opacity = 0;
    private static boolean running = false;
    private static boolean fade = false;
    private static Effect currentEffect = Effect.NOTHING;
    private static ScreenState nextState = null;

    public void tick(){
        switch(currentEffect){
            // Check for current playing effects
            case FADE_TRANSITION:
                // Check for black screen fading in
                if (tick < 27 && fade){
                    tick++;
                    // Check if fully faded (at 31 ticks)
                    if (tick == 26){
                        // Set states, and prepare to fade out
                        fade = false;
                        CoreManager.state = nextState;
                        HUDManager.onStateChange(nextState);
                        Background.onStateChange(nextState);
                        GameManager.onStateChange(nextState);
                        HUDManager.selection = 0;
                    }
                    // Check if fading out
                }else if (tick < 27 && !fade){
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
                if (tick * 10 < 255){
                    opacity = tick * 10;
                }else{
                    opacity = 255;
                }
                break;
            case GREEN_FLASH:
            case YELLOW_FLASH:
            case RED_FLASH:
            case BLUE_FLASH:
                if (tick < 12 && fade){
                    tick++;
                    if (tick == 11){
                        fade = false;
                    }
                }else if (tick < 12 && !fade){
                    tick--;
                    if (tick == 0){
                        running = false;
                        currentEffect = Effect.NOTHING;
                    }
                }
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
                case RED_FLASH:
                    // For red flash, create a red rectangle that covers the whole screen
                    Rect flash = new Rect(0, 0, CoreManager.width, CoreManager.height);
                    paint.setColor(Color.RED);
                    paint.setAlpha(opacity);
                    canvas.drawRect(flash, paint);
                    break;
                case YELLOW_FLASH:
                    // For yellow flash, create a yellow rectangle that covers the whole screen
                    Rect flashY = new Rect(0, 0, CoreManager.width, CoreManager.height);
                    paint.setColor(Color.YELLOW);
                    paint.setAlpha(opacity);
                    canvas.drawRect(flashY, paint);
                    break;
                case GREEN_FLASH:
                    // For green flash, create a green rectangle that covers the whole screen
                    Rect flashG = new Rect(0, 0, CoreManager.width, CoreManager.height);
                    paint.setColor(Color.GREEN);
                    paint.setAlpha(opacity);
                    canvas.drawRect(flashG, paint);
                    break;
                case BLUE_FLASH:
                    // For blue flash, create a blue rectangle that covers the whole screen
                    Rect flashB = new Rect(0, 0, CoreManager.width, CoreManager.height);
                    paint.setColor(Color.rgb(0,191,255));
                    paint.setAlpha(opacity);
                    canvas.drawRect(flashB, paint);
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
                    fade = true;
                    // Temporarily disable user input while transitioning
                    CoreManager.setAllowTouch(false);
                    break;
                case RED_FLASH:
                case GREEN_FLASH:
                case BLUE_FLASH:
                case YELLOW_FLASH:
                    fade = true;
                    break;
            }
        }
    }
    public static void playEffect(Effect effect){
        if (!running){
            running = true;
            currentEffect = effect;
            tick = 0;
            switch(effect){
                case FADE_TRANSITION:
                    fade = true;
                    // Temporarily disable user input while transitioning
                    CoreManager.setAllowTouch(false);
                    break;
                case RED_FLASH:
                case GREEN_FLASH:
                case BLUE_FLASH:
                case YELLOW_FLASH:
                    fade = true;
                    break;
            }
        }
    }
    public static void onStateChange(ScreenState newState){

    }
    public enum Effect {
        NOTHING, FADE_TRANSITION, SHAKE, RED_FLASH, YELLOW_FLASH, GREEN_FLASH, BLUE_FLASH
    }
}
