package com.redside.rngquest.buttons;


import android.graphics.Bitmap;
import android.graphics.Color;

import com.redside.rngquest.gameobjects.Button;
import com.redside.rngquest.managers.AnimatedTextManager;
import com.redside.rngquest.managers.CoreManager;
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
        Sound.playSound(SoundEffect.SELECT);
        if (HUDManager.selection != 0 || CoreManager.state.equals(ScreenState.LOAD)){
            SEManager.playEffect(SEManager.Effect.FADE_TRANSITION, ScreenState.STAGE_TRANSITION);
//            SEManager.playEffect(SEManager.Effect.FADE_TRANSITION, ScreenState.SHOP);
        }else{
            AnimatedTextManager.clear();
            HUDManager.displayTypingText("Choose a character...", HUDManager.width / 2, (int) (HUDManager.height * 0.83), 2, 11, Color.rgb(0,191,255), true);
        }
    }
}
