package org.firstinspires.ftc.teamcode.Subsystems;

// Intake

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class  Intake {
    // Instantiate the motor variables
    CRServo intake_fl;
    CRServo intake_fr;
    CRServo intake_bl;
    CRServo intake_br;
    boolean toggle = true;
    boolean Possession = true; //Variable telling whether we have possession of a game piece or not
    //DigitalChannel beambreak; //The "beambreak" sensor is a type of IR sensor that detects if it vision is broken

    public Intake(HardwareMap hardwareMap){                 // Motor Mapping
        intake_fl = hardwareMap.crservo.get("intake_fl");      //Sets the names of the hardware on the hardware map
        intake_fr = hardwareMap.crservo.get("intake_fr");
        intake_bl = hardwareMap.crservo.get("intake_bl");      //Sets the names of the hardware on the hardware map
        intake_br = hardwareMap.crservo.get("intake_br");      //Sets the names of the hardware on the hardware map
        // "DeviceName" must match the Config EXACTLY
        //beambreak = hardwareMap.get(DigitalChannel.class, "beambreak"); // Todo: What will the beambreak do?
        // Set motor direction based on which side of the robot the motors are on
    }

    private void runIntake(double rotationSpeed){
        intake_fl.setPower(rotationSpeed);
        intake_fr.setPower(-rotationSpeed);
        intake_bl.setPower(rotationSpeed);
        intake_br.setPower(-rotationSpeed);
    }

    public void Update_intake(double speed, Gamepad gamepad2){ //Standard intake function
        /* // Todo: What will the beambreak do?
        if(!beambreak_Down.getState() || !beambreak.getState()) {
            intake.setPower(0);//Stop intake
        }
        else{ // if beam break not broken
            Possession = false; //we do not have possession
            intake.setPower(speed); // Run intake
        }
         */
        if (gamepad2.left_bumper) {
            speed *= .5;
        }
        runIntake(speed);

    }

    public void Update_outtake(double speed, Gamepad gamepad2){ //Standard outtake function
        if (gamepad2.left_bumper) {
            speed *= .5;
        }
        runIntake(-speed);//Runs the intake
    }

    public void Teleop(Gamepad gamepad2, int position, Telemetry telemetry){ //Code to be run in Op Mode void Loop at top level
        if(gamepad2.right_trigger>0){ //if the left trigger is pulled
            Update_intake(gamepad2.right_trigger, gamepad2); //Run the outtake program
        }
        else {
            Update_outtake(gamepad2.left_trigger, gamepad2); //Otherwise run the Intake program

        }
        telemetry.addData("Posession",Possession);
    }

    // Todo: What will the beambreak do?
    /*
        public void beambreak_print(Telemetry telemetry){ //Code to be run in Op Mode void Loop at top level
            telemetry.addData("possession", Possession);
            telemetry.addData("beambreak_Up", beambreak.getState());
            telemetry.addData("beambreak_Down", beambreak_Down.getState());

        }
        public boolean getPossession(){
            return Possession; //returns the variable from thr beambreak that identifies if we have fright or not
        }

        public void Possession_Check(){
            if(!beambreak_Down.getState() || !beambreak.getState()){
                Possession = true;
            }
            else{
                Possession = false;
            }
        }

     */
    }

