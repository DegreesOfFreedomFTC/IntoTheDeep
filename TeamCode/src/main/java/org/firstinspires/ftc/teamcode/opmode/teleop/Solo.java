package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.Subsystem;
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
    private TowerControlCmd towerCmd;
    private ForearmRaiseCmd forearmRaiseCmd;
    private ForearmLowerCmd forearmLowerCmd;
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

        towerCmd = new TowerControlCmd(
                towerSubsystem,
                () -> gamepadEx.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER)
                        - gamepadEx.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER));

        forearmRaiseCmd = new ForearmRaiseCmd(forearmSubsystem);
        forearmLowerCmd = new ForearmLowerCmd(forearmSubsystem);
        forearmStopCmd = new ForearmStopCmd(forearmSubsystem);

        // Set default commands
        driveSubsystem.setDefaultCommand(driveCmd);
        towerSubsystem.setDefaultCommand(towerCmd);

        // Bind buttons
        gamepadEx.getGamepadButton(GamepadKeys.Button.START)
                .whenPressed(() -> {
                    towerSubsystem.resetPosition();
                    forearmSubsystem.resetPosition();
                });

        gamepadEx.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                .whenPressed(forearmLowerCmd)
                .whenReleased(forearmStopCmd);
        gamepadEx.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                .whenPressed(forearmRaiseCmd)
                .whenReleased(forearmStopCmd);
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
}
