package org.csc133.a3.Game.GameWorld.GameObjects.Movable;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.geom.Point;
import org.csc133.a3.Game.GameWorld.GameObjects.GameObjectCollision.ICollider;
import org.csc133.a3.Game.GameWorld.GameObjects.GameObjectCollision.IRectangle;
import org.csc133.a3.Game.GameWorld.GameObjects.Movable.Helicopters.Helicopter;
import org.csc133.a3.Game.GameWorld.GameObjects.Movable.Helicopters.PlayerHelicopter;
import org.csc133.a3.Game.GameWorld.GameWorld;
import org.csc133.a3.Game.Sound.SoundCollection;

import java.io.IOException;
import java.util.Random;

public class Bird extends Movable implements IRectangle {
    private final int numOfBirdImages = 4;
    private Image[] birdImages = new Image[numOfBirdImages];

    private final int TIME_OF_LAST_FLAP = 0;
    private final int TIME_OF_LAST_QUACK = 1;

    public Bird(GameWorld.Location location, int heading, int speed) {
        super(location, 50, ColorUtil.WHITE, heading, speed);

        try {
            for (int i = 0; i < numOfBirdImages; i++) {
                birdImages[i] = Image.createImage("/Bird_" + i % 3 + ".png");
                birdImages[i] = setImageColor(birdImages[i], getColor());
            }

        } catch (IOException e) {e.printStackTrace();}

        flapWingDuration = (int) ((1000 / numOfBirdImages) * (2 - (speed / 200.)));
    }

    @Override
    public void move(long elapsedTime) {
       meander();

       flapWings();

       quack();

       super.move(elapsedTime);
    }


    private int flapWingDuration;
    private int imageIndex = 0;

    public void flapWings() {
        if(animationTimer(TIME_OF_LAST_FLAP, flapWingDuration)) {
            imageIndex++;
            imageIndex %= numOfBirdImages;
        }
    }

    private void meander() {
        Random number = new Random();

        int headingChange = number.nextInt(11) - 5;

        setHeading(getHeading() + headingChange);
    }

    private int timeOfNextQuack = 0;

    private void quack() {
        if(animationTimer(TIME_OF_LAST_QUACK, timeOfNextQuack)) {
            Random number = new Random();

            timeOfNextQuack = number.nextInt(4000) + 1000;

            SoundCollection.getBirdSound().play(calcSoundVolume(50));
        }
    }

    @Override
    public int[] getIntersectXY() {
        int intersect = getScaledSize() / 2;

        return new int[]{intersect, intersect};
    }

    @Override
    public void handleCollision(ICollider collider) {
        if(!getCollisionHistoryMap().containsKey(collider))
            collider.handleCollision(this);

        super.handleCollision(collider);
    }

    @Override
    public void handleCollision(Helicopter helicopter) {
        if(helicopter instanceof PlayerHelicopter)
            SoundCollection.getBirdSound().play(100);
    }

    @Override
    public void draw(Graphics g, Point containerOrigin) {
        super.draw(g, containerOrigin);

        float headingRadians = (float) Math.toRadians(getHeading());

        g.setColor(getColor());

        g.rotateRadians(headingRadians, getMapXY()[0], getMapXY()[1]);

        g.drawImage(birdImages[imageIndex],
                    getImageXY()[0],
                    getImageXY()[1],
                    getScaledSize(),
                    getScaledSize()
        );

        g.rotateRadians(-headingRadians, getMapXY()[0], getMapXY()[1]);
    }
}
