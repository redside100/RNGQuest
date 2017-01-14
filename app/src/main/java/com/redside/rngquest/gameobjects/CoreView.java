package com.redside.rngquest.gameobjects;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class CoreView extends View {
    public Canvas canvas;
    public int width = getWidth(), height = getHeight();
    private CoreManager manager;
    private Loop loop;
    public CoreView(Context context) {
        super(context);
        init();
    }
    private void init(){
        loop = new Loop(this);
        manager = new CoreManager();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setTextSize(150);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        canvas.drawPaint(paint);
        manager.render(canvas, paint);
        manager.width = getWidth();
        manager.height = getHeight();
    }
    public void render(){
        invalidate();
    }
    public void tick(){
        manager.tick();
    }
}
