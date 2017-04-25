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
    private static int xOff = 0;
    public Background(){
        // Load all required assets from memory
        clouds = Assets.getBitmapFromMemory("background_title_clouds");
        block = Assets.getBitmapFromMemory("sprites_block");
        background = new ArrayList<>();
        background.add(Assets.getBitmapFromMemory("background_title1"));
        background.add(Assets.getBitmapFromMemory("background_title2"));
        animate = new Animator(background);
        // This is just defaulted for the first time initializing, assuming it starts on the title screen.
        // This speed will be changed in onStateChange()
        animate.setSpeed(450);
        animate.play();
        animate.update(System.currentTimeMillis());
        onStateChange(ScreenState.TITLE);
    }
    public void tick(){
        switch(CoreManager.state){
            // All movement for things in the background are handled here.
            case CHAR_SELECT:
            case TITLE:
            case INFO:
                if (xOff < CoreManager.width + clouds.getWidth()){
                    xOff++;
                }else{
                    xOff = 0;
                }
                break;
        }
    }
    public static void onStateChange(ScreenState newState){
        // No matter what, clear all frames in the background, and reload.
        background.clear();
        switch (newState){
            case TITLE:
            case INFO:
            case CHAR_SELECT:
                for (int i = 1; i < 3; i++){
                    background.add(Assets.getBitmapFromMemory("background_title" + i));
                }
                break;
            case STAGE_TRANSITION:
                background.add(Assets.getBitmapFromMemory("background_black"));
                break;
            case BATTLE:
                background.add(Assets.getBitmapFromMemory("background_forest"));
                break;
            default:
                background.add(Assets.getBitmapFromMemory("background_black"));
        }
        animate.replace(background);
    }
    public void render(Canvas canvas, Paint paint){
        if (background != null){
            canvas.drawBitmap(animate.sprite, 0, 0, paint);
            animate.update(System.currentTimeMillis());
        }
        switch(CoreManager.state){
            // All moving objects in the background are drawn here.
            case TITLE:
            case INFO:
            case CHAR_SELECT:
                canvas.drawBitmap(clouds, CoreManager.width - xOff, CoreManager.height / 10, paint);
                break;

        }
    }
}
