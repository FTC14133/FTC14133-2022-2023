
package org.firstinspires.ftc.teamcode.Subsystems;

// Mecanum Drivetrain

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
//import org.opencv.core.Mat;
import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.lang.Math;

public class Drivetrain  {
    // Instantiate the drivetrain motor variables
    private DcMotorEx fm; //Back left motor of drivetrain
    private DcMotorEx bm; //Back right motor of drivetrain
    private DcMotorEx lm; //Front left motor of drivetrain
    private DcMotorEx rm; //Front right motor of drivetrain
    int tolerance = 4; // Encoder tolerance
    final double countsperrev = 28; // Counts per rev of the motor
    final double wheelD =96/25.4; // Diameter of the wheel (in inches)
    final double gearratio=2*2.89*2.89; //Ratio of the entire drivetrain from the motor to the wheel
    final double countsperin=countsperrev*gearratio*(1/(Math.PI*wheelD));


    public Drivetrain(HardwareMap hardwareMap){                 // Motor Mapping
        lm = hardwareMap.get(DcMotorEx.class, "lm");      //Sets the names of the hardware on the hardware map
        rm = hardwareMap.get(DcMotorEx.class, "rm");      // "DeviceName" must match the Config EXACTLY
        fm = hardwareMap.get(DcMotorEx.class, "fm");
        bm = hardwareMap.get(DcMotorEx.class, "bm");

        // Set motor direction based on which side of the robot the motors are on
        lm.setDirection(DcMotorEx.Direction.FORWARD);
        rm.setDirection(DcMotorEx.Direction.FORWARD);
        fm.setDirection(DcMotorEx.Direction.FORWARD);
        bm.setDirection(DcMotorEx.Direction.FORWARD);
    }

    public void ForwardorBackwards(double distance, double speed) {
        lm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lm.setTargetPositionTolerance(tolerance);
        rm.setTargetPositionTolerance(tolerance);
        //Driving forward/backwards
        double encodercounts = distance * countsperin;
        int encodercountsint = (int) encodercounts;
        lm.setTargetPosition(encodercountsint);
        lm.setPower(speed);        //Sets the power for the left front wheel
        rm.setTargetPosition(encodercountsint);
        rm.setPower(speed);        //Sets the power for the right front wheel
        rm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while (lm.isBusy() || rm.isBusy()) {
            //run until motors arrive at position within tolerance
        }
    }
    //h
    public void Rotate(double turn, double speed) {
        lm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lm.setTargetPositionTolerance(tolerance);
        rm.setTargetPositionTolerance(tolerance);
        bm.setTargetPositionTolerance(tolerance);
        fm.setTargetPositionTolerance(tolerance);
        //Driving left/right
        //NOT DONE
        double encodercounts = turn * 7.123; // test iteratively //ToDo: This math needs to be redone as well
        int encodercountsint = (int) encodercounts;
        lm.setTargetPosition(encodercountsint);
        lm.setPower(speed);        //
        rm.setTargetPosition(encodercountsint);
        rm.setPower(speed);        //Sets the power for the Long arm
        fm.setTargetPosition(encodercountsint);
        fm.setPower(speed);        //Sets the power for the Long arm
        bm.setTargetPosition(encodercountsint);
        bm.setPower(speed);        //Sets the power for the Long arm
        fm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //noinspection StatementWithEmptyBody
        while (lm.isBusy() || rm.isBusy()) {
            //run until motors arrive at position within tolerance
        }
    }

    public void Strafing(double Strafe, double speed) {
        fm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bm.setTargetPositionTolerance(tolerance);
        fm.setTargetPositionTolerance(tolerance);
        //Driving left/right
        //Positive is Strafing left negative is Strafing right
        double encodercounts = Strafe * countsperin * Math.sqrt(2);
        int encodercountsint = (int) encodercounts;
        fm.setTargetPosition(encodercountsint);
        fm.setPower(speed);        //Sets the power for the Long arm
        bm.setTargetPosition(-encodercountsint);
        bm.setPower(speed);        //Sets the power for the Long arm
        fm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        while (lm.isBusy() || rm.isBusy()) {
            //run until motors arrive at position within tolerance
        }


    }

    public void Teleop(Gamepad gamepad1, Telemetry telemetry){ //Code to be run in Teleop Mode void Loop at top level
        double leftPowerY = -gamepad1.left_stick_y;      //find the value of y axis on the left joystick;
        double leftPowerX = gamepad1.left_stick_x;      //find the value of x axis on the left joystick;
        double rightPowerX = gamepad1.right_stick_x;     //find the value of x axis on the right joystick;

        if (Math.abs(rightPowerX) > 0.1){
            lm.setPower(rightPowerX);
            rm.setPower(rightPowerX);
            fm.setPower(rightPowerX);
            bm.setPower(rightPowerX);
        }else{
            lm.setPower(leftPowerX);
            rm.setPower(leftPowerX);
            fm.setPower(leftPowerY);
            bm.setPower(leftPowerY);
        }

        //telemetry.addData("LF Power", leftfrontpower);
        //telemetry.addData("LB Power", leftbackpower);
        //telemetry.addData("RF Power", rightfrontpower);
        //telemetry.addData("RB Power", rightbackpower);

    }

    public double getWheelD() {
        return wheelD;
    }


}