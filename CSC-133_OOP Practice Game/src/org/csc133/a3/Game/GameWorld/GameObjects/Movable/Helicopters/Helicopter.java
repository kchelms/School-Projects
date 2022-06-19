package org.csc133.a3.Game.GameWorld.GameObjects.Movable.Helicopters;

import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.geom.Point;
import org.csc133.a3.Game.GameFormComponents.GlassCockpit.SevenSegmentDisplays.GameTimeDisplay;
import org.csc133.a3.Game.GameWorld.GameObjects.Fixed.RefuelingBlimp;
import org.csc133.a3.Game.GameWorld.GameObjects.Fixed.SkyScraper;
import org.csc133.a3.Game.GameWorld.GameObjects.GameObjectCollision.ICircle;
import org.csc133.a3.Game.GameWorld.GameObjects.GameObjectCollision.ICollider;
import org.csc133.a3.Game.GameWorld.GameObjects.Movable.Bird;
import org.csc133.a3.Game.GameWorld.GameObjects.Movable.Movable;
import org.csc133.a3.Game.GameWorld.GameWorld;

import java.io.IOException;
import java.util.Random;

public abstract class Helicopter extends Movable implements ISteerable, ICircle {
    private final int maximumDamageLevel = 100;
    private final int fuelConsumptionRate = 3;
    private final int collisionDamage = 15;

    private int stickAngle = 0;
    private int fuelLevel = 500;
    private final int fuelMultiplier = 1000;
    private int damageLevel = 0;
    private int lastSkyScraperReached = 0;

    private Image helicopterBodyImage;
    private final Image[] helicopterRotor = new Image[2];
    private final int maximumSpeed;

    public Helicopter(GameWorld.Location location, int maximumSpeed, int color) {
        super(location, 120, color, 0, 0);

        this.fuelLevel *= fuelMultiplier;
        this.maximumSpeed = maximumSpeed;

        try {
            helicopterBodyImage = setImageColor(Image.createImage("/Helicopter_Body.png"), getColor());

            for(int i = 0; i < 2; i++)
                helicopterRotor[i] = Image.createImage("/Helicopter_Rotor_" + i + ".png");

        } catch (IOException e) {e.printStackTrace();}


        Random random = new Random();
        currentRotorRotation = random.nextInt(360);


    }

    public int getDamageLevel() {
        return damageLevel;
    }

    public int getMaximumDamageLevel() {
        return maximumDamageLevel;
    }

    public int getFuelLevel() {
        return fuelLevel / fuelMultiplier;
    }

    public int getLastSkyScraperReached() {
        return lastSkyScraperReached;
    }

    public boolean isImmobilized() {
        return  damageLevel >= maximumDamageLevel ||
                fuelLevel <= 0;
    }

    public void accelerate(int direction) {
        if(!rotorIsSpunUp() || isImmobilized()) direction = 0;

        double damageProportion = 1 - ((double) damageLevel / maximumDamageLevel);

        if(damageProportion < 0.5) damageProportion = .5;

        double currentMaximumSpeed = maximumSpeed * damageProportion;

        int newSpeed = getSpeed() + direction;

        if(newSpeed > currentMaximumSpeed) newSpeed = (int) currentMaximumSpeed;
        if(newSpeed < 0) newSpeed = 0;

        setSpeed(newSpeed);
    }

    @Override
    public void steer(int direction) {
        if(!rotorIsSpunUp() || isImmobilized()) direction = 0;

        if(direction == 0) return;

        int sign = Math.abs(direction) / direction;

        stickAngle += direction;

        if(Math.abs(stickAngle) > 40)
            stickAngle = 40 * sign;
    }

    @Override
    public void move(long elapsedTime) {
        if(stickAngle != 0) {
            int steerIncrement = (int) Math.ceil(stickAngle / 12.);

            setHeading(getHeading() + steerIncrement);

            stickAngle -= steerIncrement;
        }

        consumeFuel(elapsedTime);

        super.move(elapsedTime);
    }

