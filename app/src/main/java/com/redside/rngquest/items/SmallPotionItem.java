package com.redside.rngquest.items;

import android.graphics.Color;

import com.redside.rngquest.entities.Player;
import com.redside.rngquest.gameobjects.Item;
import com.redside.rngquest.managers.AnimatedTextManager;
import com.redside.rngquest.managers.CoreManager;
import com.redside.rngquest.managers.HUDManager;
import com.redside.rngquest.utils.Assets;


public class SmallPotionItem extends Item {
    public SmallPotionItem(){
        super(ItemType.SMALL_POTION, Player.Role.ALL, "Tonic: Restores 30 HP" , 50, Assets.getBitmapFromMemory("items_small_potion"), 0);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void use(){
        // Display text, and heal
        HUDManager.displayFadeMessage("Recovered 30 HP", CoreManager.width / 2, (int) (CoreManager.height * 0.31), 30, 18, Color.GREEN);
        HUDManager.displayParticleEffect((int) (CoreManager.height * 0.28), (int) (CoreManager.height * 0.31), Color.GREEN);
        Player.heal(30);
    }
}
