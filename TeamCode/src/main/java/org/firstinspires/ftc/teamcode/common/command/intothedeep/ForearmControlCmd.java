package org.firstinspires.ftc.teamcode.common.command.intothedeep;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.common.subsystem.ForearmSubsystem;

import java.util.function.DoubleSupplier;

@SuppressWarnings("unused")
public class ForearmControlCmd extends CommandBase {

    private final ForearmSubsystem forearmSubsystem;

    private final DoubleSupplier power;


    public ForearmControlCmd(ForearmSubsystem forearmSubsystem, DoubleSupplier power) {
        this.forearmSubsystem = forearmSubsystem;

        this.power = power;

        addRequirements(this.forearmSubsystem);
    }

    @Override
    public void execute() {
        forearmSubsystem.power(power.getAsDouble());
    }
}
