package com.redside.rngquest.buttons;

import android.graphics.Bitmap;

import com.redside.rngquest.gameobjects.Button;
import com.redside.rngquest.managers.GameManager;
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
            GameManager.useInventoryItem(selection);
        }else{
            // If not, then set it to the selection
            GameManager.invSelection = selection;
        }
    }
}
