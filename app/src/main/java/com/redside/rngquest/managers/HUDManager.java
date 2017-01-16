package com.redside.rngquest.managers;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class HUDManager {
    private int width = 0;
    private int height = 0;
    public HUDManager(){
    }
    public void tick(){
        width = CoreManager.width;
        height = CoreManager.height;
    }
    public void render(Canvas canvas, Paint paint){
       drawCenteredText("RNG Quest", canvas, width / 2, (int) (height / 3.5), paint);
    }
    public void drawCenteredText(String text, Canvas canvas, int x, int y, Paint paint){
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        x -= bounds.width() / 2;
        y -= bounds.height() / 2;
        canvas.drawText(text, x, y, paint);
    }
}
