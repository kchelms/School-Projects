package org.csc133.a3.Game.GameWorld.GameObjects;

import org.csc133.a3.Game.GameWorld.GameObjects.Fixed.Fixed;
import org.csc133.a3.Game.GameWorld.GameObjects.Fixed.RefuelingBlimp;
import org.csc133.a3.Game.GameWorld.GameObjects.Fixed.SkyScraper;
import org.csc133.a3.Game.GameWorld.GameObjects.Movable.Bird;
import org.csc133.a3.Game.GameWorld.GameObjects.Movable.Helicopters.NonPlayerHelicopter.NonPlayerHelicopter;
import org.csc133.a3.Game.GameWorld.GameObjects.Movable.Helicopters.PlayerHelicopter;
import org.csc133.a3.Game.GameWorld.GameObjects.Movable.Movable;
import org.csc133.a3.Game.GameWorld.GameWorld;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

public class GameObjectsCollection extends AbstractCollection <GameObject> {
    private PlayerHelicopter playerHelicopter;
    private HashSet<NonPlayerHelicopter> nonPlayerHelicopters = new HashSet<>();
    private HashSet<Bird> birds = new HashSet<>();
    private HashSet<RefuelingBlimp> refuelingBlimps = new HashSet<>();
    private HashSet<SkyScraper> skyScrapers = new HashSet<>();
    private HashSet<GameObject> gameObjects = new HashSet<>();

    private GameWorld.Location startSpawn;

    @Override
    public boolean add(GameObject gameObject) {
        if(gameObject instanceof SkyScraper) {
            skyScrapers.add((SkyScraper) gameObject);

            if(((SkyScraper) gameObject).getSequenceNumber() == 0) {
                startSpawn = gameObject.getLocation();
            }
        }

        else if(gameObject instanceof RefuelingBlimp)
            refuelingBlimps.add((RefuelingBlimp) gameObject);

        else if(gameObject instanceof Bird)
            birds.add((Bird) gameObject);

        else if(gameObject instanceof NonPlayerHelicopter)
            nonPlayerHelicopters.add((NonPlayerHelicopter) gameObject);


        else if(gameObject instanceof PlayerHelicopter)
            playerHelicopter = (PlayerHelicopter) gameObject;

        return gameObjects.add(gameObject);
    }

    @Override
    public boolean remove(Object o) {
        GameObject gameObject = (GameObject) o;

        if(gameObject instanceof SkyScraper) {
            if(((SkyScraper) gameObject).getSequenceNumber() == 1)
                startSpawn = null;

            skyScrapers.remove(gameObject);
        }

        else if(gameObject instanceof RefuelingBlimp)
            refuelingBlimps.remove(gameObject);

        else if(gameObject instanceof Bird)
            birds.remove(gameObject);

        else if(gameObject instanceof NonPlayerHelicopter)
            nonPlayerHelicopters.remove(gameObject);

        else if(gameObject instanceof PlayerHelicopter)
            PlayerHelicopter.destroyPlayerHelicopter();

        return gameObjects.remove(gameObject);
    }

    @Override
    public boolean addAll(Collection<? extends GameObject> c) {
        while (c.iterator().hasNext())
            if(!add(c.iterator().next()))
                return false;

        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        while(c.iterator().hasNext())
            if(!remove(c.iterator().next()))
                return false;

        return true;
    }

    @Override
    public void clear() {
        while(iterator().hasNext())
            remove(iterator().next());
    }

    public PlayerHelicopter getPlayerHelicopter() {
        return playerHelicopter;
    }

    public HashSet<NonPlayerHelicopter> getNonPlayerHelicopters() {
        return nonPlayerHelicopters;
    }

    public HashSet<Bird> getBirds() {
        return birds;
    }

    public HashSet<Movable> getMovables() {
        HashSet<Movable> movables = new HashSet<>();

        movables.addAll(nonPlayerHelicopters);
        movables.addAll(birds);
        movables.add(playerHelicopter);

        return movables;
    }

    public HashSet<Fixed> getFixedGameObjects() {
        HashSet<Fixed> fixedObjects = new HashSet<>(skyScrapers);

        fixedObjects.addAll(refuelingBlimps);

        return fixedObjects;
    }

    public HashSet<RefuelingBlimp> getRefuelingBlimps() {
        return refuelingBlimps;
    }

    public HashSet<SkyScraper> getSkyScrapers() {
        return skyScrapers;
    }

    public GameWorld.Location getStartSpawn() {
        return startSpawn;
    }

    @Override
    public Iterator<GameObject> iterator() {
        return gameObjects.iterator();
    }

    @Override
    public int size() {
        return gameObjects.size();
    }
}