package com.redside.rngquest.gameobjects;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
//ur bed

public class RNGQuest extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Used to determine what size everything should be scaled at. Only bad thing is that it requires API level 17+ due to getRealMetrics()
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        // Create new view with width and height of screen
        setContentView(new CoreView(this, width, height));
        View decorView = getWindow().getDecorView();
        // Set system flags to allow full screen and transparent non sticky navigation bar
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
    }
}
