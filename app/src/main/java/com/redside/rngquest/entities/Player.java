package com.redside.rngquest.entities;

public class Player {
    private static int hp, maxHp, atk, atkChance, evade, armor, maxArmor, gold;
    private static boolean dead = false;
    public Player(){}
    public static void spawn(int choice){
        dead = false;
        // reset stats and stuff
        // 1 is Wizard, 2 is Warrior, 3 is Tank
        switch(choice){
            case 1:
                hp = 20;
                maxHp = 20;
                atk = 15;
                atkChance = 70;
                evade = 70;
                armor = 0;
                maxArmor = 0;
                break;
            case 2:
                hp = 50;
                maxHp = 50;
                atk = 12;
                atkChance = 50;
                evade = 30;
                armor = 5;
                maxArmor = 5;
                break;
            case 3:
                hp = 90;
                maxHp = 90;
                atk = 7;
                atkChance = 40;
                evade = 20;
                armor = 20;
                maxArmor = 20;
                break;
        }

    }
    public static void addGold(int amount){
        gold += amount;
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
            amount -= armor;
            if (armor - amount >= 0){
                armor -= amount;
            }else{
                armor = 0;
            }
        }
        if (hp - amount >= 0){
            hp -= amount;
        }else{
            hp = 0;
            dead = true;
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
        return atkChance;
    }
    public static int getEvade(){
        return evade;
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
        return dead;
    }


}
