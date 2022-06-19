package org.csc133.a3.Game.GameWorld.GameObjects.Fixed;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Font;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.geom.Point;
import org.csc133.a3.Game.GameWorld.GameObjects.GameObjectCollision.ICollider;
import org.csc133.a3.Game.GameWorld.GameObjects.Movable.Helicopters.Helicopter;
import org.csc133.a3.Game.GameWorld.GameObjects.Movable.Helicopters.PlayerHelicopter;
import org.csc133.a3.Game.GameWorld.GameWorld;

import java.io.IOException;

public class SkyScraper extends Fixed {
    private final int sequenceNumber;

    private Image skyScraperImage;

    public SkyScraper(GameWorld.Location location, int sequenceNumber) {
        super(location, 220, ColorUtil.YELLOW);

        if(sequenceNumber == 0) setColor(ColorUtil.WHITE);

        skyScraperImage = setImage(getColor());

        this.sequenceNumber = sequenceNumber;
    }

    private Image setImage(int color) {
        Image image = null;

        try {
            image = setImageColor(Image.createImage("/Skyscraper.png"), color);

        } catch (IOException e) {e.printStackTrace();}

        return image;
    }

    public int getSequenceNumber(){
        return sequenceNumber;
    }

    @Override
    public void handleCollision(ICollider collider) {
        if(!getCollisionHistoryMap().containsKey(collider))
            collider.handleCollision(this);

        super.handleCollision(collider);
    }

    @Override
    public void handleCollision(Helicopter helicopter) {
        if( helicopter instanceof PlayerHelicopter &&
            helicopter.getLastSkyScraperReached() == sequenceNumber &&
            sequenceNumber != 0) {

                setColor(ColorUtil.GREEN);
                skyScraperImage = setImage(getColor());
       }
    }

    @Override
    public int[] getIntersectXY() {
        int[] intersectXY = super.getIntersectXY();

        intersectXY[0] /= 1.5;
        intersectXY[1] /= 1.5;

        return intersectXY;
    }

    @Override
    public String toString() {
        String ret = (
            super.toString() + " " +
            "sequenceNumber=" + sequenceNumber
        );

        return ret;
    }

    @Override
    public void draw(Graphics g, Point containerOrigin) {
        super.draw(g, containerOrigin);

        g.drawImage(skyScraperImage,
                    getImageXY()[0],
                    getImageXY()[1],
                    getScaledSize(),
                    getScaledSize()
        );

        String charSequenceNumber =
                sequenceNumber == 0 ?
                    "H" :
                    Integer.toString(sequenceNumber);

        g.setColor(getColor());
        g.setFont(Font.createSystemFont(Font.FACE_MONOSPACE, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
        g.drawString(   charSequenceNumber,
                        getMapXY()[0] - g.getFont().stringWidth(charSequenceNumber) / 2,
                        getMapXY()[1] - g.getFont().getHeight() / 2
        );
    }
}

