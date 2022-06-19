package org.csc133.a3.Game.GameFormComponents.GlassCockpit.SevenSegmentDisplays;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;

import java.util.Date;

public class GameTimeDisplay extends SevenSegmentDisplay {
    private static long gameStartTime = new Date().getTime();
    private static long elapsedTime;

    private boolean gamePausedFlag = false;

    public GameTimeDisplay() {
        super(6, ColorUtil.CYAN);

        displayDigits[2] = colonImage;

        resetElapsedTime();
    }

    public static long getElapsedTime() {
        return new Date().getTime() - gameStartTime;
    }

    public boolean setElapsedTime() {
        long elapsedTime = new Date().getTime() - gameStartTime;

        if(elapsedTime - this.elapsedTime < 1e2)
            return false;

        if(elapsedTime > 12e4) setLedColor(ColorUtil.rgb(255,0,0));

        this.elapsedTime = elapsedTime;

        int m = (int) (elapsedTime / 60e3);
        elapsedTime %= 60e3;
        int s = (int) (elapsedTime / 1e3);
        elapsedTime %= 1e3;
        int tenths = (int) (elapsedTime / 1e2);

        setDisplayDigits(m,s,tenths);

        return true;
    }

    private void setDisplayDigits (int m, int s, int tenths) {
        displayDigits[0] = digitImages[m / 10];
        displayDigits[1] = digitImages[m % 10];
        displayDigits[3] = digitImages[s / 10];
        displayDigits[4] = decimalDigitImages[s % 10];
        displayDigits[5] = digitImages[tenths];
    }

    public void resetElapsedTime() {
         gameStartTime = new Date().getTime();
         elapsedTime = 0;
         gamePausedFlag = false;
    }

    public void startElapsedTime() {
        gameStartTime = new Date().getTime() - elapsedTime;
        gamePausedFlag = false;
    }

    public void stopElapsedTime() {
        gamePausedFlag = true;
    }

    public void start() {
        getComponentForm().registerAnimated(this);
    }

    public void stop() {
        getComponentForm().deregisterAnimated(this);
    }

    public void laidOut() {
        this.start();
    }

    public boolean animate() {
        return !gamePausedFlag && setElapsedTime();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g.setColor(getLedColor() == ColorUtil.CYAN ? ColorUtil.BLUE : ColorUtil.rgb(150,0,0));
        g.fillRect( getDisplayX() + getDisplayWidth() - getDisplayDigitWidth(),
                getDisplayY() + 1,
                getDisplayDigitWidth() - 1,
                getDisplayHeight() - 2);

        g.drawImage(
                displayDigits[5],
                getDisplayX() + getDisplayWidth() - getDisplayDigitWidth() - 1,
                getDisplayY(),
                getDisplayDigitWidth(),
                getDisplayHeight()
        );
    }
}
