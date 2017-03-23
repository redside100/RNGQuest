package com.redside.rngquest.managers;

import android.content.Entity;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.redside.rngquest.entities.SlashAnimation;

public class BattleManager {
    private boolean preBattle = false;
    private boolean inBattle = false;
    private Entity currentEnemy = null;
    private static int tick;
    private static int width;
    private static int height;
    public BattleManager(){
        this.width = HUDManager.width;
        this.height = HUDManager.height;
    }
    public void tick(){

    }
    public void render(Canvas canvas, Paint paint){

    }
    public void startBattle(Entity enemy){
        this.currentEnemy = enemy;
        preBattle = true;
    }
    public static void playerAttack(){
        new SlashAnimation(width / 2, height / 2);
    }
    public static void playerDefend(){

    }
    public enum BattleState{
        PLAYER_TURN, ENEMY_TURN, ENEMY_DEAD, REWARD
    }
}
