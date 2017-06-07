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
    /**
     *
     * @param image The {@link Bitmap} image to be used for the button
     * @param x The x position of the button
     * @param y The y position of the button
     */
    public LoadButton(Bitmap image, int x, int y){
        super(image, x, y);
    }
    /**
     * Called when the button is pressed.
     * Tries to read save info, and load Player statistics accordingly.
     */
    @Override
    public void trigger() {
        // Play the select sound, and load the save info
        Sound.playSound(SoundEffect.SELECT);
        ArrayList<String> saveInfo = CoreView.getSave(CoreManager.context);
        // Loop through each line
        for (String line : saveInfo) {
            // Split the properties from the values
            String property = line.split(": ")[0];
            String value = line.split(": ")[1];

            // Check if the save file is complete (available should be true)
            if (property.startsWith("available")) {
                if (value.equalsIgnoreCase("true")) {
                    // The save file is complete, so load the player save
                    Player.spawnFromSave();
                    SEManager.playEffect(SEManager.Effect.FADE_TRANSITION, ScreenState.LOAD);
                } else {
                    // No save file, show a message
                    HUDManager.displayFadeMessage("No save data found", CoreManager.width / 2, (int) (CoreManager.height * 0.85), 30, 15, Color.RED);
                }
            }
        }
    }
}
