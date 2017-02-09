package com.redside.rngquest.buttons;

import android.graphics.Bitmap;

import com.redside.rngquest.gameobjects.Button;
import com.redside.rngquest.managers.HUDManager;

public class WarriorSelectButton extends Button {
    public WarriorSelectButton(Bitmap image, int x, int y){
        super(image, x, y);
    }
    @Override
    public void trigger(){
        HUDManager.selection = 2;
    }
}
