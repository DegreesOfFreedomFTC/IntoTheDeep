package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.common.command.intothedeep.ClawGrabCmd;
import org.firstinspires.ftc.teamcode.common.command.intothedeep.ClawOpenCmd;
import org.firstinspires.ftc.teamcode.common.command.teleop.FieldCentricMecanumDriveCmd;
import org.firstinspires.ftc.teamcode.common.command.teleop.RobotCentricMecanumDriveCmd;
import org.firstinspires.ftc.teamcode.common.subsystem.ClawSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystem.IMUSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystem.MecanumDriveSubsystem;
import org.firstinspires.ftc.teamcode.common.util.TelemetryLine;

@SuppressWarnings({"FieldCanBeLocal", "unused"})
@TeleOp
public class Solo extends CommandOpMode {

    /**
     * The {@link GamepadEx} that will be used to get inputs. Is is provided by FTCLib and has
     * features to make using the command based paradigm easier.
     */
    private GamepadEx gamepadEx;

    /**
     * The {@link com.arcrobotics.ftclib.command.Subsystem}s that will be used in the TeleOp for the
     * command based paradigm.
     */
    private MecanumDriveSubsystem driveSubsystem;
    private IMUSubsystem imuSubsystem;
    private ClawSubsystem clawSubsystem;

    /**
     * The {@link com.arcrobotics.ftclib.command.Command}s that will be used in the TeleOp for the
     * command based paradigm.
     */
    private RobotCentricMecanumDriveCmd robotCentricDriveCmd;
    private FieldCentricMecanumDriveCmd fieldCentricDriveCmd;
    private ClawOpenCmd clawOpenCmd;
    private ClawGrabCmd clawGrabCmd;

    @Override
    public void initialize() {
        // Instantiate the GamepadEx
        gamepadEx = new GamepadEx(gamepad1);

        // Instantiate the Subsystems
        driveSubsystem = new MecanumDriveSubsystem(hardwareMap);
        imuSubsystem = new IMUSubsystem(hardwareMap);
        clawSubsystem = new ClawSubsystem(hardwareMap);

        // Instantiate the Commands
        robotCentricDriveCmd = new RobotCentricMecanumDriveCmd(
                driveSubsystem,
                gamepadEx::getLeftY,
                gamepadEx::getLeftX,
                gamepadEx::getRightX
        );

        fieldCentricDriveCmd = new FieldCentricMecanumDriveCmd(
                driveSubsystem,
                imuSubsystem,
                (() -> gamepadEx.getLeftY() / 2.0),
                (() -> gamepadEx.getLeftX() / 2.0),
                (() -> gamepadEx.getRightX() / 2.0)
        );

        clawOpenCmd = new ClawOpenCmd(clawSubsystem);
        clawGrabCmd = new ClawGrabCmd(clawSubsystem);

        // Set default commands
        driveSubsystem.setDefaultCommand(fieldCentricDriveCmd);

        // Bind gamepad buttons
        gamepadEx.getGamepadButton(GamepadKeys.Button.A).toggleWhenPressed(clawOpenCmd, clawGrabCmd);
    }

    @Override
    public void runOpMode() {
        initialize();

        waitForStart();

        while (!isStopRequested() && opModeIsActive()) {
            run();

            TelemetryLine[] clawTelemetry = clawSubsystem.getTelemetry();

            for (TelemetryLine line :
                    clawTelemetry) {
                telemetry.addData(line.caption, line.value);
            }
            telemetry.update();
        }

        reset();
    }
}
