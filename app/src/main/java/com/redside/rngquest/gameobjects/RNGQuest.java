package com.redside.rngquest.gameobjects;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;


public class RNGQuest extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new CoreView(this));
    }
}
