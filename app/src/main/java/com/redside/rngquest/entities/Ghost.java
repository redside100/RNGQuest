package com.redside.rngquest.entities;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.redside.rngquest.managers.CoreManager;
import com.redside.rngquest.managers.HUDManager;
import com.redside.rngquest.utils.Assets;

import java.util.ArrayList;
public class Ghost extends Entity{

    private int tick = 0;

    private Bitmap currentSprite;
    private ArrayList<Bitmap> sprites = new ArrayList<>();

    /**
     *
     * @param hp The starting and max HP of the Ghost
     * @param atk The attack value of the Ghost
     * @param x The x position of the Ghost
     * @param y The y position of the Ghost
     * @param startingAlpha The starting opacity of the Ghost
     */
    public Ghost(int hp, int atk, int x, int y, int startingAlpha){

        super("Ghost", hp, atk, x, y, startingAlpha);

        // Add ghost sprites (idle, attack, damage)
        for (int i = 0; i < 3; i++){
            sprites.add(Assets.getBitmapFromMemory("sprites_ghost_" + i));
        }

        // 0 = Idle, 1 = Attack, 2 = Damage
        currentSprite = sprites.get(0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setState(EAState newState){
        super.state = newState;
        switch(newState){
            case IDLE:
                currentSprite = sprites.get(0);
                break;
            case ATTACK:
                currentSprite = sprites.get(1);
                break;
            case DAMAGE:
                currentSprite = sprites.get(2);
                break;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void tick(){
        super.tick();
        // Hover
        if (tick >= 0 && tick <= 30){
            super.y -= HUDManager.getSpeed(CoreManager.height, 1080);
        }
        else if (tick >= 31 && tick <= 60){
            super.y += HUDManager.getSpeed(CoreManager.height, 1080);
        }
        else if (tick == 61){
            tick = 0;
        }
        tick++;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render(Canvas canvas, Paint paint){
        int oldAlpha = paint.getAlpha();
        paint.setAlpha(super.currAlpha);
        drawCenteredBitmap(currentSprite, canvas, paint, (int) super.x, (int) super.y);
        paint.setAlpha(oldAlpha);
    }
    /**
     * Draws a bitmap, centered to the position given.
     *
     * @param bitmap The {@link Bitmap} image to be drawn
     * @param canvas The {@link Canvas} object to draw to
     * @param paint The {@link Paint} object to draw with
     * @param x The x position of the bitmap
     * @param y The y position of the bitmap
     */
    public void drawCenteredBitmap(Bitmap bitmap, Canvas canvas, Paint paint, int x, int y){
        x -= (bitmap.getWidth() / 2);
        y -= (bitmap.getHeight() / 2);
        canvas.drawBitmap(bitmap, x, y, paint);
    }
}
