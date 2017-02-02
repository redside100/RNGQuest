package com.redside.rngquest.buttons;


import android.graphics.Bitmap;

import com.redside.rngquest.gameobjects.Button;

public class MenuPlayButton extends Button {
    public MenuPlayButton(Bitmap image, int x, int y, String text, int textSize){
        super(image, x, y, text, textSize);
    }
    @Override
    public void trigger(){
        System.out.println("Play button clicked");
    }
}
