package com.redside.rngquest.entities;


import com.redside.rngquest.gameobjects.CoreView;
import com.redside.rngquest.gameobjects.Inventory;
import com.redside.rngquest.gameobjects.Item;
import com.redside.rngquest.items.FireballSpellItem;
import com.redside.rngquest.items.LargePotionItem;
import com.redside.rngquest.items.ManaPotionItem;
import com.redside.rngquest.items.SmallPotionItem;
import com.redside.rngquest.managers.BattleManager;
import com.redside.rngquest.managers.CoreManager;
import com.redside.rngquest.managers.GameManager;
import com.redside.rngquest.managers.HUDManager;

import java.util.ArrayList;

public class Player {
    private static int hp, maxHp, mana, maxMana, atk, atkChance, evade, armor, maxArmor, gold, atkChanceBonus = 0;
    private static Inventory inventory = new Inventory();
    private static Role role;
    private static Item currentSpell = null;
    private static boolean agility = false, lifesteal = false;
    private static double parabolicA = 0.02;
    private static double parabolicSpeed = HUDManager.getSpeed(HUDManager.width, 535);

    /**
     * Sets the Player's statistics, inventory items, and current spell from the save file.
     */
    public static void spawnFromSave(){
        atkChanceBonus = 0;
        inventory.clear();
        currentSpell = null;

        // Load all info from save file. Integer parse int should always work since there is no way the
        // user can edit the save file
        ArrayList<String> saveInfo = CoreView.getSave(CoreManager.context);
        for (String line : saveInfo){
            String property = line.split(": ")[0];
            String value = line.split(": ")[1];
            switch(property.toLowerCase()){
                case "stage":
                    GameManager.stage = Integer.parseInt(value);
                    break;
                case "hp":
                    hp = Integer.parseInt(value);
                    break;
                case "maxhp":
                    maxHp = Integer.parseInt(value);
                    break;
                case "mp":
                    mana = Integer.parseInt(value);
                    break;
                case "maxmp":
                    maxMana = Integer.parseInt(value);
                    break;
                case "atk":
                    atk = Integer.parseInt(value);
                    break;
                case "atkchance":
                    atkChance = Integer.parseInt(value);
                    break;
                case "evade":
                    evade = Integer.parseInt(value);
                    break;
                case "armor":
                    armor = Integer.parseInt(value);
                    break;
                case "maxarmor":
                    maxArmor = Integer.parseInt(value);
                    break;
                case "gold":
                    gold = Integer.parseInt(value);
                    break;
                case "role":
                    switch(value.toLowerCase()){
                        case "mage":
                            role = Role.MAGE;
                            break;
                        case "warrior":
                            role = Role.WARRIOR;
                            break;
                        case "tank":
                            role = Role.TANK;
                            break;
                    }
                    break;
                case "items":
                    String[] items = value.split(",");
                    for (String item : items){
                        if (!item.isEmpty()){
                            int id = Integer.parseInt(item);
                            inventory.addItem(Item.getItemById(id));
                        }
                    }
                    break;
                case "currentspell":
                    int id = Integer.parseInt(value);
                    currentSpell = Item.getItemById(id);
                    break;
            }
        }
        System.out.println("Loaded stats and inventory from save data!");
    }

