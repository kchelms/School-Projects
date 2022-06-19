package org.csc133.a3.Game.Sound;

public class SoundCollection {
    private final static BGSound bgMusic = new BGSound("music.wav");
    private final static Sound helicopterCollide = new Sound("collide.wav");
    private final static Sound helicopterCrash = new Sound("crash.WAV");
    private final static Sound birdSound = new Sound("duck.wav");
    private final static Sound refuel = new Sound("refuel.wav");

    private static boolean mute = false;

    public static Sound getHelicopterCollide() {
        return helicopterCollide;
    }

    public static Sound getHelicopterCrash() {
        return helicopterCrash;
    }

    public static Sound getBirdSound() {
        return birdSound;
    }

    public static Sound getRefuel() {
        return refuel;
    }

    public static Sound getBgMusic() {
        return bgMusic;
    }

    public static void setMute() {
        bgMusic.setMute(!mute);
        helicopterCollide.setMute(!mute);
        helicopterCrash.setMute(!mute);
        birdSound.setMute(!mute);
        refuel.setMute(!mute);

        mute = !mute;
    }
}
