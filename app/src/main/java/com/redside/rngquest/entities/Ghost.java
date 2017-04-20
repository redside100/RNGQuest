package com.redside.rngquest.entities;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.redside.rngquest.managers.EntityManager;
import com.redside.rngquest.utils.Assets;

import java.util.ArrayList;
public class Ghost extends Entity{

    private boolean shaking = false;
    private int tick = 0;

    private Bitmap currentSprite;
    private ArrayList<Bitmap> sprites = new ArrayList<>();

    public Ghost(int hp, int atk, int x, int y, int startingAlpha){

        super("Ghost", hp, atk, x, y, startingAlpha);

        for (int i = 0; i < 3; i++){
            sprites.add(Assets.getBitmapFromMemory("sprites_ghost_" + i));
        }

        // 0 = Idle, 1 = Attack, 2 = Damage
        currentSprite = sprites.get(0);
    }

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
    @Override
    public void tick(){
        // Hover
        if (tick >= 0 && tick <= 30){
            super.y--;
        }
        else if (tick >= 31 && tick <= 60){
            super.y++;
        }
        else if (tick == 61){
            tick = 0;
        }
        tick++;
    }

    @Override
    public void render(Canvas canvas, Paint paint){
        int oldAlpha = paint.getAlpha();
        paint.setAlpha(super.currAlpha);
        drawCenteredBitmap(currentSprite, canvas, paint, super.x, super.y);
        paint.setAlpha(oldAlpha);
    }

    public void drawCenteredBitmap(Bitmap bitmap, Canvas canvas, Paint paint, int x, int y){
        x -= (bitmap.getWidth() / 2);
        y -= (bitmap.getHeight() / 2);
        canvas.drawBitmap(bitmap, x, y, paint);
    }
}
