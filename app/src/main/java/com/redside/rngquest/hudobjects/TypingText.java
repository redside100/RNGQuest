package com.redside.rngquest.hudobjects;

public class TypingText extends AnimatedText{
    private int tick = 0;
    private int wait = 0;
    private int tickDelay;
    private boolean typing = true;
    private String currentText = "";
    private int currentIndex = 0;
    private String text;

    /**
     *
     * @param text The text to draw
     * @param tickDelay The amount of time to delay between each character
     * @param x The x position of the text
     * @param y The y position of the text
     * @param textSize The size of the text
     * @param color The color of the text
     */
    public TypingText(String text, int x, int y, int tickDelay, int textSize, int color, boolean centered){
        super("", x, y, textSize, color, 255, centered);
        this.tickDelay = tickDelay;
        this.text = text;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void tick(){
        if (super.active){
            if (typing){
                // Initial check for waiting ticks
                if (wait > 0){
                    wait--;
                    return;
                }
                // Type next char every interval according to tick delay
                if (tick % tickDelay == 0){
                    if (currentIndex <= text.length() - 1){
                        // Skip spaces
                        while (text.charAt(currentIndex) == ' '){
                            currentText += " ";
                            currentIndex++;
                        }
                        // Check for symbol for pauses
                        if (text.charAt(currentIndex) == '§'){
                            wait += 10;
                            currentIndex++;
                            return;
                        }
                        // Append next char, and continue
                        currentText += text.charAt(currentIndex);
                        super.text = currentText;
                        currentIndex++;
                    }else{
                        typing = false;
                    }
                }
                tick++;
            }
        }
    }
    public boolean finished(){
        return typing;
    }
}
