package com.redside.rngquest.hudobjects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.redside.rngquest.gameobjects.CoreView;
import com.redside.rngquest.managers.AnimatedTextManager;

public class AnimatedText {
    public double x, y;
    public String text;
    public int textSize;
    public int color;
    public boolean active = false;
    public int currAlpha;

    public AnimatedText(String text, int x, int y, int textSize, int color, int startingAlpha){
        this.text = text;
        this.textSize = textSize;
        this.color = color;
        this.x = x;
        this.y = y;
        currAlpha = startingAlpha;
    }

    // To be overridden
    public void tick(){

    }

    public void render(Canvas canvas, Paint paint){
        drawCenteredText(text, canvas, (int) x, (int) y, paint, textSize, color);
    }

    // Must be called to start the animation
    public void play(){
        active = true;
        AnimatedTextManager.addText(this);
    }

    public void destroy(){
        active = false;
        AnimatedTextManager.removeText(this);
    }

    public boolean equals(Object obj){
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof AnimatedText)) return false;
        AnimatedText o = (AnimatedText) obj;
        if (o.x == this.x && o.y == this.y && o.text.equals(this.text) && o.color == this.color){
            return true;
        }
        return false;
    }

    public void drawCenteredText(String text, Canvas canvas, int x, int y, Paint paint, int textSize, int color){
        float old = paint.getTextSize();
        double relation = Math.sqrt(canvas.getWidth() * canvas.getHeight()) / 250;
        float scaledTextSize = (float) (textSize * relation);
        paint.setTextSize(scaledTextSize);
        paint.setColor(color);
        paint.setAlpha(currAlpha);
        Rect bounds = new Rect();
        // Get bounds of the text, then center
        paint.getTextBounds(text, 0, text.length(), bounds);
        x -= bounds.width() / 2;
        y -= bounds.height() / 2;
        canvas.drawText(text, x, y, paint);
        paint.setTextSize(old);
        paint.setColor(Color.WHITE);
    }
}
