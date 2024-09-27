package org.firstinspires.ftc.teamcode.common.subsystem;

import android.util.Pair;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.common.hardware.BetterServo;
import org.firstinspires.ftc.teamcode.common.util.TelemetryLine;

@SuppressWarnings("unused")
public class ClawSubsystem extends SubsystemBase {

    private final BetterServo servo;

    private final double OPEN_ANGLE = 0;
    private final double GRABBED_ANGLE = 90;

    public ClawSubsystem(final HardwareMap hardwareMap) {
        this.servo = new BetterServo(hardwareMap, "clServo");
    }

    public void open() {
        this.servo.setAngle(0);
    }

    public void grab() {
        this.servo.setAngle(90);
    }

    public TelemetryLine[] getTelemetry() {
        return new TelemetryLine[]{new TelemetryLine("Claw Servo Angle", Double.toString(this.servo.getAngle()))};
    }
}
