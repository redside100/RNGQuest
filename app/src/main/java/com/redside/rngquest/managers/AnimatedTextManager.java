package com.redside.rngquest.managers;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.redside.rngquest.hudobjects.AnimatedText;
import com.redside.rngquest.hudobjects.FadedText;

import java.util.ArrayList;

public class AnimatedTextManager {
    public static ArrayList<AnimatedText> activeAnimatedTexts = new ArrayList<>();
    public static void addText(AnimatedText text){
        activeAnimatedTexts.add(text);
    }
    public static void removeText(AnimatedText text){
        activeAnimatedTexts.remove(text);
    }
    public static void clear(){
        activeAnimatedTexts.clear();
    }
    public void render(Canvas canvas, Paint paint){
        ArrayList<AnimatedText> temp = new ArrayList<>(activeAnimatedTexts);
        for (AnimatedText s : temp){
            s.render(canvas, paint);
        }
    }
    public void tick(){
        ArrayList<AnimatedText> temp = new ArrayList<>(activeAnimatedTexts);
        for (AnimatedText s : temp) {
            s.tick();
        }
    }
}
