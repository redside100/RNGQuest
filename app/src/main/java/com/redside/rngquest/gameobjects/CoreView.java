package com.redside.rngquest.gameobjects;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import com.redside.rngquest.managers.CoreManager;
import com.redside.rngquest.managers.Soundtrack;
import com.redside.rngquest.utils.Assets;

public class CoreView extends View {
    public Canvas canvas;
    Typeface font;
    private CoreManager manager;
    private Soundtrack soundtrack;
    private Assets assets;
    private Loop loop;
    private int width;
    private int height;
    public CoreView(Context context, int width, int height) {
        super(context);
        this.width = width;
        this.height = height;
        init();
    }
    private void init(){
        // Init assets, main manager, game loop, and set font
        assets = new Assets(getContext());
        manager = new CoreManager(width, height);
        loop = new Loop(this);
        font = Typeface.createFromAsset(getContext().getAssets(), "fonts/font.ttf");
    }
    @Override
    protected void onDraw(Canvas canvas) {
        // Where everything is drawn
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setTypeface(font);
        paint.setTextSize(150);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        canvas.drawPaint(paint);
        manager.render(canvas, paint);
    }
    @Override
    public boolean onTouchEvent(MotionEvent e){
        // Source of detecting touches. Only check for when the user lets go
        if (e.getAction() == MotionEvent.ACTION_UP){
            manager.touchEvent(e);
        }else if (e.getAction() == MotionEvent.ACTION_DOWN){
            manager.preTouchEvent(e);
        }
        return true;
    }
    public void render(){
        // Invalidate recalls onDraw()
        invalidate();
    }
    public void tick(){
        // Ticks the main manager.
        manager.tick();
    }
}
