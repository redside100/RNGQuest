package com.redside.rngquest.gameobjects;

import android.graphics.Bitmap;

public abstract class Item {
    private ItemType itemType;
    private String description;
    private ClassItem classItem;
    private int cost;
    private Bitmap bitmap;

    public Item(ItemType itemType, ClassItem classItem, String description, int cost, Bitmap bitmap){
        this.itemType = itemType;
        this.classItem = classItem;
        this.cost = cost;
        this.bitmap = bitmap;
        this.description = description;
    }

    public abstract void use();
    public int getCost(){
        return cost;
    }
    public String getDescription(){
        return description;
    }
    public Bitmap getBitmap(){
        return bitmap;
    }
    public ItemType getItemType(){
        return itemType;
    }
    public ClassItem getClassItem(){
        return classItem;
    }
    public boolean equals(Object object){
        if (object instanceof Item){
            Item item = (Item) object;
            if (item.itemType.equals(itemType) && item.classItem.equals(classItem)
                    && item.cost == cost){
                return true;
            }
        }
        return false;
    }

    public enum ItemType{
        FIREBALL_SPELL, ARMOR_SPELL, TRIPLE_ATTACK_SPELL, AGILITY_SPELL, SMALL_POTION, LARGE_POTION, MANA_POTION
    }
    public enum ClassItem{
        MAGE, WARRIOR, TANK, ALL
    }
}
