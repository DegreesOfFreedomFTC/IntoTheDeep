package org.firstinspires.ftc.teamcode.common.command.teleop;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.geometry.Vector2d;

import org.firstinspires.ftc.teamcode.common.subsystem.IMUSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystem.MecanumDriveSubsystem;

import java.util.function.DoubleSupplier;

public class FieldCentricMecanumDriveCmd extends CommandBase {

    private final MecanumDriveSubsystem driveSubsystem;
    private final IMUSubsystem imuSubsystem;

    private final DoubleSupplier driveSpeed;
    private final DoubleSupplier strafeSpeed;
    private final DoubleSupplier turnSpeed;

    public FieldCentricMecanumDriveCmd(MecanumDriveSubsystem driveSubsystem,
                                       IMUSubsystem imuSubsystem,
                                       DoubleSupplier driveSpeed,
                                       DoubleSupplier strafeSpeed,
                                       DoubleSupplier turnSpeed) {
        this.driveSubsystem = driveSubsystem;
        this.imuSubsystem = imuSubsystem;

        this.driveSpeed = driveSpeed;
        this.strafeSpeed = strafeSpeed;
        this.turnSpeed = turnSpeed;

        addRequirements(driveSubsystem, imuSubsystem);
    }

    @Override
    public void execute() {
        double heading = imuSubsystem.getYaw();

        double rotatedStrafe = strafeSpeed.getAsDouble() * Math.cos(-heading) - driveSpeed.getAsDouble() * Math.sin(-heading);
        double rotatedDrive = strafeSpeed.getAsDouble() * Math.sin(-heading) + driveSpeed.getAsDouble() * Math.cos(-heading);

        driveSubsystem.drive(
                rotatedDrive,
                rotatedStrafe,
                turnSpeed.getAsDouble()
        );
    }
}
