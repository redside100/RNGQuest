package com.redside.rngquest.items;

import android.graphics.Color;

import com.redside.rngquest.entities.Player;
import com.redside.rngquest.gameobjects.Item;
import com.redside.rngquest.managers.AnimatedTextManager;
import com.redside.rngquest.managers.CoreManager;
import com.redside.rngquest.managers.HUDManager;
import com.redside.rngquest.utils.Assets;


public class ManaPotionItem extends Item {
    public ManaPotionItem(){
        super(ItemType.MANA_POTION, Player.Role.ALL, "Mana Potion: Restores 40 MP" , 80, Assets.getBitmapFromMemory("items_mana_potion"), 2);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void use(){
        // Display text, and add mana
        HUDManager.displayFadeMessage("Recovered 40 MP", CoreManager.width / 2, (int) (CoreManager.height * 0.31), 30, 18, Color.BLUE);
        HUDManager.displayParticleEffect((int) (CoreManager.height * 0.28), (int) (CoreManager.height * 0.31), Color.BLUE);
        Player.addMana(40);
    }
}
