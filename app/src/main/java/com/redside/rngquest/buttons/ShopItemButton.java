package com.redside.rngquest.buttons;

import android.graphics.Bitmap;

import com.redside.rngquest.gameobjects.Button;
import com.redside.rngquest.managers.GameManager;
import com.redside.rngquest.managers.Sound;
import com.redside.rngquest.managers.SoundEffect;

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
        // If it the selection is already selected, then buy the item
        if (GameManager.shopSelection == selection){
            GameManager.buyShopItem(selection);
        }else{
            // If not, then set it to the prefered selection
            GameManager.shopSelection = selection;
        }
    }
}
