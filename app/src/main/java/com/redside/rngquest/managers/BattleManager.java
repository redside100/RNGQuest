package com.redside.rngquest.managers;
import android.graphics.Color;

import com.redside.rngquest.entities.Blob;
import com.redside.rngquest.entities.EAState;
import com.redside.rngquest.entities.Entity;
import com.redside.rngquest.entities.Ghost;
import com.redside.rngquest.entities.Player;
import com.redside.rngquest.entities.SlashAnimation;
import com.redside.rngquest.entities.Wizard;
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
            case BATTLE_START:
                switch(tick){
                    case 10:
                        int spawn = RNG.number(1, 3);
                        int stage = GameManager.getStage();
                        switch(spawn){
                            case 1:
                                startBattle(new Ghost((int) (12 * stage * 0.5), (int) (2 * stage * 0.5), width / 2, height / 2, 0));
                                break;
                            case 2:
                                startBattle(new Wizard((int) (20 * stage * 0.5), (int) (5 * stage * 0.5), width / 2, height / 2, 0));
                                break;
                            case 3:
                                startBattle(new Blob(7 * stage, 2 * stage, width / 2, height / 2, 0));
                                break;
                        }
                        currentEnemy.fadeIn(60);
                        break;
                    case 140:
                        battleState = BattleState.PLAYER_TURN;
                        tick = 0;
                        break;
                }
                tick++;
                break;
            case REWARD:
                switch(tick){
                    case 10:
                        int stage = GameManager.getStage();
                        int goldReward = (stage * 3) + RNG.number(0, stage * 2);
                        Player.addGold(goldReward);
                        HUDManager.displayFadeMessage("Received " + goldReward + " gold!", width / 2, height / 2, 45, 35, Color.YELLOW);
                        int reward = RNG.number(1, 5);
                        switch(reward){
                            case 1:
                                int atkChanceAmount = RNG.number(1, 2);
                                HUDManager.displayFadeMessage("Gained " + atkChanceAmount + "% ATK chance!", width / 2, (int) (height * 0.6), 45, 35, Color.GREEN);
                                Player.addATKChance(atkChanceAmount);
                                break;
                            case 2:
                                int atkAmount = RNG.number(1, 3);
                                HUDManager.displayFadeMessage("Gained " + atkAmount + " ATK!", width / 2, (int) (height * 0.6), 45, 35, Color.GREEN);
                                Player.addATK(atkAmount);
                                break;
                            case 3:
                                int healAmount = RNG.number(Player.getMaxHP() / 10, Player.getMaxHP() / 4);
                                HUDManager.displayFadeMessage("Recovered " + healAmount + " HP!", width / 2, (int) (height * 0.6), 45, 35, Color.GREEN);
                                Player.heal(healAmount);
                                break;
                            case 4:
                                int maxHpAmount = (int) (Player.getMaxHP() * ((double) RNG.number(5, 10) / (double) 100));
                                HUDManager.displayFadeMessage("Max HP increased by " + maxHpAmount + "!", width / 2, (int) (height * 0.6), 45, 35, Color.GREEN);
                                Player.increaseMaxHealth(maxHpAmount);
                                Player.heal(maxHpAmount);
                                break;
                            case 5:
                                int armorAmount = RNG.number(Player.getMaxArmor() / 5, Player.getMaxArmor() / 3);
                                HUDManager.displayFadeMessage("Gained " + armorAmount + " AMR!", width / 2, (int) (height * 0.6), 45, 35, Color.GREEN);
                                Player.addArmor(armorAmount);
                                break;
                        }
                        break;
                    case 110:
                        if (GameManager.getPart() < 8){
                            GameManager.nextPart();
                            battleState = BattleState.BATTLE_START;
                            tick = 0;
                        }else{
                            HUDManager.displayFadeMessage("Stage " + GameManager.getStage() + " cleared!", width / 2, height / 2, 90, 35, Color.YELLOW);
                        }
                        break;
                    case 230:
                        SEManager.playEffect(SEManager.Effect.FADE_TRANSITION, ScreenState.SHOP);
                        close();
                        break;
                }
                tick++;
                break;
            case PLAYER_ATTACK:
                switch(tick){
                    case 10:
                        if (RNG.pass(Player.getATKChance())){
                            if (RNG.yesNo()){
                                HUDManager.displayParabolicText("-" + Player.getATK(), currentEnemy.x, (int) (currentEnemy.y - height * 0.1), 90, 32, Color.RED, -7);
                            }else{
                                HUDManager.displayParabolicText("-" + Player.getATK(), currentEnemy.x, (int) (currentEnemy.y - height * 0.1), 90, 32, Color.RED, 7);
                            }
                            SEManager.playEffect(SEManager.Effect.YELLOW_FLASH);
                            currentEnemy.shake(70);
                            currentEnemy.damage(Player.getATK());
                            currentEnemy.setState(EAState.DAMAGE);
                        }else{
                            HUDManager.displayFadeMessage("MISS", currentEnemy.x, (int) (currentEnemy.y - height * 0.15), 30, 35, Color.RED);
                        }
                        Player.resetAtkChanceBonus();
                        break;
                    case 90:
                        if (currentEnemy.isDead()){
                            currentEnemy.fadeOut(60);
                        }else{
                            currentEnemy.setState(EAState.IDLE);
                            battleState = BattleState.ENEMY_ATTACK;
                            tick = 0;
                        }
                        break;
                    case 160:
                        currentEnemy.destroy();
                        battleState = BattleState.REWARD;
                        tick = 0;
                        break;
                }
                tick++;
                break;
            case PLAYER_DEFEND:
                switch(tick){
                    case 10:
                        SEManager.playEffect(SEManager.Effect.BLUE_FLASH);
                        int armorAmount = RNG.number(Player.getMaxArmor() / 4, Player.getMaxArmor() / 3);
                        int atkChanceAmount = RNG.number(Player.getRealATKChance() / 15, Player.getRealATKChance() / 10);
                        HUDManager.displayFadeMessage("+ " + armorAmount + " AMR", width / 2, (int) (height * 0.72), 30, 30, Color.CYAN);
                        HUDManager.displayFadeMessage("+ " + atkChanceAmount + "% ATK chance until next attack", width / 2, (int) (height * 0.8), 30, 30, Color.rgb(255, 80, 0));
                        Player.addAtkChanceBonus(atkChanceAmount);
                        Player.addArmor(armorAmount);
                        break;
                    case 90:
                        battleState = BattleState.ENEMY_ATTACK;
                        tick = 0;
                        break;
                }
                tick++;
                break;
            case ENEMY_ATTACK:
                switch(tick){
                    case 10:
                        currentEnemy.setState(EAState.ATTACK);
                        currentEnemy.shake(25);
                        break;
                    case 20:
                        if (RNG.pass(100 - Player.getEvade())){
                            SEManager.playEffect(SEManager.Effect.RED_FLASH);
                            Player.damage(currentEnemy.getAtk());
                        }else{
                            HUDManager.displayFadeMessage("Dodged!", width / 2, (int) (height * 0.75), 30, 30, Color.GREEN);
                            SEManager.playEffect(SEManager.Effect.GREEN_FLASH);
                        }
                        break;
                    case 90:
                        currentEnemy.setState(EAState.IDLE);
                        break;
                    case 110:
                        if (Player.isDead()){
                            // end game
                        }else{
                            battleState = BattleState.PLAYER_TURN;
                            tick = 0;
                        }
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
            new SlashAnimation(currentEnemy.x, currentEnemy.y);
            battleState = BattleState.PLAYER_ATTACK;
        }
    }
    public static void playerDefend(){
        if (battleState.equals(BattleState.PLAYER_TURN)){
            battleState = BattleState.PLAYER_DEFEND;
        }
    }
    public static void setBattleState(BattleState newState){
        battleState = newState;
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
        BATTLE_START, PLAYER_TURN, PLAYER_ATTACK, PLAYER_DEFEND, ENEMY_ATTACK, REWARD, NONE
    }
}
