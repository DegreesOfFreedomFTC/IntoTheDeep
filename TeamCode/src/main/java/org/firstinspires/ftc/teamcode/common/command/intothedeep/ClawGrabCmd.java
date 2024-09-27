package org.firstinspires.ftc.teamcode.common.command.intothedeep;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.common.subsystem.ClawSubsystem;

@SuppressWarnings("unused")
public class ClawGrabCmd extends CommandBase {

    private final ClawSubsystem clawSubsystem;

    public ClawGrabCmd(ClawSubsystem clawSubsystem) {
        this.clawSubsystem = clawSubsystem;
    }

    @Override
    public void execute() {
        this.clawSubsystem.grab();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
