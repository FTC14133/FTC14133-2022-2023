package org.firstinspires.ftc.teamcode.Subsystems;

// Intake

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class  Intake {
    // Instantiate the motor variables
    private DcMotorEx intake; //Todo: This year the intake will likely use servos instead of motors due to weight up high. Needs to mo midified as such
    boolean toggle = true;
    boolean Possession = true; //Variable telling whether we have possession of a game piece or not
    DigitalChannel beambreak_Up; //The "beambreak" sensor is a type of IR sensor that detects if it vision is broken
    DigitalChannel beambreak_Down; //Todo: If we do use a beam break to sense a cone, we will only be using one.
    Servo Hook;

    public Intake(HardwareMap hardwareMap){                 // Motor Mapping
        intake = hardwareMap.get(DcMotorEx.class, "intake");      //Sets the names of the hardware on the hardware map
        // "DeviceName" must match the Config EXACTLY
        beambreak_Up = hardwareMap.get(DigitalChannel.class, "beambreak_Up");
        beambreak_Down = hardwareMap.get(DigitalChannel.class, "beambreak_Down");
        // Set motor direction based on which side of the robot the motors are on
        intake.setDirection(DcMotorEx.Direction.REVERSE);
        Hook =hardwareMap.get(Servo.class,"Hook");
    }

    public void Update_intake(double speed, int position){ //Standard intake function
        if(position<0){ //if the arm is towards the back Todo: We don't need this directionality code. Intake/outtake will work the same regardless of arm position
            speed = -speed; //flip the direction of the intake
        }
        if(!beambreak_Down.getState() || !beambreak_Up.getState()) {
            intake.setPower(0);//Stop intake
        }
        else{ // if beam break not broken
            Possession = false; //we do not have possession
            intake.setPower(speed); // Run intake
        }

    }

    public void Update_outtake(double speed, int position, Gamepad gamepad2){ //Standard outtake function
        if(position>0){ //if the position of the arm is on the backside of the robot
            speed=-speed; //reverse the intake automatically so we can pick up stuff correctly

        }
        if (gamepad2.left_bumper) {
            speed *= .5;
        }
        intake.setPower(speed);//Runs the intake
    }

    public void Teleop(Gamepad gamepad2, int position, Telemetry telemetry){ //Code to be run in Op Mode void Loop at top level
        if(gamepad2.right_trigger>0){ //if the left trigger is pulled
            Update_intake(gamepad2.right_trigger*0.75, position); //Run the outtake program
        }
        else {
            Update_outtake(gamepad2.left_trigger*0.75 ,position, gamepad2); //Otherwise run the Intake program

        }
        telemetry.addData("Posession",Possession);
    }
    public void Team_Shipping_Element(Gamepad gamepad2){ //Code to be run in Op Mode void Loop at top level
        if(gamepad2.y==true){ //if the x button is pressed
            Hook.setPosition(1); //Hook extended

        }
        else {
           Hook.setPosition(0); //Hook not extended

        }
    }
        public void Home_TSE(){ //Code to be run in Op Mode void Loop at top level
            Hook.setPosition(0); //locks the hook

        }
        public void beambreak_print(Telemetry telemetry){ //Code to be run in Op Mode void Loop at top level
            telemetry.addData("possession", Possession);
            telemetry.addData("beambreak_Up", beambreak_Up.getState());
            telemetry.addData("beambreak_Down", beambreak_Down.getState());

        }
        public boolean getPossession(){
            return Possession; //returns the variable from thr beambreak that identifies if we have fright or not
        }

        public void Possession_Check(){
            if(!beambreak_Down.getState() || !beambreak_Up.getState()){
                Possession = true;
            }
            else{
                Possession = false;
            }
        }
    }

