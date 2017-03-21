package com.redside.rngquest.managers;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.redside.rngquest.buttons.AttackButton;
import com.redside.rngquest.buttons.DefendButton;
import com.redside.rngquest.buttons.StartGameButton;
import com.redside.rngquest.buttons.StateChangeButton;
import com.redside.rngquest.buttons.TankSelectButton;
import com.redside.rngquest.buttons.WarriorSelectButton;
import com.redside.rngquest.buttons.WizardSelectButton;
import com.redside.rngquest.entities.Player;
import com.redside.rngquest.hudobjects.FadedText;
import com.redside.rngquest.utils.Assets;

public class HUDManager {
    public static int width = 0;
    public static int height = 0;
    private static Bitmap play, back, info, start;
    private static ButtonManager buttonManager;
    private static FadedTextManager fadedTextManager;
    public static int selection = 0;
    public HUDManager(){
        this.width = CoreManager.width;
        this.height = CoreManager.height;
        // Init new button manager
        buttonManager = new ButtonManager();
        fadedTextManager = new FadedTextManager();
        // Load all button bitmaps needed
        play = Assets.getBitmapFromMemory("button_play");
        back = Assets.getBitmapFromMemory("button_back");
        info = Assets.getBitmapFromMemory("button_info");
        start = Assets.getBitmapFromMemory("button_start");
        onStateChange(ScreenState.TITLE);
    }
    public void tick(){
        // Tick button manager + faded text
        buttonManager.tick();
        fadedTextManager.tick();
    }
    public void touchEvent(MotionEvent e){
        buttonManager.checkButtons(e);
    }
    public static void onStateChange(ScreenState newState){
        // Clear buttons no matter what
        buttonManager.clearButtons();
        // Handle what to do depending on each state
        switch (newState){

            case TITLE:
                StateChangeButton bPlayMenu = new StateChangeButton(play, width / 2, height / 2, ScreenState.CHAR_SELECT);
                StateChangeButton bInfoMenu = new StateChangeButton(info, width / 2, (int) (height / 1.5), ScreenState.INFO);
                break;


            case INFO:
                StateChangeButton bBackInfo = new StateChangeButton(back, (int) (width * 0.9), (int) (height * 0.9), ScreenState.TITLE);
                break;


            case CHAR_SELECT:
                StateChangeButton bBackCS = new StateChangeButton(back, (int) (width * 0.1), (int) (height * 0.9), ScreenState.TITLE);
                StartGameButton bStartCS = new StartGameButton(start, (int) (width * 0.9), (int) (height * 0.9));
                Bitmap wizardCS = Assets.getBitmapFromMemory("sprites_wizard");
                Bitmap warriorCS = Assets.getBitmapFromMemory("sprites_warrior");
                Bitmap tankCS = Assets.getBitmapFromMemory("sprites_tank");
                WizardSelectButton bWizardCS = new WizardSelectButton(wizardCS, (width / 4), (height / 2));
                WarriorSelectButton bWarriorCS = new WarriorSelectButton(warriorCS, (width / 2), (height / 2));
                TankSelectButton bTankCS = new TankSelectButton(tankCS, (width / 4) * 3, (height / 2));
                break;

            case BATTLE:
                Bitmap attack = Assets.getBitmapFromMemory("button_attack");
                Bitmap defend = Assets.getBitmapFromMemory("button_defend");
                //temp
                StateChangeButton bBackB = new StateChangeButton(back, width / 2, (int) (height * 0.9), ScreenState.TITLE);
                AttackButton bAttack = new AttackButton(attack, (int) (width * 0.08), (int) (height * 0.87));
                DefendButton bDefend = new DefendButton(defend, (int) (width * 0.92), (int) (height * 0.87));
                break;
        }
    }
    public void render(Canvas canvas, Paint paint){
        // Render all buttons + faded text
        buttonManager.render(canvas, paint);
        fadedTextManager.render(canvas, paint);
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
                    case 1: // Mage
                        character = "Mage: +15 ATK (70%), +20 HP, +70% EVA";
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

            // Stage Transition Screen
            case STAGE_TRANSITION:
                paint.setColor(Color.YELLOW);
                drawCenteredText("Stage " + GameManager.getStage(), canvas, width / 2, (int) (height / 3.5), paint, 150);
                paint.setColor(Color.WHITE);
                String role = "";
                Bitmap picture = null;
                switch(Player.getRole()){
                    case MAGE:
                        role = "Mage";
                        picture = Assets.getBitmapFromMemory("sprites_wizard");
                        break;
                    case WARRIOR:
                        role = "Warrior";
                        picture = Assets.getBitmapFromMemory("sprites_warrior");
                        break;
                    case TANK:
                        role = "Tank";
                        picture = Assets.getBitmapFromMemory("sprites_tank");
                        break;
                }
                drawCenteredText(role, canvas, (int) (width * 0.3), (int) (height / 2.5), paint, 120);
                drawCenteredBitmap(picture, canvas, paint, (int) (width * 0.3), (int) (height * 0.6));

                paint.setColor(Color.YELLOW);
                drawCenteredText(Player.getGold() + " G", canvas, (int) (width * 0.3), (int) (height * 0.91), paint, 100);
                paint.setColor(Color.WHITE);

                String[] info = {
                        "HP: " + Player.getHP() + "/" + Player.getMaxHP(),
                        "ATK: " + Player.getATK() + " (" + Player.getATKChance() + "%)",
                        "AMR: " + Player.getArmor() + "/" + Player.getMaxArmor(),
                        "EVA: " + Player.getEvade() + "%"
                };
                int[] colors = {Color.GREEN, Color.RED, Color.GRAY, Color.CYAN};
                double factor = 0.47;
                for (int i = 0; i < 4; i++){
                    paint.setColor(colors[i]);
                    canvas.drawText(info[i], (int) (width * 0.5), (int) (height * factor), paint);
                    paint.setColor(Color.WHITE);
                    factor += 0.1;
                }
                break;

            // Incoming Enemy Screen
            case INCOMING_ENEMY:

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
    public void drawCenteredBitmap(Bitmap bitmap, Canvas canvas, Paint paint, int x, int y){
        x -= (bitmap.getWidth() / 2);
        y -= (bitmap.getHeight() / 2);
        canvas.drawBitmap(bitmap, x, y, paint);
    }
    public static void displayFadeMessage(String message, int x, int y, int seconds, int textSize, int color){
        FadedText fade = new FadedText(message, seconds, x, y, textSize, color);
        fade.play();
    }
}
