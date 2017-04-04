package com.redside.rngquest.managers;


import android.os.Handler;

import com.redside.rngquest.entities.Ghost;
import com.redside.rngquest.utils.Assets;

public class GameManager {
    public static int stage = 1;
    public static int part = 1;
    private static int width;
    private static int height;
    private static BattleManager battleManager;
    public GameManager(){
        battleManager = new BattleManager();
        width = HUDManager.width;
        height = HUDManager.height;
    }
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
            case TITLE:
                if (Soundtrack.getCurrentSong() != Song.TITLE){
                    Soundtrack.playSong(Song.TITLE);
                }
                break;
            case STAGE_TRANSITION:
                Soundtrack.playSong(Song.WAVE);
                // Switch to next screen in 4 seconds
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        SEManager.playEffect(SEManager.Effect.FADE_TRANSITION, ScreenState.BATTLE);
                    }
                }, 3500);
                break;
            case BATTLE:
                Soundtrack.playSong(Song.SHOP);
                battleManager.startBattle(new Ghost(30, 30, width / 2, height / 2));
                break;
        }
    }
    public void tick(){
        battleManager.tick();
    }
}
