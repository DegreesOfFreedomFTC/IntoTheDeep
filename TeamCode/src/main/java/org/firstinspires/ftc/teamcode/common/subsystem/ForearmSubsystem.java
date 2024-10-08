package org.firstinspires.ftc.teamcode.common.subsystem;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.common.util.TelemetryLine;

import java.util.LinkedList;

@SuppressWarnings("unused")
public class ForearmSubsystem extends SubsystemBase {

    private final DcMotor leftMotor;
    private final DcMotor rightMotor;

    public ForearmSubsystem(final HardwareMap hardwareMap) {
        this.leftMotor = hardwareMap.get(DcMotor.class, "faLeftMotor");
        this.rightMotor = hardwareMap.get(DcMotor.class, "faRightMotor");

        this.leftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.rightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        this.leftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.rightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        this.leftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        this.rightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void power(double power) {
        this.leftMotor.setPower(power);
        this.rightMotor.setPower(power);
    }

    public void resetPosition() {
        this.leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.leftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        this.rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.rightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public LinkedList<TelemetryLine> getTelemetry() {
        LinkedList<TelemetryLine> lines = new LinkedList<>();

        lines.add(new TelemetryLine("Forearm Pos", Double.toString(this.leftMotor.getCurrentPosition())));

        return lines;
    }
}
