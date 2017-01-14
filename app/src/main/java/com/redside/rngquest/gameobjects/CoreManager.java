package com.redside.rngquest.gameobjects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class CoreManager{
    public int width = 0;
    public int height = 0;
    public CoreManager(){

    }
    public void tick(){

    }
    public void render(Canvas canvas, Paint paint){
        int radius;
        radius = 100;
        paint.setColor(Color.parseColor("#000000"));
        canvas.drawCircle(width / 2, height / 2, radius, paint);
        canvas.drawText("New font WHOAAA", width / 2 - 600, height / 2 + 250, paint);
    }
}
