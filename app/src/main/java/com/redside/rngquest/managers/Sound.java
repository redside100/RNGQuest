package com.redside.rngquest.managers;


import android.media.AudioManager;
import android.media.SoundPool;

import com.redside.rngquest.utils.Assets;

import java.util.HashMap;

public class Sound {
    private static SoundPool sp;
    private static HashMap<SoundEffect, Integer> sounds = new HashMap<>();
    public static void loadSounds(){
        sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        sounds.put(SoundEffect.SELECT, sp.load(Assets.getSoundDescFromMemory("sound_select"), 1));
        sounds.put(SoundEffect.ENEMY_HIT, sp.load(Assets.getSoundDescFromMemory("sound_enemy_hit"), 1));
        sounds.put(SoundEffect.ARMOR, sp.load(Assets.getSoundDescFromMemory("sound_armor"), 1));
        sounds.put(SoundEffect.PLAYER_HIT, sp.load(Assets.getSoundDescFromMemory("sound_player_hit"), 1));
        sounds.put(SoundEffect.EXPLODE, sp.load(Assets.getSoundDescFromMemory("sound_explode"), 1));
        sounds.put(SoundEffect.MISS, sp.load(Assets.getSoundDescFromMemory("sound_miss"), 1));
        sounds.put(SoundEffect.PURCHASE, sp.load(Assets.getSoundDescFromMemory("sound_purchase"), 1));
        sounds.put(SoundEffect.USE_ITEM, sp.load(Assets.getSoundDescFromMemory("sound_use_item"), 1));
    }
    public static void playSound(final SoundEffect sound){
        new Thread(new Runnable(){
            public void run(){
                int id = sounds.get(sound);
                sp.play(id, 1, 1, 0, 0, 1);
            }
        }).start();
    }
}
