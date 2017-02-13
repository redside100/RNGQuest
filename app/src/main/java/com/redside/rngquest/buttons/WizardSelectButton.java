package com.redside.rngquest.buttons;

import android.graphics.Bitmap;

import com.redside.rngquest.entities.Player;
import com.redside.rngquest.gameobjects.Button;
import com.redside.rngquest.managers.HUDManager;

public class WizardSelectButton extends Button {
    public WizardSelectButton(Bitmap image, int x, int y){
        super(image, x, y);
    }
    @Override
    public void trigger(){
        HUDManager.selection = 1;
        Player.spawn(1);
    }
}
