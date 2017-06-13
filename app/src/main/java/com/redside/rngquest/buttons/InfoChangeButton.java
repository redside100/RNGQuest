package com.redside.rngquest.buttons;


import android.graphics.Bitmap;

import com.redside.rngquest.gameobjects.Button;
import com.redside.rngquest.managers.HUDManager;
import com.redside.rngquest.managers.SEManager;
import com.redside.rngquest.managers.ScreenState;
import com.redside.rngquest.managers.Sound;
import com.redside.rngquest.managers.SoundEffect;

public class InfoChangeButton extends Button {
    private ScreenState newState;
    /**
     *
     * @param image The {@link Bitmap} image to be used for the button
     * @param x The x position of the button
     * @param y The y position of the button
     */
    public InfoChangeButton(Bitmap image, int x, int y){
        super(image, x, y);
    }
    /**
     * Called when the button is pressed.
     * Transitions to the next info index.
     */
    @Override
    public void trigger(){
        Sound.playSound(SoundEffect.SELECT);
        if (HUDManager.infoState < 3){
            HUDManager.infoState++;
        }else{
            HUDManager.infoState = 0;
        }
    }
}
