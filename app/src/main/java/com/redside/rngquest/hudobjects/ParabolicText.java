package com.redside.rngquest.hudobjects;

import com.redside.rngquest.managers.HUDManager;

public class ParabolicText extends AnimatedText{
    private int ticks;
    private int tick = 0;
    private double directionVec;
    private int vertexX;
    private int vertexY;
    private double initialA;

    /**
     *
     * @param text The text to draw
     * @param ticks The amount of time to draw
     * @param x The x position of the text
     * @param y The y position of the text
     * @param textSize The size of the text
     * @param color The color of the text
     * @param directionVec The direction vector of the text (negative or positive)
     */
    public ParabolicText(String text, int ticks, int x, int y, int textSize, int color, double directionVec){
        super(text, x, y, textSize, color, 255, true);
        this.ticks = ticks;
        this.directionVec = directionVec;
        this.vertexX = x;
        this.vertexY = y;
        initialA = 0.004;
    }
    public ParabolicText(String text, int ticks, int x, int y, int textSize, int color, double directionVec, double initialA){
        super(text, x, y, textSize, color, 255, true);
        this.ticks = ticks;
        this.directionVec = directionVec;
        this.vertexX = x;
        this.vertexY = y;
        this.initialA = initialA;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void tick(){
        if (super.active){
            if (tick != ticks){
                // Increment x by the amount given (if positive, it travels right, if negative, it travels left)
                super.x += directionVec;
                // Use the equation y = -a(x - vertexX)^2 + vertexY
                // Since y gets larger as it travels down, the a value must be positive instead of negative
                // Scale a value with width
                double a = initialA * 1920 / HUDManager.width;
                super.y = (int) (a * Math.pow(x - this.vertexX, 2) + this.vertexY);
            }else{
                super.destroy();
            }
            tick++;
        }
    }

}
