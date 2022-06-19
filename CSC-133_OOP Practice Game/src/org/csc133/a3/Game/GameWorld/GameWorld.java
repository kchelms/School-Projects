package org.csc133.a3.Game.GameWorld;

import com.codename1.charts.util.ColorUtil;
import org.csc133.a3.Game.GameFormComponents.GlassCockpit.GlassCockpit;
import org.csc133.a3.Game.GameFormComponents.MapView;
import org.csc133.a3.Game.GameWorld.GameObjects.Fixed.Fixed;
import org.csc133.a3.Game.GameWorld.GameObjects.Fixed.RefuelingBlimp;
import org.csc133.a3.Game.GameWorld.GameObjects.Fixed.SkyScraper;
import org.csc133.a3.Game.GameWorld.GameObjects.GameObject;
import org.csc133.a3.Game.GameWorld.GameObjects.GameObjectCollision.CollisionDetector;
import org.csc133.a3.Game.GameWorld.GameObjects.GameObjectsCollection;
import org.csc133.a3.Game.GameWorld.GameObjects.Movable.Bird;
import org.csc133.a3.Game.GameWorld.GameObjects.Movable.Helicopters.NonPlayerHelicopter.NonPlayerHelicopter;
import org.csc133.a3.Game.GameWorld.GameObjects.Movable.Helicopters.PlayerHelicopter;
import org.csc133.a3.Game.GameWorld.GameObjects.Movable.Movable;
import org.csc133.a3.Game.Sound.SoundCollection;

import java.util.HashSet;
import java.util.Random;

public class GameWorld {
    private static GameWorld gameWorld;

    private Area worldArea;

    private final GameObjectsCollection gameObjectsCollection = new GameObjectsCollection();

    private final int numberOfSkyScrapers = 5;

    private int ticks = 0;
    private int lives = 3;
    private long timeOfLastTick = 0;

    private MapView mapView;
    private GlassCockpit glassCockpit;

    private GameWorld() {}

    public static GameWorld getGameWorld() {
        if(gameWorld == null)
            gameWorld = new GameWorld();

        return gameWorld;
    }

    public static void destroyGameWorld() {
        gameWorld = null;
    }

    //
    //Init Sequence
    //
    public void init(MapView mapView, GlassCockpit glassCockpit) {
        this.mapView = mapView;
        this.glassCockpit = glassCockpit;

        worldArea = new Area(mapView.getInnerWidth(), mapView.getInnerHeight());

        spawnSkyScrapers(5);
        spawnRefuelingBlimps(3);
        spawnBirds(3);
        spawnPlayerHelicopter();
        spawnNonPlayerHelicopters(2);

        mapView.setGameObjectsCollection(gameObjectsCollection);
        glassCockpit.setPlayerHelicopter(getPlayerHelicopter());
        playMusic();
    }

    private void spawnSkyScrapers(int n) {
        for(int i = 0; i <= n; i++){
            Location location = randomSpawnLocation();

            SkyScraper s =  new SkyScraper(location, i);

            s.setScaleFactor(getScaleFactor());

            fixedSpawnIntersectResolve(s);

            gameObjectsCollection.add(s);
        }
    }

    private void spawnRefuelingBlimps(int n) {
        Random random = new Random();

        for(int i = 0; i < n; i++){
            Location location = randomSpawnLocation();

            int size = random.nextInt(200) + 125;

            RefuelingBlimp r = new RefuelingBlimp(location, size);

            r.setScaleFactor(getScaleFactor());

            fixedSpawnIntersectResolve(r);

            gameObjectsCollection.add(r);
        }
    }

    private void fixedSpawnIntersectResolve(Fixed fixed){
        fixedSpawnIntersectResolveHelper(fixed, 0);
    }

