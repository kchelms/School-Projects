package org.csc133.a3.Game.GameWorld.GameObjects.Movable.Helicopters.NonPlayerHelicopter;

import org.csc133.a3.Game.GameFormComponents.GlassCockpit.SevenSegmentDisplays.GameTimeDisplay;
import org.csc133.a3.Game.GameWorld.GameObjects.GameObjectsCollection;
import org.csc133.a3.Game.GameWorld.GameObjects.Movable.Helicopters.Helicopter;
import org.csc133.a3.Game.GameWorld.GameObjects.Movable.Helicopters.NonPlayerHelicopter.NPHStrategies.NPHAttackStrategy;
import org.csc133.a3.Game.GameWorld.GameObjects.Movable.Helicopters.NonPlayerHelicopter.NPHStrategies.NPHMixedStrategy;
import org.csc133.a3.Game.GameWorld.GameObjects.Movable.Helicopters.NonPlayerHelicopter.NPHStrategies.NPHObjectiveStrategy;
import org.csc133.a3.Game.GameWorld.GameObjects.Movable.Helicopters.NonPlayerHelicopter.NPHStrategies.NPHStrategy;
import org.csc133.a3.Game.GameWorld.GameWorld;

import static org.csc133.a3.Game.GameWorld.GameObjects.Movable.Helicopters.NonPlayerHelicopter.NPHStrategies.NPHStrategy.*;

public class NonPlayerHelicopter extends Helicopter {
    private NPHStrategy strategy;

    public NonPlayerHelicopter (GameWorld.Location location, int color) {
        super(location, 140, color);
    }

    public void setStrategy(NPHStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(int strategy, GameObjectsCollection gameObjectsCollection){
        switch (strategy){
            case NPH_STRATEGY_ATTACK: setStrategy(new NPHAttackStrategy(this, gameObjectsCollection));
                break;
            case NPH_STRATEGY_OBJECTIVE: setStrategy(new NPHObjectiveStrategy(this, gameObjectsCollection));
                break;
            case NPH_STRATEGY_MIXED: setStrategy(new NPHMixedStrategy(this, gameObjectsCollection));
                break;
        }
    }

    public int getNphStrategy() {
        if(strategy instanceof NPHAttackStrategy) return NPH_STRATEGY_ATTACK;

        else if(strategy instanceof  NPHObjectiveStrategy) return NPH_STRATEGY_OBJECTIVE;

        else return NPH_STRATEGY_MIXED;
    }

    @Override
    public void move(long elapsedTime) {
        strategy.apply();

        if( helicopterCrashOffCourse &&
            GameTimeDisplay.getElapsedTime() - helicopterCrashTime < helicopterCrashOffCourseDuration)
                setSpeed(20);

        super.move(elapsedTime);
    }

    @Override
    public void steer(int direction) {
        if( helicopterCrashOffCourse &&
            GameTimeDisplay.getElapsedTime() - helicopterCrashTime < helicopterCrashOffCourseDuration) {

                return;
        }

        else {
            super.steer(direction);

            if(helicopterCrashOffCourse) helicopterCrashOffCourse = false;
        }
    }

    long helicopterCrashTime = GameTimeDisplay.getElapsedTime();
    int helicopterCrashOffCourseDuration = 750;
    boolean helicopterCrashOffCourse = false;

    @Override
    public void handleCollision(Helicopter helicopter) {
        super.handleCollision(helicopter);

        helicopterCrashOffCourse = true;
        helicopterCrashTime = GameTimeDisplay.getElapsedTime();
    }
}
