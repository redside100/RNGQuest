package com.redside.rngquest.managers;


import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

import com.redside.rngquest.utils.Assets;

public class Soundtrack {
    public static MediaPlayer mediaPlayer = new MediaPlayer();
    public static Song song = Song.NONE;
    public static Song getCurrentSong(){
        return song;
    }
    public static void playSong(Song newSong){
        AssetFileDescriptor afd = null;
        switch(newSong){
            case TITLE:
                afd = Assets.getSoundDescFromMemory("title_theme");
                break;
            case SHOP:
                afd = Assets.getSoundDescFromMemory("shop_theme");
                break;
            case WAVE:
                afd = Assets.getSoundDescFromMemory("wave_theme");
                break;
            default:
                afd = Assets.getSoundDescFromMemory("shop_theme");
                break;
        }
        song = newSong;
        try{
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                mediaPlayer.reset();
            }
            mediaPlayer.setDataSource(
                    afd.getFileDescriptor(),
                    afd.getStartOffset(),
                    afd.getLength()
            );
            mediaPlayer.prepare();
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        }catch(Exception e){

        }

    }
    public static void stop(){
        if (mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer.reset();
            song = Song.NONE;
        }
    }
}
