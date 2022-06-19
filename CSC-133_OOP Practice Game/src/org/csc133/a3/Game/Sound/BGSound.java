package org.csc133.a3.Game.Sound;

import com.codename1.ui.util.UITimer;
import org.csc133.a3.Game.Game;

public class BGSound extends Sound implements Runnable {
    private boolean play = false;
    private int volume = 0;

    private UITimer t = new UITimer(this);

    public BGSound (String filename) {
        super(filename);
    }

    public void play(int volume) {
        setVolume(volume);
        play();

        t.schedule(getDuration(), true, Game.getGame());
    }

    public void play() {
        super.play(volume);
        play = true;
    }
    public void pause() {
        super.pause();
        play = false;
    }

    public boolean isPlaying() {
        return play;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        super.setVolume(volume);

        if(volume > 0) volume /= 2;

        this.volume = volume;
    }

    @Override
    public void setMute(boolean mute) {
        super.setMute(mute);

        if(!mute) play();
    }

    @Override
    public void run() {
        if(play) super.play(volume);
    }
}
