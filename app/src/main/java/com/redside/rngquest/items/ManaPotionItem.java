package com.redside.rngquest.items;

import com.redside.rngquest.entities.Player;
import com.redside.rngquest.gameobjects.Item;
import com.redside.rngquest.utils.Assets;


public class ManaPotionItem extends Item {
    public ManaPotionItem(){
        super(ItemType.MANA_POTION, ClassItem.ALL, "Mana Potion: Restores 50% MP" , 75, Assets.getBitmapFromMemory("items_mana_potion"));
    }

    @Override
    public void use(){
        Player.heal(Player.getMaxHP() / 5);
    }
}
