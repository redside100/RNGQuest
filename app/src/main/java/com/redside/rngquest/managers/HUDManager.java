package com.redside.rngquest.managers;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.redside.rngquest.buttons.AttackButton;
import com.redside.rngquest.buttons.CharSelectButton;
import com.redside.rngquest.buttons.DefendButton;
import com.redside.rngquest.buttons.InventoryButton;
import com.redside.rngquest.buttons.InventoryItemButton;
import com.redside.rngquest.buttons.ShopItemButton;
import com.redside.rngquest.buttons.SpellButton;
import com.redside.rngquest.buttons.StartGameButton;
import com.redside.rngquest.buttons.StateChangeButton;
import com.redside.rngquest.entities.Entity;
import com.redside.rngquest.entities.Player;
import com.redside.rngquest.gameobjects.CoreView;
import com.redside.rngquest.gameobjects.Item;
import com.redside.rngquest.hudobjects.FadedText;
import com.redside.rngquest.hudobjects.ParabolicText;
import com.redside.rngquest.items.LargePotionItem;
import com.redside.rngquest.items.ManaPotionItem;
import com.redside.rngquest.items.SmallPotionItem;
import com.redside.rngquest.utils.Assets;
import com.redside.rngquest.utils.RNG;

import java.util.ArrayList;

