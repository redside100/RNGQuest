package com.redside.rngquest.buttons;


import android.graphics.Bitmap;

import com.redside.rngquest.gameobjects.Button;
import com.redside.rngquest.managers.HUDManager;
import com.redside.rngquest.managers.SEManager;
import com.redside.rngquest.managers.ScreenState;

public class PlayButton extends Button {
    private ScreenState nextState;
    public PlayButton(Bitmap image, int x, int y, ScreenState nextState){
        super(image, x, y);
        this.nextState = nextState;
    }
    @Override
    public void trigger(){
        SEManager.playEffect(SEManager.Effect.FADE_TRANSITION, nextState);
    }
}
