package com.redside.rngquest.hudobjects;

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
                super.x += directionVec;
                super.y = (int) (0.004 * Math.pow(x - this.vertexX, 2) + this.vertexY);
            }else{
                super.destroy();
            }
            tick++;
        }
    }

}
