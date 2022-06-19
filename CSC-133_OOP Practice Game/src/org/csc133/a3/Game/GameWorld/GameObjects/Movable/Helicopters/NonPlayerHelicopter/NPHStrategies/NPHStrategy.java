package org.csc133.a3.Game.GameWorld.GameObjects.Movable.Helicopters.NonPlayerHelicopter.NPHStrategies;

import org.csc133.a3.Game.GameWorld.GameObjects.Fixed.SkyScraper;
import org.csc133.a3.Game.GameWorld.GameObjects.GameObject;
import org.csc133.a3.Game.GameWorld.GameObjects.GameObjectCollision.CollisionDetector;
import org.csc133.a3.Game.GameWorld.GameObjects.GameObjectsCollection;
import org.csc133.a3.Game.GameWorld.GameObjects.Movable.Helicopters.NonPlayerHelicopter.NonPlayerHelicopter;
import org.csc133.a3.Game.GameWorld.GameObjects.Movable.Helicopters.PlayerHelicopter;
import org.csc133.a3.Game.GameWorld.GameObjects.Movable.Movable;
import org.csc133.a3.Game.GameWorld.GameWorld;

public abstract class NPHStrategy {
    public static final int NPH_STRATEGY_ATTACK = 0;
    public static final int NPH_STRATEGY_OBJECTIVE = 1;
    public static final int NPH_STRATEGY_MIXED = 2;

    private NonPlayerHelicopter nonPlayerHelicopter;
    private GameObjectsCollection gameObjectsCollection;
    private GameObject target;

    public NPHStrategy(NonPlayerHelicopter nonPlayerHelicopter, GameObjectsCollection gameObjectsCollection) {
        this.nonPlayerHelicopter = nonPlayerHelicopter;
        this.gameObjectsCollection = gameObjectsCollection;
    }

    public NonPlayerHelicopter getNPH() {
        return nonPlayerHelicopter;
    }

    public PlayerHelicopter getPlayerHelicopter() {
        return gameObjectsCollection.getPlayerHelicopter();
    }

    public SkyScraper getObjective() {
        for(SkyScraper s : gameObjectsCollection.getSkyScrapers())
            if(s.getSequenceNumber() == nonPlayerHelicopter.getLastSkyScraperReached() + 1)
                return s;

        return null;
    }

    public GameObjectsCollection getGameObjectsCollection() {
        return gameObjectsCollection;
    }

    public void apply(){
        steerNPH();
        accelerateNPH();
    }

    private void steerNPH() {
        int nphHeading = nonPlayerHelicopter.getHeading();
        int targetHeading = CollisionDetector.gameObjectInterceptHeading(nonPlayerHelicopter, target);

        int deltaHeading = targetHeading - nphHeading;

        if(Math.abs(deltaHeading) > 180)
            deltaHeading *= -1;

        nonPlayerHelicopter.steer(deltaHeading);
    }

    private void accelerateNPH() {
        int targetSpeed = target instanceof Movable ? ((Movable) target).getSpeed() : 0;

        int targetRange = CollisionDetector.colliderDistance(nonPlayerHelicopter, target);

        if( targetRange > 250 * GameWorld.getGameWorld().getScaleFactor() ||
            targetSpeed >= nonPlayerHelicopter.getSpeed())

                nonPlayerHelicopter.accelerate(20);

        else if(nonPlayerHelicopter.getSpeed() > 100)
            nonPlayerHelicopter.accelerate(-10);
    }

    public void setTarget(GameObject target) {
        this.target = target;
    }

}
