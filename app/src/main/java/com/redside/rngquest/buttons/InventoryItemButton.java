package com.redside.rngquest.buttons;

import android.graphics.Bitmap;

import com.redside.rngquest.gameobjects.Button;
import com.redside.rngquest.managers.GameManager;
import com.redside.rngquest.managers.Sound;
import com.redside.rngquest.managers.SoundEffect;

public class InventoryItemButton extends Button {
    private int selection;
    public InventoryItemButton(Bitmap image, int x, int y, int selection){
        super(image, x, y);
        this.selection = selection;
    }
    @Override
    public void trigger(){
        Sound.playSound(SoundEffect.SELECT);
        if (GameManager.invSelection == selection){
            GameManager.useInventoryItem(selection);
        }else{
            GameManager.invSelection = selection;
        }
    }
}
