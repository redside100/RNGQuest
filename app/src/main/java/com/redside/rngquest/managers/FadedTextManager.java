package com.redside.rngquest.managers;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.redside.rngquest.hudobjects.FadedText;

import java.util.ArrayList;

public class FadedTextManager {
    public static ArrayList<FadedText> activeFadedTexts = new ArrayList<>();
    public static void addText(FadedText text){
        activeFadedTexts.add(text);
    }
    public static void removeText(FadedText text){
        activeFadedTexts.remove(text);
    }
    public static void clear(){
        activeFadedTexts.clear();
    }
    public void render(Canvas canvas, Paint paint){
        ArrayList<FadedText> temp = new ArrayList<>(activeFadedTexts);
        for (FadedText s : temp){
            s.render(canvas, paint);
        }
    }
    public void tick(){
        ArrayList<FadedText> temp = new ArrayList<>(activeFadedTexts);
        for (FadedText s : temp) {
            s.tick();
        }
    }
}
