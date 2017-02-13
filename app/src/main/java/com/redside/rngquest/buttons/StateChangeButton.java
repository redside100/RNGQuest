package com.redside.rngquest.buttons;


import android.graphics.Bitmap;

import com.redside.rngquest.gameobjects.Button;
import com.redside.rngquest.managers.HUDManager;
import com.redside.rngquest.managers.SEManager;
import com.redside.rngquest.managers.ScreenState;

public class StateChangeButton extends Button {
    private ScreenState newState;
    public StateChangeButton(Bitmap image, int x, int y, ScreenState newState){
        super(image, x, y);
        this.newState = newState;
    }
    @Override
    public void trigger(){
        SEManager.playEffect(SEManager.Effect.FADE_TRANSITION, newState);
    }
}
