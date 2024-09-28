package org.firstinspires.ftc.teamcode.common.command.intothedeep;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.common.subsystem.ForearmSubsystem;

@SuppressWarnings("unused")
public class ForearmLowerCmd extends CommandBase {

    private final ForearmSubsystem forearmSubsystem;

    public ForearmLowerCmd(ForearmSubsystem forearmSubsystem) {
        this.forearmSubsystem = forearmSubsystem;
        addRequirements(forearmSubsystem);
    }

    @Override
    public void execute() {
        this.forearmSubsystem.power(-0.75);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
