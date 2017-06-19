package com.redside.rngquest.gameobjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.redside.rngquest.managers.ButtonManager;

/**
 * Represents a button that can be pressed, and performs actions when triggered.
 * @author Andrew Peng
 * @since July 19, 2017
 */
public class Button{
    private Bitmap image;
    private int x;
    private int y;
    private String text;
    private int textSize;
    private int currAlpha = 255;

    /**
     *
     * @param image The {@link Bitmap} to be used
     * @param x The x position of the Button
     * @param y The y position of the Button
     */
    public Button(Bitmap image, int x, int y){
        // Center the image
        this.image = image;
        this.x = x - (getBitmap().getWidth() / 2);
        this.y = y - (getBitmap().getHeight() / 2);

        // Add to manager
        ButtonManager.addButton(this);
    }

    /**
     * Called when the game ticks.
     */
    public void tick(){}

    /**
     * Called when the game renders.
     * Renders the button onto the screen.
     * @param canvas The {@link Canvas} object to render to
     * @param paint The {@link Paint} object to render with
     */
    public void render(Canvas canvas, Paint paint) {
        // Render if not null
        if (image != null){
            // Set alpha before rendering
            int oldAlpha = paint.getAlpha();
            paint.setAlpha(currAlpha);
            canvas.drawBitmap(image, x, y, paint);
            paint.setAlpha(oldAlpha);
        }
    }

    /**
     * Sets the opacity of the Button.
     * @param alpha The opacity to set to
     */
    public void setAlpha(int alpha){
        currAlpha = alpha;
    }

    /**
     * Returns the x position of the button.
     * @return The x position of the button
     */
    public int getX(){
        return x;
    }

    /**
     * Returns the y position of the button.
     * @return The y position of the button
     */
    public int getY(){
        return y;
    }

    /**
     * Called when the button is pressed.
     */
    public void trigger() {

    }

    /**
     * Returns the button image {@link Bitmap}.
     * @return The button image {@link Bitmap}
     */
    public Bitmap getBitmap(){
        return image;
    }

    /**
     * Destroys the button, preventing it to be rendered and ticked.
     */
    public void destroy(){
        ButtonManager.removeButton(this);
    }
}
