package org.csc133.a3.Game.GameWorld.GameObjects.Fixed;

import org.csc133.a3.Game.GameWorld.GameObjects.GameObject;
import org.csc133.a3.Game.GameWorld.GameObjects.GameObjectCollision.IRectangle;
import org.csc133.a3.Game.GameWorld.GameWorld;

public abstract class Fixed extends GameObject implements IRectangle {
    public Fixed(GameWorld.Location location, int size, int color) {
        super(location, size, color);
    }

    @Override
    public int[] getIntersectXY() {
        int intersect = getScaledSize() / 2;

        return new int[]{intersect, intersect};
    }
}

