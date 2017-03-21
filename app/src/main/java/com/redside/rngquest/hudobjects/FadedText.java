package com.redside.rngquest.hudobjects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.redside.rngquest.managers.FadedTextManager;

public class FadedText {
    private int tick = 0;
    private int maxTick;
    private int time;
    private int x;
    private int y;
    private int currAlpha = 0;
    private String text;
    private int textSize;
    private int color;
    private boolean active = false;

    public FadedText(String text, int time, int x, int y, int textSize, int color){

        this.text = text;
        this.time = time;
        this.textSize = textSize;
        this.color = color;
        this.x = x;
        this.y = y;

        // +1 to allow 30 ticks of fade in, and 30 ticks of fade out
        maxTick = (time + 1) * 60;

    }

    public void tick(){
        if (active){
            if (tick >= 0 && tick <= 30){
                if (currAlpha + 8 > 255){
                    currAlpha = 255;
                }else{
                    currAlpha += 8;
                }
            }
            if (tick >= (maxTick - 30) && tick <= maxTick){
                if (currAlpha - 8 < 0){
                    currAlpha = 0;
                }else{
                    currAlpha -= 8;
                }
            }
            if (tick == maxTick){
                destroy();
            }
            tick++;
        }
    }

    public void render(Canvas canvas, Paint paint){
        drawCenteredText(text, canvas, x, y, paint, textSize, color);
    }

    public void play(){
        active = true;
        FadedTextManager.addText(this);
    }

    public void destroy(){
        active = false;
        FadedTextManager.removeText(this);
    }

    public boolean equals(Object obj){
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof FadedText)) return false;
        FadedText o = (FadedText) obj;
        if (o.x == this.x && o.y == this.y && o.text.equals(this.text) && o.color == this.color){
            return true;
        }
        return false;
    }

    public void drawCenteredText(String text, Canvas canvas, int x, int y, Paint paint, int textSize, int color){
        paint.setTextSize(textSize);
        paint.setColor(color);
        paint.setAlpha(currAlpha);
        Rect bounds = new Rect();
        // Get bounds of the text, then center
        paint.getTextBounds(text, 0, text.length(), bounds);
        x -= bounds.width() / 2;
        y -= bounds.height() / 2;
        float old = paint.getTextSize();
        canvas.drawText(text, x, y, paint);
        paint.setTextSize(old);
        paint.setColor(Color.WHITE);
    }
}
