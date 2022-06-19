package org.csc133.a3.Game.GameFormComponents.GlassCockpit.SevenSegmentDisplays;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;

public class DamageDisplay extends SevenSegmentDisplay{
    private int originalLedColor;

    public DamageDisplay(int numDigitsShowing, int ledColor) {
        super(numDigitsShowing, ledColor);

        this.originalLedColor = ledColor;
    }

    @Override
    public void paint(Graphics g) {
        if(getDisplayValue() > 85)
            this.setLedColor(ColorUtil.rgb(255,0,0));

        else setLedColor(originalLedColor);

        super.paint(g);
    }
}
