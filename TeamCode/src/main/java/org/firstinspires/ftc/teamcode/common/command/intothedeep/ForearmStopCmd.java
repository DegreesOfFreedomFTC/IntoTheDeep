package org.firstinspires.ftc.teamcode.common.command.intothedeep;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.common.subsystem.ForearmSubsystem;

public class ForearmStopCmd extends CommandBase {

    private final ForearmSubsystem forearmSubsystem;

    public ForearmStopCmd(ForearmSubsystem forearmSubsystem) {
        this.forearmSubsystem = forearmSubsystem;
        addRequirements(forearmSubsystem);
    }

    @Override
    public void execute() {
        forearmSubsystem.power(0.0);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
