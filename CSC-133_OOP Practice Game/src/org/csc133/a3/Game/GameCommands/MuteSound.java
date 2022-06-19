package org.csc133.a3.Game.GameCommands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import org.csc133.a3.Game.Sound.SoundCollection;

public class MuteSound extends Command {

    public MuteSound() {
        super("Mute");
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if(!evt.isConsumed()){
            SoundCollection.setMute();
        }
    }
}
