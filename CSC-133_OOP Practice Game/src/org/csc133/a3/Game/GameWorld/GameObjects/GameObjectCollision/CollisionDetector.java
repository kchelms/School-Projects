package org.csc133.a3.Game.GameWorld.GameObjects.GameObjectCollision;

import com.codename1.util.MathUtil;
import org.csc133.a3.Game.GameWorld.GameObjects.GameObject;
import org.csc133.a3.Game.GameWorld.GameWorld;

public class CollisionDetector {

    public static boolean collidesWith(ICollider collider1, ICollider collider2) {
        if(collider1 instanceof ICircle && collider2 instanceof ICircle)
            return collidesWith_CircleCircle((ICircle) collider1, (ICircle) collider2);

        else if(collider1 instanceof ICircle || collider2 instanceof ICircle)
            return collidesWith_CircleRec(
                    collider1 instanceof ICircle ? (ICircle) collider1 : (ICircle) collider2,
                    collider2 instanceof IRectangle ? (IRectangle) collider2 : (IRectangle) collider1
            );

        else return collidesWith_RecRec((IRectangle) collider1, (IRectangle) collider2);
    }

    private static boolean collidesWith_RecRec(IRectangle rec1, IRectangle rec2) {
        if(recObjectOverlap(rec1, rec2, 0)) {
            rec1.handleCollision(rec2);
            rec2.handleCollision(rec1);
            return true;
        }

        return false;
    }

    private static boolean collidesWith_CircleCircle(ICircle circle1, ICircle circle2){
        if( colliderDistance(circle1, circle2) <
            circle1.getIntersectRadius() + circle2.getIntersectRadius()) {

                circle1.handleCollision(circle2);
                circle2.handleCollision(circle1);

            return true;
        }
        
        return false;
    }

    private static boolean collidesWith_CircleRec(ICircle circle, IRectangle rec){
        int objectDistance = colliderDistance(circle, rec);

        if (objectDistance < rec.getIntersectXY()[0] + circle.getIntersectRadius() &&
            objectDistance < rec.getIntersectXY()[1] + circle.getIntersectRadius()) {
                rec.handleCollision(circle);
                circle.handleCollision(rec);

                return true;
        }

        return false;
    }

    public static int gameObjectInterceptHeading(ICollider collider1, ICollider collider2) {
        double[] deltaXY = gameObjectDeltaXY(collider1, collider2);

        double targetHeading = MathUtil.atan2(deltaXY[0], deltaXY[1]);

        targetHeading = Math.toDegrees(targetHeading);

        if(targetHeading < 0) targetHeading += 360;

        return (int) targetHeading;
    }

    public static int colliderDistance(ICollider collider1, ICollider collider2) {
        double[] deltaXY = gameObjectDeltaXY(collider1, collider2);

        return (int) Math.sqrt((deltaXY[0] * deltaXY[0]) + (deltaXY[1] * deltaXY[1]));
    }

    private static double[] gameObjectDeltaXY(ICollider collider1, ICollider collider2) {
        GameWorld.Location obj1Location = ((GameObject) collider1).getLocation();
        GameWorld.Location obj2Location = ((GameObject) collider2).getLocation();

        double deltaX = obj2Location.getX() - obj1Location.getX();
        double deltaY = obj2Location.getY() - obj1Location.getY();

        return new double[] {deltaX, deltaY};
    }
    
    public static boolean recObjectOverlap(IRectangle rec1, IRectangle rec2, int buffer) {
        return Math.abs( ((GameObject) rec1).getLocationCoordinates()[0] - ((GameObject) rec2).getLocationCoordinates()[0])
                    < rec1.getIntersectXY()[0] + rec2.getIntersectXY()[0] + buffer &&
                Math.abs( ((GameObject) rec1).getLocationCoordinates()[1] - ((GameObject) rec2).getLocationCoordinates()[1])
                    < rec1.getIntersectXY()[1] + rec2.getIntersectXY()[1] + buffer;
    }
}