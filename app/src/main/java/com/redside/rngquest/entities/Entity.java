package com.redside.rngquest.entities;

import android.graphics.Canvas;
import android.graphics.Paint;

public abstract class Entity {

    public Object i;
    public Entity(){
        i = this;
    }
    public void tick(){}
    public void render(Canvas canvas, Paint paint){}
    public boolean equals(Object obj){
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof Entity)) return false;
        Entity o = (Entity) obj;
        return o.i == this.i;
    }
}
