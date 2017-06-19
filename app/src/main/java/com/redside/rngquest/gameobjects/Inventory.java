package com.redside.rngquest.gameobjects;


import java.util.ArrayList;

/**
 * A inventory that has managing capabilities.
 * Stores {@link Item} objects.
 * @author Andrew Peng
 * @since July 19, 2017
 */
public class Inventory {
    public ArrayList<Item> items;

    /**
     *
     * @param items The {@link Item} {@link ArrayList} to begin with
     */
    public Inventory(ArrayList<Item> items){
        this.items = new ArrayList<>(items);
    }

    /**
     * Creates a new, empty Inventory.
     */
    public Inventory(){
        this.items = new ArrayList<>();
    }

    /**
     * Adds an {@link Item} into the Inventory.
     * @param item The {@link Item} to add
     */
    public void addItem(Item item){
        items.add(item);
    }

    /**
     * Removes an {@link Item} from the Inventory, if it contains it.
     * @param item The {@link Item} to remove
     */
    public void removeItem(Item item){
        if (items.contains(item)){
            items.remove(item);
        }
    }

    /**
     * Checks if the Inventory contains an {@link Item}.
     * @param item The {@link Item} to check for
     * @return {@code true} if the Inventory contains the {@link Item}
     */
    public boolean hasItem(Item item){
        return items.contains(item);
    }

    /**
     * Uses an item in the Inventory.
     * @param item The {@link Item} to use
     */
    public void useItem(Item item){
        if (items.contains(item)){
            item.use();
            removeItem(item);
        }
    }

    /**
     * Returns an {@link Item} {@link ArrayList} of the contents in the Inventory.
     * @return The contents of the Inventory
     */
    public ArrayList<Item> getItems(){
        return items;
    }

    /**
     * Clears the inventory.
     */
    public void clear(){
        items.clear();
    }
}
