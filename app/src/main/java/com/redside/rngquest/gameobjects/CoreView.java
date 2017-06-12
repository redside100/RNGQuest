package com.redside.rngquest.gameobjects;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.os.Build;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.redside.rngquest.R;
import com.redside.rngquest.managers.CoreManager;
import com.redside.rngquest.managers.Soundtrack;
import com.redside.rngquest.utils.Assets;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class CoreView extends View {
    public Canvas canvas;
    Typeface font;
    private CoreManager manager;
    private Soundtrack soundtrack;
    private Assets assets;
    private Loop loop;
    private int width;
    private int height;
    public static Resources resources;

    /**
     *
     * @param context The {@link Context} object to pass on
     * @param width The width of the view
     * @param height The height of the view
     */
    public CoreView(Context context, int width, int height) {
        super(context);
        this.width = width;
        this.height = height;
        this.resources = getResources();
        init();
    }

    /**
     * Initializes the asset manager, core manager, game loop, and save file.
     */
    private void init(){

        // Init assets, main manager, game loop, and set font
        assets = new Assets(getContext(), width, height);
        manager = new CoreManager(getContext(), width, height);
        loop = new Loop(this);
        font = Typeface.createFromAsset(getContext().getAssets(), "fonts/font.ttf");


        // Save file: Check if it exists, if not create one
        File save = new File(getContext().getFilesDir(), "save.ini");
        if (!save.exists()){
            System.out.println("No save file found. Creating a new one...");
            try{
                // Make new file and write default stuff inside
                save.createNewFile();
                ArrayList<String> defaultInfo = new ArrayList<>();
                defaultInfo.add("available: false");
                defaultInfo.add("highStage: 1");
                save(getContext(), defaultInfo);

            }catch(IOException e){}
        }

        /* How the save file works: each line, it starts with the property (ex. hp, mp, armor, evade), proceeded by a colon ':'
         * and the value. For example, "hp: 50", "maxhp: 100"
         * Accessing the save file info will return an array list of strings, each string being a line of the save file. Then, the line can be
         * processed and determined what it represents (ex. line.startsWith("hp"), or line.startWith("mp")), and can be split by the colon, the second index
         * being its corresponding value.
         */
    }

    /**
     * Returns a {@link String} {@link ArrayList} containing save info.
     * @param context The {@link Context} object to pass on
     * @return The save info
     */
    public static ArrayList<String> getSave(Context context) {
        try {
            // Open a new input stream from save.ini using the context
            FileInputStream inputStream = context.openFileInput("save.ini");

            // Open a new input stream reader
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");

            // Open a new buffered reader
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            // Create an array list to store all the info
            ArrayList<String> info = new ArrayList<>();

            // Keep reading until the line is null
            String line;
            while ((line = bufferedReader.readLine()) != null) {
               info.add(line);
            }

            // Close the readers and input stream
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();

            return info;
        } catch (FileNotFoundException e) {
            return null;
        } catch (UnsupportedEncodingException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Saves data into the save file.
     * @param context The {@link Context} object to pass on
     * @param info The {@link String} {@link ArrayList} holding the data
     */
    public static void save(Context context, ArrayList<String> info){
        try{
            // Create temp file
            File temp = new File(context.getFilesDir(), "temp.ini");
            if (!temp.exists()){
                temp.createNewFile();
            }else{
                System.out.println("Error saving! Temp file already exists... weird.");
                return;
            }

            // Open output stream, writer, and buffered writer
            FileOutputStream outputStream = context.openFileOutput("temp.ini", Context.MODE_PRIVATE);
            OutputStreamWriter outputWriter = new OutputStreamWriter(outputStream, "UTF-8");
            BufferedWriter bufferedWriter = new BufferedWriter(outputWriter);

            for (String line : info){
                bufferedWriter.write(line + "\n");
            }

            // Close writers and streams
            bufferedWriter.close();
            outputWriter.close();
            outputStream.close();

            // Delete old save file, then rename the temp file to the new save file
            File save = new File(context.getFilesDir(), "save.ini");
            if (save.exists()){
                save.delete();
                temp.renameTo(save);
                System.out.println("File saved!");
            }else{
                System.out.println("Error saving! Save file doesn't exist... weird.");
                return;
            }

        }catch(IOException e){}
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onDraw(Canvas canvas) {
        // Where everything is drawn
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setTypeface(font);
        paint.setTextSize(150);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        canvas.drawPaint(paint);

        // Always render the core manager
        manager.render(canvas, paint);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onTouchEvent(MotionEvent e){
        // Source of detecting touches. Only check for when the user lets go
        if (e.getAction() == MotionEvent.ACTION_UP){
            manager.touchEvent(e);
        }else if (e.getAction() == MotionEvent.ACTION_DOWN){
            manager.preTouchEvent(e);
        }
        return true;
    }

    /**
     * Called when the game renders. Draws graphics.
     */
    public void render(){
        // Invalidate recalls onDraw()
        invalidate();
    }

    /**
     * Called when the game ticks. Ticks managers.
     */
    public void tick(){
        // Ticks the main manager.
        manager.tick();
    }
}
