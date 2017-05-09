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
        super(ItemType.MANA_POTION, Player.Role.ALL, "Mana Potion: Restores 50% MP" , 75, Assets.getBitmapFromMemory("items_mana_potion"));
    }

    @Override
    public void use(){
        // Display text, and add mana
        int mp = Player.getMaxMana() / 2;
        AnimatedTextManager.clear();
        HUDManager.displayFadeMessage("Recovered " + mp + "MP", CoreManager.width / 2, CoreManager.height / 3, 30, 35, Color.BLUE);
        Player.addMana(mp);
    }
}
