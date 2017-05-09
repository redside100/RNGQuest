package com.redside.rngquest.items;

import android.graphics.Color;

import com.redside.rngquest.entities.ExplosionAnimation;
import com.redside.rngquest.entities.Player;
import com.redside.rngquest.gameobjects.Item;
import com.redside.rngquest.managers.BattleManager;
import com.redside.rngquest.managers.CoreManager;
import com.redside.rngquest.managers.HUDManager;
import com.redside.rngquest.utils.Assets;


public class FireballSpellItem extends Item {
    public FireballSpellItem(){
        super(ItemType.FIREBALL_SPELL, Player.Role.MAGE, "Fireball: 75% chance to deal 1.5x total ATK" , 300, 7,
                Assets.getBitmapFromMemory("items_fireball_spell"), Assets.getBitmapFromMemory("button_fireball"));
    }

    @Override
    public void use(){
        // Check for mana
        if (Player.hasEnoughMana(7)){
            // Display text, and go to fireball battle state
            Player.removeMana(7);
            HUDManager.displayFadeMessage("Fireball", CoreManager.width / 2, (int) (CoreManager.height * 0.75), 30, 30, Color.rgb(255, 80, 0));
            BattleManager.setBattleState(BattleManager.BattleState.PLAYER_FIREBALL);
        }else{
            HUDManager.displayFadeMessage("Not enough mana!", CoreManager.width / 2, (int) (CoreManager.height * 0.75), 30, 30, Color.RED);
        }
    }
}
