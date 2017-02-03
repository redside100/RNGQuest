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
    public Button(Bitmap image, int x, int y, String text, int textSize){
        this.image = image;
        this.x = x - (getBitmap().getWidth() / 2);
        this.y = y - (getBitmap().getHeight() / 2);
        this.text = text;
        this.textSize = textSize;
    }
    public void tick(){}
    public void render(Canvas canvas, Paint paint) {
        if (image != null){
            canvas.drawBitmap(image, x, y, paint);
            drawCenteredText(text, canvas, x, y, paint);
        }
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public void trigger(){};
    public Bitmap getBitmap(){
        return image;
    }
    public void drawCenteredText(String text, Canvas canvas, int x, int y, Paint paint){
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        x -= bounds.width() / 2;
        y -= bounds.height() / 2;
        canvas.drawText(text, x, y, paint);
    }
    public void create(){
        ButtonManager.addButton(this);
    }
    public void destroy(){
        ButtonManager.removeButton(this);
    }
}
