/*
package org.firstinspires.ftc.teamcode;
// https://first-tech-challenge.github.io/SkyStone/  This is the link to ALL metered of FTC

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Subsystems.AllianceSingleton;
import org.firstinspires.ftc.teamcode.Subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Subsystems.Lift;
import org.firstinspires.ftc.teamcode.Subsystems.Sensors;

@Autonomous(name="FTC_14133_2022_Auto", group="Auto")


//My favorite shape is a nonagon
//I like to ride dirt bikes RS


public class  FTC_14133_2022_Auto extends LinearOpMode {
    private Drivetrain drivetrain=null; // This activate the sub systems
    private Intake Intake=null;
    private Lift Pivot_Arm =null;
    private Sensors Sensors=null;
    boolean GateFlag = false;
    boolean[] switches;
    boolean WT ; //This will decide if we are closer to the warehouse or turn table based on the switch on the robot
    boolean A ; //This will tell us that we are either on the red or blue alliance side
    double total_speed = 0.5; //This is the speed of most of the motors.


    public void waitForStart(Gamepad gamepad2) {

        telemetry.addData("Object Creation", "Start");
        telemetry.update();
        //switches = Sensors.Update_Switches(); // Here we will see from the switches on the robot. Below is what they represent
        //WT = switches[0]; //This will decide if we are closer to the warehouse or turn table based on the switch on the robot
        //A = switches[1]; //This will tell us that we are either on the red or blue alliance side
        A = false; //Todo: Decide which alliance is true which is false (X button is blue and B button is red)
        drivetrain = new Drivetrain(hardwareMap);
        Intake = new Intake(hardwareMap);
        Pivot_Arm = new Lift(hardwareMap);
        Sensors = new Sensors(hardwareMap);
        telemetry.addData("Object Creation", "Done");
        telemetry.update();
        AllianceSingleton.AllianceInstance().SetAlliance(gamepad2.x);
        A = AllianceSingleton.AllianceInstance().GetAlliance();
    }

    public void runOpMode() {

        //     if ((Pivot_Arm != null) && (drivetrain != null) && (Intake !=null) && (Sensors != null) && (Turn_Table != null))
        //     {
        waitForStart();
        telemetry.addData("Object", "Passed waitForStart");
        telemetry.update();

        Pivot_Arm.SetArmHome(false);

        telemetry.addData("Object", "After SetArmHome");
        telemetry.update();
        Pivot_Arm.SetArmHome(false);
        while (Pivot_Arm.GetArmHome() == false) {
            Pivot_Arm.HomeArm(); //Runs the homing sequence for the arm to reset it
        }

        //Pivot_Arm.SetArmHome(true);

        telemetry.addData("Object", "Passed while loop");
        telemetry.update();

        Intake.Home_TSE();


        if (A == false && WT == false && GateFlag == true) { //This code will check if the robot is on the BLUE side and on the Turntable side

        } else if (A == false && WT == true && GateFlag == true) { //This is a different instance where if we are starting on the BLUE side and on the warehouse side

        } else if (A == true && WT == false  && GateFlag == true) { //red and turntable side

        } else if (A == true && WT == true && GateFlag == true) { // red and warehouse side

        }
    }
}
 */