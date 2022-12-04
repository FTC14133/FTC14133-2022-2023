
package org.firstinspires.ftc.teamcode.Subsystems;

// Mecanum Drivetrain

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorImplEx;
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
    final double gearratio=(76.0/21.0)*(68.0/13.0); //Ratio of the entire drivetrain from the motor to the wheel
    final double countsperin=countsperrev*gearratio*(1/(Math.PI*wheelD));
    final double wheelBaseR = 15.5/2; //Wheel base radius in inches
    final double rotationK = 0.5; //Scaling factor for rotation (Teleop) Todo: Determine a good scaling factor for this. Should also calculate for real based on wheel diameter and location on robot.
    final double maxSpeed = 6000 * countsperrev * (1.0/60.0); //Counts per S Todo: Determine the real max speed, likely through test
    final double inchesperdegrotation = 2 * Math.PI * wheelBaseR * (1.0/360.0);

    public Drivetrain(HardwareMap hardwareMap){                 // Motor Mapping
        lf = hardwareMap.get(DcMotorEx.class, "lf");      //Sets the names of the hardware on the hardware map
        rf = hardwareMap.get(DcMotorEx.class, "rf");      // "DeviceName" must match the Config EXACTLY
        lb = hardwareMap.get(DcMotorEx.class, "lb");
        rb = hardwareMap.get(DcMotorEx.class, "rb");

        // Set motor direction based on which side of the robot the motors are on
        lb.setDirection(DcMotorEx.Direction.FORWARD);
        rb.setDirection(DcMotorEx.Direction.FORWARD);
        lf.setDirection(DcMotorEx.Direction.FORWARD);
        rf.setDirection(DcMotorEx.Direction.FORWARD);
    }



    public void DrivetrainAutoMove(double distance, double speed, double direction, double rotation) {
        /*
         * Commands the robot to move a certain direction for a certain distance
         * Distance in inches, Speed in in/s, Direction in degrees (Front of robot is 0 deg, CCW is positive), Rotation in degrees (CCW is pos)
         */
        rf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rf.setTargetPositionTolerance(tolerance);
        lf.setTargetPositionTolerance(tolerance);
        lb.setTargetPositionTolerance(tolerance);
        rb.setTargetPositionTolerance(tolerance);
        double angleR = Math.toRadians(direction); //Calculating angle of which the joystick is commanded to in radiant

        double rfspeed = (Math.sin(angleR + (3 * Math.PI / 4)) * speed) + (rotation * rotationK);     //Speed for leftfront
        double lfspeed = (Math.sin(angleR + (5 * Math.PI / 4)) * speed) + (rotation * rotationK);     //Speed for rightfront
        double lbspeed = (Math.sin(angleR + (7 * Math.PI / 4)) * speed) + (rotation * rotationK);      //Speed for rightback
        double rbspeed = (Math.sin(angleR + (9 * Math.PI / 4)) * speed) + (rotation * rotationK);    //Speed for leftback

        double maxNormalize = Math.max(Math.max(Math.abs(lfspeed), Math.abs(rfspeed)), Math.max(Math.abs(rbspeed), Math.abs(lbspeed))); //Finds the greatest power of the motors

        if ((Math.abs(lfspeed) > maxSpeed) || (Math.abs(rfspeed) > maxSpeed) || (Math.abs(rbspeed) > maxSpeed) || (Math.abs(lbspeed) > maxSpeed)){ //Normalize so no motor speed can be set above 1
            rfspeed = (lfspeed/maxNormalize) * maxSpeed;
            lfspeed = (rfspeed/maxNormalize) * maxSpeed;
            lbspeed = (rbspeed/maxNormalize) * maxSpeed;
            rbspeed = (lbspeed/maxNormalize) * maxSpeed;
        }

        double rfD = ((Math.sin(angleR + (3 * Math.PI / 4)) * direction) * distance) + (rotation * inchesperdegrotation);     //direction for leftfront
        double lfD = ((Math.sin(angleR + (5 * Math.PI / 4)) * direction) * distance) + (rotation * inchesperdegrotation);     //direction for rightfront
        double lbD = ((Math.sin(angleR + (7 * Math.PI / 4)) * direction) * distance) + (rotation * inchesperdegrotation);      //direction for rightback
        double rbD = ((Math.sin(angleR + (9 * Math.PI / 4)) * direction) * distance) + (rotation * inchesperdegrotation);    //direction for leftback

        int rfencodercounts = (int)(lfD * countsperin);
        int lfencodercounts = (int)(rfD * countsperin);
        int lbencodercounts = (int)(rbD * countsperin);
        int rbencodercounts = (int)(lbD * countsperin);

        rf.setVelocity(lfspeed);
        lf.setVelocity(rfspeed);
        lb.setVelocity(lbspeed);
        rb.setVelocity(rbspeed);
        rf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lb.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
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

    public void Teleop(Gamepad gamepad1, Telemetry telemetry, int position){ //Code to be run in Teleop Mode void Loop at top level
        double leftPowerY = -gamepad1.left_stick_y;      //find the value of y axis on the left joystick;
        double leftPowerX = gamepad1.left_stick_x;      //find the value of x axis on the left joystick;
        double rightPowerX = gamepad1.right_stick_x;     //find the value of x axis on the right joystick;


        double angleR = Math.atan2(leftPowerY, leftPowerX)/*-(Math.PI/2)*/; //Calculating angle of which the joystick is commanded to in radians
        double angleD = Math.toDegrees(angleR); //Calculating angle of which the joystick is commanded to in degrees
        double speed = Math.sqrt((leftPowerY * leftPowerY) + (leftPowerX * leftPowerX)); //Calculating the magnitude of the joystick


        telemetry.addData("Angle: ", angleD);
        telemetry.addData("Speed: ", speed);

        double rfpower = (Math.sin(angleR + (1 * Math.PI / 4)) * speed) + (rightPowerX * rotationK);     //Power level for leftfront
        double lfpower = -(Math.sin(angleR + (3 * Math.PI / 4)) * speed) + (rightPowerX * rotationK);     //Power level for rightfront
        double lbpower = (Math.sin(angleR + (5 * Math.PI / 4)) * speed) + (rightPowerX * rotationK);      //Power level for rightback
        double rbpower = -(Math.sin(angleR + (7 * Math.PI / 4)) * speed) + (rightPowerX * rotationK);    //Power level for leftback


        double max = Math.max(Math.max(Math.abs(lfpower), Math.abs(rfpower)), Math.max(Math.abs(rbpower), Math.abs(lbpower))); //Finds the greatest power of the moters

        if ((Math.abs(lfpower) > 1) || (Math.abs(rfpower) > 1) || (Math.abs(rbpower) > 1) || (Math.abs(lbpower) > 1)){ //Normalize so no motor speed can be set above 1
            rfpower /= max;
            lfpower /= max;
            lbpower /= max;
            rbpower /= max;
        }

        if ((position > -2) && (position < 2)){
            rfpower *= 0.5;
            lfpower *= 0.5;
            lbpower *= 0.5;
            rbpower *= 0.5;
        }

        rf.setPower(lbpower);
        lf.setPower(rbpower);
        lb.setPower(lfpower);
        rb.setPower(rfpower);

        telemetry.addData("RF Power", rfpower);
        telemetry.addData("LF Power", lfpower);
        telemetry.addData("LB Power", lbpower);
        telemetry.addData("RB Power", rbpower);

    }

    public double getWheelD() {
        return wheelD;
    }

    public void AutoStop(){
        rf.setVelocity(0);
        lf.setVelocity(0);
        lb.setVelocity(0);
        rb.setVelocity(0);
    }


}