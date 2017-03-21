package com.redside.rngquest.managers;

import android.content.Entity;
import android.graphics.Canvas;
import android.graphics.Paint;

public class BattleManager {
    private boolean preBattle = false;
    private boolean inBattle = false;
    private Entity currentEnemy = null;
    private int tick;
    public void tick(){

    }
    public void render(Canvas canvas, Paint paint){

    }
    public void startBattle(Entity enemy){
        this.currentEnemy = enemy;
        preBattle = true;
    }
    public static void playerAttack(){

    }
    public static void playerDefend(){

    }
    public enum BattleState{
        PLAYER_TURN, ENEMY_TURN, ENEMY_DEAD, REWARD
    }
}
