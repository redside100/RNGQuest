package com.redside.rngquest.buttons;

import android.graphics.Bitmap;

import com.redside.rngquest.entities.Player;
import com.redside.rngquest.gameobjects.Button;
import com.redside.rngquest.managers.HUDManager;

public class CharSelectButton extends Button {
    private int selection;
    public CharSelectButton(Bitmap image, int x, int y, int selection){
        super(image, x, y);
        this.selection = selection;
    }
    @Override
    public void trigger(){
        HUDManager.selection = selection;
        Player.spawn(selection);
    }
}
