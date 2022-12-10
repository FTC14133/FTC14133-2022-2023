package org.firstinspires.ftc.teamcode.Reference;

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

@TeleOp(name="ArmPositionTest", group="Iterative Opmode")

public class ArmPositionTest extends OpMode  {

    final double ArmCountsPerRev = 28; // Counts per rev of the motor
    final double ArmGearRatio = (32.0/10.0) * (76.0/21.0) * (68.0/13.0); //Ratio of the entire Pivot Arm from the motor to the arm
    final double ArmCountsPerDegree = ArmCountsPerRev * ArmGearRatio /360;


    boolean toggleLift = true;
    private DcMotor arm = null;
    public int pos = 0;

    public void init() {
        arm = hardwareMap.get(DcMotor.class, "Arm");

        arm.setDirection(DcMotorSimple.Direction.REVERSE);
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


    }

    public void init_loop() {

    }

    public void start() {

    }

    public void loop() {



        telemetry.addData("Arm Target Position", arm.getTargetPosition());
        telemetry.update();

    }

}

