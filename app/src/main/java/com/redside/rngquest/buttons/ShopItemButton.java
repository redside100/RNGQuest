package com.redside.rngquest.buttons;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.redside.rngquest.entities.Player;
import com.redside.rngquest.gameobjects.Button;
import com.redside.rngquest.gameobjects.Item;
import com.redside.rngquest.managers.AnimatedTextManager;
import com.redside.rngquest.managers.GameManager;
import com.redside.rngquest.managers.HUDManager;
import com.redside.rngquest.managers.Sound;
import com.redside.rngquest.managers.SoundEffect;

import java.util.ArrayList;

public class ShopItemButton extends Button {
    private int selection;
    /**
     *
     * @param image The {@link Bitmap} image to be used for the button
     * @param x The x position of the button
     * @param y The y position of the button
     * @param selection The index to select once pressed.
     */
    public ShopItemButton(Bitmap image, int x, int y, int selection){
        super(image, x, y);
        this.selection = selection;
    }
    /**
     * Called when the button is pressed.
     * Causes the Player to select/buy a shop item.
     */
    @Override
    public void trigger(){
        Sound.playSound(SoundEffect.SELECT);
        ArrayList<Item> spellItems = new ArrayList<>(GameManager.getShopSpellInventory().getItems());
        ArrayList<Item> consumableItems = new ArrayList<>(GameManager.getShopConsumableInventory().getItems());
        // If it the selection is already selected, then buy the item
        if (GameManager.shopSelection == selection){
            GameManager.buyShopItem(selection);
            if (GameManager.shopSelection == 7){
                AnimatedTextManager.clear();
                HUDManager.displayTypingText("Item purchased!", HUDManager.width / 2, (int) (HUDManager.height * 0.86), 2, 13, Color.WHITE, true);
            }
        }else{
            // If not, then set it to the prefered selection
            GameManager.shopSelection = selection;
            AnimatedTextManager.clear();
            // Show info for the selected item (spell)
            if (selection > 0 && selection < 4){
                Item item = spellItems.get(selection - 1);

                HUDManager.displayTypingText(item.getDescription(), HUDManager.width / 2, (int) (HUDManager.height * 0.83), 2, 13, Color.WHITE, true);

                // Check if the player has enough gold and inventory isn't full
                if (Player.hasEnoughGold(item.getCost()) && !Player.inventoryIsFull()){
                    // Check if the player has the appropriate role or the item is for all roles
                    if (!Player.getRole().equals(item.getRole()) && !item.getRole().equals(Player.Role.ALL)){
                        HUDManager.displayTypingText("Your class can't use this spell.", HUDManager.width / 2, (int) (HUDManager.height * 0.9), 2, 13, Color.RED, true);

                    } else if (Player.hasSpell()){
                        // Draw warning if player already has a spell
                        HUDManager.displayTypingText("Tap again to replace your old spell.", HUDManager.width / 2, (int) (HUDManager.height * 0.9), 2, 13, Color.YELLOW, true);

                    }else{
                        // Confirmation
                        HUDManager.displayTypingText("Tap again to purchase.", HUDManager.width / 2, (int) (HUDManager.height * 0.9), 2, 13, Color.GREEN, true);

                    }
                }else if (Player.inventoryIsFull()){
                    // If inventory is full
                    HUDManager.displayTypingText("Your inventory is full.", HUDManager.width / 2, (int) (HUDManager.height * 0.9), 2, 13, Color.RED, true);

                }else if (!Player.hasEnoughGold(item.getCost())){
                    // If not enough gold
                    HUDManager.displayTypingText("Not enough gold.", HUDManager.width / 2, (int) (HUDManager.height * 0.9), 2, 13, Color.RED, true);
                }

            } else if (selection > 3 && selection < 7){
                Item item = consumableItems.get(selection - 4);

                // Highlight and draw description
                HUDManager.displayTypingText(item.getDescription(), HUDManager.width / 2, (int) (HUDManager.height * 0.83), 2, 13, Color.WHITE, true);

                // Check if the player has enough gold and inventory isn't full
                if (Player.hasEnoughGold(item.getCost()) && !Player.inventoryIsFull()){
                    HUDManager.displayTypingText("Tap again to purchase.", HUDManager.width / 2, (int) (HUDManager.height * 0.9), 2, 13, Color.GREEN, true);
                }else if (Player.inventoryIsFull()){
                    // If inventory is full
                    HUDManager.displayTypingText("Your inventory is full.", HUDManager.width / 2, (int) (HUDManager.height * 0.9), 2, 13, Color.RED, true);
                }else if (!Player.hasEnoughGold(item.getCost())){
                    // If not enough gold
                    HUDManager.displayTypingText("Not enough gold.", HUDManager.width / 2, (int) (HUDManager.height * 0.9), 2, 13, Color.RED, true);
                }
            }

        }
    }
}
