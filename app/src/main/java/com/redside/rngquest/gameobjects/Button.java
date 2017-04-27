package com.redside.rngquest.gameobjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.redside.rngquest.managers.ButtonManager;

public abstract class Button{
    private Bitmap image;
    private int x;
    private int y;
    private String text;
    private int textSize;
    private int currAlpha = 255;
    public Button(Bitmap image, int x, int y){
        this.image = image;
        this.x = x - (getBitmap().getWidth() / 2);
        this.y = y - (getBitmap().getHeight() / 2);
        ButtonManager.addButton(this);
    }
    public void tick(){}
    public void render(Canvas canvas, Paint paint) {
        if (image != null){
            int oldAlpha = paint.getAlpha();
            paint.setAlpha(currAlpha);
            canvas.drawBitmap(image, x, y, paint);
            paint.setAlpha(oldAlpha);
        }
    }
    public void setAlpha(int alpha){
        currAlpha = alpha;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public abstract void trigger();
    public Bitmap getBitmap(){
        return image;
    }
    public void destroy(){
        ButtonManager.removeButton(this);
    }
}
