package com.redside.rngquest.managers;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.redside.rngquest.buttons.StateChangeButton;
import com.redside.rngquest.buttons.TankSelectButton;
import com.redside.rngquest.buttons.WarriorSelectButton;
import com.redside.rngquest.buttons.WizardSelectButton;
import com.redside.rngquest.utils.Assets;

public class HUDManager {
    private static int width = 0;
    private static int height = 0;
    private static Bitmap play, back, info;
    private static ButtonManager buttonManager;
    public static int selection = 0;
    public HUDManager(){
        this.width = CoreManager.width;
        this.height = CoreManager.height;
        // Init new button manager
        buttonManager = new ButtonManager();
        // Load all button bitmaps needed
        play = Assets.getBitmapFromMemory("button_play");
        back = Assets.getBitmapFromMemory("button_back");
        info = Assets.getBitmapFromMemory("button_info");
        onStateChange(ScreenState.TITLE);
    }
    public void tick(){
        // Tick button manager
        buttonManager.tick();
    }
    public void touchEvent(MotionEvent e){
        buttonManager.checkButtons(e);
    }
    public static void onStateChange(ScreenState newState){
        // Clear buttons no matter what
        buttonManager.clearButtons();
        // Handle which buttons to create depending on the new state
        switch (newState){
            case TITLE:
                StateChangeButton bPlayMenu = new StateChangeButton(play, width / 2, height / 2, ScreenState.CHAR_SELECT);
                StateChangeButton bInfoMenu = new StateChangeButton(info, width / 2, (int) (height / 1.5), ScreenState.INFO);
                break;
            case INFO:
                StateChangeButton bBackInfo = new StateChangeButton(back, (int) (width * 0.9), (int) (height * 0.9), ScreenState.TITLE);
                break;
            case CHAR_SELECT:
                StateChangeButton bBackCS = new StateChangeButton(back, (int) (width * 0.9), (int) (height * 0.9), ScreenState.TITLE);
                Bitmap wizardCS = Assets.getBitmapFromMemory("sprites_wizard");
                Bitmap warriorCS = Assets.getBitmapFromMemory("sprites_warrior");
                Bitmap tankCS = Assets.getBitmapFromMemory("sprites_tank");
                WizardSelectButton bWizardCS = new WizardSelectButton(wizardCS, (width / 4), (height / 2));
                WarriorSelectButton bWarriorCS = new WarriorSelectButton(warriorCS, (width / 2), (height / 2));
                TankSelectButton bTankCS = new TankSelectButton(tankCS, (width / 4) * 3, (height / 2));

                break;
        }
    }
    public void render(Canvas canvas, Paint paint){
        // Render all buttons
        buttonManager.render(canvas, paint);
        // Render all text, HUD items, etc. depending on state
        switch(CoreManager.state){

            // Title Screen
            case TITLE:
                drawCenteredText("RNG Quest", canvas, width / 2, (int) (height / 3.5), paint, 150);
                break;

            // Info Screen
            case INFO:
                drawCenteredText("Info", canvas, width / 2, (int) (height / 3.5), paint, 150);
                drawCenteredText("There's nothing here lol", canvas, width / 2, height / 2, paint, 100);
                break;

            // Character Selection Screen
            case CHAR_SELECT:
                drawCenteredText("Character Select", canvas, width / 2, (int) (height / 3.5), paint, 150);
                String character = "";
                switch(selection){
                    case 1: // Wizard
                        character = "Wizard: +15 ATK (70%), +20 HP, +70% EVA";
                        break;
                    case 2: // Warrior
                        character = "Warrior: +12 ATK (50%), +50 HP, +5 AMR, +30% EVA";
                        break;
                    case 3: // Tank
                        character = "Tank: +7 ATK (40%), +90 HP, +20 AMR, +20% EVA";
                        break;
                }
                drawCenteredText(character, canvas, width / 2, (int) (height * 0.83), paint, 75);
                break;
        }
    }
    public void drawCenteredText(String text, Canvas canvas, int x, int y, Paint paint, int textSize){
        paint.setTextSize(textSize);
        Rect bounds = new Rect();
        // Get bounds of the text, then center
        paint.getTextBounds(text, 0, text.length(), bounds);
        x -= bounds.width() / 2;
        y -= bounds.height() / 2;
        float old = paint.getTextSize();
        canvas.drawText(text, x, y, paint);
        paint.setTextSize(old);
    }
}