    /**
     * Sets the Player's statistics, inventory items, and current spell according to the choice given.
     * @param choice The role: 1 = Mage, 2 = Warrior, 3 = Tank
     */
    public static void spawn(int choice){
        atkChanceBonus = 0;
        gold = 150;
        inventory.clear();
        currentSpell = null;
        GameManager.reset();
        // reset stats and stuff
        // 1 is Mage, 2 is Warrior, 3 is Tank
        switch(choice){
            case 1:
                role = Role.MAGE;
                hp = 20;
                maxHp = 20;
                atk = 12;
                atkChance = 30;
                evade = 65;
                armor = 0;
                maxArmor = 0;
                mana = 60;
                maxMana = 60;
                FireballSpellItem fireball = new FireballSpellItem();
                inventory.addItem(fireball);
                currentSpell = fireball;
                inventory.addItem(new ManaPotionItem());
                break;
            case 2:
                role = Role.WARRIOR;
                hp = 50;
                maxHp = 50;
                atk = 12;
                atkChance = 60;
                evade = 35;
                armor = 15;
                maxArmor = 15;
                mana = 10;
                maxMana = 10;
                inventory.addItem(new SmallPotionItem());
                break;
            case 3:
                role = Role.TANK;
                hp = 90;
                maxHp = 90;
                atk = 7;
                atkChance = 40;
                evade = 20;
                armor = 40;
                maxArmor = 40;
                mana = 0;
                maxMana = 0;
                inventory.addItem(new LargePotionItem());
                break;
        }

    }

    /**
     * Toggles the agility ability, which causes the Player to switch to defend state after attack state.
     */
    public static void toggleAgility(){
        agility = !agility;
    }

    /**
     * Toggles the lifesteal ability, which causes the Player to heal on their next hit.
     */
    public static void toggleLifesteal(){
        lifesteal = !lifesteal;
    }

    /**
     * Checks if the Player has agility enabled.
     * @return {@code true} if agility is enabled
     */
    public static boolean hasAgility(){
        return agility;
    }

    /**
     * Checks if the Player has lifesteal enabled.
     * @return {@code true} if lifesteal is enabled
     */
    public static boolean hasLifesteal(){
        return lifesteal;
    }

    /**
     * Returns the Player's {@link Inventory}.
     * @return The {@link Inventory}
     */
    public static Inventory getInventory(){
        return inventory;
    }

    /**
     * Sets the Player's current spell.
     * @param item The spell item
     */
    public static void setCurrentSpell(Item item){
        currentSpell = item;
    }

    /**
     * Returns the Player's current spell. Returns null if the Player does not have one.
     * @return The spell item
     */
    public static Item getCurrentSpell(){
        return currentSpell;
    }

    /**
     * Checks if the Player's inventory is full. It should hold up to four items.
     * @return {@code true} if the Player's inventory is full
     */
    public static boolean inventoryIsFull(){
        return (inventory.getItems().size() >= 4);
    }

    /**
     * Grants the player a temporary attack chance bonus, which is reset after attacking.
     * @param bonus The percentage amount to add
     */
    public static void addAtkChanceBonus(int bonus){
        atkChanceBonus += bonus;
        if (BattleManager.getCurrentEnemy() != null){
            HUDManager.displayParabolicText("+" + bonus + "%", HUDManager.hudEndXPositions[3], HUDManager.hudEndYPositions[3], 130, 12, HUDManager.colors[3], parabolicSpeed, parabolicA);
        }
    }

    /**
     * Resets the Player's attack chance bonus.
     */
    public static void resetAtkChanceBonus(){
        atkChanceBonus = 0;
    }

    /**
     * Returns the Player's current MP.
     * @return The Player's MP
     */
    public static int getMana(){
        return mana;
    }

    /**
     * Returns the Player's max MP.
     * @return The Player's max MP
     */
    public static int getMaxMana(){
        return maxMana;
    }

    /**
     * Gives the Player MP.
     * @param amount The amount of MP to give
     */
    public static void addMana(int amount){
        if (mana + amount <= maxMana){
            mana += amount;
            if (BattleManager.getCurrentEnemy() != null){
                HUDManager.displayParabolicText("+" + amount, HUDManager.hudEndXPositions[1], HUDManager.hudEndYPositions[1], 130, 12, HUDManager.colors[1], parabolicSpeed, parabolicA);
            }
        }else{
            mana = maxMana;
        }
    }

    /**
     * Increases the Player's max MP.
     * @param amount The amount of MP to increase
     */
    public static void increaseMaxMana(int amount){
        maxMana += amount;
    }

