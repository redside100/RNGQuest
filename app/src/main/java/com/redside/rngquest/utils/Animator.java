package com.redside.rngquest.utils;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class Animator {
    private ArrayList<Bitmap> frames;
    private volatile boolean isRunning = false;
    private long prevTime, speed;
    public Bitmap sprite;
    private int frameAtPause, currentFrame;

    /**
     *
     * @param frames The list of individual frames
     */
    public Animator(ArrayList<Bitmap> frames){
        this.frames = new ArrayList<>(frames);
    }

    /**
     * Sets the speed of the animation.
     * @param speed The speed of the animation
     */
    public void setSpeed(long speed){
        this.speed = speed;
    }

    /**
     * Updates the frame of the current animation.
     * @param time The system's time in milliseconds
     */
    public void update(long time){
        if (isRunning){
            // Check if enough time has passed
            if (time - prevTime >= speed){
                // Update the current sprite
                try{
                    sprite = frames.get(currentFrame);
                }catch(IndexOutOfBoundsException e){
                    reset();
                    sprite = frames.get(currentFrame);
                }
                // Advance to next frame
                currentFrame++;
                prevTime = time;
            }
        }
    }

    /**
     * Replaces the current set of frames with a new set.
     * @param frames The new set of frames
     */
    public void replace(ArrayList<Bitmap> frames){
        stop();
        reset();
        this.frames = frames;
        play();
    }

    /**
     * Starts the animation.
     */
    public void play(){
        isRunning = true;
        prevTime = 0;
        frameAtPause = 0;
        currentFrame = 0;
    }

    /**
     * Returns the current frame index.
     * @return The current frame index
     */
    public int getCurrentFrame(){
        return currentFrame;
    }

    /**
     * Stops the animation.
     */
    public void stop(){
        isRunning = false;
        prevTime = 0;
        frameAtPause = 0;
        currentFrame = 0;
    }

    /**
     * Pauses the animation.
     */
    public void pause(){
        frameAtPause = currentFrame;
        isRunning = false;
    }

    /**
     * Resumes the animation.
     */
    public void resume(){
        currentFrame = frameAtPause;
        isRunning = true;
    }

    /**
     * Checks if the animation is finished.
     * @return {@code true} if the animation is finished
     */
    public boolean isDoneAnimation(){
        if (currentFrame == frames.size()){
            return true;
        }
        return false;
    }

    /**
     * Resets the animation.
     */
    public void reset(){
        currentFrame = 0;
    }

}
