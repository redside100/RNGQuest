package com.redside.rngquest.utils;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

/**
 * Loads all assets into memory, ready to be used.
 * @author Andrew Peng
 * @since July 19, 2017
 */
public class Assets {
    private static HashMap<String, Bitmap> bitmapDb;
    private static HashMap<String, AssetFileDescriptor> soundDb;
    private static HashMap<String, String> messages;
    private Context context;
    private static int width;
    private static int height;
    public Assets(Context context, int width, int height){
        this.width = width;
        this.height = height;
        this.context = context;
        bitmapDb = new HashMap<>();
        soundDb = new HashMap<>();
        messages = new HashMap<>();
        init();
    }

    /**
     * Initializes all {@link Bitmap} image assets, and sound assets.
     */
    public void init(){

        // Backgrounds
        bitmapDb.put("background_black", getBitmap(context, "backgrounds/black.png"));
        bitmapDb.put("background_title_0", getBitmap(context, "backgrounds/title/title0.png"));
        bitmapDb.put("background_title_1", getBitmap(context, "backgrounds/title/title1.png"));
        bitmapDb.put("background_title_clouds", getBitmap(context, "backgrounds/title/title_clouds.png"));
        for (int i = 0; i < 3; i++){
            bitmapDb.put("background_forest_" + i, getBitmap(context, "backgrounds/forest/forest" + i + ".png"));
        }
        bitmapDb.put("background_mountains", getBitmap(context, "backgrounds/mountains.png"));
        bitmapDb.put("background_inventory", getBitmap(context, "backgrounds/inventory.png"));

        // Char portraits
        bitmapDb.put("sprites_block", getBitmap(context, "sprites/block.png"));
        bitmapDb.put("sprites_wizard", getBitmap(context, "sprites/wizard.png"));
        bitmapDb.put("sprites_warrior", getBitmap(context, "sprites/warrior.png"));
        bitmapDb.put("sprites_tank", getBitmap(context, "sprites/tank.png"));

        // Menus
        bitmapDb.put("menu_shop_items", getBitmap(context, "menus/shop_item_menu.png"));
        bitmapDb.put("menu_inventory_items", getBitmap(context, "menus/inventory_item_menu.png"));
        bitmapDb.put("menu_selected_item", getBitmap(context, "menus/selectedItem.png"));
        bitmapDb.put("menu_dialog", getBitmap(context, "menus/dialog_menu.png"));

        // Slash
        for (int i = 0; i < 8; i++){
            bitmapDb.put("sprites_slash_" + i, getBitmap(context, "sprites/slash/slash" + i + ".png"));
        }

        // Explosion
        for (int i = 0; i < 7; i++){
            bitmapDb.put("sprites_explosion_" + i, getBitmap(context, "sprites/explosion/explosion" + i + ".png"));
        }

        // Ghost all
        for (int i = 0; i < 3; i++){
            bitmapDb.put("sprites_ghost_" + i, getBitmap(context, "sprites/ghost/ghost" + i + ".png"));
        }

        // Wizard idle
        for (int i = 0; i < 10; i++){
            bitmapDb.put("sprites_wizard_idle_" + i, getBitmap(context, "sprites/wizard/wizard_idle/wizard_idle" + i + ".png"));
        }

        // Wizard attack
        for (int i = 0; i < 10; i++){
            bitmapDb.put("sprites_wizard_attack_" + i, getBitmap(context, "sprites/wizard/wizard_attack/wizard_attack" + i + ".png"));
        }
        // Wizard damage
        bitmapDb.put("sprites_wizard_damage", getBitmap(context, "sprites/wizard/wizard_damage.png"));

        // Blob idle
        for (int i = 0; i < 5; i++){
            bitmapDb.put("sprites_blob_idle_" + i, getBitmap(context, "sprites/blob/blob_idle/blob_idle" + i + ".png"));
        }

        // Blob attack
        for (int i = 0; i < 12; i++){
            bitmapDb.put("sprites_blob_attack_" + i, getBitmap(context, "sprites/blob/blob_attack/blob_attack" + i + ".png"));
        }
        // Blob damage
        bitmapDb.put("sprites_blob_damage", getBitmap(context, "sprites/blob/blob_damage.png"));

        // Cyclo idle
        for (int i = 0; i < 4; i++){
            bitmapDb.put("sprites_cyclo_idle_" + i, getBitmap(context, "sprites/cyclo/cyclo_idle/cyclo_idle" + i + ".png"));
        }

        // Cyclo attack
        for (int i = 0; i < 10; i++){
            bitmapDb.put("sprites_cyclo_attack_" + i, getBitmap(context, "sprites/cyclo/cyclo_attack/cyclo_attack" + i + ".png"));
        }

        // Cyclo damage
        bitmapDb.put("sprites_cyclo_damage", getBitmap(context, "sprites/cyclo/cyclo_damage.png"));

        // Buttons
        bitmapDb.put("button_play", getBitmap(context, "buttons/play.png"));
        bitmapDb.put("button_back", getBitmap(context, "buttons/back.png"));
        bitmapDb.put("button_info", getBitmap(context, "buttons/info.png"));
        bitmapDb.put("button_load", getBitmap(context, "buttons/load.png"));
        bitmapDb.put("button_start", getBitmap(context, "buttons/start.png"));
        bitmapDb.put("button_attack", getBitmap(context, "buttons/attack.png"));
        bitmapDb.put("button_defend", getBitmap(context, "buttons/defend.png"));
        bitmapDb.put("button_next", getBitmap(context, "buttons/next.png"));
        bitmapDb.put("button_nextInfo", getBitmap(context, "buttons/nextInfo.png"));
        bitmapDb.put("button_inv", getBitmap(context, "buttons/inv.png"));
        bitmapDb.put("button_inventory", getBitmap(context, "buttons/inventory.png"));
        bitmapDb.put("button_empty", getBitmap(context, "buttons/empty.png"));
        bitmapDb.put("button_fireball", getBitmap(context, "buttons/fireball.png"));
        bitmapDb.put("button_agility", getBitmap(context, "buttons/agility.png"));
        bitmapDb.put("button_lifesteal", getBitmap(context, "buttons/lifesteal.png"));
        bitmapDb.put("button_recovery", getBitmap(context, "buttons/recovery.png"));

        // Icons
        bitmapDb.put("icons_hp", getBitmap(context, "icons/hp.png"));
        bitmapDb.put("icons_mana", getBitmap(context, "icons/mana.png"));
        bitmapDb.put("icons_armor", getBitmap(context, "icons/armor.png"));
        bitmapDb.put("icons_evade", getBitmap(context, "icons/evade.png"));
        bitmapDb.put("icons_swords", getBitmap(context, "icons/swords.png"));

        // Items
        bitmapDb.put("items_small_potion", getBitmap(context, "items/small_potion.png"));
        bitmapDb.put("items_large_potion", getBitmap(context, "items/large_potion.png"));
        bitmapDb.put("items_mana_potion", getBitmap(context, "items/mana_potion.png"));
        bitmapDb.put("items_manaup_potion", getBitmap(context, "items/manaup_potion.png"));
        bitmapDb.put("items_evadeup_potion", getBitmap(context, "items/evadeup_potion.png"));
        bitmapDb.put("items_fireball_spell", getBitmap(context, "items/fireball_spell.png"));
        bitmapDb.put("items_agility_spell", getBitmap(context, "items/agility_spell.png"));
        bitmapDb.put("items_lifesteal_spell", getBitmap(context, "items/lifesteal_spell.png"));
        bitmapDb.put("items_recovery_spell", getBitmap(context, "items/recovery_spell.png"));

        // Sounds
        soundDb.put("title_theme", getSoundDesc(context, "sounds/title.ogg"));
        soundDb.put("shop_theme", getSoundDesc(context, "sounds/shop.ogg"));
        soundDb.put("wave_theme", getSoundDesc(context, "sounds/wave.ogg"));
        soundDb.put("battle_theme", getSoundDesc(context, "sounds/battle.ogg"));
        soundDb.put("sound_select", getSoundDesc(context, "sounds/select.wav"));
        soundDb.put("sound_enemy_hit", getSoundDesc(context, "sounds/enemyHit.wav"));
        soundDb.put("sound_armor", getSoundDesc(context, "sounds/armor.wav"));
        soundDb.put("sound_explode", getSoundDesc(context, "sounds/explode.wav"));
        soundDb.put("sound_player_hit", getSoundDesc(context, "sounds/playerHit.wav"));
        soundDb.put("sound_miss", getSoundDesc(context, "sounds/miss.wav"));
        soundDb.put("sound_purchase", getSoundDesc(context, "sounds/purchaseFinal.wav"));
        soundDb.put("sound_use_item", getSoundDesc(context, "sounds/useItem.wav"));

        // Messages (dialogue stuff)
//        AssetManager assetManager = context.getAssets();
//        try{
//            InputStream is = assetManager.open("messages.txt");
//            BufferedReader br = new BufferedReader(new InputStreamReader(is));
//        }catch(IOException e){}



    }

