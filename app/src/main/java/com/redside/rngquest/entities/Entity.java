package com.redside.rngquest.entities;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Entity {

    public Object i;
    public int hp;
    public int atk;
    public int x;
    public int y;
    private int oldX;
    public EAState state = EAState.IDLE;
    private boolean shaking = false;
    private int lefttick = 0;
    private int righttick = 0;
    private int shaketick = 0;
    private int maxShakeTicks = 0;
    private boolean shakeleft = false, shakeright = false;
    public Entity(int hp, int atk, int x, int y){
        this.hp = hp;
        this.atk = atk;
        this.x = x;
        this.y = y;
        this.oldX = x;
        i = this;
    }
    public void setState(EAState newState){}
    public void tick(){

    }
    public void shadowTick(){
        if (shaking){
            if (shaketick == 0){
                shakeleft = true;
                shakeright = false;
            }
            if (shaketick < maxShakeTicks){
                shaketick++;
                if (shakeleft){
                    x -= 3;
                    lefttick += 1;
                    if (lefttick == 7){
                        lefttick = 0;
                        shakeleft = false;
                        shakeright = true;
                    }
                }
                if (shakeright){
                    x += 3;
                    righttick += 1;
                    if (righttick == 7){
                        righttick = 0;
                        shakeright = false;
                        shakeleft = true;
                    }
                }
            }
            else{
                shaking = false;
                shaketick = 0;
                maxShakeTicks = 0;
                lefttick = 0;
                righttick = 0;
                x = oldX;
            }
        }
    }
    public void shake(int ticks){
        shaking = true;
        maxShakeTicks = ticks;
        oldX = x;
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
