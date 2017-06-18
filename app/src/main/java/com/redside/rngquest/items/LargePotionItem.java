package com.redside.rngquest.items;

import android.graphics.Color;

import com.redside.rngquest.entities.Player;
import com.redside.rngquest.gameobjects.Item;
import com.redside.rngquest.managers.AnimatedTextManager;
import com.redside.rngquest.managers.CoreManager;
import com.redside.rngquest.managers.HUDManager;
import com.redside.rngquest.utils.Assets;


public class LargePotionItem extends Item {
    public LargePotionItem(){
        super(ItemType.LARGE_POTION, Player.Role.ALL, "Large Potion: Restores 75 HP" , 100, Assets.getBitmapFromMemory("items_large_potion"), 1);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void use(){
        // Display text, and heal
        AnimatedTextManager.clear();
        HUDManager.displayFadeMessage("Recovered 75 HP", CoreManager.width / 2, CoreManager.height / 3, 30, 18, Color.GREEN);
        Player.heal(75);
    }
}
