package org.csc133.a3.Game.GameWorld.GameObjects.Movable.Helicopters.NonPlayerHelicopter.NPHStrategies;

import org.csc133.a3.Game.GameWorld.GameObjects.GameObjectsCollection;
import org.csc133.a3.Game.GameWorld.GameObjects.Movable.Helicopters.NonPlayerHelicopter.NonPlayerHelicopter;
import org.csc133.a3.Game.GameWorld.GameObjects.Movable.Helicopters.PlayerHelicopter;

public class NPHAttackStrategy extends NPHStrategy{
    public NPHAttackStrategy(NonPlayerHelicopter nonPlayerHelicopter, GameObjectsCollection gameObjectsCollection) {
        super(nonPlayerHelicopter, gameObjectsCollection);

        setTarget(PlayerHelicopter.getPlayerHelicopter());
    }
}
