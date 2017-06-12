package com.redside.rngquest.managers;


import com.redside.rngquest.buttons.InventoryItemButton;
import com.redside.rngquest.buttons.ShopItemButton;
import com.redside.rngquest.entities.Player;
import com.redside.rngquest.gameobjects.Button;
import com.redside.rngquest.gameobjects.CoreView;
import com.redside.rngquest.gameobjects.Inventory;
import com.redside.rngquest.gameobjects.Item;
import com.redside.rngquest.items.AgilitySpellItem;
import com.redside.rngquest.items.EvadeUpPotionItem;
import com.redside.rngquest.items.FireballSpellItem;
import com.redside.rngquest.items.LargePotionItem;
import com.redside.rngquest.items.LifestealSpellItem;
import com.redside.rngquest.items.ManaPotionItem;
import com.redside.rngquest.items.ManaUpPotionItem;
import com.redside.rngquest.items.SmallPotionItem;
import com.redside.rngquest.utils.RNG;

import java.util.ArrayList;

import static com.redside.rngquest.gameobjects.CoreView.getSave;

public class GameManager {
    public static int stage = 1;
    public static int highStage = 1;
    public static int part = 1;
    private static int width;
    private static int height;
    private static BattleManager battleManager;
    private static int tick = 0;
    private static boolean sTransition = false;
    public static boolean revisit = true;
    private static Inventory shopSpellInventory = new Inventory();
    private static Inventory shopConsumableInventory = new Inventory();
    public static int shopSelection = 0;
    public static int invSelection = 0;
    public static ScreenState lastState;
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
    public static int getHighStage() { return highStage; }
    public static void updateHighStage() {
        ArrayList<String> save = CoreView.getSave(CoreManager.context);
        for (String line : save){
            if (line.split(": ")[0].equalsIgnoreCase("highStage")){
                int saveStage = Integer.parseInt(line.split(": ")[1]);
                if (highStage < saveStage){
                    highStage = saveStage;
                }
            }
        }
    }
    public static int getPart(){
        return part;
    }
    public static void onStateChange(ScreenState oldState, ScreenState newState){
        lastState = oldState;
        battleManager.close();
        switch(newState){
            case TITLE:
                // Reset level and part count
                reset();
                if (Soundtrack.getCurrentSong() != Song.TITLE){
                    Soundtrack.playSong(Song.TITLE);
                }
                break;
            case INFO:
                break;
            case STAGE_TRANSITION:
                // Flag the shop to be restocked the next time
                revisit = true;

                // Save game in case the user bought items from the shop
                updateHighStage();
                saveGame();

                Soundtrack.playSong(Song.WAVE);
                // Switch to next screen in 3 seconds
                sTransition = true;
                break;
            case BATTLE:
                // If coming from inventory screen, then resume the battle
                if (oldState.equals(ScreenState.INVENTORY)){
                    BattleManager.resumeBattle(BattleManager.savedEnemy);
                }else{
                    // If not, then start the stage
                    Soundtrack.playSong(Song.BATTLE);
                    BattleManager.setBattleState(BattleManager.BattleState.BATTLE_START);
                }
                break;
            case SHOP:
                // If not coming from inventory screen, increment the stage counter
                if (!oldState.equals(ScreenState.INVENTORY)){
                    Soundtrack.playSong(Song.SHOP);
                    nextStage();
                    saveGame();
                }
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
        // Check if a spell is selected (top row, 1-3)
        if (selection > 0 && selection < 4){
            int index = selection - 1;
            Item item = getShopSpellInventory().getItems().get(index);

            // Check if the player has enough gold and inventory isn't full
            if (Player.hasEnoughGold(item.getCost()) && !Player.inventoryIsFull()){
                // Check if the item is available to all roles, or is available to the player's role
                if (item.getRole().equals(Player.Role.ALL) || item.getRole().equals(Player.getRole())){
                    // Check if the player already has a spell
                    if (Player.hasSpell()){
                        // Remove it
                        Player.getInventory().removeItem(Player.getCurrentSpell());
                    }
                    // Add the item, remove gold, remove item from shop, and set current spell
                    Player.getInventory().addItem(item);
                    Player.setCurrentSpell(item);
                    Player.removeGold(item.getCost());
                    getShopSpellInventory().removeItem(item);

                    // Save the game
                    saveGame();

                    // Change to "purchased" state, and redraw the item buttons
                    shopSelection = 7;
                    Sound.playSound(SoundEffect.PURCHASE);
                    recreateShopButtons();
                }
            }
            // Check if a consumable is selected (bottom row, 4-6)
        }else if (selection > 3 && selection < 7){
            int index = selection - 4;
            Item item = getShopConsumableInventory().getItems().get(index);

            // Check if the player has gold and inventory isn't full
            if (Player.hasEnoughGold(item.getCost()) && !Player.inventoryIsFull()){
                // Add item, remove gold, remove item from shop
                Player.getInventory().addItem(item);
                Player.removeGold(item.getCost());
                getShopConsumableInventory().removeItem(item);

                // Save the game
                saveGame();

                // Change the purchased state, and redraw shop items
                shopSelection = 7;
                Sound.playSound(SoundEffect.PURCHASE);
                recreateShopButtons();
            }
        }
    }
    public static void saveGame(){
        // Save game data into a file
        // First add all stats into the list
        ArrayList<String> data = new ArrayList<>();
        data.add("available: true");
        if (stage > highStage){
            data.add("highStage: " + stage);
        }else{
            data.add("highStage: " + highStage);
        }
        data.add("stage: " + stage);
        data.add("hp: " + Player.getHP());
        data.add("maxhp: " + Player.getMaxHP());
        data.add("mp: " + Player.getMana());
        data.add("maxmp: " + Player.getMaxMana());
        data.add("atk: " + Player.getATK());
        data.add("atkchance: " + Player.getRealATKChance());
        data.add("evade: " + Player.getEvade());
        data.add("armor: " + Player.getArmor());
        data.add("maxarmor: " + Player.getMaxArmor());
        data.add("gold: " + Player.getGold());

        // Then the role
        switch(Player.getRole()){
            case MAGE:
                data.add("role: mage");
                break;
            case WARRIOR:
                data.add("role: warrior");
                break;
            case TANK:
                data.add("role: tank");
                break;
        }

        // Then inventory items
        String items = "";
        // Get a temp list of player items
        ArrayList<Item> playerItems = new ArrayList<>(Player.getInventory().getItems());
        // Loop through, and attach the item id and a comma
        for (Item item : playerItems){
            items += item.getId() + ",";
        }
        if (Player.getInventory().items.size() > 0){
            // Add to list if has at least one item
            data.add("items: " + items);
        }

        // Add current spell id if has a spell
        if (Player.hasSpell() && Player.getCurrentSpell() != null){
            data.add("currentspell: " + Player.getCurrentSpell().getId());
        }

        // Save
        CoreView.save(CoreManager.context, data);
    }
    public static void useInventoryItem(int selection){
        // Check if the selection is in range (1-4)
        if (selection > 0 && selection < 5){
            int index = selection - 1;
            Item item = Player.getInventory().getItems().get(index);

            // Check if it is a spell (spells can't be used in the inventory)
            if (!Item.isSpell(item)){
                // Use the item and play a sound
                Player.getInventory().useItem(item);
                Sound.playSound(SoundEffect.USE_ITEM);

                // Save the game if the player is using it in the shop
                if (lastState.equals(ScreenState.SHOP)){
                    saveGame();
                }

                // Change to "used" state, and redraw inventory items
                invSelection = 5;
                recreateInventoryButtons();
            }
        }
    }
    public static void generateShop(){
        // Check if it should be restocked
        // It shouldn't if coming from inventory screen
        if (revisit){
            revisit = false;
            shopSelection = 0;

            // Clear both inventories
            shopSpellInventory.clear();
            shopConsumableInventory.clear();

            // Restock with three random spells and consumables
            for (int i = 0; i < 3; i++){
                switch (RNG.number(1, 5)){
                    case 1:
                        shopConsumableInventory.addItem(new SmallPotionItem());
                        break;
                    case 2:
                        shopConsumableInventory.addItem(new LargePotionItem());
                        break;
                    case 3:
                        shopConsumableInventory.addItem(new ManaPotionItem());
                        break;
                    case 4:
                        shopConsumableInventory.addItem(new ManaUpPotionItem());
                        break;
                    case 5:
                        shopConsumableInventory.addItem(new EvadeUpPotionItem());
                        break;
                }
            }
            for (int i = 0; i < 3; i++){
                switch (RNG.number(1, 3)){
                    case 1:
                        shopSpellInventory.addItem(new FireballSpellItem());
                        break;
                    case 2:
                        shopSpellInventory.addItem(new AgilitySpellItem());
                        break;
                    case 3:
                        shopSpellInventory.addItem(new LifestealSpellItem());
                        break;
                }
            }

        }
    }
    private static void recreateShopButtons(){
        // Destroy all shop item buttons
        ArrayList<Button> temp = new ArrayList<>(ButtonManager.getButtons());
        for (Button button : temp){
            if (button instanceof ShopItemButton){
                button.destroy();
            }
        }
        // Get updated inventories
        ArrayList<Item> spellItems = new ArrayList<>(getShopSpellInventory().getItems());
        ArrayList<Item> consumableItems = new ArrayList<>(getShopConsumableInventory().getItems());
        double sFactor = 0.613;
        // Recreate spell items
        for (int i = 0; i < spellItems.size(); i++){
            ShopItemButton itemB = new ShopItemButton(spellItems.get(i).getBitmap(), (int) (width * sFactor), height / 6, i + 1);
            sFactor += 0.144;
        }
        // Recreate consumable items
        sFactor = 0.613;
        for (int i = 0; i < consumableItems.size(); i++){
            ShopItemButton itemS = new ShopItemButton(consumableItems.get(i).getBitmap(), (int) (width * sFactor), (int) (height * 0.49), i + 4);
            sFactor += 0.144;
        }
    }
    private static void recreateInventoryButtons(){
        // Destroy all inventory item buttons
        ArrayList<Button> temp = new ArrayList<>(ButtonManager.getButtons());
        for (Button button : temp){
            if (button instanceof InventoryItemButton){
                button.destroy();
            }
        }
        // Get updated inventory
        ArrayList<Item> playerItems = new ArrayList<>(Player.getInventory().getItems());
        double invFactor = 0.285;
        // Recreate inventory items
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
        // Tick battle manager
        battleManager.tick();
        // Check if it is transitioning, if so proceed to battle state if it hits 195 ticks
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
