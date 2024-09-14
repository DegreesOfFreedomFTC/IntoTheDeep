package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@SuppressWarnings({"FieldCanBeLocal", "unused"})
@TeleOp
public class Solo extends CommandOpMode {

    /**
     * The {@link GamepadEx} that will be used to get inputs. Is is provided by FTCLib and has
     * features to make using the command based paradigm easier.
     */
    private GamepadEx gamepadEx;

    @Override
    public void initialize() {
        // Instantiate the GamepadEx
        gamepadEx = new GamepadEx(gamepad1);
    }
}
