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

        EntityManager.addEntity(this);
        alive = true;
    }

    public void setState(EAState newState){
        state = newState;
    }
    public String getName(){
        return name;
    }
    public EAState getState(){
        return state;
    }
    public int getHP(){
        return hp;
    }
    public int getMaxHP(){
        return maxHp;
    }
    public int getAtk(){
        return atk;
    }
    public void damage(int amount){
        if (hp - amount > 0){
            hp -= amount;
        }else{
            hp = 0;
        }
    }
    public void heal(int amount){
        if (hp + amount < maxHp){
            hp += amount;
        }else{
            hp = maxHp;
        }
    }
    public boolean isDead(){
        return (hp == 0);
    }

    // To be overridden
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
    public void shake(int ticks){
        shaking = true;
        maxShakeTicks = ticks;
        oldX = x;
    }
    public void destroy(){
        alive = false;
        EntityManager.removeEntity(this);
    }
    public void fadeIn(int ticks){
        fadingIn = true;
        maxFadeTicks = ticks;
    }
    public void fadeOut(int ticks){
        fadingOut = true;
        maxFadeTicks = ticks;
    }
    public void render(Canvas canvas, Paint paint){}
    public boolean equals(Object obj){
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof Entity)) return false;
        Entity o = (Entity) obj;
        return o.i == this.i;
    }
}
