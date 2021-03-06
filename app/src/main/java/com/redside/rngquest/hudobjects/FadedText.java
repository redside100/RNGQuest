package com.redside.rngquest.hudobjects;

public class FadedText extends AnimatedText{
    private int tick = 0;
    private int maxTick;
    private int time;

    /**
     *
     * @param text The text to draw
     * @param ticks The amount of time to draw
     * @param x The x position of the text
     * @param y The y position of the text
     * @param textSize The size of the text
     * @param color The color of the text
     */
    public FadedText(String text, int ticks, int x, int y, int textSize, int color){

        super(text, x, y, textSize, color, 0, true);
        // Allow 30 ticks of fade in, and 30 ticks of fade out
        maxTick = ticks + 60;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void tick(){
        if (super.active){
            // Fading in for 30 ticks
            if (tick >= 0 && tick <= 30){
                // Increase alpha by 8 until 255
                if (super.currAlpha + 8 > 255){
                    super.currAlpha = 255;
                }else{
                    super.currAlpha += 8;
                }
            }
            // Fading out for 30 ticks
            if (tick >= (maxTick - 30) && tick <= maxTick){
                // Decrease alpha by 8 until 0
                if (super.currAlpha - 8 < 0){
                    super.currAlpha = 0;
                }else{
                    super.currAlpha -= 8;
                }
            }
            // End
            if (tick == maxTick){
                super.destroy();
            }
            tick++;
        }
    }
}
