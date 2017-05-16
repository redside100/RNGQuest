package com.redside.rngquest.buttons;


import android.graphics.Bitmap;

import com.redside.rngquest.gameobjects.Button;
import com.redside.rngquest.managers.SEManager;
import com.redside.rngquest.managers.ScreenState;
import com.redside.rngquest.managers.Sound;
import com.redside.rngquest.managers.SoundEffect;

public class StateChangeButton extends Button {
    private ScreenState newState;
    public StateChangeButton(Bitmap image, int x, int y, ScreenState newState){
        super(image, x, y);
        this.newState = newState;
    }
    @Override
    public void trigger(){
        Sound.playSound(SoundEffect.SELECT);
        SEManager.playEffect(SEManager.Effect.FADE_TRANSITION, newState);
    }
}
