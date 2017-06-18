package com.redside.rngquest.managers;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.redside.rngquest.utils.Animator;
import com.redside.rngquest.utils.Assets;

import java.util.ArrayList;


public class Background {
    private static ArrayList<Bitmap> background;
    private Bitmap clouds, block;
    private static Animator animate;
    private static double xOff = 0;
    public Background(){
        // Load all required assets from memory
        clouds = Assets.getBitmapFromMemory("background_title_clouds");
        block = Assets.getBitmapFromMemory("sprites_block");
        background = new ArrayList<>();
        background.add(Assets.getBitmapFromMemory("background_title_0"));
        background.add(Assets.getBitmapFromMemory("background_title_1"));
        animate = new Animator(background);
        // This is just defaulted for the first time initializing, assuming it starts on the title screen.
        // This speed will be changed in onStateChange()
        animate.setSpeed(450);
        animate.play();
        animate.update(System.currentTimeMillis());
        onStateChange(ScreenState.TITLE, ScreenState.TITLE);
    }

    /**
     * Called when the game ticks.
     * Handles moving elements in the background.
     */
    public void tick(){
        switch(CoreManager.state){
            // All movement for things in the background are handled here.
            case CHAR_SELECT:
            case TITLE:
            case LOAD:
            case INFO:
                if (xOff < CoreManager.width + clouds.getWidth()){
                    xOff += HUDManager.getSpeed(CoreManager.width, 1500);
                }else{
                    xOff = 0;
                }
                break;
        }
    }

    /**
     * Called when the {@link ScreenState} changes.
     * @param oldState The previous {@link ScreenState}
     * @param newState The new {@link ScreenState}
     */
    public static void onStateChange(ScreenState oldState, ScreenState newState){
        // No matter what, clear all frames in the background, and reload.
        background.clear();
        switch (newState){
            case TITLE:
            case INFO:
            case LOAD:
            case CHAR_SELECT:
                animate.setSpeed(450);
                for (int i = 0; i < 2; i++){
                    background.add(Assets.getBitmapFromMemory("background_title_" + i));
                }
                break;
            case STAGE_TRANSITION:
                background.add(Assets.getBitmapFromMemory("background_black"));
                break;
            case BATTLE:
                animate.setSpeed(750);
                for (int i = 0; i < 3; i++){
                    background.add(Assets.getBitmapFromMemory("background_forest_" + i));
                }
                background.add(Assets.getBitmapFromMemory("background_forest_1"));
                break;
            case SHOP:
                background.add(Assets.getBitmapFromMemory("background_mountains"));
                break;
            case INVENTORY:
                background.add(Assets.getBitmapFromMemory("background_inventory"));
                break;
            default:
                background.add(Assets.getBitmapFromMemory("background_mountains"));
        }
        animate.replace(background);
    }

    /**
     * Called when the game renders.
     * @param canvas The {@link Canvas} to draw on
     * @param paint The {@link Paint} object to draw with
     */
    public void render(Canvas canvas, Paint paint){
        // Draw current frame and update animator with time
        if (background.size() > 0){
            canvas.drawBitmap(animate.sprite, 0, 0, paint);
            animate.update(System.currentTimeMillis());
        }
        switch(CoreManager.state){
            // All moving objects in the background are drawn here.
            case TITLE:
            case INFO:
            case LOAD:
            case CHAR_SELECT:
                // Clouds
                canvas.drawBitmap(clouds, CoreManager.width - (int) xOff, CoreManager.height / 10, paint);
                break;

        }
    }
}
