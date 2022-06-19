package org.csc133.a3.Game.GameCommands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import org.csc133.a3.Game.Game;

public class PlayPause extends Command {
    public PlayPause() {
        super("Play / Pause Game");
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if(!evt.isConsumed()){
            if(Game.getGame().getPauseFlag())
                Game.getGame().play();

            else Game.getGame().pause();
        }
    }
}
