package com.redside.rngquest.gameobjects;

import android.graphics.Bitmap;

import com.redside.rngquest.entities.Player;
import com.redside.rngquest.items.AgilitySpellItem;
import com.redside.rngquest.items.EvadeUpPotionItem;
import com.redside.rngquest.items.FireballSpellItem;
import com.redside.rngquest.items.LargePotionItem;
import com.redside.rngquest.items.LifestealSpellItem;
import com.redside.rngquest.items.ManaPotionItem;
import com.redside.rngquest.items.ManaUpPotionItem;
import com.redside.rngquest.items.SmallPotionItem;

public class Item {
    private ItemType itemType;
    private String description;
    private Player.Role role;
    private int id;
    private int cost;
    private int manaCost;
    private Bitmap bitmap;
    private Bitmap buttonBitmap;

    // Constructor for consumable items
    public Item(ItemType itemType, Player.Role role, String description, int cost, Bitmap bitmap, int id){
        this.itemType = itemType;
        this.role = role;
        this.cost = cost;
        this.manaCost = 0;
        this.bitmap = bitmap;
        this.description = description;
        buttonBitmap = null;
        this.id = id;
    }
    // Constructor for spell items
    public Item(ItemType itemType, Player.Role role, String description, int cost, int manaCost, Bitmap bitmap, Bitmap buttonBitmap, int id){
        this.itemType = itemType;
        this.role = role;
        this.cost = cost;
        this.manaCost = manaCost;
        this.bitmap = bitmap;
        this.description = description;
        this.buttonBitmap = buttonBitmap;
        this.id = id;
    }

    public void use(){}
    public int getCost(){
        return cost;
    }
    public int getManaCost() {return manaCost;}
    public String getDescription(){
        return description;
    }
    public Bitmap getBitmap(){
        return bitmap;
    }
    public Bitmap getButtonBitmap() {
        return buttonBitmap;
    }
    public ItemType getItemType(){
        return itemType;
    }
    public Player.Role getRole(){
        return role;
    }
    public boolean equals(Object object){
        if (object instanceof Item){
            Item item = (Item) object;
            if (item.itemType.equals(itemType) && item.role.equals(role)
                    && item.cost == cost){
                return true;
            }
        }
        return false;
    }
    public int getId(){
        return id;
    }
    public static boolean isSpell(Item item){
        // Check item types
        ItemType type = item.getItemType();
        switch(type){
            case FIREBALL_SPELL:
            case ARMOR_SPELL:
            case LIFESTEAL_SPELL:
            case TRIPLE_ATTACK_SPELL:
            case AGILITY_SPELL:
                return true;
        }
        return false;
    }
    public static Item getItemById(int id){
        switch(id){
            case 0:
                return new SmallPotionItem();
            case 1:
                return new LargePotionItem();
            case 2:
                return new ManaPotionItem();
            case 3:
                return new ManaUpPotionItem();
            case 4:
                return new EvadeUpPotionItem();
            case 5:
                return new FireballSpellItem();
            case 6:
                return new AgilitySpellItem();
            case 7:
                return new LifestealSpellItem();
        }
        return null;
    }

    public enum ItemType{
        FIREBALL_SPELL, LIFESTEAL_SPELL, ARMOR_SPELL, TRIPLE_ATTACK_SPELL, AGILITY_SPELL, SMALL_POTION, LARGE_POTION, MANA_POTION, MANAUP_POTION, EVADEUP_POTION
    }
}
