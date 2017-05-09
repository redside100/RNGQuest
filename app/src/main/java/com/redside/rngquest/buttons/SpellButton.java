package com.redside.rngquest.buttons;

import android.graphics.Bitmap;

import com.redside.rngquest.gameobjects.Button;
import com.redside.rngquest.managers.BattleManager;

public class SpellButton extends Button {
    public SpellButton(Bitmap image, int x, int y){
        super(image, x, y);
    }
    @Override
    public void trigger(){
        BattleManager.playerSpell();
    }
}
