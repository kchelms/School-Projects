package org.csc133.a3.Game.GameWorld.GameObjects.Movable;

import org.csc133.a3.Game.GameWorld.GameObjects.GameObject;
import org.csc133.a3.Game.GameWorld.GameWorld;


public abstract class Movable extends GameObject {
    private int heading;
    private int speed;

    public Movable(GameWorld.Location location, int size, int color, int heading, int  speed) {
        super(location, size, color);

        this.heading = heading;
        this.speed = speed;
    }

    public int getHeading() {
        return heading;
    }

    public int getSpeed() {
        return speed;
    }

    public void setHeading(int heading) {
        if(heading >= 360)
            heading -= 360;

        if(heading < 0)
            heading += 360;

        this.heading = heading;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void move(long elapsedTime) {
        double headingRadians = Math.toRadians(heading);

        GameWorld.Location l = this.getLocation();

        float x = (float) (l.getX() + getScaleFactor() * speed * elapsedTime / 1e3 * Math.sin(headingRadians));
        float y = (float) (l.getY() + getScaleFactor() * speed * elapsedTime / 1e3 * Math.cos(headingRadians));

        this.setLocation(x,y);

        if (this.getLocation().isOutOfBounds()) bounceInBounds();
    }

    private void bounceInBounds() {
        GameWorld.Location l = getLocation();

        float x = l.getX() - 2 * l.xOutOfBounds();
        float y = l.getY() - 2 * l.yOutOfBounds();

        headingReflect();

        this.setLocation(x,y);
    }

    private void headingReflect() {
        if(getLocation().xOutOfBounds() != 0)
            setHeading(-heading);

        if(getLocation().yOutOfBounds() != 0)
            setHeading(-heading + 180);
    }

    public String toString() {
        String ret = (
            super.toString() + " " +
            "heading="  + heading + " " +
            "speed="    + speed
        );

        return ret;
    }
}



