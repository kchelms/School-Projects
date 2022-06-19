package org.csc133.a3.Game.GameCommands;

import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;
import org.csc133.a3.Game.Game;

public class ExitGame extends Command {
    public ExitGame() {
        super("Exit Game");
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if (!evt.isConsumed()) {
            Game.getGame().pause();

            Dialog exitGame = new Dialog("Exit Game?");
            Button yes = new Button("Yes");
            Button no = new Button("No");

            exitGame.addComponent(yes);
            exitGame.addComponent(no);

            yes.setCommand(Command.create("Yes", null, evt12 -> {
                if (!evt12.isConsumed()) {
                    Game.exit();
                }
            }));

            no.setCommand(Command.create("No", null, evt1 -> {
                if (!evt1.isConsumed()) {
                    exitGame.dispose();
                    Game.getGame().play();
                }
            }));

            exitGame.show();
        }
    }
}