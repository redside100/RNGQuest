package com.redside.rngquest.managers;


import android.os.Handler;

public class GameManager {
    public static int stage = 1;
    public static int part = 1;
    public static void nextStage(){
        stage++;
    }
    public static void nextPart(){
        if (part < 8){
            part++;
        }else{
            part = 1;
            nextStage();
        }
    }
    public static int getStage(){
        return stage;
    }
    public static int getPart(){
        return part;
    }
    public static void onStateChange(ScreenState newState){
        switch(newState){
            case STAGE_TRANSITION:
                // Switch to next screen in 4 seconds
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        SEManager.playEffect(SEManager.Effect.FADE_TRANSITION, ScreenState.BATTLE);
                    }
                }, 3500);
                break;
        }
    }
}
