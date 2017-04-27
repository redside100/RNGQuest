package com.redside.rngquest.gameobjects;


import java.util.ArrayList;

public class Inventory {
    public ArrayList<Item> items;
    public Inventory(ArrayList<Item> items){
        this.items = new ArrayList<>(items);
    }
    public Inventory(){
        this.items = new ArrayList<>();
    }
    public void addItem(Item item){
        items.add(item);
    }
    public void removeItem(Item item){
        if (items.contains(item)){
            items.remove(item);
        }
    }
    public boolean hasItem(Item item){
        return items.contains(item);
    }
}
