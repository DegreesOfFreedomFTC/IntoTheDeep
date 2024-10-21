package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.Subsystem;
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
import org.firstinspires.ftc.teamcode.common.util.TelemetryLine;

import java.util.LinkedList;

@SuppressWarnings({"FieldCanBeLocal", "unused"})
@TeleOp
public class Solo extends CommandOpMode {

    /**
     * The {@link GamepadEx} that will be used to get inputs. Is is provided by FTCLib and has
     * features to make using the command based paradigm easier.
     */
    private GamepadEx gamepadEx;

    /**
     * The {@link Subsystem}s that will be used in the TeleOp for the
     * command based paradigm.
     */
    private MecanumDriveSubsystem driveSubsystem;
    private TowerSubsystem towerSubsystem;
    private ForearmSubsystem forearmSubsystem;

    /**
     * The {@link Command}s that will be used in the TeleOp for the
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
        // Make telemetry go to dashboard
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        // Instantiate the GamepadEx
        gamepadEx = new GamepadEx(gamepad1);

        // Instantiate the Subsystems
        driveSubsystem = new MecanumDriveSubsystem(hardwareMap);
        towerSubsystem = new TowerSubsystem(hardwareMap);
        forearmSubsystem = new ForearmSubsystem(hardwareMap);

        // Instantiate the Commands
        driveCmd = new RobotCentricMecanumDriveCmd(
                driveSubsystem,
                gamepadEx::getLeftY,
                gamepadEx::getLeftX,
                gamepadEx::getRightX
        );

        towerNormalCmd = new TowerControlCmd(
                towerSubsystem,
                () -> (gamepadEx.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER)
                        - gamepadEx.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER)) / 2.0);

        towerHangingCmd = new TowerControlCmd(
                towerSubsystem,
                () -> gamepadEx.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER)
                        - gamepadEx.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER));

        forearmNormalRaiseCmd = new ForearmNormalRaiseCmd(forearmSubsystem);
        forearmNormalLowerCmd = new ForearmNormalLowerCmd(forearmSubsystem);
        forearmHangingRaiseCmd = new ForearmHangingRaiseCmd(forearmSubsystem);
        forearmHangingLowerCmd = new ForearmHangingLowerCmd(forearmSubsystem);
        forearmStopCmd = new ForearmStopCmd(forearmSubsystem);

        // Set default commands
        driveSubsystem.setDefaultCommand(driveCmd);
        towerSubsystem.setDefaultCommand(towerNormalCmd);

        // Bind buttons
        gamepadEx.getGamepadButton(GamepadKeys.Button.START)
                .whenPressed(() -> {
                    towerSubsystem.resetPosition();
                    forearmSubsystem.resetPosition();
                });

        gamepadEx.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                .whenPressed(forearmNormalLowerCmd)
                .whenReleased(forearmStopCmd);
        gamepadEx.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                .whenPressed(forearmNormalRaiseCmd)
                .whenReleased(forearmStopCmd);

        gamepadEx.getGamepadButton(GamepadKeys.Button.X).toggleWhenPressed(
                this::enableHanging,
                this::disableHanging
        );
    }

    @Override
    public void runOpMode() {
        initialize();

        waitForStart();

        while (!isStopRequested() && opModeIsActive()) {
            run();

            LinkedList<TelemetryLine> telemetryLines = new LinkedList<>();

            telemetryLines.addAll(towerSubsystem.getTelemetry());
            telemetryLines.addAll(forearmSubsystem.getTelemetry());

            for (TelemetryLine line : telemetryLines) {
                telemetry.addData(line.caption, line.value);
            }
            telemetry.update();
        }

        reset();
    }

    private void enableHanging() {
        towerSubsystem.setDefaultCommand(towerHangingCmd);

        gamepadEx.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                .whenPressed(forearmHangingLowerCmd)
                .whenReleased(forearmStopCmd);

        gamepadEx.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                .whenPressed(forearmHangingRaiseCmd)
                .whenReleased(forearmStopCmd);
    }

    private void disableHanging() {
        towerSubsystem.setDefaultCommand(towerNormalCmd);

        gamepadEx.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                .whenPressed(forearmNormalLowerCmd)
                .whenReleased(forearmStopCmd);

        gamepadEx.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                .whenPressed(forearmNormalRaiseCmd)
                .whenReleased(forearmStopCmd);
    }
}
