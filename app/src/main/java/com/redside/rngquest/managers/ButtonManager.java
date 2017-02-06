package com.redside.rngquest.managers;

import android.graphics.Bitmap;
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
        for (Button button : buttons){
            if (buttonTouched(button, event)){
                button.trigger();
            }
        }
    }
    public boolean buttonTouched(Button button, MotionEvent event) {
        // Super spaghetti, but works
        if (event.getX() >= button.getX() && event.getX() < (button.getX() + button.getBitmap().getWidth())
                && event.getY() >= button.getY() && event.getY() < (button.getY() + button.getBitmap().getHeight())) {
            return true;
        }
        return false;
    }
}
