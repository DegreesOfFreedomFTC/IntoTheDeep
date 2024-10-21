package org.firstinspires.ftc.teamcode.common.command.intothedeep;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.common.subsystem.ForearmSubsystem;

@SuppressWarnings("unused")
public class ForearmHangingLowerCmd extends CommandBase {

    private final ForearmSubsystem forearmSubsystem;

    public ForearmHangingLowerCmd(ForearmSubsystem forearmSubsystem) {
        this.forearmSubsystem = forearmSubsystem;
        addRequirements(forearmSubsystem);
    }

    @Override
    public void execute() {
        this.forearmSubsystem.power(-1);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
