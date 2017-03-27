package com.redside.rngquest.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.redside.rngquest.managers.CoreManager;

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

        db.put("background_black", getBitmap(context, "backgrounds/black.png"));
        db.put("background_title1", getBitmap(context, "backgrounds/title1.png"));
        db.put("background_title2", getBitmap(context, "backgrounds/title2.png"));
        db.put("background_title_clouds", getBitmap(context, "backgrounds/title_clouds.png"));
        db.put("background_forest", getBitmap(context, "backgrounds/forest.png"));

        db.put("sprites_block", getBitmap(context, "sprites/block.png"));
        db.put("sprites_wizard", getBitmap(context, "sprites/wizard.png"));
        db.put("sprites_warrior", getBitmap(context, "sprites/warrior.png"));
        db.put("sprites_tank", getBitmap(context, "sprites/tank.png"));

        for (int i = 0; i < 8; i++){
            db.put("sprites_slash_" + i, getBitmap(context, "sprites/slash/slash" + i + ".png"));
        }

        for (int i = 0; i < 3; i++){
            db.put("sprites_ghost_" + i, getBitmap(context, "sprites/ghost/ghost" + i + ".png"));
        }

        db.put("button_play", getBitmap(context, "buttons/play.png"));
        db.put("button_back", getBitmap(context, "buttons/back.png"));
        db.put("button_info", getBitmap(context, "buttons/info.png"));
        db.put("button_start", getBitmap(context, "buttons/start.png"));
        db.put("button_attack", getBitmap(context, "buttons/attack.png"));
        db.put("button_defend", getBitmap(context, "buttons/defend.png"));

        db.put("icons_hp", getBitmap(context, "icons/hp.png"));
        db.put("icons_armor", getBitmap(context, "icons/armor.png"));
        db.put("icons_evade", getBitmap(context, "icons/evade.png"));
        db.put("icons_swords", getBitmap(context, "icons/swords.png"));

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
