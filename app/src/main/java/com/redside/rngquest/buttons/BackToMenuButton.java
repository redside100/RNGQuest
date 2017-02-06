package com.redside.rngquest.buttons;


import android.graphics.Bitmap;

import com.redside.rngquest.gameobjects.Button;
import com.redside.rngquest.managers.SEManager;
import com.redside.rngquest.managers.ScreenState;

public class BackToMenuButton extends Button {
    public BackToMenuButton(Bitmap image, int x, int y){
        super(image, x, y);
    }
    @Override
    public void trigger(){
        SEManager.playEffect(SEManager.Effect.FADE_TRANSITION, ScreenState.TITLE);
    }
}
