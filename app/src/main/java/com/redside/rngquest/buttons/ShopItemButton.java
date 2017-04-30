package com.redside.rngquest.buttons;

import android.graphics.Bitmap;

import com.redside.rngquest.gameobjects.Button;
import com.redside.rngquest.managers.GameManager;

public class ShopItemButton extends Button {
    private int selection;
    public ShopItemButton(Bitmap image, int x, int y, int selection){
        super(image, x, y);
        this.selection = selection;
    }
    @Override
    public void trigger(){
        GameManager.shopSelection = selection;
    }
}
