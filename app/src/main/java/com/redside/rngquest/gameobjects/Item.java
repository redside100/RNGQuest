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

    /**
     * Initializes a consumable item.
     * @param itemType The {@link ItemType} to identify
     * @param role The {@link Player.Role} the item belongs to
     * @param description The description of the item
     * @param cost The gold cost of the item
     * @param bitmap The item {@link Bitmap}
     * @param id The number ID of the item
     */
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

    /**
     * Initializes a spell item.
     * @param itemType The {@link ItemType} to identify
     * @param role The {@link Player.Role} the spell belongs to
     * @param description The description of the spell
     * @param cost The gold cost of the spell
     * @param manaCost The MP cost of the spell
     * @param bitmap The item {@link Bitmap} of the spell
     * @param buttonBitmap The button {@link Bitmap} of the spell
     * @param id The number ID of the spell item
     */
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

    /**
     * Called when the Item is used.
     */
    public void use(){}

    /**
     * Returns the gold cost of the Item.
     * @return The gold cost of the Item
     */
    public int getCost(){
        return cost;
    }

    /**
     * Returns the MP cost of the Item.
     * @return The MP cost of the Item
     */
    public int getManaCost() {return manaCost;}

    /**
     * Returns the description of the Item.
     * @return The description of the Item
     */
    public String getDescription(){
        return description;
    }

    /**
     * Returns the Item's {@link Bitmap}.
     * @return The Item's {@link Bitmap}
     */
    public Bitmap getBitmap(){
        return bitmap;
    }

    /**
     * Returns the Item's button {@link Bitmap}.
     * Returns null if the Item is a consumable.
     * @return The Item's button {@link Bitmap}
     */
    public Bitmap getButtonBitmap() {
        return buttonBitmap;
    }

    /**
     * Returns the {@link ItemType} of the Item.
     * @return The {@link ItemType} of the Item
     */
    public ItemType getItemType(){
        return itemType;
    }

    /**
     * Returns the {@link Player.Role} the Item belongs to
     * @return The {@link Player.Role} the Item belongs to
     */
    public Player.Role getRole(){
        return role;
    }

    /**
     * Checks if two Items are equal.
     * @param object The object to compare to
     * @return {@code true} if the two are equal
     */
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

    /**
     * Returns the Item's ID.
     * @return The Item's ID
     */
    public int getId(){
        return id;
    }

    /**
     * Checks if an Item is a spell.
     * @param item The {@link Item} to check
     * @return {@code true} if the {@link Item} is a spell
     */
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

    /**
     * Returns an {@link Item}, according to the ID given.
     * @param id The ID of the Item
     * @return The {@link Item}, according to the ID
     */
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
