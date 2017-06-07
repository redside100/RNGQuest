package com.redside.rngquest.buttons;

import android.graphics.Bitmap;

import com.redside.rngquest.entities.Player;
import com.redside.rngquest.gameobjects.Button;
import com.redside.rngquest.managers.HUDManager;
import com.redside.rngquest.managers.Sound;
import com.redside.rngquest.managers.SoundEffect;

public class CharSelectButton extends Button {
    private int selection;
    /**
     *
     * @param image The {@link Bitmap} image to be used for the button
     * @param x The x position of the button
     * @param y The y position of the button
     * @param selection The index to select once pressed
     */
    public CharSelectButton(Bitmap image, int x, int y, int selection){
        super(image, x, y);
        this.selection = selection;
    }
    /**
     * Called when the button is pressed.
     * Selects a character, according to the selection index.
     */
    @Override
    public void trigger(){
        Sound.playSound(SoundEffect.SELECT);
        HUDManager.selection = selection;
        Player.spawn(selection);
    }
}
