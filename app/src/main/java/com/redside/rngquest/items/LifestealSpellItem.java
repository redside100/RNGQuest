package com.redside.rngquest.items;

import android.graphics.Color;

import com.redside.rngquest.entities.Player;
import com.redside.rngquest.gameobjects.Item;
import com.redside.rngquest.managers.BattleManager;
import com.redside.rngquest.managers.CoreManager;
import com.redside.rngquest.managers.HUDManager;
import com.redside.rngquest.utils.Assets;


public class LifestealSpellItem extends Item {
    public LifestealSpellItem(){
        super(ItemType.LIFESTEAL_SPELL, Player.Role.ALL, "Lifesteal: Attack and recover 35% of the damage as HP" , 250, 4,
                Assets.getBitmapFromMemory("items_lifesteal_spell"), Assets.getBitmapFromMemory("button_lifesteal"), 7);
    }

    @Override
    public void use(){
        // Check for mana
        if (Player.hasEnoughMana(4)){
            // Display text, and go to attack battle state, while toggling lifesteal
            Player.removeMana(4);
            HUDManager.displayFadeMessage("Lifesteal", CoreManager.width / 2, (int) (CoreManager.height * 0.75), 30, 15, Color.GREEN);
            BattleManager.setBattleState(BattleManager.BattleState.PLAYER_ATTACK);
            Player.toggleLifesteal();
        }else{
            HUDManager.displayFadeMessage("Not enough mana!", CoreManager.width / 2, (int) (CoreManager.height * 0.75), 30, 15, Color.RED);
        }
    }
}
