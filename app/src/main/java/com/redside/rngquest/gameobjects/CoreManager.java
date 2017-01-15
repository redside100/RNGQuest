package com.redside.rngquest.gameobjects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class CoreManager{
    public int width;
    public int height;
    private int tick = 0;
    private int xoff = 0;
    boolean left = true;
    public CoreManager(){
        init();
    }
    public void init(){}

    public void tick(){
        // Just testing a left and right moving animation for the text
        tick++;
        if (left){
            xoff += 1;
            if (tick == 20){
                tick = 0;
                left = false;
            }
        }else{
            xoff -= 1;
            if (tick == 20){
                tick = 0;
                left = true;
            }
        }
    }
    public void render(Canvas canvas, Paint paint){
        int radius;
        radius = 100;
        paint.setColor(Color.parseColor("#000000"));
        canvas.drawCircle(width / 2, height / 2, radius, paint);
        drawCenteredText("New font WHOAAA", canvas, width / 2 - xoff, height / 2 + 300, paint);
    }
    public void drawCenteredText(String text, Canvas canvas, int x, int y, Paint paint){
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        x -= bounds.width() / 2;
        y -= bounds.height() / 2;
        canvas.drawText(text, x, y, paint);
    }
}
