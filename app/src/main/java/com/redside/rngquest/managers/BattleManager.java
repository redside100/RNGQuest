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
import com.redside.rngquest.gameobjects.CoreView;
import com.redside.rngquest.utils.RNG;

import java.util.ArrayList;

/**
 * Manages combat logic and states.
 * @author Andrew Peng
 * @since July 19, 2017
 */
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

    /**
     * Called when the game ticks.
     * Handles all combat logic.
     */
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
                    // Set state to player's turn 110 ticks after initiating
                    case 120:
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
                        int goldReward = 6 + (int) (stage * 1.4) + RNG.number(1, (int) ((stage + 1) * 0.84));
                        int rewardDisplayTime = 10;
                        int rewardTextSize = 18;

                        Player.addGold(goldReward);

                        HUDManager.displayFadeMessage("Received " + goldReward + " gold!", width / 2, height / 2, rewardDisplayTime, rewardTextSize, Color.YELLOW);
                        // Give random stat reward
                        int reward = RNG.number(1, 6);
                        switch(reward){
                            case 1:
                                // Give out 1-2 atk chance
                                int atkChanceAmount = RNG.number(1, 2);
                                HUDManager.displayFadeMessage("Gained " + atkChanceAmount + "% ATK chance!", width / 2, (int) (height * 0.6), rewardDisplayTime, rewardTextSize, Color.GREEN);
                                Player.addATKChance(atkChanceAmount);
                                break;
                            case 2:
                                // Give out 1-3 atk
                                int atkAmount = RNG.number(1, 3);
                                HUDManager.displayFadeMessage("Gained " + atkAmount + " ATK!", width / 2, (int) (height * 0.6), rewardDisplayTime, rewardTextSize, Color.GREEN);
                                Player.addATK(atkAmount);
                                break;
                            case 3:
                                // Give out 5-15% HP
                                int healAmount = RNG.number(Player.getMaxHP() / 20, (int) (Player.getMaxHP() * 0.15));
                                HUDManager.displayFadeMessage("Recovered " + healAmount + " HP!", width / 2, (int) (height * 0.6), rewardDisplayTime, rewardTextSize, Color.GREEN);
                                Player.heal(healAmount);
                                break;
                            case 4:
                                // Give out 5-8% max HP
                                int maxHpAmount = (int) (Player.getMaxHP() * ((double) RNG.number(5, 8) / (double) 100));
                                HUDManager.displayFadeMessage("Max HP increased by " + maxHpAmount + "!", width / 2, (int) (height * 0.6), rewardDisplayTime, rewardTextSize, Color.GREEN);
                                Player.increaseMaxHealth(maxHpAmount);
                                Player.heal(maxHpAmount);
                                break;
                            case 5:
                                // Give out 20-33% armor
                                int armorAmount = RNG.number(Player.getMaxArmor() / 5, Player.getMaxArmor() / 3);
                                HUDManager.displayFadeMessage("Gained " + armorAmount + " AMR!", width / 2, (int) (height * 0.6), rewardDisplayTime, rewardTextSize, Color.GREEN);
                                Player.addArmor(armorAmount);
                                break;
                            case 6:
                                // Give out 15-25% MP
                                int manaAmount = RNG.number((int) (Player.getMaxMana() * 0.15), Player.getMaxMana() / 4);
                                HUDManager.displayFadeMessage("Gained " + manaAmount + " MP!", width / 2, (int) (height * 0.6), rewardDisplayTime, rewardTextSize, Color.GREEN);
                                Player.addMana(manaAmount);
                                break;
                        }
                        break;
                    // Next battle, or announce stage clear
                    case 65:
                        // Check if it's the end of the stage (7 enemies)
                        if (GameManager.getPart() < 8){
                            GameManager.nextPart();
                            battleState = BattleState.BATTLE_START;
                            tick = 0;
                        }else{
                            HUDManager.displayFadeMessage("Stage " + GameManager.getStage() + " cleared!", width / 2, height / 2, 90, 18, Color.YELLOW);
                            HUDManager.displayParticleEffect();
                        }
                        break;
                    // If stage cleared, then proceed to shop
                    case 150:
                        SEManager.playEffect(SEManager.Effect.FADE_TRANSITION, ScreenState.SHOP);
                        close();
                        break;
                }
                tick++;
                break;
            case PLAYER_ATTACK:
                switch(tick){
                    case 1:
                        new SlashAnimation((int) currentEnemy.x, (int) currentEnemy.y);
                        break;
                    // Check for hit
                    case 10:
                        // Roll the dice
                        if (RNG.pass(Player.getATKChance())){
                            Sound.playSound(SoundEffect.ENEMY_HIT);
                            damageEnemy(Player.getATK());

                            // Check if the player has lifesteal
                            if (Player.hasLifesteal()){
                                // Heal the player for the amount
                                int heal = (int) (Player.getATK() * 0.35);
                                HUDManager.displayFadeMessage("+ " + heal + " HP", width / 2, (int) (height * 0.82), 30, 15, Color.GREEN);
                                Player.heal(heal);
                                Player.toggleLifesteal();
                            }
                        }else{

                            // Toggle life steal if player has it without any reward (since the player missed)
                            if (Player.hasLifesteal()){
                                Player.toggleLifesteal();
                            }
                            Sound.playSound(SoundEffect.MISS);
                            HUDManager.displayFadeMessage("MISS", (int) currentEnemy.x, (int) (currentEnemy.y - height * 0.15), 30, 18, Color.RED);
                        }
                        Player.resetAtkChanceBonus();
                        break;
                    // Check if the enemy is dead, if it's not, proceed to enemy's turn
                    case 80:
                        if (currentEnemy.isDead()){
                            if (Player.hasAgility()){
                                Player.toggleAgility();
                            }
                            currentEnemy.fadeOut(50);
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
                    case 120:
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
                            // Damage the enemy for 1.5x atk (since this is a fireball)
                            damageEnemy((int) (Player.getATK() * 1.5));
                        }else{
                            Sound.playSound(SoundEffect.MISS);
                            HUDManager.displayFadeMessage("MISS", (int) currentEnemy.x, (int) (currentEnemy.y - height * 0.15), 30, 18, Color.RED);
                        }
                        break;
                    // Check if the enemy is dead
                    case 85:
                        if (currentEnemy.isDead()){
                            currentEnemy.fadeOut(50);
                        }else{
                            currentEnemy.setState(EAState.IDLE);
                            battleState = BattleState.ENEMY_ATTACK;
                            tick = 0;
                        }
                        break;
                    // If the enemy is dead, proceed to reward state
                    case 125:
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
                        Sound.playSound(SoundEffect.ARMOR);
                        // Armor amount (25-33%), attack chance amount (7-10% of real atk)
                        int armorAmount = RNG.number(Player.getMaxArmor() / 4, Player.getMaxArmor() / 3);
                        int atkChanceAmount = RNG.number(Player.getRealATKChance() / 15, Player.getRealATKChance() / 10);
                        AnimatedTextManager.clear();
                        HUDManager.displayFadeMessage("+ " + armorAmount + " AMR", width / 2, (int) (height * 0.72), 30, 14, Color.CYAN);
                        HUDManager.displayFadeMessage("+ " + atkChanceAmount + "% ATK chance until next attack", width / 2, (int) (height * 0.8), 30, 14, Color.rgb(255, 80, 0));
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
                            HUDManager.displayFadeMessage("Hit for " + currentEnemy.getAtk(), width / 2, (int) (height * 0.75), 30, 15, Color.RED);
                            Player.damage(currentEnemy.getAtk());
                        }else{
                            Sound.playSound(SoundEffect.MISS);
                            HUDManager.displayFadeMessage("Dodged!", width / 2, (int) (height * 0.75), 30, 15, Color.GREEN);
                            SEManager.playEffect(SEManager.Effect.GREEN_FLASH);
                        }
                        break;
                    // Stop attack state
                    case 90:
                        currentEnemy.setState(EAState.IDLE);
                        break;
                    // Check if player is dead, if not, go to player's turn
                    case 100:
                        // For now, if the player is dead, return them back to the title screen and delete their save
                        if (Player.isDead()){
                            ArrayList<String> erase = new ArrayList<>();
                            erase.add("available: false");
                            erase.add("highStage: " + GameManager.getHighStage());
                            CoreView.save(CoreManager.context, erase);
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

    /**
     * Initiates a new battle with an {@link Entity}
     * @param enemy The {@link Entity} to fight
     */
    public static void startBattle(Entity enemy){
        // Start new battle with given enemy
        currentEnemy = enemy;
        battleState = BattleState.PLAYER_TURN;
        tick = 0;
        currentEnemy.fadeIn(50);
    }

    /**
     * Resumes a battle with an {@link Entity}
     * @param enemy The {@link Entity} to fight
     */
    public static void resumeBattle(Entity enemy){
        // Resume battle with given enemy (no fade in)
        currentEnemy = enemy;
        currentEnemy.spawn();
        battleState = BattleState.PLAYER_TURN;
        tick = 0;
    }

    /**
     * Uses the Player's spell.
     */
    public static void playerSpell(){
        // Use spell if it's player turn
        // Play spell sound
        if (battleState.equals(BattleState.PLAYER_TURN)){
            Player.getCurrentSpell().use();
        }
    }

    /**
     * Makes the Player attack.
     */
    public static void playerAttack(){
        // Spawn slash animation and go to attack state if it's player turn
        if (battleState.equals(BattleState.PLAYER_TURN)){
            battleState = BattleState.PLAYER_ATTACK;
        }
    }

    /**
     * Makes the Player defend.
     */
    public static void playerDefend(){
        // Go to defend state if it's player turn
        if (battleState.equals(BattleState.PLAYER_TURN)){
            // Play defend sound in the future
            battleState = BattleState.PLAYER_DEFEND;
        }
    }

    /**
     * Enters the Player's {@link com.redside.rngquest.gameobjects.Inventory}
     */
    public static void playerInventory(){
        // Save the current enemy and go to inventory screen if it's player turn
        if (battleState.equals(BattleState.PLAYER_TURN)){
            Sound.playSound(SoundEffect.SELECT);
            savedEnemy = currentEnemy;
            SEManager.playEffect(SEManager.Effect.FADE_TRANSITION, ScreenState.INVENTORY);
        }
    }

    /**
     * Sets the new {@link BattleState}
     * @param newState The new {@link BattleState}
     */
    public static void setBattleState(BattleState newState){
        battleState = newState;
    }

    /**
     * Safely closes the BattleManager (until a new battle is initiated)
     */
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

    /**
     * Damages the current enemy.
     * @param amount The amount of damage to inflict
     */
    private static void damageEnemy(int amount){
        // Speed for the flying text
        double speed = HUDManager.getSpeed(CoreManager.width, 274);
        // Determine if it should fly left or right
        if (RNG.yesNo()){
            HUDManager.displayParabolicText("-" + amount, (int) currentEnemy.x, (int) (currentEnemy.y - height * 0.1), 90, 16, Color.RED, -speed);
        }else{
            HUDManager.displayParabolicText("-" + amount, (int) currentEnemy.x, (int) (currentEnemy.y - height * 0.1), 90, 16, Color.RED, speed);
        }
        // Flash, shake, damage, and set state to damaged
        SEManager.playEffect(SEManager.Effect.YELLOW_FLASH);
        currentEnemy.shake(50);
        currentEnemy.damage(amount);
        currentEnemy.setState(EAState.DAMAGE);
    }

    /**
     * Returns the current enemy.
     * @return The enemy as an {@link Entity}
     */
    public static Entity getCurrentEnemy(){
        return currentEnemy;
    }
    public enum BattleState{
        BATTLE_START, PLAYER_TURN, PLAYER_ATTACK, PLAYER_FIREBALL, PLAYER_AGILITY, PLAYER_DEFEND, ENEMY_ATTACK, REWARD, NONE
    }
}
