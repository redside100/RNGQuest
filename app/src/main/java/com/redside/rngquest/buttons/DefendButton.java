package com.redside.rngquest.buttons;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.redside.rngquest.entities.Player;
import com.redside.rngquest.gameobjects.Button;
import com.redside.rngquest.managers.BattleManager;
import com.redside.rngquest.managers.HUDManager;

public class DefendButton extends Button {
    public DefendButton(Bitmap image, int x, int y){
        super(image, x, y);
    }
    @Override
    public void trigger(){
        BattleManager.playerDefend();
        HUDManager.displayFadeMessage("Lol this button does nothing", (HUDManager.width / 2), (HUDManager.height / 2), 2, 100, Color.RED);
    }
}
