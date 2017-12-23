package com.redside.rngquest.items;

import android.graphics.Color;

import com.redside.rngquest.entities.Player;
import com.redside.rngquest.gameobjects.Item;
import com.redside.rngquest.managers.AnimatedTextManager;
import com.redside.rngquest.managers.CoreManager;
import com.redside.rngquest.managers.HUDManager;
import com.redside.rngquest.utils.Assets;


public class ManaUpPotionItem extends Item {
    public ManaUpPotionItem(){
        super(ItemType.MANA_POTION, Player.Role.ALL, "Mana Up: Increases max MP by 10" , 100, Assets.getBitmapFromMemory("items_manaup_potion"), 3);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void use(){
        // Display text, and add mana
        HUDManager.displayFadeMessage("Max MP increased by 10", CoreManager.width / 2, (int) (CoreManager.height * 0.31), 30, 18, Color.BLUE);
        HUDManager.displayParticleEffect((int) (CoreManager.height * 0.28), (int) (CoreManager.height * 0.31), Color.BLUE);
        Player.increaseMaxMana(10);
        Player.addMana(10);
    }
}
