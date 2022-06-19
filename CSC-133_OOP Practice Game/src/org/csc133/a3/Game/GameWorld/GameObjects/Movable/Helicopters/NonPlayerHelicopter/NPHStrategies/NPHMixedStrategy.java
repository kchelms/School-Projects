package org.csc133.a3.Game.GameWorld.GameObjects.Movable.Helicopters.NonPlayerHelicopter.NPHStrategies;

import org.csc133.a3.Game.GameFormComponents.GlassCockpit.SevenSegmentDisplays.GameTimeDisplay;
import org.csc133.a3.Game.GameWorld.GameObjects.GameObjectCollision.CollisionDetector;
import org.csc133.a3.Game.GameWorld.GameObjects.GameObjectsCollection;
import org.csc133.a3.Game.GameWorld.GameObjects.Movable.Helicopters.NonPlayerHelicopter.NonPlayerHelicopter;

public class NPHMixedStrategy extends NPHStrategy{
    private NPHStrategy nphStrategy;

    private long chaseTimer = GameTimeDisplay.getElapsedTime();

    public NPHMixedStrategy(NonPlayerHelicopter nonPlayerHelicopter, GameObjectsCollection gameObjectsCollection) {
        super(nonPlayerHelicopter, gameObjectsCollection);

        implementStrategy(NPH_STRATEGY_OBJECTIVE);
    }

    @Override
    public void apply() {
        boolean nphIsCloserToPlayerHelicopter =
                    CollisionDetector.colliderDistance(getNPH(), getPlayerHelicopter()) <
                    CollisionDetector.colliderDistance(getNPH(), getObjective());

        boolean nphHasCollidedWithPlayerHelicopter = getNPH().getCollisionHistoryMap().containsKey(getPlayerHelicopter());
        boolean chaseTimerHasElapsed = GameTimeDisplay.getElapsedTime() - chaseTimer >= 1000;
        boolean nphIsApplyingAttackStrategy = nphStrategy instanceof NPHAttackStrategy;

        if( nphIsCloserToPlayerHelicopter &&
            (!nphHasCollidedWithPlayerHelicopter || chaseTimerHasElapsed) &&
            !nphIsApplyingAttackStrategy) {

                if(!nphHasCollidedWithPlayerHelicopter) getNPH().getCollisionHistoryMap().remove(getPlayerHelicopter());

                implementStrategy(NPH_STRATEGY_ATTACK);
                chaseTimer = GameTimeDisplay.getElapsedTime();
        }

        else if((!nphIsCloserToPlayerHelicopter || nphHasCollidedWithPlayerHelicopter || chaseTimerHasElapsed) &&
                nphIsApplyingAttackStrategy) {

                    implementStrategy(NPH_STRATEGY_OBJECTIVE);
                    chaseTimer = GameTimeDisplay.getElapsedTime();
        }

        nphStrategy.apply();
    }

    private void implementStrategy(int strategy) {
        switch (strategy) {
            case NPH_STRATEGY_ATTACK: {
                nphStrategy = new NPHAttackStrategy(getNPH(), getGameObjectsCollection());
                break;
            }

            case NPH_STRATEGY_OBJECTIVE: {
                nphStrategy = new NPHObjectiveStrategy(getNPH(), getGameObjectsCollection());
                break;
            }

            default:
                System.err.println("Invalid Strategy");
        }
    }
}