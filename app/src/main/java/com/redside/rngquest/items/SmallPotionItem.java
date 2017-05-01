package com.redside.rngquest.items;

import com.redside.rngquest.entities.Player;
import com.redside.rngquest.gameobjects.Item;
import com.redside.rngquest.utils.Assets;


public class SmallPotionItem extends Item {
    public SmallPotionItem(){
        super(ItemType.SMALL_POTION, ClassItem.ALL, "Tonic: Restores 20% HP" , 50, Assets.getBitmapFromMemory("items_small_potion"));
    }

    @Override
    public void use(){
        Player.heal(Player.getMaxHP() / 5);
    }
}
