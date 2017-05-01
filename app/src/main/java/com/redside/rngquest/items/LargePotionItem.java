package com.redside.rngquest.items;

import com.redside.rngquest.entities.Player;
import com.redside.rngquest.gameobjects.Item;
import com.redside.rngquest.utils.Assets;


public class LargePotionItem extends Item {
    public LargePotionItem(){
        super(ItemType.LARGE_POTION, ClassItem.ALL, "Large Potion: Restores 50% HP" , 120, Assets.getBitmapFromMemory("items_large_potion"));
    }

    @Override
    public void use(){
        Player.heal(Player.getMaxHP() / 2);
    }
}
