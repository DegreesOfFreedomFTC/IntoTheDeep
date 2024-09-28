package org.firstinspires.ftc.teamcode.common.command.intothedeep.tower;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.common.subsystem.TowerSubsystem;

import java.util.function.DoubleSupplier;

@SuppressWarnings("unused")
public class TowerControlCmd extends CommandBase {

    private final TowerSubsystem towerSubsystem;

    private final DoubleSupplier power;


    public TowerControlCmd(TowerSubsystem towerSubsystem, DoubleSupplier power) {
        this.towerSubsystem = towerSubsystem;

        this.power = power;

        addRequirements(towerSubsystem);
    }

    @Override
    public void execute() {
        towerSubsystem.power(power.getAsDouble());
    }
}
