package org.firstinspires.ftc.teamcode.common.command.teleop;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.common.subsystem.MecanumDriveSubsystem;

import java.util.function.DoubleSupplier;

@SuppressWarnings("unused")
public class RobotCentricMecanumDriveCmd extends CommandBase {

    private final MecanumDriveSubsystem driveSubsystem;

    private final DoubleSupplier driveSpeed;
    private final DoubleSupplier strafeSpeed;
    private final DoubleSupplier turnSpeed;

    public RobotCentricMecanumDriveCmd(MecanumDriveSubsystem driveSubsystem,
                                       DoubleSupplier driveSpeed,
                                       DoubleSupplier strafeSpeed,
                                       DoubleSupplier turnSpeed) {
        this.driveSubsystem = driveSubsystem;

        this.driveSpeed = driveSpeed;
        this.strafeSpeed = strafeSpeed;
        this.turnSpeed = turnSpeed;

        addRequirements(driveSubsystem);
    }

    @Override
    public void execute() {
        driveSubsystem.drive(
                driveSpeed.getAsDouble(),
                strafeSpeed.getAsDouble(),
                turnSpeed.getAsDouble()
        );
    }
}
