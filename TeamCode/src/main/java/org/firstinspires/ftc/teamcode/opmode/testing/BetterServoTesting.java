package org.firstinspires.ftc.teamcode.opmode.testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.common.hardware.BetterServo;

@SuppressWarnings("unused")
@TeleOp
public class BetterServoTesting extends LinearOpMode {

    private BetterServo servo;

    private final ElapsedTime timer = new ElapsedTime();

    @Override
    public void runOpMode() {
        servo = new BetterServo(hardwareMap, "servo");

        waitForStart();

        timer.reset();

        while (opModeIsActive()) {
            while (timer.seconds() <= 5.0 && opModeIsActive()) {
                servo.setAngle(90);
                showAngle();
            }
            timer.reset();
            while (timer.seconds() <= 5.0 && opModeIsActive()) {
                servo.setAngle(-90);
                showAngle();
            }
            timer.reset();
            while (timer.seconds() <= 5.0 && opModeIsActive()) {
                servo.setAngle(0);
                showAngle();
            }
            timer.reset();
        }
    }

    private void showAngle() {
        telemetry.addData("Servo angle", servo.getAngle());
        telemetry.update();
    }
}
