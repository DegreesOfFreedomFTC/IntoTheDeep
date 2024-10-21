package org.firstinspires.ftc.teamcode.common.command.intothedeep;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.common.subsystem.ForearmSubsystem;

@SuppressWarnings("unused")
public class ForearmNormalRaiseCmd extends CommandBase {

    private final ForearmSubsystem forearmSubsystem;

    public ForearmNormalRaiseCmd(ForearmSubsystem forearmSubsystem) {
        this.forearmSubsystem = forearmSubsystem;
        addRequirements(forearmSubsystem);
    }

    @Override
    public void execute() {
        this.forearmSubsystem.power(0.5);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
