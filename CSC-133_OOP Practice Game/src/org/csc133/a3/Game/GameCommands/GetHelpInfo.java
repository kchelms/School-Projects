package org.csc133.a3.Game.GameCommands;

import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.TextArea;
import com.codename1.ui.events.ActionEvent;
import org.csc133.a3.Game.Game;

public class GetHelpInfo extends MenuCommand{
    public GetHelpInfo() {
        super("Get Help Information");
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if(!evt.isConsumed()){
            Game.getGame().pause();

            Dialog help = new Dialog("Help");
            TextArea info = new TextArea(
                    "a - accelerate\n" +
                    "b - brake\n" +
                    "l - left turn\n" +
                    "r - right turn\n" +
                    "x - exit game\n" +
                    ". - play / pause" +
                    "m - mute sound"
            );

            info.setEditable(false);

            help.addComponent(info);

            Button ok = new Button("OK");
            ok.setCommand(Command.create("OK", null, evt1 -> {
                if(!evt1.isConsumed()) {
                    help.dispose();
                    Game.getGame().play();
                }
            }));

            help.addComponent(ok);

            help.show();
        }
    }
}
