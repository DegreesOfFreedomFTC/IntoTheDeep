package org.firstinspires.ftc.teamcode.common.subsystem;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

@SuppressWarnings("unused")
public class MecanumDriveSubsystem extends SubsystemBase {

    private final DcMotor frontLeftMotor;
    private final DcMotor frontRightMotor;
    private final DcMotor backLeftMotor;
    private final DcMotor backRightMotor;

    public MecanumDriveSubsystem(final HardwareMap hardwareMap) {
        this.frontLeftMotor = hardwareMap.get(DcMotor.class, "dtFrontLeftMotor");
        this.frontRightMotor = hardwareMap.get(DcMotor.class, "dtFrontRightMotor");
        this.backLeftMotor = hardwareMap.get(DcMotor.class, "dtBackLeftMotor");
        this.backRightMotor = hardwareMap.get(DcMotor.class, "dtBackRightMotor");

        this.frontLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.frontRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.backLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.backRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        this.frontLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        this.frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        this.backLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        this.backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        this.frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void drive(double driveSpeed, double strafeSpeed, double turnSpeed) {
        // Ensure that speeds have the same ratio
        double denominator = Math.max(
                Math.abs(driveSpeed) + Math.abs(strafeSpeed) + Math.abs(turnSpeed),
                1.0
        );

        double frontLeftSpeed  = ((driveSpeed + strafeSpeed + turnSpeed) / denominator) / 1.5;
        double frontRightSpeed = ((driveSpeed - strafeSpeed - turnSpeed) / denominator) / 1.5;
        double backLeftSpeed   = ((driveSpeed - strafeSpeed + turnSpeed) / denominator) / 1.5;
        double backRightSpeed  = ((driveSpeed + strafeSpeed - turnSpeed) / denominator) / 1.5;

        frontLeftMotor.setPower(frontLeftSpeed);
        frontRightMotor.setPower(frontRightSpeed);
        backLeftMotor.setPower(backLeftSpeed);
        backRightMotor.setPower(backRightSpeed);
    }
}
