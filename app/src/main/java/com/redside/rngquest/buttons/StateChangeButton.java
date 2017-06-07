package com.redside.rngquest.buttons;


import android.graphics.Bitmap;

import com.redside.rngquest.gameobjects.Button;
import com.redside.rngquest.managers.SEManager;
import com.redside.rngquest.managers.ScreenState;
import com.redside.rngquest.managers.Sound;
import com.redside.rngquest.managers.SoundEffect;

public class StateChangeButton extends Button {
    private ScreenState newState;
    /**
     *
     * @param image The {@link Bitmap} image to be used for the button
     * @param x The x position of the button
     * @param y The y position of the button
     * @param newState The new {@link ScreenState} to transition to when pressed
     */
    public StateChangeButton(Bitmap image, int x, int y, ScreenState newState){
        super(image, x, y);
        this.newState = newState;
    }
    /**
     * Called when the button is pressed.
     * Transitions into a new {@link ScreenState}.
     */
    @Override
    public void trigger(){
        Sound.playSound(SoundEffect.SELECT);
        SEManager.playEffect(SEManager.Effect.FADE_TRANSITION, newState);
    }
}
