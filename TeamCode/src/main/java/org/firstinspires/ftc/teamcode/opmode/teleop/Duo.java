package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.common.command.intothedeep.ForearmHangingLowerCmd;
import org.firstinspires.ftc.teamcode.common.command.intothedeep.ForearmHangingRaiseCmd;
import org.firstinspires.ftc.teamcode.common.command.intothedeep.ForearmNormalLowerCmd;
import org.firstinspires.ftc.teamcode.common.command.intothedeep.ForearmNormalRaiseCmd;
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

    private ForearmNormalRaiseCmd forearmNormalRaiseCmd;
    private ForearmNormalLowerCmd forearmNormalLowerCmd;

    private ForearmHangingRaiseCmd forearmHangingRaiseCmd;
    private ForearmHangingLowerCmd forearmHangingLowerCmd;

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

        forearmNormalRaiseCmd = new ForearmNormalRaiseCmd(forearmSubsystem);
        forearmNormalLowerCmd = new ForearmNormalLowerCmd(forearmSubsystem);
        forearmHangingRaiseCmd = new ForearmHangingRaiseCmd(forearmSubsystem);
        forearmHangingLowerCmd = new ForearmHangingLowerCmd(forearmSubsystem);
        forearmStopCmd = new ForearmStopCmd(forearmSubsystem);

        // Set default commands
        driveSubsystem.setDefaultCommand(driveCmd);
        towerSubsystem.setDefaultCommand(towerNormalCmd);

        // Bind buttons
        gamepadEx2.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                .whenPressed(forearmNormalLowerCmd)
                .whenReleased(forearmStopCmd);
        gamepadEx2.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                .whenPressed(forearmNormalRaiseCmd)
                .whenReleased(forearmStopCmd);

        gamepadEx2.getGamepadButton(GamepadKeys.Button.X).toggleWhenPressed(
                this::enableHanging,
                this::disableHanging
        );
    }

    private void enableHanging() {
        towerSubsystem.setDefaultCommand(towerHangingCmd);

        gamepadEx2.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                .whenPressed(forearmHangingLowerCmd)
                .whenReleased(forearmStopCmd);

        gamepadEx2.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                .whenPressed(forearmHangingRaiseCmd)
                .whenReleased(forearmStopCmd);
    }

    private void disableHanging() {
        towerSubsystem.setDefaultCommand(towerNormalCmd);

        gamepadEx2.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                .whenPressed(forearmNormalLowerCmd)
                .whenReleased(forearmStopCmd);

        gamepadEx2.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                .whenPressed(forearmNormalRaiseCmd)
                .whenReleased(forearmStopCmd);
    }
}
