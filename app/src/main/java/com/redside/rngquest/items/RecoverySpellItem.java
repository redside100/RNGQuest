package com.redside.rngquest.items;

import android.graphics.Color;

import com.redside.rngquest.entities.Player;
import com.redside.rngquest.gameobjects.Item;
import com.redside.rngquest.managers.BattleManager;
import com.redside.rngquest.managers.CoreManager;
import com.redside.rngquest.managers.HUDManager;
import com.redside.rngquest.managers.SEManager;
import com.redside.rngquest.utils.Assets;


public class RecoverySpellItem extends Item {
    public RecoverySpellItem(){
        super(ItemType.AGILITY_SPELL, Player.Role.ALL, "Recovery Orb: +1 HP, +1 AMR each encounter" , 70, 0,
                Assets.getBitmapFromMemory("items_recovery_spell"), Assets.getBitmapFromMemory("button_recovery"), 8);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void use(){
        HUDManager.displayFadeMessage("Passive", CoreManager.width / 2, (int) (CoreManager.height * 0.75), 0, 15, Color.rgb(180, 50, 255));
    }
}
