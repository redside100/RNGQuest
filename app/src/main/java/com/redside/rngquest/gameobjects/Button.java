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
        this.x = x;
        this.y = y;
        this.text = text;
        this.textSize = textSize;
    }
    public void tick(){}
    public void render(Canvas canvas, Paint paint) {
        canvas.drawBitmap(image, x, y, paint);
        int nx = (x + getBitmap().getWidth()) / 2;
        int ny = (y + getBitmap().getHeight()) / 2;
        drawCenteredText(text, canvas, nx, ny, paint);
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
