package org.csc133.a3.Game.GameWorld.GameObjects.Movable.Helicopters;

import com.codename1.charts.util.ColorUtil;
import org.csc133.a3.Game.GameWorld.GameObjects.Movable.Bird;
import org.csc133.a3.Game.GameWorld.GameWorld;
import org.csc133.a3.Game.Sound.SoundCollection;

public class PlayerHelicopter extends Helicopter{
    private static PlayerHelicopter playerHelicopter;

    private PlayerHelicopter(GameWorld.Location location) {
        super(location, 150, ColorUtil.BLUE);
    }

    public static PlayerHelicopter spawnPlayerHelicopter(GameWorld.Location location) {
        if(playerHelicopter == null)
            playerHelicopter = new PlayerHelicopter(location);

        return playerHelicopter;
    }

    public static PlayerHelicopter getPlayerHelicopter() {
        return playerHelicopter;
    }

    public static void destroyPlayerHelicopter() {
        playerHelicopter = null;
    }

    @Override
    public void handleCollision(Helicopter helicopter) {
        super.handleCollision(helicopter);

        SoundCollection.getHelicopterCollide().play(100);
    }

    @Override
    public void handleCollision(Bird bird) {
        super.handleCollision(bird);

        SoundCollection.getHelicopterCollide().play(50);
    }
}
