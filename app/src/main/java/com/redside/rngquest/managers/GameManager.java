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
    private static int tick = 0;
    private static boolean sTransition = false;
    public GameManager(){
        battleManager = new BattleManager();
        width = HUDManager.width;
        height = HUDManager.height;
    }
    public static void nextStage(){
        stage++;
    }
    public static void nextPart(){
        part++;
    }
    public static int getStage(){
        return stage;
    }
    public static int getPart(){
        return part;
    }
    public static void onStateChange(ScreenState newState){
        battleManager.close();
        switch(newState){
            case TITLE:
                reset();
                if (Soundtrack.getCurrentSong() != Song.TITLE){
                    Soundtrack.playSong(Song.TITLE);
                }
                break;
            case STAGE_TRANSITION:
                Soundtrack.playSong(Song.WAVE);
                // Switch to next screen in 3 seconds
                sTransition = true;
                break;
            case INFO:
                Soundtrack.playSong(Song.SHOP);
                break;
            case BATTLE:
                Soundtrack.playSong(Song.BATTLE);
                BattleManager.setBattleState(BattleManager.BattleState.BATTLE_START);
                break;
        }
    }
    public static void reset(){
        stage = 1;
        part = 1;
    }
    public void tick(){
        battleManager.tick();
        if (sTransition){
            tick++;
            if (tick == 195){
                tick = 0;
                sTransition = false;
                SEManager.playEffect(SEManager.Effect.FADE_TRANSITION, ScreenState.BATTLE);
            }
        }
    }
}
