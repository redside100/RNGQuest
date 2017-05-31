package com.redside.rngquest.buttons;


import android.graphics.Bitmap;
import android.graphics.Color;

import com.redside.rngquest.entities.Player;
import com.redside.rngquest.gameobjects.Button;
import com.redside.rngquest.gameobjects.CoreView;
import com.redside.rngquest.managers.CoreManager;
import com.redside.rngquest.managers.HUDManager;
import com.redside.rngquest.managers.SEManager;
import com.redside.rngquest.managers.ScreenState;
import com.redside.rngquest.managers.Sound;
import com.redside.rngquest.managers.SoundEffect;

import java.util.ArrayList;

public class LoadButton extends Button {
    public LoadButton(Bitmap image, int x, int y){
        super(image, x, y);
    }
    @Override
    public void trigger() {
        Sound.playSound(SoundEffect.SELECT);
        ArrayList<String> saveInfo = CoreView.getSave(CoreManager.context);
        for (String line : saveInfo) {
            String property = line.split(": ")[0];
            String value = line.split(": ")[1];
            if (property.startsWith("available")) {
                if (value.equalsIgnoreCase("true")) {
                    Player.spawnFromSave();
                    SEManager.playEffect(SEManager.Effect.FADE_TRANSITION, ScreenState.LOAD);
                } else {
                    HUDManager.displayFadeMessage("No save data found", CoreManager.width / 2, (int) (CoreManager.height * 0.85), 30, 15, Color.RED);
                }
            }
        }
    }
}
