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
import org.firstinspires.ftc.teamcode.common.command.teleop.FieldCentricMecanumDriveCmd;
import org.firstinspires.ftc.teamcode.common.command.teleop.RobotCentricMecanumDriveCmd;
import org.firstinspires.ftc.teamcode.common.subsystem.ForearmSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystem.IMUSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystem.MecanumDriveSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystem.TowerSubsystem;
import org.firstinspires.ftc.teamcode.common.util.TelemetryLine;

import java.util.ConcurrentModificationException;
import java.util.LinkedList;

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
    private TowerSubsystem towerSubsystem;
    private ForearmSubsystem forearmSubsystem;

    /**
     * The {@link com.arcrobotics.ftclib.command.Command}s that will be used in the TeleOp for the
     * command based paradigm.
     */
    private RobotCentricMecanumDriveCmd robotCentricDriveCmd;
    private FieldCentricMecanumDriveCmd fieldCentricDriveCmd;

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
        imuSubsystem = new IMUSubsystem(hardwareMap);
        towerSubsystem = new TowerSubsystem(hardwareMap);
        forearmSubsystem = new ForearmSubsystem(hardwareMap);

        // Instantiate the Commands
        robotCentricDriveCmd = new RobotCentricMecanumDriveCmd(
                driveSubsystem,
                gamepadEx1::getLeftY,
                gamepadEx1::getLeftX,
                gamepadEx1::getRightX
        );

        fieldCentricDriveCmd = new FieldCentricMecanumDriveCmd(
                driveSubsystem,
                imuSubsystem,
                (() -> gamepadEx1.getLeftX() / 2.0),
                (() -> gamepadEx1.getLeftY() / 2.0),
                (() -> gamepadEx1.getRightX() / 2.0)
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
        driveSubsystem.setDefaultCommand(fieldCentricDriveCmd);
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

    @Override
    public void runOpMode() {
        initialize();

        waitForStart();

        while (!isStopRequested() && opModeIsActive()) {
            try {
                run();
            } catch (ConcurrentModificationException e) {
                telemetry.addLine("Concurrent Modification at CommandScheduler.getInstance.run()");
            }

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
        try {
            towerNormalCmd.cancel();
        } catch (ConcurrentModificationException e) {
            telemetry.addLine("Concurrent Modification at towerNormalCmd.cancel()");
        }

        try {
            towerHangingCmd.schedule(true);
        } catch (ConcurrentModificationException e) {
            telemetry.addLine("Concurrent Modification at towerHangingCmd.schedule(true)");
        }

        try {
            gamepadEx2.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                    .whenPressed(forearmHangingLowerCmd, true)
                    .whenReleased(forearmStopCmd, true);

            gamepadEx2.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                    .whenPressed(forearmHangingRaiseCmd, true)
                    .whenReleased(forearmStopCmd, true);
        } catch (ConcurrentModificationException e) {
            telemetry.addLine("Concurrent Modification at forearm button reassignment");
        }

        telemetry.update();
    }

    private void disableHanging() {
        try {
            towerHangingCmd.cancel();
        } catch (ConcurrentModificationException e) {
            telemetry.addLine("Concurrent Modification at towerHangingCmd.cancel()");
        }

        try {
            towerNormalCmd.schedule(true);
        } catch (ConcurrentModificationException e) {
            telemetry.addLine("Concurrent Modification at towerNormalCmd.schedule(true)");
        }

        try {
            gamepadEx2.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                    .whenPressed(forearmNormalLowerCmd)
                    .whenReleased(forearmStopCmd);

            gamepadEx2.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                    .whenPressed(forearmNormalRaiseCmd)
                    .whenReleased(forearmStopCmd);
        } catch (ConcurrentModificationException e) {
            telemetry.addLine("Concurrent Modification at forearm button reassignment");
        }

        telemetry.update();
        driveSubsystem.setDefaultCommand(fieldCentricDriveCmd);
    }
}
