package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.common.command.intothedeep.ForearmLowerCmd;
import org.firstinspires.ftc.teamcode.common.command.intothedeep.ForearmRaiseCmd;
import org.firstinspires.ftc.teamcode.common.command.intothedeep.ForearmStopCmd;
import org.firstinspires.ftc.teamcode.common.command.intothedeep.TowerControlCmd;
import org.firstinspires.ftc.teamcode.common.command.teleop.RobotCentricMecanumDriveCmd;
import org.firstinspires.ftc.teamcode.common.subsystem.ForearmSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystem.MecanumDriveSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystem.TowerSubsystem;

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
    private TowerSubsystem towerSubsystem;
    private ForearmSubsystem forearmSubsystem;

    /**
     * The {@link com.arcrobotics.ftclib.command.Command}s that will be used in the TeleOp for the
     * command based paradigm.
     */
    private RobotCentricMecanumDriveCmd driveCmd;
    private TowerControlCmd towerNormalCmd;
    private TowerControlCmd towerHangingCmd;
    private ForearmRaiseCmd forearmRaiseCmd;
    private ForearmLowerCmd forearmLowerCmd;
    private ForearmStopCmd forearmStopCmd;

    @Override
    public void initialize() {
        // Instantiate the GamepadExs
        gamepadEx1 = new GamepadEx(gamepad1);
        gamepadEx2 = new GamepadEx(gamepad2);

        // Instantiate the Subsystems
        driveSubsystem = new MecanumDriveSubsystem(hardwareMap);
        towerSubsystem = new TowerSubsystem(hardwareMap);
        forearmSubsystem = new ForearmSubsystem(hardwareMap);

        // Instantiate the Commands
        driveCmd = new RobotCentricMecanumDriveCmd(
                driveSubsystem,
                gamepadEx1::getLeftY,
                gamepadEx1::getLeftX,
                gamepadEx1::getRightX
        );

        towerNormalCmd = new TowerControlCmd(
                towerSubsystem,
                () -> (gamepadEx2.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER)
                        - gamepadEx2.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER)) / 2.0);

        towerHangingCmd = new TowerControlCmd(
                towerSubsystem,
                () -> gamepadEx2.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER)
                        - gamepadEx2.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER));

        forearmRaiseCmd = new ForearmRaiseCmd(forearmSubsystem);
        forearmLowerCmd = new ForearmLowerCmd(forearmSubsystem);
        forearmStopCmd = new ForearmStopCmd(forearmSubsystem);

        // Set default commands
        driveSubsystem.setDefaultCommand(driveCmd);
        towerSubsystem.setDefaultCommand(towerNormalCmd);

        // Bind buttons
        gamepadEx2.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                .whenPressed(forearmLowerCmd)
                .whenReleased(forearmStopCmd);
        gamepadEx2.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                .whenPressed(forearmRaiseCmd)
                .whenReleased(forearmStopCmd);

        gamepadEx2.getGamepadButton(GamepadKeys.Button.X).toggleWhenPressed(
                () -> towerSubsystem.setDefaultCommand(towerHangingCmd),
                () -> towerSubsystem.setDefaultCommand(towerNormalCmd)
        );
    }
}
