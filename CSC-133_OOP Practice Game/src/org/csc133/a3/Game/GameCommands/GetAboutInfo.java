package org.csc133.a3.Game.GameCommands;

import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.TextArea;
import com.codename1.ui.events.ActionEvent;
import org.csc133.a3.Game.Game;

public class GetAboutInfo extends MenuCommand {
    public GetAboutInfo() {
        super("Get About Information");
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if(!evt.isConsumed()){
            Game.getGame().pause();

            Dialog about = new Dialog("About");
            TextArea info = new TextArea(   "SkyMail 3000\n" +
                                            "Developed By Kendall Helms\n" +
                                            "CSU Sacramento CSC 133\n" +
                                            "Spring 2021\n" +
                                            "Version 0.3.0"
                                        );

            info.setEditable(false);

            about.addComponent(info);

            Button ok = new Button("OK");
            ok.setCommand(Command.create("OK", null, evt1 -> {
                if(!evt1.isConsumed()) {
                    about.dispose();
                    Game.getGame().play();
                }
            }));

            about.addComponent(ok);

            about.show();
        }
    }
}