    private void fixedSpawnIntersectResolveHelper(Fixed fixed, int iter) {
        Location location = fixed.getLocation();

        boolean intersectFlag = false;

        int buffer = (int) (20 * getScaleFactor());

        int[] intersects = fixed.getIntersectXY();

        if( location.getX() - intersects[0] < buffer || location.getX() + intersects[0] > worldArea.getW() - buffer ||
            location.getY() - intersects[1] < buffer || location.getY() + intersects[1] > worldArea.getH() - buffer) {

            intersectFlag = true;
        }

        if(!intersectFlag)
            for (Fixed fixed2 : gameObjectsCollection.getFixedGameObjects()) {

                if(CollisionDetector.recObjectOverlap(fixed, fixed2, buffer)){

                    intersectFlag = true;
                    break;
                }
            }

        if(intersectFlag && iter < 10) {
            fixed.setLocation(randomSpawnLocation());

            fixedSpawnIntersectResolveHelper(fixed, ++iter);
        }

        else if (iter >= 10)
            resetGameWorld();
    }

    private void spawnBirds(int n) {
        Random random = new Random();

        for(int i = 0; i < n; i++){
            Location location = randomSpawnLocation();

            int heading = random.nextInt(360);
            int speed   = random.nextInt(130) + 60;

            Bird b = new Bird(location, heading, speed);

            b.setScaleFactor(getScaleFactor());

            gameObjectsCollection.add(b);
        }
    }

    private void spawnNonPlayerHelicopters(int n) {
        Random random = new Random();

        int[] color = {ColorUtil.CYAN, ColorUtil.YELLOW, ColorUtil.GRAY, ColorUtil.GREEN, ColorUtil.MAGENTA};

        for(SkyScraper s : gameObjectsCollection.getSkyScrapers()){
            if(n == 0) return;

            if(s.getSequenceNumber() > 1) {
                Location l = new Location(s.getLocation());

                NonPlayerHelicopter nph = new NonPlayerHelicopter(l, color[n % color.length]);
                nph.setStrategy(random.nextInt(3), gameObjectsCollection);
                nph.setScaleFactor(getScaleFactor());

                gameObjectsCollection.add(nph);

                n--;
            }
        }
    }

    private void spawnPlayerHelicopter() {
        Location startLocation = new Location(gameObjectsCollection.getStartSpawn());

        gameObjectsCollection.add(PlayerHelicopter.spawnPlayerHelicopter(startLocation));

        getPlayerHelicopter().setScaleFactor(getScaleFactor());
    }

    private Location randomSpawnLocation() {
        Random random = new Random();

        int x = random.nextInt(worldArea.getW());
        int y = random.nextInt(worldArea.getH());

        return new Location(x,y);
    }

    public float getScaleFactor() {

        return (float) Math.min(
                mapView.getInnerWidth() / 1000.,
                mapView.getInnerHeight() / 1000.
        );
    }

    private void scaleObjectSizes() {
        for(GameObject g : gameObjectsCollection)
            g.setScaleFactor(getScaleFactor());
    }

    public void clearGameObjectsCollection() {
        gameObjectsCollection.clear();
    }

    public void playMusic(){
        SoundCollection.getBgMusic().play(100);
    }

    public void pauseMusic(){
        SoundCollection.getBgMusic().pause();
    }

    //
    //GameWorld Data
    //
    public String getPlayerHelicopterData() {
        return  "Last Skyscraper Reached: " + getPlayerHelicopter().getLastSkyScraperReached() + "\n" +
                "Helicopter Fuel Level: " + getPlayerHelicopter().getFuelLevel() + "\n" +
                "Helicopter Damage Level: " + getPlayerHelicopter().getDamageLevel() + "\n";
    }

    public boolean isWinCondition() {
        return getPlayerHelicopter().getLastSkyScraperReached() == numberOfSkyScrapers;
    }

    public int isLoseCondition() {
        if(lives == 0) return 1;

        for(NonPlayerHelicopter nph : gameObjectsCollection.getNonPlayerHelicopters())
            if(nph.getLastSkyScraperReached() == numberOfSkyScrapers)
                return 2;

        return 0;
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();

        for (GameObject gameObject : gameObjectsCollection)
            ret.append(gameObject.toString()).append("\n");

        return ret.toString();
    }

    public PlayerHelicopter getPlayerHelicopter() {
        return gameObjectsCollection.getPlayerHelicopter();
    }

