package com.redside.rngquest.gameobjects;

import android.graphics.Bitmap;

import com.redside.rngquest.entities.Player;

public class Item {
    private ItemType itemType;
    private String description;
    private Player.Role role;
    private int cost;
    private int manaCost;
    private Bitmap bitmap;
    private Bitmap buttonBitmap;

    // Constructor for consumable items
    public Item(ItemType itemType, Player.Role role, String description, int cost, Bitmap bitmap){
        this.itemType = itemType;
        this.role = role;
        this.cost = cost;
        this.manaCost = 0;
        this.bitmap = bitmap;
        this.description = description;
        buttonBitmap = null;
    }
    // Constructor for spell items
    public Item(ItemType itemType, Player.Role role, String description, int cost, int manaCost, Bitmap bitmap, Bitmap buttonBitmap){
        this.itemType = itemType;
        this.role = role;
        this.cost = cost;
        this.manaCost = manaCost;
        this.bitmap = bitmap;
        this.description = description;
        this.buttonBitmap = buttonBitmap;
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
    public static boolean isSpell(Item item){
        // Check item types
        ItemType type = item.getItemType();
        switch(type){
            case FIREBALL_SPELL:
            case ARMOR_SPELL:
            case TRIPLE_ATTACK_SPELL:
            case AGILITY_SPELL:
                return true;
        }
        return false;
    }

    public enum ItemType{
        FIREBALL_SPELL, ARMOR_SPELL, TRIPLE_ATTACK_SPELL, AGILITY_SPELL, SMALL_POTION, LARGE_POTION, MANA_POTION
    }
}