    /**
     * Removes an amount of the Player's MP.
     * @param amount The amount of MP to remove
     */
    public static void removeMana(int amount){
        if (mana - amount >= 0){
            mana -= amount;
            if (BattleManager.getCurrentEnemy() != null){
                HUDManager.displayParabolicText("-" + amount, HUDManager.hudEndXPositions[1], HUDManager.hudEndYPositions[1], 130, 12, HUDManager.colors[1], parabolicSpeed, parabolicA);
            }
        }else{
            mana = 0;
        }
    }

    /**
     * Checks if the Player has enough MP.
     * @param amount The amount of MP to check
     * @return {@code true} if the Player has more than or equal to the amount given
     */
    public static boolean hasEnoughMana(int amount){
        return mana >= amount;
    }

    /**
     * Increases the Player's gold, up to 9999.
     * @param amount The amount of gold to give
     */
    public static void addGold(int amount){
        if (gold + amount < 9999){
            gold += amount;
        }else{
            gold = 9999;
        }
    }

    /**
     * Removes an amount of the Player's gold.
     * @param amount The amount of gold to remove
     */
    public static void removeGold(int amount){
        if (gold - amount >= 0){
            gold -= amount;
        }else{
            gold = 0;
        }
    }

    /**
     * Checks if the Player has an amount of gold.
     * @param amount The amount of gold to check
     * @return {@code true} if the Player has enough gold
     */
    public static boolean hasEnoughGold(int amount){
        return (gold >= amount);
    }

    /**
     * Damages the Player, causing the Player to lose HP.
     * @param amount The amount of HP to remove
     */
    public static void damage(int amount){
        if (armor > 0){
            if (armor - amount >= 0){
                armor -= amount;
                if (BattleManager.getCurrentEnemy() != null){
                    HUDManager.displayParabolicText("-" + amount, HUDManager.hudEndXPositions[2], HUDManager.hudEndYPositions[2], 130, 12, HUDManager.colors[2], parabolicSpeed, parabolicA);
                }
                amount = 0;
            }else{
                amount -= armor;
                if (BattleManager.getCurrentEnemy() != null){
                    HUDManager.displayParabolicText("-" + armor, HUDManager.hudEndXPositions[2], HUDManager.hudEndYPositions[2], 130, 12, HUDManager.colors[2], parabolicSpeed, parabolicA);
                }
                armor = 0;
            }
        }
        if (hp - amount >= 0){
            hp -= amount;
        }else{
            hp = 0;
        }
        if (BattleManager.getCurrentEnemy() != null && amount > 0){
            HUDManager.displayParabolicText("-" + amount, HUDManager.hudEndXPositions[0], HUDManager.hudEndYPositions[0], 130, 12, HUDManager.colors[0], parabolicSpeed, parabolicA);
        }
    }

    /**
     * Heals the Player, causing the Player to gain HP.
     * @param amount The amount of HP to restore
     */
    public static void heal(int amount){
        if (hp + amount <= maxHp){
            hp += amount;
        }else{
            hp = maxHp;
        }
        if (BattleManager.getCurrentEnemy() != null){
            HUDManager.displayParabolicText("+" + amount, HUDManager.hudEndXPositions[0], HUDManager.hudEndYPositions[0], 130, 12, HUDManager.colors[0], parabolicSpeed, parabolicA);
        }
    }

    /**
     * Gives the Player armor.
     * @param amount The amount of armor to add.
     */
    public static void addArmor(int amount){
        if (armor + amount <= maxArmor){
            armor += amount;
        }else{
            armor = maxArmor;
        }
        if (BattleManager.getCurrentEnemy() != null && amount > 0){
            HUDManager.displayParabolicText("+" + amount, HUDManager.hudEndXPositions[2], HUDManager.hudEndYPositions[2], 130, 12, HUDManager.colors[2], parabolicSpeed, parabolicA);
        }
    }

    /**
     * Increases the Player's max HP.
     * @param amount The amount of HP to increase
     */
    public static void increaseMaxHealth(int amount){
        maxHp += amount;
        if (BattleManager.getCurrentEnemy() != null){
            HUDManager.displayParabolicText("+" + amount, HUDManager.hudEndXPositions[0], HUDManager.hudEndYPositions[0], 130, 12, HUDManager.colors[0], parabolicSpeed, parabolicA);
        }
    }

