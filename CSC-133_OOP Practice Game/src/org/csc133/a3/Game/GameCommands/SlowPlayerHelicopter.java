package org.csc133.a3.Game.GameCommands;

import com.codename1.ui.events.ActionEvent;
import org.csc133.a3.Game.GameWorld.GameWorld;

public class SlowPlayerHelicopter extends GameplayCommand {
    public SlowPlayerHelicopter() {
        super("");
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if(!evt.isConsumed()) {
            GameWorld gameWorld = GameWorld.getGameWorld();

            gameWorld.acceleratePlayerHelicopter(-50);
        }
    }
}
