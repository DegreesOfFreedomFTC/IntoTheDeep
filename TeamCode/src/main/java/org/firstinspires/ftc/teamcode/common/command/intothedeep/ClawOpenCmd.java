package org.firstinspires.ftc.teamcode.common.command.intothedeep;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.common.subsystem.ClawSubsystem;

@SuppressWarnings("unused")
public class ClawOpenCmd extends CommandBase {

    private final ClawSubsystem clawSubsystem;

    public ClawOpenCmd(ClawSubsystem clawSubsystem) {
        this.clawSubsystem = clawSubsystem;
    }

    @Override
    public void execute() {
        this.clawSubsystem.open();
    }

    @Override
    public boolean isFinished() {
        return this.clawSubsystem.getState() == ClawSubsystem.State.OPEN;
    }
}
