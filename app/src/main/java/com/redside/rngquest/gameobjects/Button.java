package com.redside.rngquest.gameobjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.redside.rngquest.managers.ButtonManager;

public class Button{
    private Bitmap image;
    private int x;
    private int y;
    private String text;
    private int textSize;
    public Button(Bitmap image, int x, int y){
        this.image = image;
        this.x = x - (getBitmap().getWidth() / 2);
        this.y = y - (getBitmap().getHeight() / 2);
    }
    public void tick(){}
    public void render(Canvas canvas, Paint paint) {
        if (image != null){
            canvas.drawBitmap(image, x, y, paint);
        }
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public void trigger(){}
    public Bitmap getBitmap(){
        return image;
    }
    public void create(){
        ButtonManager.addButton(this);
    }
    public void destroy(){
        ButtonManager.removeButton(this);
    }
}
