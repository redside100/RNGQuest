package com.redside.rngquest.utils;


import java.util.Random;

public class RNG {
    public static Random random = new Random();
    public static boolean pass(int chance){
        return (random.nextInt(100) + 1 <= chance);
    }
    public static int number(int min, int max){
        return random.nextInt(max - min + 1) + min;
    }
    public static boolean yesNo(){
        return random.nextBoolean();
    }
}
