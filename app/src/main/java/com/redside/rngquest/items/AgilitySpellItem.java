package com.redside.rngquest.items;

import android.graphics.Color;

import com.redside.rngquest.entities.Player;
import com.redside.rngquest.gameobjects.Item;
import com.redside.rngquest.managers.BattleManager;
import com.redside.rngquest.managers.CoreManager;
import com.redside.rngquest.managers.HUDManager;
import com.redside.rngquest.managers.SEManager;
import com.redside.rngquest.utils.Assets;


public class AgilitySpellItem extends Item {
    public AgilitySpellItem(){
        super(ItemType.AGILITY_SPELL, Player.Role.ALL, "Agility: Attack and defend on the same turn" , 250, 4,
                Assets.getBitmapFromMemory("items_agility_spell"), Assets.getBitmapFromMemory("button_agility"));
    }

    @Override
    public void use(){
        // Check for mana
        if (Player.hasEnoughMana(4)){
            // Display text, and go to fireball battle state
            Player.removeMana(4);
            SEManager.playEffect(SEManager.Effect.PURPLE_FLASH);
            HUDManager.displayFadeMessage("Agility", CoreManager.width / 2, (int) (CoreManager.height * 0.75), 30, 30, Color.rgb(180, 50, 255));
            BattleManager.playerAttack();
            Player.toggleAgility();
        }else{
            HUDManager.displayFadeMessage("Not enough mana!", CoreManager.width / 2, (int) (CoreManager.height * 0.75), 30, 30, Color.RED);
        }
    }
}
