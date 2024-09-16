package org.firstinspires.ftc.teamcode.common.hardware;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;

/**
 * A class that makes servo motor usage better. This class adds setAngle() and getAngle() methods
 * that make improve the QOL in using servos. It also practically removes the need for the SRS
 * programmer, as all angles can be programmed in software.
 */
@SuppressWarnings("unused")
public class BetterServo implements Servo {

    private final Servo servo;

    private final double ZERO_DEGREES_POS = 0.5;
    private final double FULL_RANGE = 270.0;
    private final double COUNTS_PER_DEGREE = 1.0 / FULL_RANGE;

    public BetterServo(HardwareMap hardwareMap, String name) {
        this.servo = hardwareMap.get(Servo.class, name);
    }

    public void setAngle(double angle) {
        double pos = ZERO_DEGREES_POS + angle * COUNTS_PER_DEGREE;

        this.setPosition(pos);
    }

    public double getAngle() {
        double pos = this.getPosition();

        return (pos - ZERO_DEGREES_POS) / COUNTS_PER_DEGREE;
    }

    @Override
    public ServoController getController() {
        return this.servo.getController();
    }

    @Override
    public int getPortNumber() {
        return this.servo.getPortNumber();
    }

    @Override
    public void setDirection(Direction direction) {
        this.servo.setDirection(direction);
    }

    @Override
    public Direction getDirection() {
        return this.servo.getDirection();
    }

    @Override
    public void setPosition(double position) {
        this.servo.setPosition(position);
    }

    @Override
    public double getPosition() {
        return this.servo.getPosition();
    }

    @Override
    public void scaleRange(double min, double max) {
        this.servo.scaleRange(min, max);
    }

    @Override
    public Manufacturer getManufacturer() {
        return this.servo.getManufacturer();
    }

    @Override
    public String getDeviceName() {
        return this.servo.getDeviceName();
    }

    @Override
    public String getConnectionInfo() {
        return this.servo.getConnectionInfo();
    }

    @Override
    public int getVersion() {
        return this.servo.getVersion();
    }

    @Override
    public void resetDeviceConfigurationForOpMode() {
        this.servo.resetDeviceConfigurationForOpMode();
    }

    @Override
    public void close() {
        this.servo.close();
    }
}
