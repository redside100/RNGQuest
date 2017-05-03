package com.redside.rngquest.managers;


import android.graphics.Bitmap;
import android.os.Handler;

import com.redside.rngquest.buttons.InventoryItemButton;
import com.redside.rngquest.buttons.ShopItemButton;
import com.redside.rngquest.buttons.StateChangeButton;
import com.redside.rngquest.entities.Entity;
import com.redside.rngquest.entities.Ghost;
import com.redside.rngquest.entities.Player;
import com.redside.rngquest.gameobjects.Button;
import com.redside.rngquest.gameobjects.Inventory;
import com.redside.rngquest.gameobjects.Item;
import com.redside.rngquest.items.LargePotionItem;
import com.redside.rngquest.items.ManaPotionItem;
import com.redside.rngquest.items.SmallPotionItem;
import com.redside.rngquest.utils.Assets;
import com.redside.rngquest.utils.RNG;

import java.util.ArrayList;

public class GameManager {
    public static int stage = 1;
    public static int part = 1;
    private static int width;
    private static int height;
    private static BattleManager battleManager;
    private static int tick = 0;
    private static boolean sTransition = false;
    private static Inventory shopSpellInventory = new Inventory();
    private static Inventory shopConsumableInventory = new Inventory();
    public static int shopSelection = 0;
    public static int invSelection = 0;
    public GameManager(){
        battleManager = new BattleManager();
        width = HUDManager.width;
        height = HUDManager.height;
    }
    public static void nextStage(){
        stage++;
        part = 1;
    }
    public static void nextPart(){
        part++;
    }
    public static int getStage(){
        return stage;
    }
    public static int getPart(){
        return part;
    }
    public static void onStateChange(ScreenState oldState, ScreenState newState){
        battleManager.close();
        switch(newState){
            case TITLE:
                reset();
                if (Soundtrack.getCurrentSong() != Song.TITLE){
                    Soundtrack.playSong(Song.TITLE);
                }
                break;
            case STAGE_TRANSITION:
                Soundtrack.playSong(Song.WAVE);
                // Switch to next screen in 3 seconds
                sTransition = true;
                break;
            case INFO:
                Soundtrack.playSong(Song.SHOP);
                break;
            case BATTLE:
                if (oldState.equals(ScreenState.INVENTORY)){
                    BattleManager.resumeBattle(BattleManager.savedEnemy);
                }else{
                    Soundtrack.playSong(Song.BATTLE);
                    BattleManager.setBattleState(BattleManager.BattleState.BATTLE_START);
                }
                break;
            case SHOP:
                Soundtrack.playSong(Song.SHOP);
                nextStage();
                break;
        }
    }
    public static Inventory getShopSpellInventory(){
        return shopSpellInventory;
    }
    public static Inventory getShopConsumableInventory(){
        return shopConsumableInventory;
    }
    public static void buyShopItem(int selection){
        if (selection > 0 && selection < 4){
            int index = selection - 1;
            Item item = getShopSpellInventory().getItems().get(index);
            if (Player.hasEnoughGold(item.getCost()) && !Player.inventoryIsFull()){
                if (Player.hasSpell()){
                    Player.getInventory().removeItem(Player.getCurrentSpell());
                }
                Player.getInventory().addItem(item);
                //Player.setCurrentSpell(item);
                Player.removeGold(item.getCost());
                getShopSpellInventory().removeItem(item);
                shopSelection = 7;
                recreateShopButtons();
            }
        }else if (selection > 3 && selection < 7){
            int index = selection - 4;
            Item item = getShopConsumableInventory().getItems().get(index);
            if (Player.hasEnoughGold(item.getCost()) && !Player.inventoryIsFull()){
                Player.getInventory().addItem(item);
                Player.removeGold(item.getCost());
                getShopConsumableInventory().removeItem(item);
                shopSelection = 7;
                recreateShopButtons();
            }
        }
    }
    public static void useInventoryItem(int selection){
        if (selection > 0 && selection < 5){
            int index = selection - 1;
            Item item = Player.getInventory().getItems().get(index);
            if (!Item.isSpell(item)){
                item.use();
                Player.getInventory().removeItem(item);
                invSelection = 5;
                recreateInventoryButtons();
            }
        }
    }
    public static void generateShop(){
        GameManager.shopSelection = 0;
        shopSpellInventory.clear();
        shopConsumableInventory.clear();
        for (int i = 0; i < 3; i++){
            switch (RNG.number(1, 3)){
                case 1:
                    shopConsumableInventory.addItem(new SmallPotionItem());
                    shopSpellInventory.addItem(new LargePotionItem());
                    break;
                case 2:
                    shopConsumableInventory.addItem(new LargePotionItem());
                    shopSpellInventory.addItem(new ManaPotionItem());
                    break;
                case 3:
                    shopConsumableInventory.addItem(new ManaPotionItem());
                    shopSpellInventory.addItem(new SmallPotionItem());
                    break;
            }
        }
    }
    private static void recreateShopButtons(){
        ArrayList<Button> temp = new ArrayList<>(ButtonManager.getButtons());
        for (Button button : temp){
            if (button instanceof ShopItemButton){
                button.destroy();
            }
        }
        ArrayList<Item> spellItems = new ArrayList<>(getShopSpellInventory().getItems());
        ArrayList<Item> consumableItems = new ArrayList<>(getShopConsumableInventory().getItems());
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
    }
    private static void recreateInventoryButtons(){
        ArrayList<Button> temp = new ArrayList<>(ButtonManager.getButtons());
        for (Button button : temp){
            if (button instanceof InventoryItemButton){
                button.destroy();
            }
        }
        ArrayList<Item> playerItems = new ArrayList<>(Player.getInventory().getItems());
        double invFactor = 0.285;
        for (int i = 0; i < playerItems.size(); i++){
            InventoryItemButton itemButton = new InventoryItemButton(playerItems.get(i).getBitmap(), (int) (width * invFactor), height / 2, i + 1);
            invFactor += 0.1435;
        }
    }
    public static void reset(){
        stage = 1;
        part = 1;
    }
    public void tick(){
        battleManager.tick();
        if (sTransition){
            tick++;
            if (tick == 195){
                tick = 0;
                sTransition = false;
                SEManager.playEffect(SEManager.Effect.FADE_TRANSITION, ScreenState.BATTLE);
            }
        }
    }
}
