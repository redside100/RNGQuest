package com.redside.rngquest.managers;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.redside.rngquest.entities.Entity;

import java.util.ArrayList;

/**
 * Manages all entities on the screen.
 * Renders and ticks entities that are alive.
 * @author Andrew Peng
 * @since July 19, 2017
 */
public class EntityManager {
    private static ArrayList<Entity> entities;
    public EntityManager(){
        entities = new ArrayList<>();
    }

    /**
     * Adds an {@link Entity} to the list.
     * @param entity The {@link Entity} to add
     */
    public static void addEntity(Entity entity){
        entities.add(entity);
    }

    /**
     * Removes an {@link Entity} from the list.
     * @param entity The {@link Entity} to remove
     */
    public static void removeEntity(Entity entity){
        entities.remove(entity);
    }

    /**
     * Removes all entities from the list.
     */
    public static void clear(){
        entities.clear();
    }

    /**
     * Called when the game ticks.
     * Ticks all entities.
     */
    public void tick(){
        // Create temp array to avoid concurrent modification exceptions
        ArrayList<Entity> temp = new ArrayList<>(entities);
        for (Entity e : temp){
            e.tick();
        }
    }

    /**
     * Called when the game renders.
     * Renders all entities.
     * @param canvas The {@link Canvas} to draw on
     * @param paint The {@link Paint} object to draw with
     */
    public void render(Canvas canvas, Paint paint){
        // Create temp array to avoid concurrent modification exceptions
        ArrayList<Entity> temp = new ArrayList<>(entities);
        for (Entity e : temp){
            e.render(canvas, paint);
        }
    }
}