    /**
     * Increases the Player's max armor.
     * @param amount The amount of armor to increase
     */
    public static void increaseMaxArmor(int amount){
        maxArmor += amount;
        if (BattleManager.getCurrentEnemy() != null){
            HUDManager.displayParabolicText("+" + amount, HUDManager.hudEndXPositions[2], HUDManager.hudEndYPositions[2], 130, 12, HUDManager.colors[2], parabolicSpeed, parabolicA);
        }
    }

    /**
     * Returns the Player's current HP.
     * @return The Player's HP
     */
    public static int getHP(){
        return hp;
    }

    /**
     * Returns the Player's max HP.
     * @return The Player's max HP
     */
    public static int getMaxHP(){
        return maxHp;
    }

    /**
     * Returns the Player's attack value.
     * @return The Player's attack value
     */
    public static int getATK(){
        return atk;
    }

    /**
     * Returns the Player's total attack chance in percentage (including bonus)
     * @return The Player's total attack chance
     */
    public static int getATKChance(){
        return atkChance + atkChanceBonus;
    }

    /**
     * Returns the Player's base attack chance in percentage
     * @return The Player's base attack chance
     */
    public static int getRealATKChance(){
        return atkChance;
    }

    /**
     * Increases the Player's attack value, up to 9999.
     * @param amount The amount of attack to increase
     */
    public static void addATK(int amount){
        if (atk + amount < 9999){
            atk += amount;
        }else{
            atk = 9999;
        }
        if (BattleManager.getCurrentEnemy() != null){
            HUDManager.displayParabolicText("+" + amount, HUDManager.hudEndXPositions[3], HUDManager.hudEndYPositions[3], 130, 12, HUDManager.colors[3], parabolicSpeed, parabolicA);
        }
    }

    /**
     * Checks if the Player has a spell in their {@link Inventory}.
     * @return {@code true} if the Player has a spell in their {@link Inventory}
     */
    public static boolean hasSpell(){
        for (Item item : inventory.getItems()){
            if (Item.isSpell(item)){
                return true;
            }
        }
        return false;
    }

    /**
     * Increases the Player's base attack chance, up to 80.
     * @param amount
     */
    public static void addATKChance(int amount){
        if (atkChance + amount < 80){
            atkChance += amount;
        }else{
            atkChance = 80;
        }
        if (BattleManager.getCurrentEnemy() != null){
            HUDManager.displayParabolicText("+" + amount + "%", HUDManager.hudEndXPositions[3], HUDManager.hudEndYPositions[3], 130, 12, HUDManager.colors[3], parabolicSpeed, parabolicA);
        }
    }

    /**
     * Returns the Player's evade chance.
     * @return The Player's evade chance
     */
    public static int getEvade(){
        return evade;
    }

    /**
     * Increases the Player's evade chance, up to 75.
     * @param amount The amount of evade to increase
     */
    public static void addEvade(int amount){
        if (evade + amount < 75){
            evade += amount;
        }else{
            evade = 75;
        }
    }

    /**
     * Returns the Player's current amount of armor.
     * @return The Player's current amount of armor
     */
    public static int getArmor(){
        return armor;
    }

    /**
     * Returns the Player's max armor.
     * @return The Player's max armor
     */
    public static int getMaxArmor(){
        return  maxArmor;
    }

    /**
     * Returns the Player's current gold.
     * @return The Player's current gold
     */
    public static int getGold(){
        return gold;
    }

    /**
     * Checks if the Player is dead.
     * @return {@code true} if the Player's current HP is 0
     */
    public static boolean isDead(){
        return (hp == 0);
    }

    /**
     * Returns the Player's role.
     * @return The Player's role
     */
    public static Role getRole(){
        return role;
    }
    public enum Role{
        MAGE, WARRIOR, TANK, ALL
    }


}
