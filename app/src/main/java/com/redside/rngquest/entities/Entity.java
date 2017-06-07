package com.redside.rngquest.entities;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.redside.rngquest.managers.CoreManager;
import com.redside.rngquest.managers.EntityManager;
import com.redside.rngquest.managers.HUDManager;

public class Entity {

    public Object i;
    public int hp, maxHp, atk;
    public double x, y, oldX;
    public String name;
    public EAState state = EAState.IDLE;
    public int currAlpha = 0;
    public boolean shaking = false, fadingIn = false, fadingOut = false, alive = false;
    private int leftTick = 0;
    private int rightTick = 0;
    private int fadeTick = 0;
    private int shakeTick = 0;
    private int maxShakeTicks = 0;
    private int maxFadeTicks = 0;
    private boolean shakeLeft = false, shakeRight = false;

    /**
     *
     * @param name The name of the Entity
     * @param hp The starting and max HP of the Entity
     * @param atk The attack value of the Entity
     * @param x The x position of the Entity
     * @param y The y position of the Entity
     * @param startingAlpha The starting opacity of the Entity
     */
    public Entity(String name, int hp, int atk, int x, int y, int startingAlpha){

        currAlpha = startingAlpha;
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
        this.atk = atk;
        this.x = x;
        this.y = y;
        this.oldX = x;

        // Used for comparing this object in lists
        i = this;

        spawn();
    }

    /**
     * Sets the entity to a new animation state.
     * @param newState The new {@link EAState} to switch to
     */
    public void setState(EAState newState){
        state = newState;
    }

    /**
     * Returns the Entity's name.
     * @return The name
     */
    public String getName(){
        return name;
    }

    /**
     * Returns the current animation state.
     * @return The {@link EAState}
     */
    public EAState getState(){
        return state;
    }

    /**
     * Sets the Entity's max HP.
     * @param maxHp The desired max HP value
     */
    public void setMaxHP(int maxHp){
        this.maxHp = maxHp;
    }

    /**
     * Returns the Entity's current HP.
     * @return The HP value
     */
    public int getHP(){
        return hp;
    }

    /**
     * Returns the Entity's max HP.
     * @return The max HP value
     */
    public int getMaxHP(){
        return maxHp;
    }

    /**
     * Returns the Entity's attack value.
     * @return The attack value
     */
    public int getAtk(){
        return atk;
    }

    /**
     * Damages the Entity, causing it to lose HP.
     * @param amount The amount of the damage
     */
    public void damage(int amount){
        if (hp - amount > 0){
            hp -= amount;
        }else{
            hp = 0;
        }
    }

    /**
     * Heals the Entity, causing it to gain HP.
     * @param amount The amount of the healing
     */
    public void heal(int amount){
        if (hp + amount < maxHp){
            hp += amount;
        }else{
            hp = maxHp;
        }
    }

    /**
     * Checks if the Entity is dead.
     * @return {@code true} if Entity is dead
     */
    public boolean isDead(){
        return (hp == 0);
    }

    /**
     * Called when the game ticks.
     * Handles shaking and fading.
     */
    public void tick(){
        if (shaking){

            // Start by shaking left
            if (shakeTick == 0){
                shakeLeft = true;
                shakeRight = false;
            }
            // Make sure it hasn't reached the max ticks
            if (shakeTick < maxShakeTicks){

                shakeTick++;


                if (shakeLeft){
                    x -= HUDManager.getSpeed(CoreManager.width, 600);
                    leftTick += 1;
                    // Allow seven ticks left
                    if (leftTick == 7){
                        // Switch to right
                        leftTick = 0;
                        shakeLeft = false;
                        shakeRight = true;
                    }
                }
                if (shakeRight){
                    x += HUDManager.getSpeed(CoreManager.width, 600);
                    rightTick += 1;
                    // Allow seven ticks right
                    if (rightTick == 7){
                        // Switch to left
                        rightTick = 0;
                        shakeRight = false;
                        shakeLeft = true;
                    }
                }
            }
            else{
                // Maximum ticks reached, reset
                shaking = false;
                shakeTick = 0;
                maxShakeTicks = 0;
                leftTick = 0;
                rightTick = 0;
                x = oldX;
            }
        }
        if (fadingIn){
            if (fadeTick < maxFadeTicks){
                // Increment the alpha evenly according to the total tick time
                int amount = 255 / maxFadeTicks + 1;
                fadeTick++;

                if (currAlpha + amount < 255){
                    currAlpha += amount;
                }else{
                    currAlpha = 255;
                }
            }else{
                // Maximum ticks reach, reset
                fadingIn = false;
                fadeTick = 0;
                maxFadeTicks = 0;
            }
        }
        if (fadingOut){
            if (fadeTick < maxFadeTicks){
                // Inc alpha evenly
                int amount = 255 / maxFadeTicks + 1;
                fadeTick++;

                if (currAlpha - amount > 0){
                    currAlpha -= amount;
                }else{
                    currAlpha = 0;
                }
            }else{
                // Max reached, reset
                fadingOut = false;
                fadeTick = 0;
                maxFadeTicks = 0;
            }
        }
    }

    /**
     * Shakes the Entity, left to right.
     * @param ticks The amount of time to shake, in ticks
     */
    public void shake(int ticks){
        shaking = true;
        maxShakeTicks = ticks;
        oldX = x;
    }

    /**
     * Spawns the Entity, causing it to be rendered and ticked.
     */
    public void spawn(){
        alive = true;
        EntityManager.addEntity(this);
    }

    /**
     * Destroys the Entity, causing it to no longer be rendered and ticked.
     */
    public void destroy(){
        alive = false;
        EntityManager.removeEntity(this);
    }

    /**
     * Fades the Entity in.
     * @param ticks The amount of time to fade in, in ticks
     */
    public void fadeIn(int ticks){
        fadingIn = true;
        maxFadeTicks = ticks;
    }

    /**
     * Fades the Entity out.
     * @param ticks The amount of time to fade out, in ticks
     */
    public void fadeOut(int ticks){
        fadingOut = true;
        maxFadeTicks = ticks;
    }

    /**
     * Called when the game renders.
     * Renders the Entity onto the screen.
     * @param canvas The {@link Canvas} object to render to
     * @param paint The {@link Paint} object to render with
     */
    public void render(Canvas canvas, Paint paint){}

    /**
     * Checks if the Entity is equal to another object.
     * @param obj The object to compare to
     * @return {@code true} if they are equal
     */
    public boolean equals(Object obj){
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof Entity)) return false;
        Entity o = (Entity) obj;
        return o.i == this.i;
    }
}