    private void consumeFuel(long elapsedTime){
        int fuelConsumptionRate = this.fuelConsumptionRate * fuelMultiplier;

        fuelLevel -= fuelConsumptionRate * elapsedTime / 1e3;

        if(fuelLevel <= 0) {
            fuelLevel = 0;
            setSpeed(0);
        }
    }

    @Override
    public void handleCollision(ICollider collider) {
        if(!getCollisionHistoryMap().containsKey(collider))
            collider.handleCollision(this);

        super.handleCollision(collider);
    }

    @Override
    public void handleCollision(SkyScraper skyScraper) {
        if(skyScraper.getSequenceNumber() == (lastSkyScraperReached + 1)) {
            lastSkyScraperReached++;
        }
    }

    @Override
    public void handleCollision(RefuelingBlimp r) {
        fuelLevel += r.getCapacity() * fuelMultiplier;
    }

    @Override
    public void handleCollision(Bird bird) {
        damageLevel += collisionDamage / 2;

        double damageProportion = 1 - ((double) damageLevel / maximumDamageLevel);

        double newSpeed = getSpeed() * damageProportion;

        setSpeed((int) newSpeed);
    }

    private static boolean leftOrRight = false;

    @Override
    public void handleCollision(Helicopter helicopter) {
        damageLevel += collisionDamage;

        double damageProportion = 1 - ((double) damageLevel / maximumDamageLevel);

        double newSpeed = getSpeed() * damageProportion;

        setSpeed((int) newSpeed);

        steer(leftOrRight ? 60 : -60);

        leftOrRight = !leftOrRight;
    }

    @Override
    public int getIntersectRadius() {
        return getScaledSize() / 3;
    }

    private final long spinupStartTime = GameTimeDisplay.getElapsedTime();
    private final int rotorSpinUpDuration = 1500;
    private double currentRotorRotation;

    private float rotateRotor() {
        if (!rotorIsSpunUp()) {
            double nextRotation = 30 * (GameTimeDisplay.getElapsedTime() - spinupStartTime) / rotorSpinUpDuration;

            currentRotorRotation += nextRotation;

            nextRotation = Math.toRadians(currentRotorRotation);

            return (float) nextRotation;
        }

        currentRotorRotation += 30 + (15. * getSpeed() / maximumSpeed);
        currentRotorRotation %= 180;

        return (float) Math.toRadians(currentRotorRotation);
    }

    private boolean rotorIsSpunUp() {
        return GameTimeDisplay.getElapsedTime() - spinupStartTime >= rotorSpinUpDuration;
    }

    public void drawHelicopterBody(Graphics g, int imageX, int imageY) {
        float headingRadians = (float) Math.toRadians(getHeading());

        g.rotateRadians(headingRadians, getMapXY()[0], getMapXY()[1]);

        g.drawImage(helicopterBodyImage, imageX, imageY, getScaledSize(), getScaledSize());

        g.rotateRadians(-headingRadians, getMapXY()[0], getMapXY()[1]);
    }

    public void drawHelicopterRotor(Graphics g, int imageX, int imageY) {
        float rotorRotate = rotateRotor();

        g.rotateRadians(rotorRotate, getMapXY()[0], getMapXY()[1]);

        g.drawImage(helicopterRotor[rotorIsSpunUp() ? 1 : 0], imageX, imageY, getScaledSize(), getScaledSize());

        g.rotateRadians(-rotorRotate, getMapXY()[0], getMapXY()[1]);
    }

    @Override
    public void draw(Graphics g, Point containerOrigin) {
        super.draw(g,containerOrigin);

        int imageX = getImageXY()[0];
        int imageY = getImageXY()[1];

        drawHelicopterBody(g, imageX, imageY);

        drawHelicopterRotor(g, imageX, imageY);
    }

    public String toString() {
        String ret = (
            super.toString() + " " +
            "maxSpeed=" + maximumSpeed + " " +
            "stickAngle=" + stickAngle + " " +
            "fuelLevel=" + fuelLevel + " " +
            "damageLevel=" + damageLevel + " " +
            "lastSkyScraperReached=" + lastSkyScraperReached
        );

        return ret;
    }
}

