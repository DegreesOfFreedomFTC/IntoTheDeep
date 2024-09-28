package org.firstinspires.ftc.teamcode.common.subsystem;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.common.util.TelemetryLine;

import java.util.LinkedList;

@SuppressWarnings("unused")
public class TowerSubsystem extends SubsystemBase {

    private final DcMotor motor;

    public TowerSubsystem(final HardwareMap hardwareMap) {
        this.motor = hardwareMap.get(DcMotor.class, "twMotor");
        this.motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void power(double power) {
        this.motor.setPower(power);
    }

    public void resetPosition() {
        this.motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public LinkedList<TelemetryLine> getTelemetry() {
        LinkedList<TelemetryLine> lines = new LinkedList<>();

        lines.add(new TelemetryLine("Tower Pos", Double.toString(this.motor.getCurrentPosition())));

        return lines;
    }
}
