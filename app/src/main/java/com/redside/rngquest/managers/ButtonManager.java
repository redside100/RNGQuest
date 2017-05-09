package com.redside.rngquest.managers;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.redside.rngquest.gameobjects.Button;

import java.util.ArrayList;

public class ButtonManager {
    private static ArrayList<Button> buttons;
    public ButtonManager(){
        this.buttons = new ArrayList<>();
    }
    public static ArrayList<Button> getButtons(){
        return buttons;
    }
    public static void addButton(Button button){
        if (!buttons.contains(button)){
            buttons.add(button);
        }
    }
    public static void removeButton(Button button){
        if (buttons.contains(button)){
            buttons.remove(button);
        }
    }
    public static void clearButtons(){
        buttons.clear();
    }
    public void render(Canvas canvas, Paint paint){
        // Renders all active buttons
        for (Button button : buttons){
            button.render(canvas, paint);
        }
    }
    public void tick(){
        // Ticks all active buttons
        for (Button button : buttons){
            button.tick();
        }
    }
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
    public void checkButtonPretouch(MotionEvent event){
        ArrayList<Button> temp = new ArrayList<>(buttons);
        for (Button button : temp){
            if (buttonTouched(button, event)){
                // Set current button alpha to 153 (slightly transparent) for buttons that are being pressed
                button.setAlpha(153);
            }
        }
    }
    public boolean buttonTouched(Button button, MotionEvent event) {
        // Check if the bounds of the button contain the x and y of the touched spot
        if (event.getX() >= button.getX() && event.getX() < (button.getX() + button.getBitmap().getWidth())
                && event.getY() >= button.getY() && event.getY() < (button.getY() + button.getBitmap().getHeight())) {
            return true;
        }
        return false;
    }
}
