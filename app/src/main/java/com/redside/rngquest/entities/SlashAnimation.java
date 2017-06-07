package com.redside.rngquest.entities;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.redside.rngquest.managers.EntityManager;
import com.redside.rngquest.utils.Animator;
import com.redside.rngquest.utils.Assets;

import java.util.ArrayList;

public class SlashAnimation extends Entity{

    private Animator animator;

    /**
     *
     * @param x The x position of the Slash
     * @param y The y position of the Slash
     */
    public SlashAnimation(int x, int y){
        super("SlashAnimation", 0, 0, x, y, 255);

        ArrayList<Bitmap> frames = new ArrayList<>();
        // Add slash frames
        for (int i = 0; i < 8; i++){
            Bitmap frame = Assets.getBitmapFromMemory("sprites_slash_" + i);
            frames.add(frame);
        }
        animator = new Animator(frames);
        animator.setSpeed(90);
        animator.play();
        animator.update(System.currentTimeMillis());

    }

    /**
     * {@inheritDoc}
     */
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
