package com.redside.rngquest.managers;
import com.redside.rngquest.entities.Ghost;
import com.redside.rngquest.entities.SlashAnimation;

public class BattleManager {
    private boolean preBattle = false;
    private boolean inBattle = false;
    private Object currentEnemy = null;
    private static int tick = 0;
    private static int width;
    private static int height;
    public BattleManager(){
        this.width = HUDManager.width;
        this.height = HUDManager.height;
    }
    public void tick(){

    }
    public void startBattle(Object enemy){
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
