package com.redside.rngquest.managers;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.redside.rngquest.entities.Entity;

import java.util.ArrayList;

public class EntityManager {
    private static ArrayList<Entity> entities;
    public EntityManager(){
        entities = new ArrayList<>();
    }
    public static void addEntity(Entity entity){
        entities.add(entity);
    }
    public static void removeEntity(Entity entity){
        entities.remove(entity);
    }
    public static void clear(){
        entities.clear();
    }
    public void tick(){
        ArrayList<Entity> temp = new ArrayList<>(entities);
        for (Entity e : temp){
            e.tick();
            e.shadowTick();
        }
    }
    public void render(Canvas canvas, Paint paint){
        ArrayList<Entity> temp = new ArrayList<>(entities);
        for (Entity e : temp){
            e.render(canvas, paint);
        }
    }
}
