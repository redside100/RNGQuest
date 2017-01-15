package com.redside.rngquest.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class Assets {
    private static HashMap<String, Bitmap> db;
    private Context context;
    public Assets(Context context){
        this.context = context;
        db = new HashMap<>();
        init();
    }
    public void init(){
        db.put("background_title", getBitmap(context, "backgrounds/title.png"));
    }
    public static Bitmap getBitmap(Context context, String filePath) {
        AssetManager assetManager = context.getAssets();
        InputStream istr;
        Bitmap bitmap = null;
        try {
            istr = assetManager.open(filePath);
            bitmap = BitmapFactory.decodeStream(istr);
        } catch (IOException e) {}

        return bitmap;
    }
    public static Bitmap getBitmapFromMemory(String name){
        if (db.containsKey(name)){
            return db.get(name);
        }
        return null;
    }
}
