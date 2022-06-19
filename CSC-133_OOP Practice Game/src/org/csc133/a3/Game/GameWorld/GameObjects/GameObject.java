package org.csc133.a3.Game.GameWorld.GameObjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.geom.Point;
import org.csc133.a3.Game.GameFormComponents.GlassCockpit.SevenSegmentDisplays.GameTimeDisplay;
import org.csc133.a3.Game.GameWorld.GameObjects.Fixed.RefuelingBlimp;
import org.csc133.a3.Game.GameWorld.GameObjects.Fixed.SkyScraper;
import org.csc133.a3.Game.GameWorld.GameObjects.GameObjectCollision.CollisionDetector;
import org.csc133.a3.Game.GameWorld.GameObjects.GameObjectCollision.ICollider;
import org.csc133.a3.Game.GameWorld.GameObjects.Movable.Bird;
import org.csc133.a3.Game.GameWorld.GameObjects.Movable.Helicopters.Helicopter;
import org.csc133.a3.Game.GameWorld.GameObjects.Movable.Helicopters.PlayerHelicopter;
import org.csc133.a3.Game.GameWorld.GameWorld;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public abstract class GameObject implements IDrawable, ICollider {
    private GameWorld.Location location;
    private final int size;
    private int color;

    private int[] mapXY;
    private int[] imageXY;
    private double scaleFactor = 1;

    private final double collisionCooldownTime = 15e2;
    HashMap<ICollider, Long> collisionHistoryMap = new HashMap<>();

    ArrayList<Long> animationTimerList = new ArrayList<>();

    public GameObject(GameWorld.Location location, int size, int color) {
        this.location = location;
        this.size = size;
        this.color = color;
    }

    public int[] getColorArray() {

        return new int[]{
            ColorUtil.red(color),
            ColorUtil.green(color),
            ColorUtil.blue(color)
        };
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public double getScaleFactor() {
        return scaleFactor;
    }

    public void setScaleFactor(double scaleFactor) {
        this.scaleFactor = scaleFactor;
    }

    public int getScaledSize() {
        return (int) (size * scaleFactor);
    }

    public HashMap<ICollider, Long> getCollisionHistoryMap() {
        return collisionHistoryMap;
    }

    public boolean animationTimer(int timerIndex, int duration) {
        if( timerIndex >= animationTimerList.size() ||
            GameTimeDisplay.getElapsedTime() >= animationTimerList.get(timerIndex) + duration) {

                animationTimerList.add(timerIndex, GameTimeDisplay.getElapsedTime());

                return true;
        }

        return false;
    }

    public int calcSoundVolume(int maxVol) {
        int playerHelicopterDistance = CollisionDetector.colliderDistance(this, PlayerHelicopter.getPlayerHelicopter());

        int soundThreshold = (int) (200 * GameWorld.getGameWorld().getScaleFactor());

        return maxVol * (1 - (playerHelicopterDistance / soundThreshold));
    }

    @Override
    public void handleCollision(ICollider collider) {
        if( collisionHistoryMap.containsKey(collider) &&
            GameTimeDisplay.getElapsedTime() - collisionHistoryMap.get(collider) > collisionCooldownTime)

                collisionHistoryMap.remove(collider);

        else if (!collisionHistoryMap.containsKey(collider)) collisionHistoryMap.put(collider, GameTimeDisplay.getElapsedTime());
    }

    @Override
    public void handleCollision(SkyScraper skyScraper) {
    }

    @Override
    public void handleCollision(RefuelingBlimp refuelingBlimp) {
    }

    @Override
    public void handleCollision(Bird bird) {
    }

    @Override
    public void handleCollision(Helicopter helicopter) {
    }

    public GameWorld.Location getLocation() {
        return location;
    }

    public float[] getLocationCoordinates() {
        return location.getCoordinates();
    }

    public void setLocation(GameWorld.Location location) {
        this.location = location;
    }

    public void setLocation(float x, float y) {
        location.setLocation(x, y);
    }

    public String toString(){
        String ret = (
            getClass().getSimpleName() + ": " +
            "loc="      + Arrays.toString(location.getCoordinates()) + " " +
            "color="    + Arrays.toString(getColorArray()) + " " +
            "size="     + size
        );

        return ret;
    }

    public int[] getMapXY() {
        return mapXY;
    }

    public int[] getImageXY() {
        return imageXY;
    }

    public Image setImageColor(Image image, int color) {
        int[] imageRGB = image.getRGB();

        for(int i = 0; i <  imageRGB.length; i++) {
            if (imageRGB[i] == ColorUtil.WHITE)
                imageRGB[i] = color;
        }

        return Image.createImage(imageRGB, image.getWidth(), image.getHeight());
    }

    @Override
    public void draw(Graphics g, Point containerOrigin) {
        mapXY = new int[]{
                (int) (containerOrigin.getX() + getLocationCoordinates()[0]),
                (int) (containerOrigin.getY() - getLocationCoordinates()[1])
        };

        imageXY = new int[]{
                (int) (mapXY[0] - (getScaledSize() / 2)),
                (int) (mapXY[1] - (getScaledSize() / 2))
        };
    }
}