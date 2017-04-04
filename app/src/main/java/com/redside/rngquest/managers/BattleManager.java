package com.redside.rngquest.managers;
import com.redside.rngquest.entities.EAState;
import com.redside.rngquest.entities.Entity;
import com.redside.rngquest.entities.SlashAnimation;

public class BattleManager {
    private boolean preBattle = false;
    private boolean inBattle = false;
    private static Entity currentEnemy = null;
    private static int tick = 0;
    private static int width;
    private static int height;
    private static BattleState battleState = BattleState.NONE;
    public BattleManager(){
        this.width = HUDManager.width;
        this.height = HUDManager.height;
        battleState = BattleState.PLAYER_TURN;
    }
    public void tick(){

        switch(battleState){
            case PLAYER_TURN:
            case NONE:
                break;
            case PLAYER_ATTACK:
                if (tick == 20){
                    currentEnemy.shake(80);
                    currentEnemy.setState(EAState.DAMAGE);
                }
                if (tick == 100){
                    currentEnemy.setState(EAState.IDLE);
                    battleState = BattleState.PLAYER_TURN;
                    tick = 0;
                }
                tick++;
        }

    }
    public void startBattle(Entity enemy){
        this.currentEnemy = enemy;
        preBattle = true;
    }
    public static void playerAttack(){
        if (battleState.equals(BattleState.PLAYER_TURN)){
            new SlashAnimation(width / 2, height / 2);
            battleState = BattleState.PLAYER_ATTACK;
        }
    }
    public static void playerDefend(){

    }
    public enum BattleState{
        PLAYER_TURN, PLAYER_ATTACK, ENEMY_TURN, ENEMY_ATTACK, ENEMY_DEAD, REWARD, NONE
    }
}
