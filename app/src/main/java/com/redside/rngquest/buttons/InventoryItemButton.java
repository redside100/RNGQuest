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

public class InventoryItemButton extends Button {
    private int selection;
    /**
     *
     * @param image The {@link Bitmap} image to be used for the button
     * @param x The x position of the button
     * @param y The y position of the button
     * @param selection The index to select once pressed.
     */
    public InventoryItemButton(Bitmap image, int x, int y, int selection){
        super(image, x, y);
        this.selection = selection;
    }
    /**
     * Called when the button is pressed.
     * Causes the Player to select/use an item.
     */
    @Override
    public void trigger(){
        Sound.playSound(SoundEffect.SELECT);
        // If the selection is the same, then use the item
        if (GameManager.invSelection == selection){
            int invSel = GameManager.invSelection - 1;
            Item item = Player.getInventory().getItems().get(invSel);
            GameManager.useInventoryItem(selection);
            if (!item.isSpell()){
                AnimatedTextManager.clear();
                HUDManager.displayTypingText("Item used.", HUDManager.width / 2, (int) (HUDManager.height * 0.825), 2, 13, Color.WHITE, true);
            }
        }else{
            AnimatedTextManager.clear();
            // If not, then set it to the selection
            GameManager.invSelection = selection;
            int invSel = GameManager.invSelection - 1;
            Item item = Player.getInventory().getItems().get(invSel);
            HUDManager.displayTypingText(item.getDescription(), HUDManager.width / 2, (int) (HUDManager.height * 0.825), 2, 13, Color.WHITE, true);
            // Draw spell description if it's a spell, or usage instructions otherwise
            if (Item.isSpell(item)){
                HUDManager.displayTypingText("Costs " + item.getManaCost() + " MP per use.", HUDManager.width / 2, (int) (HUDManager.height * 0.89), 2, 13, Color.rgb(0,191,255), true);
            }else{
                HUDManager.displayTypingText("Tap again to use.", HUDManager.width / 2, (int) (HUDManager.height * 0.89), 2, 13, Color.GREEN, true);
            }
        }
    }
}
