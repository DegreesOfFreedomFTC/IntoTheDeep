package org.firstinspires.ftc.teamcode.common.subsystem;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.common.hardware.BetterServo;

@SuppressWarnings("unused")
public class ClawSubsystem extends SubsystemBase {

    private final BetterServo servo;

    private final double OPEN_ANGLE = 0;
    private final double GRABBED_ANGLE = 90;

    private State state = State.MOVING;

    public ClawSubsystem(final HardwareMap hardwareMap) {
        this.servo = new BetterServo(hardwareMap, "clServo");
    }

    public void open() {
        this.servo.setAngle(OPEN_ANGLE);
    }

    public void grab() {
        this.servo.setAngle(GRABBED_ANGLE);
    }

    public State getState() {
        return this.state;
    }

    @Override
    public void periodic() {
        if (Math.round(this.servo.getAngle()) == GRABBED_ANGLE)  {
            this.state = State.GRABBED;
        } else if (Math.round(this.servo.getAngle()) == OPEN_ANGLE) {
            this.state = State.OPEN;
        } else {
            this.state = State.MOVING;
        }
    }

    public enum State {
        OPEN,
        GRABBED,
        MOVING,
    }
}
