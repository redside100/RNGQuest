package com.redside.rngquest.entities;

import com.redside.rngquest.gameobjects.Inventory;
import com.redside.rngquest.gameobjects.Item;
import com.redside.rngquest.items.FireballSpellItem;
import com.redside.rngquest.items.LargePotionItem;
import com.redside.rngquest.items.ManaPotionItem;
import com.redside.rngquest.items.SmallPotionItem;

public class Player {
    private static int hp, maxHp, mana, maxMana, atk, atkChance, evade, armor, maxArmor, gold, atkChanceBonus = 0;
    private static Inventory inventory = new Inventory();
    private static Role role;
    private static Item currentSpell = null;
    public static void spawn(int choice){
        atkChanceBonus = 0;
        gold = 100;
        inventory.clear();
        currentSpell = null;
        // reset stats and stuff
        // 1 is Mage, 2 is Warrior, 3 is Tank
        switch(choice){
            case 1:
                role = Role.MAGE;
                hp = 20;
                maxHp = 20;
                atk = 12;
                atkChance = 25;
                evade = 65;
                armor = 0;
                maxArmor = 0;
                mana = 50;
                maxMana = 50;
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
                atkChance = 55;
                evade = 35;
                armor = 10;
                maxArmor = 10;
                mana = 0;
                maxMana = 0;
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
    public static Inventory getInventory(){
        return inventory;
    }
    public static void setCurrentSpell(Item item){
        currentSpell = item;
    }
    public static Item getCurrentSpell(){
        return currentSpell;
    }
    public static boolean inventoryIsFull(){
        return (inventory.getItems().size() >= 4);
    }
    public static void addAtkChanceBonus(int bonus){
        atkChanceBonus += bonus;
    }
    public static void resetAtkChanceBonus(){
        atkChanceBonus = 0;
    }
    public static int getMana(){
        return mana;
    }
    public static int getMaxMana(){
        return maxMana;
    }
    public static void addMana(int amount){
        if (mana + amount <= maxMana){
            mana += amount;
        }else{
            mana = maxMana;
        }
    }
    public static void increaseMaxMana(int amount){
        maxMana += amount;
    }
    public static void removeMana(int amount){
        if (mana - amount >= 0){
            mana -= amount;
        }else{
            mana = 0;
        }
    }
    public static boolean hasEnoughMana(int amount){
        return mana >= amount;
    }
    public static void addGold(int amount){
        if (gold + amount < 9999){
            gold += amount;
        }else{
            gold = 9999;
        }
    }
    public static void removeGold(int amount){
        if (gold - amount >= 0){
            gold -= amount;
        }else{
            gold = 0;
        }
    }
    public static boolean hasEnoughGold(int amount){
        return (gold >= amount);
    }
    public static void damage(int amount){
        if (armor > 0){
            if (armor - amount >= 0){
                armor -= amount;
                amount = 0;
            }else{
                amount -= armor;
                armor = 0;
            }
        }
        if (hp - amount >= 0){
            hp -= amount;
        }else{
            hp = 0;
        }
    }
    public static void heal(int amount){
        if (hp + amount <= maxHp){
            hp += amount;
        }else{
            hp = maxHp;
        }
    }
    public static void addArmor(int amount){
        if (armor + amount <= maxArmor){
            armor += amount;
        }else{
            armor = maxArmor;
        }
    }
    public static void increaseMaxHealth(int amount){
        maxHp += amount;
    }
    public static void increaseMaxArmor(int amount){
        maxArmor += amount;
    }
    public static int getHP(){
        return hp;
    }
    public static int getMaxHP(){
        return maxHp;
    }
    public static int getATK(){
        return atk;
    }
    public static int getATKChance(){
        return atkChance + atkChanceBonus;
    }
    public static int getRealATKChance(){
        return atkChance;
    }
    public static void addATK(int amount){
        if (atk + amount < 9999){
            atk += amount;
        }else{
            atk = 9999;
        }
    }
    public static boolean hasSpell(){
        for (Item item : inventory.getItems()){
            if (Item.isSpell(item)){
                return true;
            }
        }
        return false;
    }
    public static void addATKChance(int amount){
        if (atkChance + amount < 100){
            atkChance += amount;
        }else{
            atkChance = 100;
        }
    }
    public static int getEvade(){
        return evade;
    }
    public static void addEvade(int amount){
        if (evade + amount < 80){
            evade += amount;
        }else{
            evade = 80;
        }
    }
    public static int getArmor(){
        return armor;
    }
    public static int getMaxArmor(){
        return  maxArmor;
    }
    public static int getGold(){
        return gold;
    }
    public static boolean isDead(){
        return (hp == 0);
    }
    public static Role getRole(){
        return role;
    }
    public enum Role{
        MAGE, WARRIOR, TANK, ALL
    }


}
