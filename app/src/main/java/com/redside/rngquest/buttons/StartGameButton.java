package com.redside.rngquest.buttons;


import android.graphics.Bitmap;

import com.redside.rngquest.gameobjects.Button;
import com.redside.rngquest.managers.CoreManager;
import com.redside.rngquest.managers.GameManager;
import com.redside.rngquest.managers.HUDManager;
import com.redside.rngquest.managers.SEManager;
import com.redside.rngquest.managers.ScreenState;
import com.redside.rngquest.managers.Sound;
import com.redside.rngquest.managers.SoundEffect;

public class StartGameButton extends Button {
    /**
     *
     * @param image The {@link Bitmap} image to be used for the button
     * @param x The x position of the button
     * @param y The y position of the button
     */
    public StartGameButton(Bitmap image, int x, int y){
        super(image, x, y);
    }
    /**
     * Called when the button is pressed.
     * Starts the game.
     */
    @Override
    public void trigger(){
        if (HUDManager.selection != 0 || CoreManager.state.equals(ScreenState.LOAD)){
            Sound.playSound(SoundEffect.SELECT);
            SEManager.playEffect(SEManager.Effect.FADE_TRANSITION, ScreenState.STAGE_TRANSITION);
        }
    }
}
