package com.redside.rngquest.buttons;

import android.graphics.Bitmap;

import com.redside.rngquest.entities.Player;
import com.redside.rngquest.gameobjects.Button;
import com.redside.rngquest.managers.HUDManager;
import com.redside.rngquest.managers.Sound;
import com.redside.rngquest.managers.SoundEffect;

public class CharSelectButton extends Button {
    private int selection;
    public CharSelectButton(Bitmap image, int x, int y, int selection){
        super(image, x, y);
        this.selection = selection;
    }
    @Override
    public void trigger(){
        Sound.playSound(SoundEffect.SELECT);
        HUDManager.selection = selection;
        Player.spawn(selection);
    }
}
