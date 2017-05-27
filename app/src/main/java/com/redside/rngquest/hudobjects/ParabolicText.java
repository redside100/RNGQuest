package com.redside.rngquest.hudobjects;

import com.redside.rngquest.managers.HUDManager;

public class ParabolicText extends AnimatedText{
    private int ticks;
    private int tick = 0;
    private double directionVec;
    private int vertexX;
    private int vertexY;

    public ParabolicText(String text, int ticks, int x, int y, int textSize, int color, double directionVec){
        super(text, x, y, textSize, color, 255);
        this.ticks = ticks;
        this.directionVec = directionVec;
        this.vertexX = x;
        this.vertexY = y;
    }

    @Override
    public void tick(){
        if (super.active){
            if (tick != ticks){
                // Increment x by the amount given (if positive, it travels right, if negative, it travels left)
                super.x += directionVec;
                // Use the equation y = -0.004(x - vertexX)^2 + vertexY
                // Since y gets larger as it travels down, the a value must be positive instead of negative
                // Scale a value with width
                double a = 0.004 * 1920 / HUDManager.width;
                super.y = (int) (a * Math.pow(x - this.vertexX, 2) + this.vertexY);
            }else{
                super.destroy();
            }
            tick++;
        }
    }

}
