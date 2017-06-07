package com.redside.rngquest.buttons;

import android.graphics.Bitmap;

import com.redside.rngquest.gameobjects.Button;
import com.redside.rngquest.managers.BattleManager;
import com.redside.rngquest.managers.Sound;

public class InventoryButton extends Button {
    /**
     *
     * @param image The {@link Bitmap} image to be used for the button
     * @param x The x position of the button
     * @param y The y position of the button
     */
    public InventoryButton(Bitmap image, int x, int y){
        super(image, x, y);
    }
    /**
     * Called when the button is pressed.
     * Causes the Player to open the inventory while in battle.
     */
    @Override
    public void trigger(){
        BattleManager.playerInventory();
    }
}
