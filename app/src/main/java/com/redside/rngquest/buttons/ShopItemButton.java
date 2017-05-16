package com.redside.rngquest.buttons;

import android.graphics.Bitmap;

import com.redside.rngquest.gameobjects.Button;
import com.redside.rngquest.managers.GameManager;
import com.redside.rngquest.managers.Sound;
import com.redside.rngquest.managers.SoundEffect;

public class ShopItemButton extends Button {
    private int selection;
    public ShopItemButton(Bitmap image, int x, int y, int selection){
        super(image, x, y);
        this.selection = selection;
    }
    @Override
    public void trigger(){
        Sound.playSound(SoundEffect.SELECT);
        if (GameManager.shopSelection == selection){
            GameManager.buyShopItem(selection);
        }else{
            GameManager.shopSelection = selection;
        }
    }
}
