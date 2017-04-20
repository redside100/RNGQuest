package com.redside.rngquest.entities;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.redside.rngquest.managers.EntityManager;

public class Entity {

    public Object i;
    public int hp, maxHp, atk, x, y, oldX;
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
    public void tick(){

    }
    public void shadowTick(){
        if (shaking){
            if (shakeTick == 0){
                shakeLeft = true;
                shakeRight = false;
            }
            if (shakeTick < maxShakeTicks){
                shakeTick++;
                if (shakeLeft){
                    x -= 3;
                    leftTick += 1;
                    if (leftTick == 7){
                        leftTick = 0;
                        shakeLeft = false;
                        shakeRight = true;
                    }
                }
                if (shakeRight){
                    x += 3;
                    rightTick += 1;
                    if (rightTick == 7){
                        rightTick = 0;
                        shakeRight = false;
                        shakeLeft = true;
                    }
                }
            }
            else{
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
                int amount = 255 / maxFadeTicks + 1;
                fadeTick++;

                if (currAlpha + amount < 255){
                    currAlpha += amount;
                }else{
                    currAlpha = 255;
                }
            }else{
                fadingIn = false;
                fadeTick = 0;
                maxFadeTicks = 0;
            }
        }
        if (fadingOut){
            if (fadeTick < maxFadeTicks){
                int amount = 255 / maxFadeTicks + 1;
                fadeTick++;

                if (currAlpha - amount > 0){
                    currAlpha -= amount;
                }else{
                    currAlpha = 0;
                }
            }else{
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
