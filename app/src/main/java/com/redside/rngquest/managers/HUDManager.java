package com.redside.rngquest.managers;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.redside.rngquest.buttons.MenuPlayButton;
import com.redside.rngquest.utils.Assets;

public class HUDManager {
    private static int width = 0;
    private static int height = 0;
    private static Bitmap play;
    private static ButtonManager buttonManager;
    public HUDManager(){
        this.width = CoreManager.width;
        this.height = CoreManager.height;
        buttonManager = new ButtonManager();
        play = Assets.getBitmapFromMemory("button_play");
        onStateChange(ScreenState.TITLE);
    }
    public void tick(){
        buttonManager.tick();
        width = CoreManager.width;
        height = CoreManager.height;
    }
    public void touchEvent(MotionEvent e){
        buttonManager.checkButtons(e);
    }
    public static void onStateChange(ScreenState newState){
        buttonManager.clearButtons();
        switch (newState){
            case TITLE:
                MenuPlayButton b = new MenuPlayButton(play, width / 2, height / 2, "", 0);
                b.create();
                break;
        }
    }
    public void render(Canvas canvas, Paint paint){
        buttonManager.render(canvas, paint);
        switch(CoreManager.state){
            case TITLE:
                drawCenteredText("RNG Quest", canvas, width / 2, (int) (height / 3.5), paint);
                break;
        }
    }
    public void drawCenteredText(String text, Canvas canvas, int x, int y, Paint paint){
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        x -= bounds.width() / 2;
        y -= bounds.height() / 2;
        canvas.drawText(text, x, y, paint);
    }
}
