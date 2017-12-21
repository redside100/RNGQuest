package com.redside.rngquest.buttons;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.redside.rngquest.entities.Player;
import com.redside.rngquest.gameobjects.Button;
import com.redside.rngquest.managers.AnimatedTextManager;
import com.redside.rngquest.managers.HUDManager;
import com.redside.rngquest.managers.Sound;
import com.redside.rngquest.managers.SoundEffect;

public class CharSelectButton extends Button {
    private int selection;
    /**
     *
     * @param image The {@link Bitmap} image to be used for the button
     * @param x The x position of the button
     * @param y The y position of the button
     * @param selection The index to select once pressed
     */
    public CharSelectButton(Bitmap image, int x, int y, int selection){
        super(image, x, y);
        this.selection = selection;
    }
    /**
     * Called when the button is pressed.
     * Selects a character, according to the selection index.
     */
    @Override
    public void trigger(){
        AnimatedTextManager.clear();
        Sound.playSound(SoundEffect.SELECT);
        HUDManager.selection = selection;
        String character = "";
        // Draw depending on character selection
        switch(selection){
            case 1: // Mage
                character = "Mage: +12 ATK (30%), +20 HP, +60 MP, +65% EVA, +Fireball";
                break;
            case 2: // Warrior
                character = "Warrior: +12 ATK (60%), +50 HP, +10 MP, +15 AMR, +35% EVA";
                break;
            case 3: // Tank
                character = "Tank: +7 ATK (40%), +90 HP, +40 AMR, +20% EVA";
                break;
        }
        HUDManager.displayTypingText(character, HUDManager.width / 2, (int) (HUDManager.height * 0.81), 2, 11, Color.rgb(0,191,255), true);
        Player.spawn(selection);
    }
}
