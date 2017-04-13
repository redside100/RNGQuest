package com.redside.rngquest.managers;
import android.graphics.Color;

import com.redside.rngquest.entities.EAState;
import com.redside.rngquest.entities.Entity;
import com.redside.rngquest.entities.Player;
import com.redside.rngquest.entities.SlashAnimation;
import com.redside.rngquest.utils.RNG;

public class BattleManager {
    public static Entity currentEnemy = null;
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
                switch(tick){
                    case 20:
                        if (RNG.pass(Player.getATKChance())){
                            SEManager.playEffect(SEManager.Effect.YELLOW_FLASH);
                            currentEnemy.shake(80);
                            currentEnemy.damage(Player.getATK());
                            currentEnemy.setState(EAState.DAMAGE);
                        }else{
                            HUDManager.displayFadeMessage("MISS", currentEnemy.x, (int) (currentEnemy.y - height * 0.1), 30, 100, Color.RED);
                        }
                        break;
                    case 100:
                        currentEnemy.setState(EAState.IDLE);
                        battleState = BattleState.ENEMY_ATTACK;
                        tick = 0;
                        break;
                }
                tick++;
                break;
            case ENEMY_ATTACK:
                switch(tick){
                    case 20:
                        currentEnemy.setState(EAState.ATTACK);
                        currentEnemy.shake(25);
                        if (RNG.pass(100 - Player.getEvade())){
                            SEManager.playEffect(SEManager.Effect.RED_FLASH);
                            Player.damage(currentEnemy.getAtk());

                        }else{
                            HUDManager.displayFadeMessage("Dodged!", width / 2, (int) (height * 0.75), 30, 100, Color.GREEN);
                            SEManager.playEffect(SEManager.Effect.GREEN_FLASH);
                        }
                        break;
                    case 80:
                        currentEnemy.setState(EAState.IDLE);
                        break;
                    case 100:
                        battleState = BattleState.PLAYER_TURN;
                        tick = 0;
                        break;

                }
                tick++;
                break;
        }

    }
    public void startBattle(Entity enemy){
        this.currentEnemy = enemy;
        battleState = BattleState.PLAYER_TURN;
        tick = 0;
    }
    public static void playerAttack(){
        if (battleState.equals(BattleState.PLAYER_TURN)){
            new SlashAnimation(width / 2, height / 2);
            battleState = BattleState.PLAYER_ATTACK;
        }
    }
    public static void playerDefend(){

    }
    public static void close(){
        currentEnemy = null;
        tick = 0;
        battleState = BattleState.NONE;
    }
    public static Entity getCurrentEnemy(){
        return currentEnemy;
    }
    public enum BattleState{
        PLAYER_TURN, PLAYER_ATTACK, ENEMY_TURN, ENEMY_ATTACK, ENEMY_DEAD, REWARD, NONE
    }
}
