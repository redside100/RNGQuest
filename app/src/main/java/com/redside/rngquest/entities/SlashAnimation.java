package com.redside.rngquest.entities;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.redside.rngquest.managers.EntityManager;
import com.redside.rngquest.utils.Animator;
import com.redside.rngquest.utils.Assets;

import java.util.ArrayList;

public class SlashAnimation extends Entity{

    private boolean alive;
    private Animator animator;

    public SlashAnimation(int x, int y){
        super("SlashAnimation", 0, 0, x, y, 255);
        super.i = this;
        ArrayList<Bitmap> frames = new ArrayList<>();
        for (int i = 0; i < 8; i++){
            Bitmap frame = Assets.getBitmapFromMemory("sprites_slash_" + i);
            frames.add(frame);
        }
        animator = new Animator(frames);
        animator.setSpeed(90);
        animator.play();
        animator.update(System.currentTimeMillis());
        alive = true;
        EntityManager.addEntity(this);
    }

    @Override
    public void tick(){
    }

    @Override
    public void render(Canvas canvas, Paint paint){
        if (alive){
            drawCenteredBitmap(animator.sprite, canvas, paint, x, y);
            if (animator.isDoneAnimation()){
                destroy();
                animator.stop();
            }
            animator.update(System.currentTimeMillis());
        }
    }

    public void destroy(){
        alive = false;
        EntityManager.removeEntity(this);
    }

    public void drawCenteredBitmap(Bitmap bitmap, Canvas canvas, Paint paint, int x, int y){
        x -= (bitmap.getWidth() / 2);
        y -= (bitmap.getHeight() / 2);
        canvas.drawBitmap(bitmap, x, y, paint);
    }
}
