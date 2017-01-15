package com.redside.rngquest.managers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.redside.rngquest.utils.Assets;


public class Background {
    private Bitmap background;
    public Background(){
        background = Assets.getBitmapFromMemory("background_title");
    }
    public void tick(){
        switch(CoreManager.state){
            case TITLE:
                background = Assets.getBitmapFromMemory("background_title");
                break;
        }
    }
    public void render(Canvas canvas, Paint paint){
        if (background != null){
            canvas.drawBitmap(background, 0, 0, paint);
        }
    }
}
