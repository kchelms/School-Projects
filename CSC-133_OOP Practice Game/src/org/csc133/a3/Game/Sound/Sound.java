package org.csc133.a3.Game.Sound;

import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import com.codename1.ui.Display;

public class Sound implements Cloneable{
    private Media m;
    boolean mute = false;

    public Sound(String fileName) {
        try {
            m = MediaManager.createMedia(
                    Display.getInstance().getResourceAsStream(getClass(),
                            "/" + fileName), "audio/wav");

        } catch (Exception e) {e.printStackTrace();}
    }

    public void play(int volume) {
        if(mute)
            return;

        if(volume > 0) volume /= 2;

        m.setVolume(volume);
        m.setTime(0);
        m.play();
    }

    public void pause() {
        m.pause();
    }

    public void setVolume(int volume) {
        m.setVolume(volume);
    }

    public void setMute(boolean mute) {
        this.mute = mute;

        if(mute) pause();
    }

    public int getDuration() {
        return m.getDuration();
    }
}
