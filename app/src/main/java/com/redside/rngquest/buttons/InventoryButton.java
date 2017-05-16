package com.redside.rngquest.buttons;

import android.graphics.Bitmap;

import com.redside.rngquest.gameobjects.Button;
import com.redside.rngquest.managers.BattleManager;
import com.redside.rngquest.managers.Sound;

public class InventoryButton extends Button {
    public InventoryButton(Bitmap image, int x, int y){
        super(image, x, y);
    }
    @Override
    public void trigger(){
        BattleManager.playerInventory();
    }
}
