package com.redside.rngquest.utils;


import java.util.Random;

public class RNG {
    public static Random random = new Random();
    public static boolean pass(int chance){
        return (random.nextInt(100) + 1 < chance);
    }
}
