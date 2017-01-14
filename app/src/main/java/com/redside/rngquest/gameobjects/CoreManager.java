package com.redside.rngquest.gameobjects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class CoreManager{
    public int width;
    public int height;
    private int xoff = 530;
    private int yoff = 250;
    private int tick = 0;
    boolean left = true;
    public CoreManager(){
        init();
    }
    public void init(){}

    public void tick(){
        // Just testing a left and right moving animation for the text
        tick++;
        if (left){
            xoff += 3;
            if (tick == 50){
                tick = 0;
                left = false;
            }
        }else{
            xoff -= 3;
            if (tick == 50){
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
        canvas.drawText("New font WHOAAA", width / 2 - xoff, height / 2 + yoff, paint);
    }
}
