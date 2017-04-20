package com.redside.rngquest.utils;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.redside.rngquest.managers.CoreManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class Assets {
    private static HashMap<String, Bitmap> bitmapDb;
    private static HashMap<String, AssetFileDescriptor> soundDb;
    private Context context;
    public Assets(Context context){
        this.context = context;
        bitmapDb = new HashMap<>();
        soundDb = new HashMap<>();
        init();
    }
    public void init(){

        bitmapDb.put("background_black", getBitmap(context, "backgrounds/black.png"));
        bitmapDb.put("background_title1", getBitmap(context, "backgrounds/title1.png"));
        bitmapDb.put("background_title2", getBitmap(context, "backgrounds/title2.png"));
        bitmapDb.put("background_title_clouds", getBitmap(context, "backgrounds/title_clouds.png"));
        bitmapDb.put("background_forest", getBitmap(context, "backgrounds/forest.png"));

        bitmapDb.put("sprites_block", getBitmap(context, "sprites/block.png"));
        bitmapDb.put("sprites_wizard", getBitmap(context, "sprites/wizard.png"));
        bitmapDb.put("sprites_warrior", getBitmap(context, "sprites/warrior.png"));
        bitmapDb.put("sprites_tank", getBitmap(context, "sprites/tank.png"));

        for (int i = 0; i < 8; i++){
            bitmapDb.put("sprites_slash_" + i, getBitmap(context, "sprites/slash/slash" + i + ".png"));
        }

        for (int i = 0; i < 3; i++){
            bitmapDb.put("sprites_ghost_" + i, getBitmap(context, "sprites/ghost/ghost" + i + ".png"));
        }

        for (int i = 0; i < 10; i++){
            bitmapDb.put("sprites_wizard_idle_" + i, getBitmap(context, "sprites/wizard/wizard_idle/wizard_idle" + i + ".png"));
        }

        for (int i = 0; i < 8; i++){
            bitmapDb.put("sprites_wizard_attack_" + i, getBitmap(context, "sprites/wizard/wizard_attack/wizard_attack" + i + ".png"));
        }

        bitmapDb.put("sprites_wizard_damage", getBitmap(context, "sprites/wizard/wizard_damage.png"));

        bitmapDb.put("button_play", getBitmap(context, "buttons/play.png"));
        bitmapDb.put("button_back", getBitmap(context, "buttons/back.png"));
        bitmapDb.put("button_info", getBitmap(context, "buttons/info.png"));
        bitmapDb.put("button_start", getBitmap(context, "buttons/start.png"));
        bitmapDb.put("button_attack", getBitmap(context, "buttons/attack.png"));
        bitmapDb.put("button_defend", getBitmap(context, "buttons/defend.png"));

        bitmapDb.put("icons_hp", getBitmap(context, "icons/hp.png"));
        bitmapDb.put("icons_armor", getBitmap(context, "icons/armor.png"));
        bitmapDb.put("icons_evade", getBitmap(context, "icons/evade.png"));
        bitmapDb.put("icons_swords", getBitmap(context, "icons/swords.png"));

        soundDb.put("title_theme", getSoundDesc(context, "sounds/title.ogg"));
        soundDb.put("shop_theme", getSoundDesc(context, "sounds/shop.ogg"));
        soundDb.put("wave_theme", getSoundDesc(context, "sounds/wave.ogg"));
        soundDb.put("battle_theme", getSoundDesc(context, "sounds/battle.ogg"));

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
    public static AssetFileDescriptor getSoundDesc(Context context, String filePath){
        try{
            AssetFileDescriptor afd = context.getAssets().openFd(filePath);
            return afd;
        }catch(IOException e){
            return null;
        }
    }
    public static AssetFileDescriptor getSoundDescFromMemory(String name){
        if (soundDb.containsKey(name)){
            return soundDb.get(name);
        }
        return null;
    }
    public static Bitmap getBitmapFromMemory(String name){
        if (bitmapDb.containsKey(name)){
            return bitmapDb.get(name);
        }
        return null;
    }
}
