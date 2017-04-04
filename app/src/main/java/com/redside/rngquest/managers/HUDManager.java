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
    private static AnimatedTextManager animatedTextManager;
    private static EntityManager entityManager;
    public static int selection = 0;
    public HUDManager(){
        this.width = CoreManager.width;
        this.height = CoreManager.height;
        // Init new button manager
        buttonManager = new ButtonManager();
        animatedTextManager = new AnimatedTextManager();
        entityManager = new EntityManager();
        // Load all button bitmaps needed
        play = Assets.getBitmapFromMemory("button_play");
        back = Assets.getBitmapFromMemory("button_back");
        info = Assets.getBitmapFromMemory("button_info");
        start = Assets.getBitmapFromMemory("button_start");
        onStateChange(ScreenState.TITLE);
    }
    public void tick(){
        // Tick button manager + faded text
        entityManager.tick();
        buttonManager.tick();
        animatedTextManager.tick();
    }
    public void touchEvent(MotionEvent e){
        buttonManager.checkButtons(e);
    }
    public static void onStateChange(ScreenState newState){
        // Clear buttons no matter what
        buttonManager.clearButtons();
        entityManager.clear();
        animatedTextManager.clear();
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
        entityManager.render(canvas, paint);
        animatedTextManager.render(canvas, paint);
        // Render all text, HUD items, etc. depending on state
        switch(CoreManager.state){

            // Title Screen
            case TITLE:
                drawCenteredText("RNG Quest", canvas, width / 2, (int) (height / 3.5), paint, 150, Color.WHITE);
                break;

            // Info Screen
            case INFO:
                drawCenteredText("Info", canvas, width / 2, (int) (height / 3.5), paint, 150, Color.WHITE);
                drawCenteredText("There's nothing here lol", canvas, width / 2, height / 2, paint, 100, Color.WHITE);
                break;

            // Character Selection Screen
            case CHAR_SELECT:
                drawCenteredText("Character Select", canvas, width / 2, (int) (height / 3.5), paint, 150, Color.WHITE);
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
                drawCenteredText(character, canvas, width / 2, (int) (height * 0.83), paint, 75, Color.rgb(0,191,255));
                break;

            // Stage Transition Screen
            case STAGE_TRANSITION:
                drawCenteredText("Stage " + GameManager.getStage(), canvas, width / 2, (int) (height / 3.5), paint, 150, Color.YELLOW);
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
                drawCenteredText(role, canvas, (int) (width * 0.3), (int) (height / 2.5), paint, 120, Color.WHITE);
                drawCenteredBitmap(picture, canvas, paint, (int) (width * 0.3), (int) (height * 0.6));

                drawCenteredText(Player.getGold() + " G", canvas, (int) (width * 0.3), (int) (height * 0.91), paint, 100, Color.YELLOW);

                String[] info = {
                        "HP: " + Player.getHP() + "/" + Player.getMaxHP(),
                        "ATK: " + Player.getATK() + " (" + Player.getATKChance() + "%)",
                        "AMR: " + Player.getArmor() + "/" + Player.getMaxArmor(),
                        "EVA: " + Player.getEvade() + "%"
                };
                int[] colors = {Color.RED, Color.rgb(255, 80, 0), Color.CYAN, Color.GREEN};
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

            // Battle Screen
            case BATTLE:
                Bitmap hpIcon = Assets.getBitmapFromMemory("icons_hp");
                Bitmap armorIcon = Assets.getBitmapFromMemory("icons_armor");
                Bitmap evadeIcon = Assets.getBitmapFromMemory("icons_evade");
                Bitmap swordsIcon = Assets.getBitmapFromMemory("icons_swords");
                String iconInfo[] = {
                        Player.getHP() + "/" + Player.getMaxHP(),
                        Player.getArmor() + "/" + Player.getMaxArmor(),
                        Player.getATK() + " (" + Player.getATKChance() + "%)",
                        Player.getEvade() + "%"
                };
                int[] iconColors = {Color.RED, Color.CYAN, Color.rgb(255, 80, 0), Color.GREEN};
                double iconFactor = 0.1;
                drawCenteredBitmap(hpIcon, canvas, paint, (int) (width * 0.05), (int) (height * 0.08));
                drawCenteredBitmap(armorIcon, canvas, paint, (int) (width * 0.05), (int) (height * 0.18));
                drawCenteredBitmap(swordsIcon, canvas, paint, (int) (width * 0.05), (int) (height * 0.28));
                drawCenteredBitmap(evadeIcon, canvas, paint, (int) (width * 0.05), (int) (height * 0.38));
                for (int i = 0; i < 4; i++){
                    drawTextWithBorder(iconInfo[i], canvas, (int) (width * 0.1), (int) (height * iconFactor), paint, 75, iconColors[i]);
                    iconFactor += 0.1;
                }
                break;
        }
    }
    public void drawCenteredText(String text, Canvas canvas, int x, int y, Paint paint, int textSize, int color){
        paint.setColor(color);
        paint.setTextSize(textSize);
        Rect bounds = new Rect();
        // Get bounds of the text, then center
        paint.getTextBounds(text, 0, text.length(), bounds);
        x -= bounds.width() / 2;
        y -= bounds.height() / 2;
        float old = paint.getTextSize();
        canvas.drawText(text, x, y, paint);
        paint.setTextSize(old);
        paint.setColor(Color.WHITE);
    }
    public void drawCenteredBitmap(Bitmap bitmap, Canvas canvas, Paint paint, int x, int y){
        x -= (bitmap.getWidth() / 2);
        y -= (bitmap.getHeight() / 2);
        canvas.drawBitmap(bitmap, x, y, paint);
    }
    public void drawTextWithBorder(String text, Canvas canvas, int x, int y, Paint paint, int textSize, int color){
        paint.setColor(color);
        float old = paint.getTextSize();
        paint.setTextSize(textSize);
        paint.setStyle(Paint.Style.FILL);
        paint.setShadowLayer(3, 0, 0, Color.BLACK);
        // Draw normal text
        canvas.drawText(text, x, y, paint);
        paint.setShadowLayer(0, 0, 0, Color.BLACK);
        paint.setTextSize(old);
        paint.setColor(Color.WHITE);
    }
    public void drawCenteredTextWithBorder(String text, Canvas canvas, int x, int y, Paint paint, int textSize, int color){
        paint.setColor(color);
        float old = paint.getTextSize();
        paint.setTextSize(textSize);
        paint.setStyle(Paint.Style.FILL);

        Rect bounds = new Rect();
        // Get bounds of the text, then center
        paint.getTextBounds(text, 0, text.length(), bounds);
        x -= bounds.width() / 2;
        y -= bounds.height() / 2;

        // Draw normal text
        paint.setShadowLayer(3, 0, 0, Color.BLACK);
        canvas.drawText(text, x, y, paint);
        paint.setShadowLayer(0, 0, 0, Color.BLACK);
        // Draw black border

        paint.setTextSize(old);
        paint.setColor(Color.WHITE);
    }
    public static void displayFadeMessage(String message, int x, int y, int seconds, int textSize, int color){
        FadedText fade = new FadedText(message, seconds, x, y, textSize, color);
        fade.play();
    }
}
