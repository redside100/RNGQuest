package com.redside.rngquest.managers;


import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

import com.redside.rngquest.utils.Assets;

/**
 * Plays longer sound files, such as the game's soundtrack.
 * @author Andrew Peng
 * @since July 19, 2017
 */
public class Soundtrack {
    public static MediaPlayer mediaPlayer = new MediaPlayer();
    public static Song song = Song.NONE;

    /**
     * Returns the current {@link Song} playing.
     * @return The current {@link Song}
     */
    public static Song getCurrentSong(){
        return song;
    }

    /**
     * Plays a {@link Song}, at a slightly reduced volume.
     * @param newSong The {@link Song} to play
     */
    public static void playSong(Song newSong){
        int max = 100;
        // Scale volume logarithmically
        // Set volume to 90%
        final float volume = (float) (1 - (Math.log(max - 90) / Math.log(max)));
        mediaPlayer.setVolume(volume, volume);

        // Load the song from memory
        AssetFileDescriptor afd = null;
        switch(newSong){
            case TITLE:
                afd = Assets.getSoundDescFromMemory("title_theme");
                break;
            case SHOP:
                afd = Assets.getSoundDescFromMemory("shop_theme");
                break;
            case BATTLE:
                afd = Assets.getSoundDescFromMemory("battle_theme");
                break;
            case WAVE:
                afd = Assets.getSoundDescFromMemory("wave_theme");
                break;
            default:
                afd = Assets.getSoundDescFromMemory("shop_theme");
                break;
        }
        song = newSong;
        // Play the song, and loop it
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

    /**
     * Stops the current {@link Song}.
     */
    public static void stop(){
        if (mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer.reset();
            song = Song.NONE;
        }
    }

    /**
     * Pauses the current {@link Song}.
     */
    public static void pause(){
        if (mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }
    }

    /**
     * Resumes the current {@link Song}.
     */
    public static void resume(){
        if (!mediaPlayer.isPlaying() && mediaPlayer != null){
            mediaPlayer.start();
        }
    }
}
