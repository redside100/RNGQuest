package com.redside.rngquest.managers;
import android.graphics.Color;

import com.redside.rngquest.entities.Blob;
import com.redside.rngquest.entities.EAState;
import com.redside.rngquest.entities.Entity;
import com.redside.rngquest.entities.ExplosionAnimation;
import com.redside.rngquest.entities.Ghost;
import com.redside.rngquest.entities.Player;
import com.redside.rngquest.entities.SlashAnimation;
import com.redside.rngquest.entities.Wizard;
import com.redside.rngquest.utils.RNG;

public class BattleManager {
    public static Entity currentEnemy = null;
    public static Entity savedEnemy = null;
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
                    // Initiate new battle
                    case 10:
                        // Spawn new enemy, scale stats with stage level
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
                        break;
                    // Set state to player's turn 120 ticks after initiating
                    case 130:
                        battleState = BattleState.PLAYER_TURN;
                        tick = 0;
                        break;
                }
                tick++;
                break;
            case REWARD:
                switch(tick){
                    // Give out rewards
                    case 10:
                        int stage = GameManager.getStage();

                        // Give gold reward, scale with stage level
                        int goldReward = 5 + (int) (stage * 1.5) + RNG.number(0, stage);
                        Player.addGold(goldReward);

                        HUDManager.displayFadeMessage("Received " + goldReward + " gold!", width / 2, height / 2, 45, 35, Color.YELLOW);

                        // Give random stat reward
                        int reward = RNG.number(1, 6);
                        switch(reward){
                            case 1:
                                // Give out 1-2 atk chance
                                int atkChanceAmount = RNG.number(1, 2);
                                HUDManager.displayFadeMessage("Gained " + atkChanceAmount + "% ATK chance!", width / 2, (int) (height * 0.6), 45, 35, Color.GREEN);
                                Player.addATKChance(atkChanceAmount);
                                break;
                            case 2:
                                // Give out 1-3 atk
                                int atkAmount = RNG.number(1, 3);
                                HUDManager.displayFadeMessage("Gained " + atkAmount + " ATK!", width / 2, (int) (height * 0.6), 45, 35, Color.GREEN);
                                Player.addATK(atkAmount);
                                break;
                            case 3:
                                // Give out 10-25% HP
                                int healAmount = RNG.number(Player.getMaxHP() / 10, Player.getMaxHP() / 4);
                                HUDManager.displayFadeMessage("Recovered " + healAmount + " HP!", width / 2, (int) (height * 0.6), 45, 35, Color.GREEN);
                                Player.heal(healAmount);
                                break;
                            case 4:
                                // Give out 5-10% max HP
                                int maxHpAmount = (int) (Player.getMaxHP() * ((double) RNG.number(5, 10) / (double) 100));
                                HUDManager.displayFadeMessage("Max HP increased by " + maxHpAmount + "!", width / 2, (int) (height * 0.6), 45, 35, Color.GREEN);
                                Player.increaseMaxHealth(maxHpAmount);
                                Player.heal(maxHpAmount);
                                break;
                            case 5:
                                // Give out 20-33% armor
                                int armorAmount = RNG.number(Player.getMaxArmor() / 5, Player.getMaxArmor() / 3);
                                HUDManager.displayFadeMessage("Gained " + armorAmount + " AMR!", width / 2, (int) (height * 0.6), 45, 35, Color.GREEN);
                                Player.addArmor(armorAmount);
                                break;
                            case 6:
                                // Give out 10-20% MP
                                int manaAmount = RNG.number(Player.getMaxMana() / 10, Player.getMaxMana() / 5);
                                HUDManager.displayFadeMessage("Gained " + manaAmount + " MP!", width / 2, (int) (height * 0.6), 45, 35, Color.GREEN);
                                Player.addMana(manaAmount);
                                break;
                        }
                        break;
                    // Next battle, or announce stage clear
                    case 100:
                        // Check if it's the end of the stage (7 enemies)
                        if (GameManager.getPart() < 8){
                            GameManager.nextPart();
                            battleState = BattleState.BATTLE_START;
                            tick = 0;
                        }else{
                            HUDManager.displayFadeMessage("Stage " + GameManager.getStage() + " cleared!", width / 2, height / 2, 90, 35, Color.YELLOW);
                        }
                        break;
                    // If stage cleared, then proceed to shop
                    case 220:
                        SEManager.playEffect(SEManager.Effect.FADE_TRANSITION, ScreenState.SHOP);
                        close();
                        break;
                }
                tick++;
                break;
            case PLAYER_ATTACK:
                switch(tick){
                    // Check for hit
                    case 10:
                        // Roll the dice
                        if (RNG.pass(Player.getATKChance())){
                            Sound.playSound(SoundEffect.ENEMY_HIT);
                            damageEnemy(Player.getATK());
                        }else{
                            Sound.playSound(SoundEffect.MISS);
                            HUDManager.displayFadeMessage("MISS", (int) currentEnemy.x, (int) (currentEnemy.y - height * 0.15), 30, 35, Color.RED);
                        }
                        Player.resetAtkChanceBonus();
                        break;
                    // Check if the enemy is dead, if it's not, proceed to enemy's turn
                    case 80:
                        if (currentEnemy.isDead()){
                            if (Player.hasAgility()){
                                Player.toggleAgility();
                            }
                            currentEnemy.fadeOut(60);
                        }else{
                            currentEnemy.setState(EAState.IDLE);
                            if (Player.hasAgility()){
                                Player.toggleAgility();
                                battleState = BattleState.PLAYER_DEFEND;
                                tick = 0;
                            }else{
                                battleState = BattleState.ENEMY_ATTACK;
                                tick = 0;
                            }
                        }
                        break;
                    // If the enemy is dead, continue to reward state
                    case 140:
                        currentEnemy.destroy();
                        battleState = BattleState.REWARD;
                        tick = 0;
                        break;
                }
                tick++;
                break;
            case PLAYER_FIREBALL:
                switch(tick){
                    // Create explosion at enemy
                    case 10:
                        Sound.playSound(SoundEffect.EXPLODE);
                        ExplosionAnimation explode = new ExplosionAnimation((int) currentEnemy.x, (int) currentEnemy.y);
                        break;
                    // Check for hit
                    case 15:
                        // Roll the dice
                        if (RNG.pass(75)){
                            damageEnemy((int) (Player.getATK() * 1.5));
                        }else{
                            Sound.playSound(SoundEffect.MISS);
                            HUDManager.displayFadeMessage("MISS", (int) currentEnemy.x, (int) (currentEnemy.y - height * 0.15), 30, 35, Color.RED);
                        }
                        break;
                    // Check if the enemy is dead
                    case 85:
                        if (currentEnemy.isDead()){
                            currentEnemy.fadeOut(60);
                        }else{
                            currentEnemy.setState(EAState.IDLE);
                            battleState = BattleState.ENEMY_ATTACK;
                            tick = 0;
                        }
                        break;
                    // If the enemy is dead, proceed to reward state
                    case 145:
                        currentEnemy.destroy();
                        battleState = BattleState.REWARD;
                        tick = 0;
                        break;
                }
                tick++;
                break;
            case PLAYER_DEFEND:
                switch(tick){
                    // Add armor and atk bonus
                    case 10:
                        SEManager.playEffect(SEManager.Effect.BLUE_FLASH);
                        // Armor amount (25-33%), attack chance amount (7-10% of real atk)
                        int armorAmount = RNG.number(Player.getMaxArmor() / 4, Player.getMaxArmor() / 3);
                        int atkChanceAmount = RNG.number(Player.getRealATKChance() / 15, Player.getRealATKChance() / 10);
                        AnimatedTextManager.clear();
                        HUDManager.displayFadeMessage("+ " + armorAmount + " AMR", width / 2, (int) (height * 0.72), 30, 28, Color.CYAN);
                        HUDManager.displayFadeMessage("+ " + atkChanceAmount + "% ATK chance until next attack", width / 2, (int) (height * 0.8), 30, 28, Color.rgb(255, 80, 0));
                        Player.addAtkChanceBonus(atkChanceAmount);
                        Player.addArmor(armorAmount);
                        break;
                    // Proceed to enemy's turn
                    case 90:
                        battleState = BattleState.ENEMY_ATTACK;
                        tick = 0;
                        break;
                }
                tick++;
                break;
            case ENEMY_ATTACK:
                switch(tick){
                    // Attack animation
                    case 10:
                        currentEnemy.setState(EAState.ATTACK);
                        currentEnemy.shake(25);
                        break;
                    // Check if hit
                    case 20:
                        // Roll the dice
                        if (RNG.pass(100 - Player.getEvade())){
                            Sound.playSound(SoundEffect.PLAYER_HIT);
                            SEManager.playEffect(SEManager.Effect.RED_FLASH);
                            HUDManager.displayFadeMessage("Took " + currentEnemy.getAtk() + " DMG", width / 2, (int) (height * 0.75), 30, 30, Color.RED);
                            Player.damage(currentEnemy.getAtk());
                        }else{
                            Sound.playSound(SoundEffect.MISS);
                            HUDManager.displayFadeMessage("Dodged!", width / 2, (int) (height * 0.75), 30, 30, Color.GREEN);
                            SEManager.playEffect(SEManager.Effect.GREEN_FLASH);
                        }
                        break;
                    // Stop attack state
                    case 90:
                        currentEnemy.setState(EAState.IDLE);
                        break;
                    // Check if player is dead, if not, go to player's turn
                    case 100:
                        if (Player.isDead()){
                            SEManager.playEffect(SEManager.Effect.FADE_TRANSITION, ScreenState.TITLE);
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
    public static void startBattle(Entity enemy){
        // Start new battle with given enemy
        currentEnemy = enemy;
        battleState = BattleState.PLAYER_TURN;
        tick = 0;
        currentEnemy.fadeIn(60);
    }
    public static void resumeBattle(Entity enemy){
        // Resume battle with given enemy (no fade in)
        currentEnemy = enemy;
        currentEnemy.spawn();
        battleState = BattleState.PLAYER_TURN;
        tick = 0;
    }
    public static void playerSpell(){
        // Use spell if it's player turn
        // Play spell sound
        if (battleState.equals(BattleState.PLAYER_TURN)){
            Player.getCurrentSpell().use();
        }
    }
    public static void playerAttack(){
        // Spawn slash animation and go to attack state if it's player turn
        if (battleState.equals(BattleState.PLAYER_TURN)){
            // Play attack sound in the future
            new SlashAnimation((int) currentEnemy.x, (int) currentEnemy.y);
            battleState = BattleState.PLAYER_ATTACK;
        }
    }
    public static void playerDefend(){
        // Go to defend state if it's player turn
        if (battleState.equals(BattleState.PLAYER_TURN)){
            // Play defend sound in the future
            battleState = BattleState.PLAYER_DEFEND;
        }
    }
    public static void playerInventory(){
        // Save the current enemy and go to inventory screen if it's player turn
        if (battleState.equals(BattleState.PLAYER_TURN)){
            Sound.playSound(SoundEffect.SELECT);
            savedEnemy = currentEnemy;
            SEManager.playEffect(SEManager.Effect.FADE_TRANSITION, ScreenState.INVENTORY);
        }
    }
    public static void setBattleState(BattleState newState){
        battleState = newState;
    }
    public static void close(){
        // Discard currentEnemy entity
        if (currentEnemy != null){
            currentEnemy.destroy();
            currentEnemy = null;
        }
        // Reset tick counter and battle state
        tick = 0;
        battleState = BattleState.NONE;
    }
    private static void damageEnemy(int amount){
        // Speed for the flying text
        double speed = HUDManager.getSpeed(CoreManager.width, 274);
        // Determine if it should fly left or right
        if (RNG.yesNo()){
            HUDManager.displayParabolicText("-" + amount, (int) currentEnemy.x, (int) (currentEnemy.y - height * 0.1), 90, 32, Color.RED, -speed);
        }else{
            HUDManager.displayParabolicText("-" + amount, (int) currentEnemy.x, (int) (currentEnemy.y - height * 0.1), 90, 32, Color.RED, speed);
        }
        // Flash, shake, damage, and set state to damaged
        SEManager.playEffect(SEManager.Effect.YELLOW_FLASH);
        currentEnemy.shake(50);
        currentEnemy.damage(amount);
        currentEnemy.setState(EAState.DAMAGE);
    }
    public static Entity getCurrentEnemy(){
        return currentEnemy;
    }
    public enum BattleState{
        BATTLE_START, PLAYER_TURN, PLAYER_ATTACK, PLAYER_FIREBALL, PLAYER_AGILITY, PLAYER_DEFEND, ENEMY_ATTACK, REWARD, NONE
    }
}
