package org.csc133.a3.Game.GameFormComponents;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;
import org.csc133.a3.Game.GameWorld.GameObjects.Fixed.RefuelingBlimp;
import org.csc133.a3.Game.GameWorld.GameObjects.Fixed.SkyScraper;
import org.csc133.a3.Game.GameWorld.GameObjects.GameObjectsCollection;
import org.csc133.a3.Game.GameWorld.GameObjects.Movable.Bird;
import org.csc133.a3.Game.GameWorld.GameObjects.Movable.Helicopters.NonPlayerHelicopter.NonPlayerHelicopter;


public class MapView extends Form {
    private GameObjectsCollection gameObjectsCollection;

    public MapView() {
        this.getAllStyles().setMargin(5,5,5,5);
        this.setScrollable(false);
    }

    public void update() {
        repaint();
        System.out.println(gameObjectsCollection.toString() + "\n");
    }

    public void setGameObjectsCollection(GameObjectsCollection gameObjectsCollection) {
        this.gameObjectsCollection = gameObjectsCollection;
    }

    public void paint(Graphics g) {
        super.paint(g);

        g.setColor(ColorUtil.LTGRAY);
        g.fillRect( getInnerX(),
                    getInnerY(),
                    getInnerWidth(),
                    getInnerHeight()
        );

        if(gameObjectsCollection == null) return;

        Point mapOrigin  = new Point(getInnerX(), getInnerHeight() + getInnerY());

        for(SkyScraper s : gameObjectsCollection.getSkyScrapers())
            s.draw(g, mapOrigin);

        for (RefuelingBlimp r : gameObjectsCollection.getRefuelingBlimps())
            r.draw(g, mapOrigin);

        gameObjectsCollection.getPlayerHelicopter().draw(g, mapOrigin);

        for (NonPlayerHelicopter h : gameObjectsCollection.getNonPlayerHelicopters())
            h.draw(g, mapOrigin);

        for (Bird b : gameObjectsCollection.getBirds())
            b.draw(g, mapOrigin);
    }
}
