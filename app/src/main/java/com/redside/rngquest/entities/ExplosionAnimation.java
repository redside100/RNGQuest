package com.redside.rngquest.entities;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.redside.rngquest.utils.Animator;
import com.redside.rngquest.utils.Assets;

import java.util.ArrayList;

public class ExplosionAnimation extends Entity{

    private Animator animator;

    public ExplosionAnimation(int x, int y){
        super("ExplosionAnimation", 0, 0, x, y, 255);

        ArrayList<Bitmap> frames = new ArrayList<>();
        // Add explode frames
        for (int i = 0; i < 7; i++){
            Bitmap frame = Assets.getBitmapFromMemory("sprites_explosion_" + i);
            frames.add(frame);
        }
        animator = new Animator(frames);
        animator.setSpeed(90);
        animator.play();
        animator.update(System.currentTimeMillis());

    }

    @Override
    public void render(Canvas canvas, Paint paint){
        drawCenteredBitmap(animator.sprite, canvas, paint, (int) x, (int) y);
        // Destroy this entity if the animation is done
        if (animator.isDoneAnimation()){
            super.destroy();
            animator.stop();
        }
        animator.update(System.currentTimeMillis());
    }

    public void drawCenteredBitmap(Bitmap bitmap, Canvas canvas, Paint paint, int x, int y){
        x -= (bitmap.getWidth() / 2);
        y -= (bitmap.getHeight() / 2);
        canvas.drawBitmap(bitmap, x, y, paint);
    }
}
