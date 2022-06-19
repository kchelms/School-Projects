package org.csc133.a3.Game;

import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.UITimer;
import org.csc133.a3.Game.GameCommands.*;
import org.csc133.a3.Game.GameFormComponents.GlassCockpit.GlassCockpit;
import org.csc133.a3.Game.GameFormComponents.MapView;
import org.csc133.a3.Game.GameFormComponents.PlayerHelicopterControls;
import org.csc133.a3.Game.GameWorld.GameWorld;
import org.csc133.a3.Game.Sound.SoundCollection;

import java.util.HashSet;

public class Game extends Form implements Runnable {
    private static Game game;

    private GameWorld gameWorld = GameWorld.getGameWorld();
    private final GlassCockpit glassCockpit = new GlassCockpit();
    private final MapView mapView = new MapView();
    private final PlayerHelicopterControls playerHelicopterControls = new PlayerHelicopterControls();
    private final SoundCollection soundCollection = new SoundCollection();

    private Command acceleratePlayerHelicopter = new AcceleratePlayerHelicopter();
    private Command slowPlayerHelicopter = new SlowPlayerHelicopter();
    private Command steerPlayerHelicopterLeft = new SteerPlayerHelicopterLeft();
    private Command steerPlayerHelicopterRight = new SteerPlayerHelicopterRight();
    private Command changeNPHStrategy = new ChangeNPHStrategy();
    private Command getHelpInfo = new GetHelpInfo();
    private Command getAboutInfo = new GetAboutInfo();
    private Command muteSound = new MuteSound();
    private Command playPause = new PlayPause();
    private Command exitGame = new ExitGame();

    private boolean gameInitFlag = false;
    private boolean pauseFlag = false;

    private UITimer t = new UITimer(this);

    private Game() {
        createSideMenu();

        setLayout(new BorderLayout());
        add(BorderLayout.CENTER, mapView);
        add(BorderLayout.NORTH, glassCockpit);
        add(BorderLayout.SOUTH, playerHelicopterControls);

        addKeyListeners();

        play();
    }

    private void addKeyListeners() {
        addKeyListener('a', playerHelicopterControls.getButtonCommand(PlayerHelicopterControls.ACCELERATE_BUTTON));
        addKeyListener('b', playerHelicopterControls.getButtonCommand(PlayerHelicopterControls.BRAKE_BUTTON));
        addKeyListener('l', playerHelicopterControls.getButtonCommand(PlayerHelicopterControls.LEFT_BUTTON));
        addKeyListener('r', playerHelicopterControls.getButtonCommand(PlayerHelicopterControls.RIGHT_BUTTON));
        addKeyListener('m', muteSound);
        addKeyListener('.', playPause);
        addKeyListener('x', exitGame);
    }

    private void createSideMenu() {
        this.getToolbar().setTitle("SkyMail 3000");
        this.getToolbar().addMaterialCommandToRightSideMenu("Change NPH Strategy", 'c', changeNPHStrategy);
        this.getToolbar().addMaterialCommandToRightSideMenu("About", 'i', getAboutInfo);
        this.getToolbar().addMaterialCommandToRightSideMenu("Help", 'h', getHelpInfo);
        this.getToolbar().addMaterialCommandToRightSideMenu("Exit", 'x', exitGame);

        this.getToolbar().getRightSideMenuButton().setCommand(Command.create("", null, evt -> {
            Game.getGame().getToolbar().openRightSideMenu();
            Game.getGame().pause();

            for(Command c : Game.game.getAllCommands()){
                if(c instanceof GameplayCommand)
                    c.setEnabled(false);

                if(c instanceof MenuCommand)
                    c.setEnabled(true);
            }
        }));
    }

    private HashSet<Command> getAllCommands() {
        HashSet<Command> ret =  new HashSet<>();

        ret.add(acceleratePlayerHelicopter);
        ret.add(slowPlayerHelicopter);
        ret.add(steerPlayerHelicopterRight);
        ret.add(steerPlayerHelicopterLeft);
        ret.add(changeNPHStrategy);
        ret.add(getAboutInfo);
        ret.add(getHelpInfo);
        ret.add(exitGame);

        return ret;
    }

    public static Game getGame() {
        if(game == null)
            game = new Game();

        return game;
    }

    public static void exit() {
        System.exit(0);
    }

    @Override
    public void run() {
        if(!gameInitFlag) {
            gameWorld.init(mapView, glassCockpit);
            gameInitFlag = true;
        }

        if(checkWinLose()) return;

        gameWorld.tick();
    }

    public boolean getPauseFlag() {
        return pauseFlag;
    }

    public void play() {
        t.schedule(20, true, this);
        glassCockpit.startElapsedTime();

        for(Command c : getAllCommands()) {
            if(c instanceof GameplayCommand)
                c.setEnabled(true);

            if(c instanceof MenuCommand)
                c.setEnabled(false);
        }

        if(gameInitFlag) gameWorld.playMusic();

        pauseFlag = false;
    }

    public void pause() {
        t.cancel();
        glassCockpit.stopElapsedTime();

        for (Command c : getAllCommands())
            if(c instanceof GameplayCommand)
                c.setEnabled(false);

        gameWorld.pauseMusic();

        pauseFlag = true;
    }

    private boolean checkWinLose(){
        if(gameWorld.isWinCondition()){
            pause();

            Dialog win = new Dialog("You Win!");

            addPlayAgainDialogComponents(win);

            win.show();

            return true;
        }

        if(gameWorld.isLoseCondition() != 0){
            String loseString;

            switch (gameWorld.isLoseCondition()){
                case 1: loseString = "Game Over!";
                    break;
                case 2: loseString = "Game over, a non-player helicopter wins!";
                    break;
                default: loseString = "Whoops! You weren't supposed to see this!";
            }

            pause();

            Dialog lose = new Dialog(loseString);

            addPlayAgainDialogComponents(lose);

            lose.show();

            return true;
        }

        return false;
    }

    private void addPlayAgainDialogComponents(Dialog dialog) {
        dialog.setLayout(new BoxLayout(BoxLayout.Y_AXIS));

        dialog.addComponent(new Label("Would you like to play again?"));

        Button yes = new Button("Yes");
        yes.setCommand(Command.create("Yes", null, evt -> {
            dialog.dispose();
            gameWorld.clearGameObjectsCollection();
            GameWorld.destroyGameWorld();
            gameWorld = GameWorld.getGameWorld();

            glassCockpit.resetElapsedTime();

            gameInitFlag = false;

            play();
        }));

        Button no = new Button("No");
        no.setCommand(Command.create("No", null, evt -> Game.exit()));

        Container buttons = new Container();
        buttons.setLayout(new FlowLayout(Component.CENTER));

        buttons.addComponent(yes);
        buttons.addComponent(no);

        dialog.addComponent(buttons);
    }
}