package com.redside.rngquest.buttons;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.redside.rngquest.entities.Player;
import com.redside.rngquest.gameobjects.Button;
import com.redside.rngquest.managers.BattleManager;
import com.redside.rngquest.managers.HUDManager;

public class DefendButton extends Button {
    /**
     *
     * @param image The {@link Bitmap} image to be used for the button
     * @param x The x position of the button
     * @param y The y position of the button
     */
    public DefendButton(Bitmap image, int x, int y){
        super(image, x, y);
    }
    /**
     * Called when the button is pressed.
     * Causes the Player to defend while in battle.
     */
    @Override
    public void trigger(){
        BattleManager.playerDefend();
    }
}
