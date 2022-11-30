
package org.firstinspires.ftc.teamcode;
// https://first-tech-challenge.github.io/SkyStone/  This is the link to ALL metered of FTC

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Subsystems.AllianceSingleton;
import org.firstinspires.ftc.teamcode.Subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Subsystems.Lift;
import org.firstinspires.ftc.teamcode.Subsystems.Sensors;
import org.firstinspires.ftc.teamcode.Subsystems.Detection;

@Autonomous(name="FTC_14133_2022_Auto", group="Auto")


//My favorite shape is a nonagon
//I like to ride dirt bikes RS


public class  FTC_14133_2022_Auto extends LinearOpMode {
    private Drivetrain drivetrain=null; // This activate the sub systems
    private Intake Intake=null;
    private Lift Lift =null;
    private Sensors Sensors=null;
    private Detection Detection=null;
    boolean[] switches;
    boolean A ; //This will tell us that we are either on the red or blue alliance side
    double total_speed = 0.5; //This is the speed of most of the motors.
    boolean AllianceSelected = false;
    boolean AutoSelected = false;
    String AllianceString = "Not Selected";
    int routine = 0;
    int detected = -1;


    public void waitForStart(Gamepad gamepad2) {
        telemetry.addData("Object Creation", "Start");
        telemetry.update();
        //switches = Sensors.Update_Switches(); // Here we will see from the switches on the robot. Below is what they represent
        //WT = switches[0]; //This will decide if we are closer to the warehouse or turn table based on the switch on the robot
        //A = switches[1]; //This will tell us that we are either on the red or blue alliance side
        A = false;
        drivetrain = new Drivetrain(hardwareMap);
        Intake = new Intake(hardwareMap);
        Lift = new Lift(hardwareMap);
        Sensors = new Sensors(hardwareMap);
        Detection = new Detection(hardwareMap);
        telemetry.addData("Object Creation", "Done");
        telemetry.addLine("Input Alliance (X = blue, B = red)");
        telemetry.update();
        while (!AllianceSelected){
            if (A){
                AllianceString = "Blue";
            }else {
                AllianceString = "Red";
            }
            telemetry.addData("Alliance: ", AllianceString);
            if (gamepad1.x){
                AllianceSingleton.AllianceInstance().SetAlliance(true);
            }if (gamepad1.b){
                AllianceSingleton.AllianceInstance().SetAlliance(false);
            }if (gamepad1.start){
                AllianceSelected = true;
            }
            A = AllianceSingleton.AllianceInstance().GetAlliance();
            telemetry.update();
        }

        telemetry.addLine("Input Auto Routine (Up = V1, Right = V2, Down = V3, Left = V4)");
        telemetry.update();
        while (!AutoSelected){
            telemetry.addData("Alliance: ", "V"+routine);
            if (gamepad1.dpad_up){
                routine = 1;
            }else if (gamepad1.dpad_right){
                routine = 2;
            }else if (gamepad1.dpad_down){
                routine = 3;
            }else if (gamepad1.dpad_left){
                routine = 4;
            }else if (gamepad1.start){
                AutoSelected = true;
            }
            telemetry.update();
        }

    }

    public void runOpMode() {

        //     if ((Pivot_Arm != null) && (drivetrain != null) && (Intake !=null) && (Sensors != null) && (Turn_Table != null))
        //     {
        waitForStart();
        telemetry.addData("Object", "Passed waitForStart");
        telemetry.update();

        Lift.SetArmHome(false);
        Lift.SetElevatorHome(false);

        Lift.Home();

        telemetry.addData("Object", "After Home");
        telemetry.update();

        detected = Detection.AprilTagDetection(telemetry);



        if (routine == 0) { //This code will run if auto routine 0 is selected

        }else if (routine == 1){ //
            drivetrain.DrivetrainAutoMove(5, 0.75, 0);
        }
        else if (routine == 2){
            drivetrain.DrivetrainAutoMove(5, 0.75, 90);
        }
        else if (routine == 3){
            drivetrain.DrivetrainAutoMove(5, 0.75, 0, 90);
        }
        else if (routine == 4){ //This code will run if auto routine 4 is selected
            drivetrain.DrivetrainAutoMove(0.75, 90);
        }
    }
}
