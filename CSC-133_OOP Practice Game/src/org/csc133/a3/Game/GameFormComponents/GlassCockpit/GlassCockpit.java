package org.csc133.a3.Game.GameFormComponents.GlassCockpit;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Font;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.FlowLayout;
import org.csc133.a3.Game.GameFormComponents.GlassCockpit.SevenSegmentDisplays.DamageDisplay;
import org.csc133.a3.Game.GameFormComponents.GlassCockpit.SevenSegmentDisplays.GameTimeDisplay;
import org.csc133.a3.Game.GameFormComponents.GlassCockpit.SevenSegmentDisplays.SevenSegmentDisplay;
import org.csc133.a3.Game.GameWorld.GameObjects.Movable.Helicopters.PlayerHelicopter;


public class GlassCockpit extends Container {
    private GameTimeDisplay     gameTimeDisplay         = new GameTimeDisplay();
    private SevenSegmentDisplay fuelDisplay             = new SevenSegmentDisplay(4, ColorUtil.GREEN);
    private SevenSegmentDisplay damageDisplay           = new DamageDisplay(2, ColorUtil.WHITE);
    private SevenSegmentDisplay livesDisplay            = new SevenSegmentDisplay(1, ColorUtil.CYAN);
    private SevenSegmentDisplay lastSkyScraperDisplay   = new SevenSegmentDisplay(1, ColorUtil.CYAN);
    private SevenSegmentDisplay headingDisplay          = new SevenSegmentDisplay(3, ColorUtil.YELLOW);

    private PlayerHelicopter playerHelicopter;

    public GlassCockpit() {
        this.getAllStyles().setMargin(0,0,10,10);
        this.getAllStyles().setPadding(0,20,0,0);
        this.getAllStyles().setBgTransparency(255);
        this.getAllStyles().setBgColor(ColorUtil.GRAY);

        FlowLayout f = new FlowLayout();
        f.setAlign(Component.CENTER);
        this.setLayout(f);

        this.add(containerFactory("GAME TIME", gameTimeDisplay));
        this.add(containerFactory("FUEL", fuelDisplay));
        this.add(containerFactory("DAMAGE", damageDisplay));
        this.add(containerFactory("LIVES", livesDisplay));
        this.add(containerFactory("LAST", lastSkyScraperDisplay));
        this.add(containerFactory("HEADING", headingDisplay));
    }

    private Container containerFactory (String title, Component component) {
        Container ret = new Container(new BorderLayout());

        Label t = new Label(title);
        t.getUnselectedStyle().setFont(Font.createSystemFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD, Font.SIZE_SMALL));
        t.getUnselectedStyle().setFgColor(ColorUtil.WHITE);
        t.getAllStyles().setAlignment(Component.CENTER);

        component.getAllStyles().setMargin(0,0,0,10);
        component.getAllStyles().setPadding(0,0,0,0);

        ret.add(BorderLayout.NORTH, t);
        ret.addComponent(BorderLayout.CENTER, component);

        return ret;
    }

    public void setPlayerHelicopter(PlayerHelicopter playerHelicopter) {
        this.playerHelicopter = playerHelicopter;
    }

    public long getElapsedTime() {
        return gameTimeDisplay.getElapsedTime();
    }

    public void startElapsedTime(){
        gameTimeDisplay.startElapsedTime();
    }

    public void stopElapsedTime() {
        gameTimeDisplay.stopElapsedTime();
    }

    public void resetElapsedTime() {gameTimeDisplay.resetElapsedTime();}

    public void updateHelicopterDisplays() {
        fuelDisplay.setDisplayValue(playerHelicopter.getFuelLevel());
        damageDisplay.setDisplayValue(playerHelicopter.getDamageLevel());
        lastSkyScraperDisplay.setDisplayValue(playerHelicopter.getLastSkyScraperReached());
        headingDisplay.setDisplayValue(playerHelicopter.getHeading());

        repaint();
    }

    public void updateLivesDisplay(int lives) {
        livesDisplay.setDisplayValue(lives);
    }
}