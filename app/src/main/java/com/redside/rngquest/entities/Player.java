package com.redside.rngquest.entities;

public class Player {
    private static int hp, maxHp, atk, atkChance, evade, armor, maxArmor;
    public Player(){}
    public static void spawn(int choice){
        // reset stats and stuff
        // 0 is Wizard, 1 is Warrior, 2 is Tank
        switch(choice){

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


}
