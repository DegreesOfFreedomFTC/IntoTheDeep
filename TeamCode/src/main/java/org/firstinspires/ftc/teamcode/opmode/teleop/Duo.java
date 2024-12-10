package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.common.command.teleop.FieldCentricMecanumDriveCmd;
import org.firstinspires.ftc.teamcode.common.command.teleop.RobotCentricMecanumDriveCmd;
import org.firstinspires.ftc.teamcode.common.subsystem.IMUSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystem.MecanumDriveSubsystem;

@SuppressWarnings({"FieldCanBeLocal", "unused"})
@TeleOp
public class Duo extends CommandOpMode {

    /**
     * The {@link GamepadEx}s that will be used to get inputs. Is is provided by FTCLib and has
     * features to make using the command based paradigm easier.
     */
    private GamepadEx gamepadEx1;
    private GamepadEx gamepadEx2;

    /**
     * The {@link com.arcrobotics.ftclib.command.Subsystem}s that will be used in the TeleOp for the
     * command based paradigm.
     */
    private MecanumDriveSubsystem driveSubsystem;
    private IMUSubsystem imuSubsystem;

    /**
     * The {@link com.arcrobotics.ftclib.command.Command}s that will be used in the TeleOp for the
     * command based paradigm.
     */
    private RobotCentricMecanumDriveCmd robotCentricDriveCmd;
    private FieldCentricMecanumDriveCmd fieldCentricDriveCmd;

    @Override
    public void initialize() {
        // Instantiate the GamepadExs
        gamepadEx1 = new GamepadEx(gamepad1);
        gamepadEx2 = new GamepadEx(gamepad2);

        // Instantiate the Subsystems
        driveSubsystem = new MecanumDriveSubsystem(hardwareMap);
        imuSubsystem = new IMUSubsystem(hardwareMap);

        // Instantiate the Commands
        robotCentricDriveCmd = new RobotCentricMecanumDriveCmd(
                driveSubsystem,
                gamepadEx1::getLeftY,
                gamepadEx1::getLeftX,
                gamepadEx1::getRightX
        );

        // Instantiate the Commands
        fieldCentricDriveCmd = new FieldCentricMecanumDriveCmd(
                driveSubsystem,
                imuSubsystem,
                (() -> gamepadEx1.getLeftX() / 2.0),
                (() -> gamepadEx1.getLeftY() / 2.0),
                (() -> gamepadEx1.getRightX() / 2.0)
        );

        // Set default commands
        driveSubsystem.setDefaultCommand(fieldCentricDriveCmd);
    }
}
