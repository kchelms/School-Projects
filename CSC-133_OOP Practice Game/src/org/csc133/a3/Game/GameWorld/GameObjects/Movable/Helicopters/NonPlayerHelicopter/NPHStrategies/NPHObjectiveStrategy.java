package org.csc133.a3.Game.GameWorld.GameObjects.Movable.Helicopters.NonPlayerHelicopter.NPHStrategies;

import org.csc133.a3.Game.GameWorld.GameObjects.GameObjectsCollection;
import org.csc133.a3.Game.GameWorld.GameObjects.Movable.Helicopters.NonPlayerHelicopter.NonPlayerHelicopter;

public class NPHObjectiveStrategy extends NPHStrategy{
    public NPHObjectiveStrategy(NonPlayerHelicopter nonPlayerHelicopter, GameObjectsCollection gameObjectsCollection) {
        super(nonPlayerHelicopter, gameObjectsCollection);

        setTarget(getObjective());
    }

    @Override
    public void apply() {
        setTarget(getObjective());

        super.apply();
    }
}
