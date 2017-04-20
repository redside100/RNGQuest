package com.redside.rngquest.entities;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.redside.rngquest.utils.Animator;
import com.redside.rngquest.utils.Assets;

import java.util.ArrayList;

public class Wizard extends Entity{

    private boolean shaking = false;
    private int tick = 0;
    private Animator animator;

    private ArrayList<Bitmap> idleFrames = new ArrayList<>();
    private ArrayList<Bitmap> attackFrames = new ArrayList<>();
    private ArrayList<Bitmap> damageFrame = new ArrayList<>();

    public Wizard(int hp, int atk, int x, int y, int startingAlpha){

        super("Wizard", hp, atk, x, y, startingAlpha);

        for (int i = 0; i < 10; i++){
            idleFrames.add(Assets.getBitmapFromMemory("sprites_wizard_idle_" + i));
        }
        for (int i = 9; i >= 0; i--){
            idleFrames.add(Assets.getBitmapFromMemory("sprites_wizard_idle_" + i));
        }

        for (int i = 0; i < 10; i++){
            attackFrames.add(Assets.getBitmapFromMemory("sprites_wizard_attack_" + i));
        }

        damageFrame.add(Assets.getBitmapFromMemory("sprites_wizard_damage"));

        animator = new Animator(idleFrames);
        animator.setSpeed(120);
        animator.play();
        animator.update(System.currentTimeMillis());
    }

    @Override
    public void setState(EAState newState){
        super.state = newState;
        switch(newState){
            case IDLE:
                animator.replace(idleFrames);
                break;
            case ATTACK:
                animator.replace(attackFrames);
                break;
            case DAMAGE:
                animator.replace(damageFrame);
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
        drawCenteredBitmap(animator.sprite, canvas, paint, super.x, super.y);
        animator.update(System.currentTimeMillis());
        paint.setAlpha(oldAlpha);
    }

    public void drawCenteredBitmap(Bitmap bitmap, Canvas canvas, Paint paint, int x, int y){
        x -= (bitmap.getWidth() / 2);
        y -= (bitmap.getHeight() / 2);
        canvas.drawBitmap(bitmap, x, y, paint);
    }
}
