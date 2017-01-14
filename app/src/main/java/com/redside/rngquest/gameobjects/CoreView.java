package com.redside.rngquest.gameobjects;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.View;

public class CoreView extends View {
    public Canvas canvas;
    Typeface font;
    public int width = getWidth(), height = getHeight();
    private CoreManager manager;
    private Loop loop;
    public CoreView(Context context) {
        super(context);
        init();
    }
    private void init(){
        manager = new CoreManager();
        loop = new Loop(this);
        font = Typeface.createFromAsset(getContext().getAssets(), "fonts/font.ttf");
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setTypeface(font);
        paint.setTextSize(150);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        manager.width = getWidth();
        manager.height = getHeight();
        canvas.drawPaint(paint);
        manager.render(canvas, paint);
    }
    public void render(){
        invalidate();
    }
    public void tick(){
        manager.tick();
    }
}
