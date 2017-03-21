package com.redside.rngquest.buttons;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.redside.rngquest.gameobjects.Button;
import com.redside.rngquest.hudobjects.FadedText;
import com.redside.rngquest.managers.BattleManager;
import com.redside.rngquest.managers.CoreManager;
import com.redside.rngquest.managers.HUDManager;

public class AttackButton extends Button {
    public AttackButton(Bitmap image, int x, int y){
        super(image, x, y);
    }
    @Override
    public void trigger(){
        BattleManager.playerAttack();
        HUDManager.displayFadeMessage("Lol this button does nothing", (HUDManager.width / 2), (HUDManager.height / 2), 2, 125, Color.WHITE);
    }
}
