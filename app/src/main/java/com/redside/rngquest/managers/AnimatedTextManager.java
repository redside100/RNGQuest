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

    /**
     * Called when the game renders.
     * @param canvas The {@link Canvas} to draw on
     * @param paint The {@link Paint} object to draw with
     */
    public void render(Canvas canvas, Paint paint){
        // Create temp array to prevent concurrent modification exceptions
        ArrayList<AnimatedText> temp = new ArrayList<>(activeAnimatedTexts);
        for (AnimatedText s : temp){
            s.render(canvas, paint);
        }
    }

    /**
     * Called when the game ticks.
     */
    public void tick(){
        // Create temp array to prevent concurrent modification exceptions
        ArrayList<AnimatedText> temp = new ArrayList<>(activeAnimatedTexts);
        for (AnimatedText s : temp) {
            s.tick();
        }
    }
}
