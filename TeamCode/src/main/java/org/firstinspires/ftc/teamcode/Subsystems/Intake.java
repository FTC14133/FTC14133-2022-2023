package org.firstinspires.ftc.teamcode.Subsystems;

// Intake

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Intake {
    // Instantiate the motor variables
    CRServo intake;
    //CRServo intake_b;
    boolean toggle = true;
    boolean Possession = true; //Variable telling whether we have possession of a game piece or not
    DigitalChannel IntakeTouch; //The "beambreak" sensor is a type of IR sensor that detects if it vision is broken

    public Intake(HardwareMap hardwareMap){                 // Motor Mapping
        intake = hardwareMap.crservo.get("intake");      //Sets the names of the hardware on the hardware map
        //intake_b = hardwareMap.crservo.get("intake_b");
        // "DeviceName" must match the Config EXACTLY
        IntakeTouch = hardwareMap.get(DigitalChannel.class, "IntakeTouch");
        // Set motor direction based on which side of the robot the motors are on
    }
/*
    private void runIntake(double rotationSpeed){
        intake.setPower(rotationSpeed);
        //intake_b.setPower(-rotationSpeed);
    }

 */

    public void Stop_intake(){
        intake.setPower(0);
    }

    public void Update_intake(double speed, boolean intakeSlow){ //Standard intake function
        if (intakeSlow) {
            speed *= .5;
        }
        if(!IntakeTouch.getState()) {
            intake.setPower(0);//Stop intake
        }
        else{ // if beam break not broken
            Possession = false; //we do not have possession
            intake.setPower(speed); // Run intake
        }
    }

    public void Update_intake(double speed){
        Update_intake(speed, false);
    }

    public void Update_outtake(double speed, boolean outakeSlow){ //Standard outtake function
        if (outakeSlow) {
            speed *= .5;
        }
        intake.setPower(-speed);//Runs the intake
    }

    public void Update_outtake(double speed){
        Update_outtake(speed, false);
    }

    public void Teleop(Gamepad gamepad2, int position, Telemetry telemetry){ //Code to be run in Op Mode void Loop at top level
        if(gamepad2.right_trigger>0){ //if the left trigger is pulled
            Update_intake(gamepad2.right_trigger, gamepad2.right_trigger > 0); //Run the intake program
        }
        else {
            Update_outtake(gamepad2.left_trigger, gamepad2.left_trigger > 0); //Otherwise run the outtake program

        }
        telemetry.addData("Possession",Possession);
    }


    public void beambreak_print(Telemetry telemetry){ //Code to be run in Op Mode void Loop at top level
        telemetry.addData("possession", Possession);
        telemetry.addData("IntakeTouch", IntakeTouch.getState());

    }
    public boolean getPossession(){
        return Possession; //returns the variable from thr beambreak that identifies if we have fright or not
    }

    public void Possession_Check(){
        if(IntakeTouch.getState()){
            Possession = true;
        }
        else{
            Possession = false;
        }
    }
}

