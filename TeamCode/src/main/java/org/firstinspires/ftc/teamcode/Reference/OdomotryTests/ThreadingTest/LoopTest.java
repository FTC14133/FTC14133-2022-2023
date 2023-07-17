package org.firstinspires.ftc.teamcode.Reference.OdomotryTests.ThreadingTest;

import java.lang.Math;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;

import java.util.*;

import org.firstinspires.ftc.teamcode.Subsystems.Drivetrain;

public class LoopTest extends Thread{

    public Telemetry telemetry;
    public boolean loop = true;

    private static DcMotorEx lf;

    public LoopTest(HardwareMap hardwareMap, Telemetry privateTelemetry){

        telemetry = privateTelemetry;

        lf = hardwareMap.get(DcMotorEx.class, "lf");
        lf.setDirection(DcMotorEx.Direction.FORWARD);

    }

    public void StopThreading(){
        loop = false;
    }

    public void run(){

        while (loop) {

            lf.setPower(0.75);

            try {
                Thread.sleep(1000);
            } catch (Exception e){}

            lf.setPower(0.25);

            try {
                Thread.sleep(1000);
            } catch (Exception e){}


        }
    }
}
