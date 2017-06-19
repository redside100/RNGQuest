package com.redside.rngquest.managers;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.redside.rngquest.gameobjects.Button;

import java.util.ArrayList;

/**
 * Manages all buttons on the screen.
 * Ticks and renders active ones.
 * @author Andrew Peng
 * @since July 19, 2017
 */
public class ButtonManager {
    private static ArrayList<Button> buttons;
    public ButtonManager(){
        this.buttons = new ArrayList<>();
    }
    public static ArrayList<Button> getButtons(){
        return buttons;
    }

    /**
     * Adds a {@link Button} into the list.
     * @param button The {@link Button} to add
     */
    public static void addButton(Button button){
        if (!buttons.contains(button)){
            buttons.add(button);
        }
    }
    /**
     * Removes a {@link Button} from the list.
     * @param button The {@link Button} to remove
     */
    public static void removeButton(Button button){
        if (buttons.contains(button)){
            buttons.remove(button);
        }
    }

    /**
     * Removes all Buttons from the list.
     */
    public static void clearButtons(){
        buttons.clear();
    }

    /**
     * Called when the game renders.
     * Renders all buttons onto the screen.
     * @param canvas The {@link Canvas} to draw on
     * @param paint The {@link Paint} object to draw with
     */
    public void render(Canvas canvas, Paint paint){
        // Renders all active buttons
        for (Button button : buttons){
            button.render(canvas, paint);
        }
    }

    /**
     * Called when the game ticks.
     * Ticks all buttons.
     */
    public void tick(){
        // Ticks all active buttons
        for (Button button : buttons){
            button.tick();
        }
    }

    /**
     * Triggers all buttons that have been touched.
     * @param event The {@link MotionEvent} to listen to
     */
    public void checkButtons(MotionEvent event){
        ArrayList<Button> temp = new ArrayList<>(buttons);
        for (Button button : temp){
            // Reset alpha to 255 for all buttons
            button.setAlpha(255);
            if (buttonTouched(button, event)){
                button.trigger();
            }
        }
    }

    /**
     * Makes all touched buttons partially transparent.
     * @param event The {@link MotionEvent} to listen to
     */
    public void checkButtonPretouch(MotionEvent event){
        ArrayList<Button> temp = new ArrayList<>(buttons);
        for (Button button : temp){
            if (buttonTouched(button, event)){
                // Set current button alpha to 153 (slightly transparent) for buttons that are being pressed
                button.setAlpha(153);
            }
        }
    }

    /**
     * Checks if a button was touched.
     * @param button The {@link Button} to check for
     * @param event The {@link MotionEvent} to listen to
     * @return {@code true} if the {@link Button} was touched
     */
    public boolean buttonTouched(Button button, MotionEvent event) {
        // Check if the bounds of the button contain the x and y of the touched spot
        if (event.getX() >= button.getX() && event.getX() < (button.getX() + button.getBitmap().getWidth())
                && event.getY() >= button.getY() && event.getY() < (button.getY() + button.getBitmap().getHeight())) {
            return true;
        }
        return false;
    }
}