    /**
     * Returns a {@link Bitmap} object from a file, scaled accordingly to the screen size.
     * @param context The {@link Context} to use
     * @param filePath The file path
     * @return A {@link Bitmap}
     */
    public static Bitmap getBitmap(Context context, String filePath) {
        AssetManager assetManager = context.getAssets();
        // Create a new input stream, and open the path
        InputStream istr;
        Bitmap bitmap = null;
        try {
            istr = assetManager.open(filePath);
            // Assuming it is a bitmap, decode the stream
            bitmap = BitmapFactory.decodeStream(istr);
        } catch (IOException e) {}

        // Scale bitmap to screen height and width
        double x = bitmap.getWidth() * width / 1920;
        double y = bitmap.getHeight() * height / 1080;

        return Bitmap.createScaledBitmap(bitmap, (int) x, (int) y, true);
    }

    /**
     * Returns the {@link AssetFileDescriptor} of a sound file.
     * @param context The {@link Context} to use
     * @param filePath The file path
     * @return A {@link AssetFileDescriptor}
     */
    public static AssetFileDescriptor getSoundDesc(Context context, String filePath){
        try{
            AssetFileDescriptor afd = context.getAssets().openFd(filePath);
            return afd;
        }catch(IOException e){
            return null;
        }
    }

    /**
     * Returns a {@link AssetFileDescriptor} from memory.
     * @param name The name of the {@link AssetFileDescriptor}
     * @return A {@link AssetFileDescriptor}
     */
    public static AssetFileDescriptor getSoundDescFromMemory(String name){
        if (soundDb.containsKey(name)){
            return soundDb.get(name);
        }
        return null;
    }

    /**
     * Returns a {@link Bitmap} from memory.
     * @param name The name of the {@link Bitmap}
     * @return A {@link Bitmap}
     */
    public static Bitmap getBitmapFromMemory(String name){
        if (bitmapDb.containsKey(name)){
            return bitmapDb.get(name);
        }
        return null;
    }
}
