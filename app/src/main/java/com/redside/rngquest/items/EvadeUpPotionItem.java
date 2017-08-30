package com.redside.rngquest.items;

import android.graphics.Color;

import com.redside.rngquest.entities.Player;
import com.redside.rngquest.gameobjects.Item;
import com.redside.rngquest.managers.AnimatedTextManager;
import com.redside.rngquest.managers.CoreManager;
import com.redside.rngquest.managers.HUDManager;
import com.redside.rngquest.utils.Assets;


public class EvadeUpPotionItem extends Item {
    public EvadeUpPotionItem(){
        super(ItemType.MANA_POTION, Player.Role.ALL, "Evade Up: Increases evade by 2%, up to 75%" , 240, Assets.getBitmapFromMemory("items_evadeup_potion"), 4);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void use(){
        // Display text, and add evade
        HUDManager.displayFadeMessage("Evade increased by 2%", CoreManager.width / 2, (int) (CoreManager.height * 0.31), 30, 18, Color.GREEN);
        Player.addEvade(2);
    }
}
