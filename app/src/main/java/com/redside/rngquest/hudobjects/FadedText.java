package com.redside.rngquest.hudobjects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.redside.rngquest.managers.AnimatedTextManager;

public class FadedText extends AnimatedText{
    private int tick = 0;
    private int maxTick;
    private int time;

    public FadedText(String text, int ticks, int x, int y, int textSize, int color){

        super(text, x, y, textSize, color, 0);
        // Allow 30 ticks of fade in, and 30 ticks of fade out
        maxTick = ticks + 60;

    }

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