    //
    //World Events
    //
    public void tick() {
        replaceEmptyRefuelingBlimps();
        despawnImmobilizedNPHs();
        moveMovableObjects();
        detectCollisions();

        if(isImmobilizedPlayerHelicopter())
            resetGameWorld();

        ticks++;
        timeOfLastTick = glassCockpit.getElapsedTime();

        glassCockpit.updateHelicopterDisplays();
        glassCockpit.updateLivesDisplay(lives);
        mapView.update();
    }

    private void moveMovableObjects() {
        for(Movable object : gameObjectsCollection.getMovables()) {
            object.move(glassCockpit.getElapsedTime() - timeOfLastTick);
        }
    }

    private void replaceEmptyRefuelingBlimps() {
        HashSet<RefuelingBlimp> refuelingBlimpsCollection = new HashSet<>(gameObjectsCollection.getRefuelingBlimps());

        for (RefuelingBlimp refuelingBlimp : refuelingBlimpsCollection) {
            if(refuelingBlimp.getCapacity() == 0) {
                gameObjectsCollection.remove(refuelingBlimp);
                spawnRefuelingBlimps(1);
            }
        }
    }

    private void despawnImmobilizedNPHs() {
        HashSet<NonPlayerHelicopter> nphSet = new HashSet<>(gameObjectsCollection.getNonPlayerHelicopters());

        for(NonPlayerHelicopter nph : nphSet){
            if(nph.isImmobilized()) gameObjectsCollection.remove(nph);
        }
    }

    public void detectCollisions(){
        HashSet<GameObject> tempGameObjectsCollection = new HashSet<>(gameObjectsCollection);
        HashSet<GameObject> consumedColliders = new HashSet<>();

        for(GameObject object1 : tempGameObjectsCollection){
            if(consumedColliders.contains(object1))
                continue;

            HashSet<GameObject> tempGameObjectsCollection2 = new HashSet<>(gameObjectsCollection);

            tempGameObjectsCollection2.remove(object1);

            for(GameObject object2 : tempGameObjectsCollection2)
                CollisionDetector.collidesWith(object1, object2);

            consumedColliders.add(object1);
        }
    }

    public void acceleratePlayerHelicopter(int direction) {
        getPlayerHelicopter().accelerate(direction);
    }

    public void steerPlayerHelicopter(int direction) {
        getPlayerHelicopter().steer(direction);
    }

    public void switchNPHStrategy() {
        for(NonPlayerHelicopter nph : gameObjectsCollection.getNonPlayerHelicopters()) {
            nph.setStrategy((nph.getNphStrategy() + 1) % 3, gameObjectsCollection);
        }
    }

    private void resetGameWorld(){
        if(lives > 0) {
            gameObjectsCollection.clear();
            init(mapView,glassCockpit);
        }

        else glassCockpit.updateLivesDisplay(lives);
    }

    private boolean isImmobilizedPlayerHelicopter() {
        return getPlayerHelicopter().isImmobilized();
    }

    //
    //GameWorld Area Type Classes
    //
    public class Area {
        private final int W;
        private final int H;

        public Area (int w, int h) {
            W = w;
            H = h;
        }

        public int getW() {
            return W;
        }

        public int getH() {
            return H;
        }


    }

    public class Location {
        private float x;
        private float y;
        private final float boundary = 50 * getScaleFactor();

        public Location (Location location) {
            x = location.x;
            y = location.y;
        }

        public Location (int x, int y) {
            setLocation(x,y);
        }

        public float getX() {
            return x;
        }

        public float getY() {
            return y;
        }

        public float[] getCoordinates() {
            return new float[]{x,y};
        }

        public void setLocation(float x, float y) {
            this.x = x;
            this.y = y;
        }

        public boolean isOutOfBounds() {
            return  x < -boundary || x > worldArea.W + boundary ||
                    y < -boundary || y > worldArea.H + boundary;
        }

        public float xOutOfBounds() {
            float xBound = worldArea.W + boundary;

            if(x < - boundary) return x + boundary;

            if(x > xBound) return x - xBound;

            return 0;
        }

        public float yOutOfBounds() {
            float yBound = worldArea.H + boundary;

            if(y < - boundary) return y + boundary;

            if(y > yBound) return y - yBound;

            return 0;
        }
    }
}