package org.csc133.a3.Game.GameWorld.GameObjects.Fixed;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Font;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.geom.Point;
import org.csc133.a3.Game.GameWorld.GameObjects.GameObjectCollision.CollisionDetector;
import org.csc133.a3.Game.GameWorld.GameObjects.GameObjectCollision.ICollider;
import org.csc133.a3.Game.GameWorld.GameObjects.Movable.Helicopters.Helicopter;
import org.csc133.a3.Game.GameWorld.GameObjects.Movable.Helicopters.PlayerHelicopter;
import org.csc133.a3.Game.GameWorld.GameWorld;
import org.csc133.a3.Game.Sound.SoundCollection;

import java.io.IOException;
import java.util.Random;

public class RefuelingBlimp extends Fixed {
   private int capacity;
   private int rotation;
   private boolean isVertical;

   private Image[] blimpImages;
   private int color2;

    public RefuelingBlimp(GameWorld.Location location, int size) {
        super(location, size / 2, ColorUtil.YELLOW);

        Random random = new Random();

        rotation = random.nextInt(5) + 1;
        isVertical = rotation % 2 != 0;

        capacity = size / 3;

        color2 = ColorUtil.BLUE;

        blimpImages = setImages(getColor(), color2);
    }

    @Override
    public int[] getIntersectXY() {
        int intersectX = isVertical ? getScaledSize() / 2 : getScaledSize() / 4;
        int intersectY = isVertical ? getScaledSize() / 4 : getScaledSize() / 2;

        return new int[]{intersectX, intersectY};
    }

    public int getCapacity() {
        if(capacity < 0) respawnTimer();

        return capacity;
    }

    @Override
    public void handleCollision(ICollider collider) {
        if(!getCollisionHistoryMap().containsKey(collider))
            collider.handleCollision(this);

        super.handleCollision(collider);
    }

    @Override
    public void handleCollision(Helicopter helicopter) {
        int c = capacity;
        capacity = -30;

        setColor(ColorUtil.GRAY);
        color2 = ColorUtil.BLACK;

        blimpImages = setImages(getColor(), color2);

        int playerHelicopterDistance = CollisionDetector.colliderDistance(this, PlayerHelicopter.getPlayerHelicopter());
        int soundThreshold = (int) (200 * GameWorld.getGameWorld().getScaleFactor());

        SoundCollection.getRefuel().play(100 * (1 - (playerHelicopterDistance / soundThreshold)));
    }

    private void respawnTimer() {
        capacity++;

        int newAlpha = 255 - (8 * (30 + capacity));

        blimpImages[0] = blimpImages[0].modifyAlpha((byte) newAlpha);
        blimpImages[1] = blimpImages[1].modifyAlpha((byte) newAlpha);
    }

    private Image[] setImages(int color1, int color2) {
        Image[] images = null;

        try {
            images = new Image[]{
                    setImageColor(Image.createImage("/Blimp_BG.png"), color1),
                    setImageColor(Image.createImage("/Blimp_Outline.png"), color2)
            };

        } catch (IOException e) {e.printStackTrace();}

        return images;
    }

    @Override
    public void draw(Graphics g, Point containerOrigin) {
        super.draw(g, containerOrigin);

        int imageX = getMapXY()[0] - getScaledSize() / 2;
        int imageY = getMapXY()[1] - getScaledSize() / 4;

        g.rotateRadians((float) Math.toRadians(rotation * 90), getMapXY()[0], getMapXY()[1]);

        g.drawImage(blimpImages[0],
                    imageX,
                    imageY,
                    getScaledSize(),
                    getScaledSize() / 2
        );

        g.drawImage(blimpImages[1],
                    imageX,
                    imageY,
                    getScaledSize(),
                    getScaledSize() / 2
        );

        String capacityString = Integer.toString(Math.max(getCapacity(), 0));

        g.setColor(color2);
        g.setFont(Font.createSystemFont(Font.FACE_MONOSPACE, Font.STYLE_BOLD, Font.SIZE_SMALL));
        g.drawString(   capacityString,
                        getMapXY()[0] - g.getFont().stringWidth(capacityString) / 2,
                        getMapXY()[1] - g.getFont().getHeight() / 2 );

        g.rotateRadians(-(float) Math.toRadians(rotation * 90), getMapXY()[0], getMapXY()[1]);
    }

    @Override
    public String toString() {
        String ret = (
            super.toString() + " " +
            "capacity=" + capacity
        );

        return ret;
    }
}
