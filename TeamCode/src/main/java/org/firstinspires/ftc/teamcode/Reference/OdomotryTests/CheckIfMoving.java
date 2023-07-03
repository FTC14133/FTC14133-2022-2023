package org.firstinspires.ftc.teamcode.Reference.OdomotryTests;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp(name="ArmPositionTest", group="Iterative Opmode")
//@Disabled
public class CheckIfMoving extends OpMode  {
    private static DcMotorEx leftMotor;
    private static DcMotorEx rightMotor;

    private int previousEncoderValueLeftMotor = 0;
    private int previousEncoderValueRightMotor = 0;

    public void init() {
        leftMotor = hardwareMap.get(DcMotorEx.class, "lf");
        rightMotor = hardwareMap.get(DcMotorEx.class, "rf");

        leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void loop() {
        int currentEncoderValueLeftMotor = leftMotor.getCurrentPosition();
        int currentEncoderValueRightMotor = rightMotor.getCurrentPosition();

        boolean leftMotorMovingForward = currentEncoderValueLeftMotor > previousEncoderValueLeftMotor;
        boolean rightMotorMovingForward = currentEncoderValueRightMotor > previousEncoderValueRightMotor;

        telemetry.addData("Moving Forwards/Backwards:", (leftMotorMovingForward && rightMotorMovingForward));
        telemetry.addData("Robot Moved:", previousEncoderValueLeftMotor-currentEncoderValueLeftMotor);

        telemetry.update();
    }
}
