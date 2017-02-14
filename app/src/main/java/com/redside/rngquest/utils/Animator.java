package com.redside.rngquest.utils;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class Animator {
    private ArrayList<Bitmap> frames;
    private volatile boolean isRunning = false;
    private long prevTime, speed;
    public Bitmap sprite;
    private int frameAtPause, currentFrame;

    public Animator(ArrayList<Bitmap> frames){
        this.frames = new ArrayList<>(frames);
    }

    public void setSpeed(long speed){
        this.speed = speed;
    }
    public void update(long time){
        if (isRunning){
            if (time - prevTime >= speed){
                currentFrame++;
                try{
                    sprite = frames.get(currentFrame);
                }catch(IndexOutOfBoundsException e){
                    reset();
                    sprite = frames.get(currentFrame);
                }
                prevTime = time;
            }
        }
    }
    public void replace(ArrayList<Bitmap> frames){
        stop();
        reset();
        this.frames = frames;
        play();
    }
    public void play(){
        isRunning = true;
        prevTime = 0;
        frameAtPause = 0;
        currentFrame = 0;
    }
    public int getCurrentFrame(){
        return currentFrame;
    }
    public void stop(){
        isRunning = false;
        prevTime = 0;
        frameAtPause = 0;
        currentFrame = 0;
    }
    public void pause(){
        frameAtPause = currentFrame;
        isRunning = false;
    }
    public void resume(){
        currentFrame = frameAtPause;
        isRunning = true;
    }
    public boolean isDoneAnimation(){
        if (currentFrame == frames.size()){
            return true;
        }
        return false;
    }
    public void reset(){
        currentFrame = 0;
    }

}
