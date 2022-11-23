
package org.firstinspires.ftc.teamcode.Subsystems;

// Mecanum Drivetrain

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;


import java.lang.Math;

public class Drivetrain  {
    // Instantiate the drivetrain motor variables
    private DcMotorEx lf; //Back left motor of drivetrain
    private DcMotorEx rf; //Back right motor of drivetrain
    private DcMotorEx lb; //Front left motor of drivetrain
    private DcMotorEx rb; //Front right motor of drivetrain
    int tolerance = 4; // Encoder tolerance
    final double countsperrev = 28; // Counts per rev of the motor
    final double wheelD =96/25.4; // Diameter of the wheel (in inches)
    final double gearratio=(76/21)*(68/13); //Ratio of the entire drivetrain from the motor to the wheel
    final double countsperin=countsperrev*gearratio*(1/(Math.PI*wheelD));
    final double wheelBaseR = 15.5/2; //Wheel base radius in inches
    final double rotationK = 1; //Scaling factor for rotation (Teleop) Todo: Determine a good scaling factor for this. Should also calculate for real based on wheel diameter and location on robot.
    final double maxSpeed = 6000 * countsperrev * (1/60); //Counts per S Todo: Determine the real max speed, likely through test
    final double inchesperdegrotation = 2 * Math.PI * wheelBaseR * (1/360);

    public Drivetrain(HardwareMap hardwareMap){                 // Motor Mapping
        lf = hardwareMap.get(DcMotorEx.class, "lf");      //Sets the names of the hardware on the hardware map
        rf = hardwareMap.get(DcMotorEx.class, "rf");      // "DeviceName" must match the Config EXACTLY
        lb = hardwareMap.get(DcMotorEx.class, "lb");
        rb = hardwareMap.get(DcMotorEx.class, "rb");

        // Set motor direction based on which side of the robot the motors are on
        lb.setDirection(DcMotorEx.Direction.REVERSE);
        rb.setDirection(DcMotorEx.Direction.FORWARD);
        lf.setDirection(DcMotorEx.Direction.REVERSE);
        rf.setDirection(DcMotorEx.Direction.FORWARD);
    }



    public void DrivetrainAutoMove(double distance, double speed, double direction, double rotation) {
        /*
         * Commands the robot to move a certain direction for a certain distance
         * Distance in inches, Speed in in/s, Direction in degrees (Front of robot is 0 deg, CCW is positive), Rotation in degrees (CCW is pos)
         */
        lf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lf.setTargetPositionTolerance(tolerance);
        rf.setTargetPositionTolerance(tolerance);
        rb.setTargetPositionTolerance(tolerance);
        lb.setTargetPositionTolerance(tolerance);
        double angleR = Math.toRadians(direction); //Calculating angle of which the joystick is commanded to in radiant

        double lfspeed = (Math.sin(angleR + (3 * Math.PI / 4)) * speed) - (rotation * rotationK);     //Speed for leftfront
        double rfspeed = (Math.sin(angleR + (5 * Math.PI / 4)) * speed) + (rotation * rotationK);     //Speed for rightfront
        double rbspeed = (Math.sin(angleR + (7 * Math.PI / 4)) * speed) + (rotation * rotationK);      //Speed for rightback
        double lbspeed = (Math.sin(angleR + (9 * Math.PI / 4)) * speed) - (rotation * rotationK);    //Speed for leftback

        double maxNormalize = Math.max(Math.max(Math.abs(lfspeed), Math.abs(rfspeed)), Math.max(Math.abs(rbspeed), Math.abs(lbspeed))); //Finds the greatest power of the motors

        if ((Math.abs(lfspeed) > maxSpeed) || (Math.abs(rfspeed) > maxSpeed) || (Math.abs(rbspeed) > maxSpeed) || (Math.abs(lbspeed) > maxSpeed)){ //Normalize so no motor speed can be set above 1
            lfspeed = (lfspeed/maxNormalize) * maxSpeed;
            rfspeed = (rfspeed/maxNormalize) * maxSpeed;
            rbspeed = (rbspeed/maxNormalize) * maxSpeed;
            lbspeed = (lbspeed/maxNormalize) * maxSpeed;
        }

        double lfD = ((Math.sin(angleR + (3 * Math.PI / 4)) * direction) * distance) - (rotation * inchesperdegrotation);     //direction for leftfront
        double rfD = ((Math.sin(angleR + (5 * Math.PI / 4)) * direction) * distance) + (rotation * inchesperdegrotation);     //direction for rightfront
        double rbD = ((Math.sin(angleR + (7 * Math.PI / 4)) * direction) * distance) + (rotation * inchesperdegrotation);      //direction for rightback
        double lbD = ((Math.sin(angleR + (9 * Math.PI / 4)) * direction) * distance) - (rotation * inchesperdegrotation);    //direction for leftback

        int lfencodercounts = (int)(lfD * countsperin);
        int rfencodercounts = (int)(rfD * countsperin);
        int rbencodercounts = (int)(rbD * countsperin);
        int lbencodercounts = (int)(lbD * countsperin);

        lf.setVelocity(lfspeed);
        rf.setVelocity(rfspeed);
        lb.setVelocity(lbspeed);
        rb.setVelocity(rbspeed);
        lb.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rb.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        while ((Math.abs(lf.getCurrentPosition()) < (Math.abs(lfencodercounts))) || (Math.abs(rf.getCurrentPosition()) < (Math.abs(rfencodercounts))) || (Math.abs(lb.getCurrentPosition()) < (Math.abs(lbencodercounts))) || (Math.abs(rb.getCurrentPosition()) < (Math.abs(rbencodercounts)))) {
            //run until motors arrive at position within tolerance
        }
    }
    public void DrivetrainAutoMove(double distance, double speed, double direction){
        DrivetrainAutoMove(distance, speed, direction, 0);
    }

