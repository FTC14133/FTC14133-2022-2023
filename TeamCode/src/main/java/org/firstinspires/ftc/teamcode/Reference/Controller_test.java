package org.firstinspires.ftc.teamcode;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp(name="Controller_Test", group="Iterative Opmode")

public class Controller_test extends OpMode  {

    private DcMotor test_motor = null;

    public void init() {
        //test_motor = hardwareMap.get(DcMotorEx.class, "turn_table");

    }

    public void init_loop() {

    }

    public void start() {

    }

    public void loop() {
        //Log.i("Hi", "HI");
        telemetry.addData("right_stick_y", String.valueOf(gamepad2.right_stick_y));
        telemetry.addData("right_stick_y", String.valueOf(gamepad2.right_stick_x));
        telemetry.addData("right_stick_y", String.valueOf(gamepad2.left_stick_y));
        telemetry.update();

        //test_motor.setPower(1);

    }

}

