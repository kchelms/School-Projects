package org.csc133.a3.Game.GameCommands;

import com.codename1.ui.events.ActionEvent;
import org.csc133.a3.Game.Game;
import org.csc133.a3.Game.GameWorld.GameWorld;

public class ChangeNPHStrategy extends MenuCommand {
    public ChangeNPHStrategy() {
        super("Change Non-Player Helicopter Strategy");
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if(!evt.isConsumed()){
            GameWorld.getGameWorld().switchNPHStrategy();
            Game.getGame().play();
        }
    }
}