    public void DrivetrainAutoMove(double speed, double rotation){
        DrivetrainAutoMove(0, speed, 0, rotation);
    }

    public void Teleop(Gamepad gamepad1, Telemetry telemetry){ //Code to be run in Teleop Mode void Loop at top level
        double leftPowerY = -gamepad1.left_stick_y;      //find the value of y axis on the left joystick;
        double leftPowerX = gamepad1.left_stick_x;      //find the value of x axis on the left joystick;
        double rightPowerX = gamepad1.right_stick_x;     //find the value of x axis on the right joystick;

        double angleD = Math.toDegrees(Math.atan2(leftPowerY, leftPowerX)); //Calculating angle of which the joystick is commanded to in degrees
        double angleR = Math.atan2(leftPowerY, leftPowerX); //Calculating angle of which the joystick is commanded to in radiant
        double speed = Math.sqrt((leftPowerY * leftPowerY) + (leftPowerX * leftPowerX)); //Calculating the magnitude of the joystick


        telemetry.addData("Angle: ", angleD);
        telemetry.addData("Speed: ", speed);

        double lfpower = (Math.sin(angleR + (3 * Math.PI / 4)) * speed) - (rightPowerX * rotationK);     //Power level for leftfront
        double rfpower = (Math.sin(angleR + (5 * Math.PI / 4)) * speed) + (rightPowerX * rotationK);     //Power level for rightfront
        double rbpower = (Math.sin(angleR + (7 * Math.PI / 4)) * speed) + (rightPowerX * rotationK);      //Power level for rightback
        double lbpower = (Math.sin(angleR + (9 * Math.PI / 4)) * speed) - (rightPowerX * rotationK);    //Power level for leftback

        double max = Math.max(Math.max(Math.abs(lfpower), Math.abs(rfpower)), Math.max(Math.abs(rbpower), Math.abs(lbpower))); //Finds the greatest power of the moters

        if ((Math.abs(lfpower) > 1) || (Math.abs(rfpower) > 1) || (Math.abs(rbpower) > 1) || (Math.abs(lbpower) > 1)){ //Normalize so no motor speed can be set above 1
            lfpower /= max;
            rfpower /= max;
            rbpower /= max;
            lbpower /= max;
        }

        lb.setPower(lbpower);
        rb.setPower(rbpower);
        lf.setPower(lfpower);
        rf.setPower(rfpower);

        telemetry.addData("LF Power", lfpower);
        telemetry.addData("LB Power", lbpower);
        telemetry.addData("RF Power", rfpower);
        telemetry.addData("RB Power", rbpower);

    }

    public double getWheelD() {
        return wheelD;
    }

    public void AutoStop(){
        lf.setVelocity(0);
        rf.setVelocity(0);
        rb.setVelocity(0);
        lb.setVelocity(0);
    }


}