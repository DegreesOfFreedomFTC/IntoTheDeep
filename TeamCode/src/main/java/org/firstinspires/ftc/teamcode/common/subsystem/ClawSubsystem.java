package org.firstinspires.ftc.teamcode.common.subsystem;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.common.hardware.BetterServo;
import org.firstinspires.ftc.teamcode.common.util.TelemetryLine;

@SuppressWarnings("unused")
public class ClawSubsystem extends SubsystemBase {

    private final BetterServo servo;

    public ClawSubsystem(final HardwareMap hardwareMap) {
        this.servo = new BetterServo(hardwareMap, "clServo");
    }

    public void open() {
        this.servo.setPosition(0.0);
    }

    public void grab() {
        this.servo.setPosition(1.0);
    }

    public TelemetryLine[] getTelemetry() {
        return new TelemetryLine[]{new TelemetryLine("Claw Servo Position", Double.toString(this.servo.getPosition()))};
    }
}