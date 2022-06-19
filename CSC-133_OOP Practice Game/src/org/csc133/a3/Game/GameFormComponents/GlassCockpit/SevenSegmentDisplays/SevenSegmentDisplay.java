package org.csc133.a3.Game.GameFormComponents.GlassCockpit.SevenSegmentDisplays;

import com.codename1.ui.Component;
import com.codename1.ui.Display;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.geom.Dimension;
import com.codename1.util.MathUtil;

import java.io.IOException;

public class SevenSegmentDisplay extends Component {
    protected Image[] digitImages = new Image[10];
    protected Image[] decimalDigitImages = new Image[10];
    protected Image colonImage;

    private int numDigitsShowing;
    protected Image[] displayDigits;
    private int displayValue;

    private int ledColor;

    public SevenSegmentDisplay(int numDigitsShowing, int ledColor) {
        this.numDigitsShowing = numDigitsShowing;
        this.ledColor = ledColor;

        displayDigits = new Image[numDigitsShowing];

        try {
            for(int i = 0; i < 10; i++) {
                digitImages[i] = Image.createImage("/LED_digit_" + i + ".png");
                decimalDigitImages[i] = Image.createImage("/LED_digit_" + i + "_with_dot.png");
            }

            colonImage = Image.createImage("/LED_colon.png");

        } catch (IOException e) {e.printStackTrace();}

        for (int i = 0; i < numDigitsShowing; i++)
            displayDigits[i] = digitImages[0];
    }

    public int getLedColor() {
        return ledColor;
    }

    public void setLedColor(int ledColor) {
        this.ledColor = ledColor;
    }

    public int getDisplayValue() {
        return displayValue;
    }

    public void setDisplayValue(int displayValue) {
        if(this.displayValue != displayValue) {
            this.displayValue = displayValue;
            setDisplayDigits();
        }
    }

    private void setDisplayDigits() {
        int displayValue = this.displayValue;

        displayValue %= MathUtil.pow(10, numDigitsShowing);

        for(int i = numDigitsShowing - 1; i >= 0; i--) {
            int digitPlace = (int) MathUtil.pow(10, i);

            displayDigits[numDigitsShowing - i - 1] = digitImages[displayValue / digitPlace];
            displayValue %= digitPlace;
        }

        repaint();
    }

    private float getScaleFactor() {
        return Math.min(
                getInnerHeight() / (float) colonImage.getHeight(),
                getInnerWidth() / (float) (colonImage.getWidth() * numDigitsShowing)
        );
    }

    protected int getDisplayDigitWidth() {
        return (int) (getScaleFactor() * colonImage.getWidth());
    }

    protected int getDisplayWidth() {
        return getDisplayDigitWidth() * numDigitsShowing;
    }

    protected int getDisplayHeight() {
        return (int) (getScaleFactor() * colonImage.getHeight());
    }

    protected int getDisplayX() {
        return getInnerX() + (getInnerWidth() - getDisplayWidth()) / 2;
    }

    protected int getDisplayY() {
        return getInnerY() + getInnerHeight() - getDisplayHeight();
    }

    @Override
    protected Dimension calcPreferredSize() {
        int digitDisplayProportion = Display.getInstance().getDisplayWidth() / 20;

        return colonImage == null ?
                new Dimension(0,0) :
                new Dimension(digitDisplayProportion * numDigitsShowing, (int) (digitDisplayProportion * 1.5));

    }

    public void paint(Graphics g) {
        super.paint(g);
        final int COLOR_PAD = 1;

        g.setColor(ledColor);
        g.fillRect( getDisplayX() + COLOR_PAD,
                getDisplayY() + COLOR_PAD,
                getDisplayWidth() - COLOR_PAD * 2,
                getDisplayHeight() - COLOR_PAD * 2);

        for (int i = 0; i < numDigitsShowing; i++) {
            g.drawImage(
                    displayDigits[i],
                    getDisplayX() + i * getDisplayDigitWidth(),
                    getDisplayY(),
                    getDisplayDigitWidth(),
                    getDisplayHeight()
            );
        }
    }
}