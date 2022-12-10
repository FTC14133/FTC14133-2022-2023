/*package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name="Encoder_Test", group="Auto")

public class Encoder_Test extends LinearOpMode{
    DcMotor Motor; //Makes a motor that is a test

    public void runOpMode(){
        Motor = hardwareMap.dcMotor.get("motor"); //Sets the variable to the robot

        Motor.setDirection(DcMotor.Direction.FORWARD); //When running the motor in a positive power the motor will go forwards

        Motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); //Resets the current position of the encoder to 0.

        Motor.setPower(0.25); //Will give the power needed for the position provided to go to
        Motor.setTargetPosition(1120); //1120 is the amount of motor ticks are there for a AndyMark DcMotor, This also tells where to go

        telemetry.addData("Mode", "running"); //Will tell the phone/screen that the motor is going to run
        telemetry.update();

        try {
            wait(2000); //Will wait for 2 seconds (2000 milliseconds)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Motor.setTargetPosition(0); //Sets the motor to its original position

    }
}


 */