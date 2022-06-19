package org.csc133.a3.Game.GameFormComponents;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import org.csc133.a3.Game.GameCommands.AcceleratePlayerHelicopter;
import org.csc133.a3.Game.GameCommands.SlowPlayerHelicopter;
import org.csc133.a3.Game.GameCommands.SteerPlayerHelicopterLeft;
import org.csc133.a3.Game.GameCommands.SteerPlayerHelicopterRight;

import java.io.IOException;

public class PlayerHelicopterControls extends Container {
    public static final int ACCELERATE_BUTTON = 0;
    public static final int  BRAKE_BUTTON = 1;
    public static final int LEFT_BUTTON = 2;
    public static final int RIGHT_BUTTON = 3;

    private Image accelerateImage;
    private Image brakeImage;
    private Image leftImage;
    private Image rightImage;

    private Button accelerateButton;
    private Button brakeButton;
    private Button leftButton;
    private Button rightButton;

    public PlayerHelicopterControls() {
        try {
            accelerateImage = Image.createImage("/ButtonControls_Accelerate.png");
            brakeImage = Image.createImage("/ButtonControls_Slow.png");
            leftImage = Image.createImage("/ButtonControls_Left.png");
            rightImage = Image.createImage("/ButtonControls_Right.png");

        } catch (IOException e) {e.printStackTrace();}

        int[] buttonImageDimensions = new int[]{
                Display.getInstance().getDisplayWidth() / 4,
                Display.getInstance().getDisplayHeight() / 20
        };

        accelerateImage = accelerateImage.scaled(buttonImageDimensions[0], buttonImageDimensions[1]);
        brakeImage = brakeImage.scaled(buttonImageDimensions[0], buttonImageDimensions[1]);
        leftImage = leftImage.scaled(buttonImageDimensions[0], buttonImageDimensions[1]);
        rightImage = rightImage.scaled(buttonImageDimensions[0], buttonImageDimensions[1]);

        accelerateButton = new Button("", accelerateImage);
        brakeButton = new Button("", brakeImage);
        leftButton = new Button("", leftImage);
        rightButton = new Button("", rightImage);

        accelerateButton.setCommand(new AcceleratePlayerHelicopter());
        brakeButton.setCommand(new SlowPlayerHelicopter());
        leftButton.setCommand(new SteerPlayerHelicopterLeft());
        rightButton.setCommand(new SteerPlayerHelicopterRight());

        accelerateButton.getAllStyles().setPadding(0,0,0,0);
        brakeButton.getAllStyles().setPadding(0,0,0,0);
        leftButton.getAllStyles().setPadding(0,10,0,0);
        rightButton.getAllStyles().setPadding(0,10,0,0);

        accelerateButton.getAllStyles().setMargin(0,0,0,0);
        brakeButton.getAllStyles().setMargin(0,0,0,0);
        leftButton.getAllStyles().setMargin(0,0,0,0);
        rightButton.getAllStyles().setMargin(0,0,0,0);

        this.getAllStyles().setPadding(5,0,5,5);
        this.getAllStyles().setMargin(0,0,5,5);

        this.setLayout(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_SCALE));

        this.addComponent(BorderLayout.NORTH, accelerateButton);
        this.addComponent(BorderLayout.CENTER, brakeButton);
        this.addComponent(BorderLayout.WEST, leftButton);
        this.addComponent(BorderLayout.EAST, rightButton);

        this.getAllStyles().setBgTransparency(255);
        this.getAllStyles().setBgColor(ColorUtil.rgb(49,49,49));
    }

    public Command getButtonCommand(int button) {
        switch (button) {
            case ACCELERATE_BUTTON: return accelerateButton.getCommand();

            case BRAKE_BUTTON: return brakeButton.getCommand();

            case LEFT_BUTTON: return leftButton.getCommand();

            case RIGHT_BUTTON: return rightButton.getCommand();

            default: System.err.println("Invalid Button");
        }

        return null;
    }
}
