package org.csc133.a3.Game.GameWorld.GameObjects.GameObjectCollision;

import org.csc133.a3.Game.GameWorld.GameObjects.Fixed.RefuelingBlimp;
import org.csc133.a3.Game.GameWorld.GameObjects.Fixed.SkyScraper;
import org.csc133.a3.Game.GameWorld.GameObjects.Movable.Bird;
import org.csc133.a3.Game.GameWorld.GameObjects.Movable.Helicopters.Helicopter;

public interface ICollider {
    void handleCollision(ICollider obj);

    void handleCollision(SkyScraper skyScraper);

    void handleCollision(RefuelingBlimp refuelingBlimp);

    void handleCollision(Bird bird);

    void handleCollision(Helicopter helicopter);
}
