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
}
