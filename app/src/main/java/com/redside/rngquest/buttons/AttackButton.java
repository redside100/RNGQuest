package com.redside.rngquest.buttons;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.redside.rngquest.gameobjects.Button;
import com.redside.rngquest.hudobjects.FadedText;
import com.redside.rngquest.managers.BattleManager;
import com.redside.rngquest.managers.CoreManager;
import com.redside.rngquest.managers.HUDManager;

public class AttackButton extends Button {
    /**
     *
     * @param image The {@link Bitmap} image to be used for the button
     * @param x The x position of the button
     * @param y The y position of the button
     */
    public AttackButton(Bitmap image, int x, int y){
        super(image, x, y);
    }

    /**
     * Called when the button is pressed.
     * Causes the Player to attack while in battle.
     */
    @Override
    public void trigger(){
        BattleManager.playerAttack();
    }
}
