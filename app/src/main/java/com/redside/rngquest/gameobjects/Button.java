package com.redside.rngquest.gameobjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Button{
    private Bitmap image;
    private int x;
    private int y;
    public Button(Bitmap image, int x, int y){
        this.image = image;
        this.x = x;
        this.y = y;
        init();
    }
    public void init(){

    }
    public void tick(){

    }
    public void render(Canvas canvas, Paint paint) {
        canvas.drawBitmap(image, x, y, paint);
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public void trigger(){

    }
    public Bitmap getBitmap(){
        return image;
    }
}
