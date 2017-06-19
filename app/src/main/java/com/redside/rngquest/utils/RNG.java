package com.redside.rngquest.utils;


import java.util.Random;

/**
 * A random number generator.
 * @author Andrew Peng
 * @since July 19, 2017
 */
public class RNG {
    public static Random random = new Random();

    /**
     * Checks if the random number generator is within a given percentage.
     * @param chance The percentage chance
     * @return {@code true} if within the chance
     */
    public static boolean pass(int chance){
        return (random.nextInt(100) + 1 <= chance);
    }

    /**
     * Returns a random integer within the given range.
     * @param min The minimum number
     * @param max The maximum number
     * @return A random integer within the range
     */
    public static int number(int min, int max){
        return random.nextInt(max - min + 1) + min;
    }

    /**
     * Returns a random boolean.
     * @return A random boolean
     */
    public static boolean yesNo(){
        return random.nextBoolean();
    }
}
