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
        super(ItemType.MANA_POTION, Player.Role.ALL, "Evade Up: Increases evade by 3%, up to 80%" , 220, Assets.getBitmapFromMemory("items_evadeup_potion"));
    }

    @Override
    public void use(){
        // Display text, and add evade
        AnimatedTextManager.clear();
        HUDManager.displayFadeMessage("Evade increased by 3%", CoreManager.width / 2, CoreManager.height / 3, 30, 35, Color.GREEN);
        Player.addEvade(3);
    }
}
