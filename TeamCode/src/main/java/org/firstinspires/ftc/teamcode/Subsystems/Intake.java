package org.firstinspires.ftc.teamcode.Subsystems;

// Intake

import com.qualcomm.hardware.rev.RevTouchSensor;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class  Intake {
    // Instantiate the motor variables
    CRServo intake_f;
    CRServo intake_b;
    boolean toggle = true;
    boolean Possession = true; //Variable telling whether we have possession of a game piece or not
    RevTouchSensor IntakeTouch; //The "beambreak" sensor is a type of IR sensor that detects if it vision is broken

    public Intake(HardwareMap hardwareMap){                 // Motor Mapping
        intake_f = hardwareMap.crservo.get("intake_f");      //Sets the names of the hardware on the hardware map
        intake_b = hardwareMap.crservo.get("intake_b");
        // "DeviceName" must match the Config EXACTLY
        IntakeTouch = hardwareMap.get(RevTouchSensor.class, "IntakeTouch");
        // Set motor direction based on which side of the robot the motors are on
    }

    private void runIntake(double rotationSpeed, int positionArm){ //Todo: positionArm no longer needs to be an input to this function
        if (positionArm > 0) {
            rotationSpeed *= -1; //Todo: We do not need to switch the direction of the intake based on arm direction like we did last year.
        }
        intake_f.setPower(rotationSpeed); //Todo: one of these may need to be backwards
        intake_b.setPower(rotationSpeed);
    }

    public void Update_intake(double speed, int positionArm, Gamepad gamepad2){ //Standard intake function
        if (gamepad2.left_bumper) {
            speed *= .5;
        }
        if(!IntakeTouch.isPressed()) {
            runIntake(0, positionArm);//Stop intake
        }
        else{ // if beam break not broken
            Possession = false; //we do not have possession
            runIntake(speed, positionArm); // Run intake
        }


    }

    public void Update_outtake(double speed, int positionArm, Gamepad gamepad2){ //Standard outtake function Todo: does not need positionArm as an input
        if (gamepad2.left_bumper) {
            speed *= .5;
        }
        runIntake(-speed, positionArm);//Runs the intake
    }

    public void Teleop(Gamepad gamepad2, int positionArm, Telemetry telemetry){ //Code to be run in Op Mode void Loop at top level
        if(gamepad2.right_trigger>0){ //if the left trigger is pulled
            Update_intake(gamepad2.right_trigger, positionArm, gamepad2); //Run the intake program Todo: no longer needs positionArm as an input.
        }
        else {
            Update_outtake(gamepad2.left_trigger, positionArm, gamepad2); //Otherwise run the outtake program

        }
        telemetry.addData("Possession",Possession);
    }


    public void beambreak_print(Telemetry telemetry){ //Code to be run in Op Mode void Loop at top level
        telemetry.addData("possession", Possession);
        telemetry.addData("IntakeTouch", IntakeTouch.isPressed());

    }
    public boolean getPossession(){
        return Possession; //returns the variable from thr beambreak that identifies if we have fright or not
    }

    public void Possession_Check(){
        if(IntakeTouch.isPressed()){
            Possession = true;
        }
        else{
            Possession = false;
        }
    }


    }