import static com.redside.rngquest.managers.ScreenState.SHOP;

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
        onStateChange(ScreenState.TITLE, ScreenState.TITLE);
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
    public void preTouchEvent(MotionEvent e){
        buttonManager.checkButtonPretouch(e);
    }
    public static void onStateChange(ScreenState oldState, ScreenState newState){
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
                CharSelectButton bWizardCS = new CharSelectButton(wizardCS, (width / 4), (height / 2), 1);
                CharSelectButton bWarriorCS = new CharSelectButton(warriorCS, (width / 2), (height / 2), 2);
                CharSelectButton bTankCS = new CharSelectButton(tankCS, (width / 4) * 3, (height / 2), 3);
                break;

            case BATTLE:
                Bitmap attack = Assets.getBitmapFromMemory("button_attack");
                Bitmap defend = Assets.getBitmapFromMemory("button_defend");
                Bitmap inventory = Assets.getBitmapFromMemory("button_inventory");
                //temp
                StateChangeButton bBackB = new StateChangeButton(back, width / 2, (int) (height * 0.92), ScreenState.TITLE);
                AttackButton bAttack = new AttackButton(attack, (int) (width * 0.08), (int) (height * 0.87));
                DefendButton bDefend = new DefendButton(defend, (int) (width * 0.92), (int) (height * 0.87));
                InventoryButton inventoryButton = new InventoryButton(inventory, (int) (width * 0.92), (int) (height * 0.64));
                if (Player.hasSpell()){
                    SpellButton spellButton = new SpellButton(Player.getCurrentSpell().getButtonBitmap(), (int) (width * 0.08), (int) (height * 0.64));
                }
                break;

            case INVENTORY:
                ArrayList<Item> playerItems = new ArrayList<>(Player.getInventory().getItems());
                double invFactor = 0.285;
                for (int i = 0; i < playerItems.size(); i++){
                    InventoryItemButton itemButton = new InventoryItemButton(playerItems.get(i).getBitmap(), (int) (width * invFactor), height / 2, i + 1);
                    invFactor += 0.1435;
                }
                StateChangeButton invBack = new StateChangeButton(back, (int) (width * 0.09), (int) (height * 0.075), oldState);
                break;
            case SHOP:
                GameManager.generateShop();
                ArrayList<Item> spellItems = new ArrayList<>(GameManager.getShopSpellInventory().getItems());
                ArrayList<Item> consumableItems = new ArrayList<>(GameManager.getShopConsumableInventory().getItems());
                double sFactor = 0.613;
                for (int i = 0; i < spellItems.size(); i++){
                    ShopItemButton itemB = new ShopItemButton(spellItems.get(i).getBitmap(), (int) (width * sFactor), height / 6, i + 1);
                    sFactor += 0.144;
                }
                sFactor = 0.613;
                for (int i = 0; i < consumableItems.size(); i++){
                    ShopItemButton itemS = new ShopItemButton(consumableItems.get(i).getBitmap(), (int) (width * sFactor), (int) (height * 0.49), i + 4);
                    sFactor += 0.144;
                }
                Bitmap next = Assets.getBitmapFromMemory("button_next");
                Bitmap inv = Assets.getBitmapFromMemory("button_inv");
                StateChangeButton nextB = new StateChangeButton(next, (int) (width * 0.07), (int) (height * 0.075), ScreenState.STAGE_TRANSITION);
                StateChangeButton invB = new StateChangeButton(inv, (int) (width * 0.07), (int) (height * 0.195), ScreenState.INVENTORY);
                break;
        }
    }
    public void render(Canvas canvas, Paint paint){
        // Render all text, HUD items, etc. depending on state
        switch(CoreManager.state){

            // Title Screen
            case TITLE:
                drawCenteredText("RNG Quest", canvas, width / 2, (int) (height / 3.5), paint, 50, Color.WHITE);
                break;

            // Info Screen
            case INFO:
                drawCenteredText("Info", canvas, width / 2, (int) (height / 3.5), paint, 50, Color.WHITE);
                drawCenteredText("There's nothing here lol", canvas, width / 2, height / 2, paint, 30, Color.WHITE);
                break;

            // Character Selection Screen
            case CHAR_SELECT:
                drawCenteredText("Character Select", canvas, width / 2, (int) (height / 3.5), paint, 50, Color.WHITE);
                String character = "";
                switch(selection){
                    case 0:
                        character = "Choose a character...";
                        break;
                    case 1: // Mage
                        character = "Mage: +12 ATK (25%), +20 HP, +50 MP, +65% EVA, +Fireball";
                        break;
                    case 2: // Warrior
                        character = "Warrior: +12 ATK (55%), +50 HP, +10 AMR, +35% EVA";
                        break;
                    case 3: // Tank
                        character = "Tank: +7 ATK (40%), +90 HP, +40 AMR, +20% EVA";
                        break;
                }
                drawCenteredText(character, canvas, width / 2, (int) (height * 0.83), paint, 25, Color.rgb(0,191,255));
                break;

            // Stage Transition Screen
            case STAGE_TRANSITION:
                drawCenteredText("Stage " + GameManager.getStage(), canvas, width / 2, (int) (height / 3.5), paint, 50, Color.YELLOW);
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
                drawCenteredText(role, canvas, (int) (width * 0.3), (int) (height / 2.5), paint, 40, Color.WHITE);
                drawCenteredBitmap(picture, canvas, paint, (int) (width * 0.3), (int) (height * 0.6));

                drawCenteredText(Player.getGold() + " G", canvas, (int) (width * 0.3), (int) (height * 0.91), paint, 35, Color.YELLOW);

                String[] info = {
                        "HP: " + Player.getHP() + "/" + Player.getMaxHP(),
                        "MP: " + Player.getMana() + "/" + Player.getMaxMana(),
                        "AMR: " + Player.getArmor() + "/" + Player.getMaxArmor(),
                        "ATK: " + Player.getATK() + " (" + Player.getATKChance() + "%)",
                        "EVA: " + Player.getEvade() + "%"
                };

                int[] colors = {Color.RED, Color.BLUE, Color.CYAN, Color.rgb(255, 80, 0), Color.GREEN};
                double factor = 0.44;
                paint.setTextSize(35 * CoreView.resources.getDisplayMetrics().density);
                for (int i = 0; i < 5; i++){
                    paint.setColor(colors[i]);
                    canvas.drawText(info[i], (int) (width * 0.5), (int) (height * factor), paint);
                    paint.setColor(Color.WHITE);
                    factor += 0.09;
                }
                break;

            // Battle Screen
            case BATTLE:

                // Draw all icons and player stats
                Bitmap hpIcon = Assets.getBitmapFromMemory("icons_hp");
                Bitmap mpIcon = Assets.getBitmapFromMemory("icons_mana");
                Bitmap armorIcon = Assets.getBitmapFromMemory("icons_armor");
                Bitmap evadeIcon = Assets.getBitmapFromMemory("icons_evade");
                Bitmap swordsIcon = Assets.getBitmapFromMemory("icons_swords");

                String iconInfo[] = {
                        Player.getHP() + "/" + Player.getMaxHP(),
                        Player.getMana() + "/" + Player.getMaxMana(),
                        Player.getArmor() + "/" + Player.getMaxArmor(),
                        Player.getATK() + " (" + Player.getATKChance() + "%)",
                        Player.getEvade() + "%"
                };
                int[] iconColors = {Color.RED, Color.BLUE, Color.CYAN, Color.rgb(255, 80, 0), Color.GREEN};
                double iconFactor = 0.098;
                drawCenteredBitmap(hpIcon, canvas, paint, (int) (width * 0.05), (int) (height * 0.08));
                drawCenteredBitmap(mpIcon, canvas, paint, (int) (width * 0.05), (int) (height * 0.17));
                drawCenteredBitmap(armorIcon, canvas, paint, (int) (width * 0.05), (int) (height * 0.27));
                drawCenteredBitmap(swordsIcon, canvas, paint, (int) (width * 0.05), (int) (height * 0.36));
                drawCenteredBitmap(evadeIcon, canvas, paint, (int) (width * 0.05), (int) (height * 0.45));
                for (int i = 0; i < 5; i++){
                    drawTextWithBorder(iconInfo[i], canvas, (int) (width * 0.1), (int) (height * iconFactor), paint, 23, iconColors[i]);
                    iconFactor += 0.093;
                }

                // Draw current enemy HP
                Entity currEnemy = BattleManager.getCurrentEnemy();
                if (currEnemy != null){
                    drawCenteredTextWithBorder(currEnemy.getName() + ": " + currEnemy.getHP() + "/" + currEnemy.getMaxHP(), canvas, width / 2,
                            (int) (height * 0.12), paint, 25, Color.rgb(0,191,255));
                }else{
                    drawCenteredTextWithBorder("Waiting for enemy...", canvas, width / 2, (int) (height * 0.12), paint, 25, Color.rgb(0,191,255));
                }

                Bitmap emptyButton = Assets.getBitmapFromMemory("button_empty");
                if (Player.getCurrentSpell() == null){
                    drawCenteredBitmap(emptyButton, canvas, paint, (int) (width * 0.08), (int) (height * 0.64));
                }
                break;

            case INVENTORY:
                Bitmap invMenu = Assets.getBitmapFromMemory("menu_inventory_items");
                Bitmap invSelected = Assets.getBitmapFromMemory("menu_selected_item");
                canvas.drawBitmap(invMenu, 0, 0, paint);
                drawCenteredText("Inventory", canvas, width / 2, (int) (height * 0.2), paint, 40, Color.WHITE);
                if (GameManager.invSelection == 0){
                    drawCenteredText("Tap on an item for more info.", canvas, width / 2, (int) (height * 0.84), paint, 25, Color.WHITE);
                }
                if (GameManager.invSelection > 0 && GameManager.invSelection < 5){
                    int invSel = GameManager.invSelection - 1;
                    Item item = Player.getInventory().getItems().get(invSel);
                    drawCenteredBitmap(invSelected, canvas, paint, (int) (width * (0.285 + (0.1435 * invSel))), (int) (height * 0.498));
                    drawCenteredText(item.getDescription(), canvas, width / 2, (int) (height * 0.825), paint, 25, Color.WHITE);
                    if (Item.isSpell(item)){
                        drawCenteredText("Costs " + item.getManaCost() + " MP per use.", canvas, width / 2, (int) (height * 0.89), paint, 25, Color.rgb(0,191,255));
                    }else{
                        drawCenteredText("Tap again to use.", canvas, width / 2, (int) (height * 0.89), paint, 25, Color.GREEN);
                    }
                } else if (GameManager.invSelection == 5){
                    drawCenteredText("Item used.", canvas, width / 2, (int) (height * 0.84), paint, 25, Color.WHITE);
                }
                break;
            case SHOP:

                Bitmap itemMenu = Assets.getBitmapFromMemory("menu_shop_items");
                Bitmap shopSelected = Assets.getBitmapFromMemory("menu_selected_item");
                canvas.drawBitmap(itemMenu, 0, 0, paint);

                drawCenteredText(Player.getGold() + " G", canvas, (int) (width * 0.07), height / 3, paint, 25, Color.YELLOW);

                ArrayList<Item> spellItems = new ArrayList<>(GameManager.getShopSpellInventory().getItems());
                ArrayList<Item> consumableItems = new ArrayList<>(GameManager.getShopConsumableInventory().getItems());
                double sFactor = 0.613;
                for (Item item : spellItems){
                    drawCenteredText(item.getCost() + " G", canvas, (int) (width * sFactor), (int) (height * 0.34), paint, 25, Color.YELLOW);
                    sFactor += 0.144;
                }
                sFactor = 0.613;
                for (Item item : consumableItems){
                    drawCenteredText(item.getCost() + " G", canvas, (int) (width * sFactor), (int) (height * 0.66), paint, 25, Color.YELLOW);
                    sFactor += 0.144;
                }

                int shopSel = GameManager.shopSelection;
                if (shopSel == 0){
                    drawCenteredText("Welcome to the shop!", canvas, width / 2, (int) (height * 0.88), paint, 25, Color.WHITE);
                }
                else if (shopSel > 0 && shopSel < 4){
                    Item item = spellItems.get(shopSel - 1);
                    drawCenteredBitmap(shopSelected, canvas, paint, (int) (width * (0.469 + (0.1438 * shopSel))), height / 6);
                    drawCenteredText(item.getDescription(), canvas, width / 2, (int) (height * 0.85), paint, 25, Color.WHITE);
                    if (Player.hasEnoughGold(item.getCost()) && !Player.inventoryIsFull()){
                        if (!Player.getRole().equals(item.getRole()) && !item.getRole().equals(Player.Role.ALL)){
                            drawCenteredText("Your class can't use this spell.", canvas, width / 2, (int) (height * 0.92), paint, 25, Color.RED);
                        } else if (Player.hasSpell()){
                            drawCenteredText("Tap again to purchase. This will replace your old spell.", canvas, width / 2, (int) (height * 0.92), paint, 25, Color.YELLOW);
                        }else{
                            drawCenteredText("Tap again to purchase.", canvas, width / 2, (int) (height * 0.92), paint, 25, Color.GREEN);
                        }
                    }else if (Player.inventoryIsFull()){
                        drawCenteredText("Your inventory is full.", canvas, width / 2, (int) (height * 0.92), paint, 25, Color.RED);
                    }else if (!Player.hasEnoughGold(item.getCost())){
                        drawCenteredText("Not enough gold.", canvas, width / 2, (int) (height * 0.92), paint, 25, Color.RED);
                    }
                }else if (shopSel > 3 && shopSel < 7){
                    Item item = consumableItems.get(shopSel - 4);
                    drawCenteredBitmap(shopSelected, canvas, paint, (int) (width * (0.469 + (0.1438 * (shopSel - 3)))), (int) (height * 0.488));
                    drawCenteredText(item.getDescription(), canvas, width / 2, (int) (height * 0.85), paint, 25, Color.WHITE);
                    if (Player.hasEnoughGold(item.getCost()) && !Player.inventoryIsFull()){
                        drawCenteredText("Tap again to purchase.", canvas, width / 2, (int) (height * 0.92), paint, 25, Color.GREEN);
                    }else if (Player.inventoryIsFull()){
                        drawCenteredText("Your inventory is full.", canvas, width / 2, (int) (height * 0.92), paint, 25, Color.RED);
                    }else if (!Player.hasEnoughGold(item.getCost())){
                        drawCenteredText("Not enough gold.", canvas, width / 2, (int) (height * 0.92), paint, 25, Color.RED);
                    }
                }
                else if (shopSel == 7){
                    drawCenteredText("Item purchased!", canvas, width / 2, (int) (height * 0.88), paint, 25, Color.WHITE);
                }


        }
        // Render all buttons + faded text
        buttonManager.render(canvas, paint);
        entityManager.render(canvas, paint);
        animatedTextManager.render(canvas, paint);
    }
    public void drawCenteredText(String text, Canvas canvas, int x, int y, Paint paint, int textSize, int color){
        float old = paint.getTextSize();
        double scaledTextSize = textSize * CoreView.resources.getDisplayMetrics().density;
        paint.setColor(color);
        paint.setTextSize((int) scaledTextSize);
        Rect bounds = new Rect();
        // Get bounds of the text, then center
        paint.getTextBounds(text, 0, text.length(), bounds);
        x -= bounds.width() / 2;
        y -= bounds.height() / 2;
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
        double scaledTextSize = textSize * CoreView.resources.getDisplayMetrics().density;
        paint.setTextSize((int) scaledTextSize);
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
        double scaledTextSize = textSize * CoreView.resources.getDisplayMetrics().density;
        paint.setTextSize((int) scaledTextSize);
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
    public static void displayFadeMessage(String message, int x, int y, int ticks, int textSize, int color){
        FadedText fade = new FadedText(message, ticks, x, y, textSize, color);
        fade.play();
    }
    public static void displayParabolicText(String message, int x, int y, int ticks, int textSize, int color, double directionVec){
        ParabolicText parabola = new ParabolicText(message, ticks, x, y, textSize, color, directionVec);
        parabola.play();
    }
    public static double getSpeed(double distance, int ticksToReach){
        return distance / (double) ticksToReach;
    }
}
