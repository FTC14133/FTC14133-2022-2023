
package org.firstinspires.ftc.teamcode;
// https://first-tech-challenge.github.io/SkyStone/  This is the link to ALL metered of FTC

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Subsystems.AllianceSingleton;
import org.firstinspires.ftc.teamcode.Subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Subsystems.Lift;
import org.firstinspires.ftc.teamcode.Subsystems.Sensors;
import org.firstinspires.ftc.teamcode.Subsystems.Detection;

@Autonomous(name="FTC_14133_2022_Auto", group="Auto")


//My favorite shape is a nonagon
//I like to ride dirt bikes RS


public class  FTC_14133_2022_Auto extends LinearOpMode{
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
    int routine = 2;
    int detected = -1;


    public void HardwareStart() {
        telemetry.addData("Object Creation", "Start");
        telemetry.update();
        drivetrain = new Drivetrain(hardwareMap);
        Intake = new Intake(hardwareMap);
        Lift = new Lift(hardwareMap);
        Sensors = new Sensors(hardwareMap);
        Detection = new Detection(hardwareMap);
        telemetry.addData("Object Creation", "Done");
        telemetry.addLine("Input Alliance (X = blue, B = red)");
        telemetry.update();
/*        while (!AllianceSelected){
            if (A){
                AllianceString = "Blue";
            }else {
                AllianceString = "Red";
            }
            telemetry.addData("Alliance: ", AllianceString);
            if (gamepad1.x){
                //AllianceSingleton.AllianceInstance().SetAlliance(true);
            }if (gamepad1.b){
                //AllianceSingleton.AllianceInstance().SetAlliance(false);
            }if (gamepad1.start){
                AllianceSelected = true;
            }
            A = AllianceSingleton.AllianceInstance().GetAlliance();
            telemetry.update();
        }

        telemetry.addLine("Input Auto Routine (Up = V1, Right = V2, Down = V3, Left = V4)");
        telemetry.update();
        while (!AutoSelected){
            telemetry.addData("Alliance: ", "V"+(routine));
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
        }*/

    }

    public void runOpMode(){

        //     if ((Pivot_Arm != null) && (drivetrain != null) && (Intake !=null) && (Sensors != null) && (Turn_Table != null))
        //     {
        HardwareStart();
        //waitForStart();

        while (!opModeIsActive() && !isStopRequested()) {
            detected = Detection.AprilTagDetection(telemetry);
        }

        telemetry.addData("Object", "Passed waitForStart");
        telemetry.update();

        Lift.SetArmHome(false);
        Lift.SetElevatorHome(false);

        Lift.Home(telemetry);

        telemetry.addData("Object", "After Home");
        telemetry.update();

        //detected = Detection.AprilTagDetection(telemetry);

        telemetry.addData("detected", detected);
        telemetry.update();
        
        Intake.Update_intake(0);
        sleep(1000);


       // drivetrain.DrivetrainAutoMove(5, 0.5, 90);


/*        drivetrain.DrivetrainAutoMove(0.75, 90, telemetry);
        telemetry.addData("Object", "After rotation");
        telemetry.update();
        drivetrain.DrivetrainAutoMove(2, 0.75, 90, telemetry);
        telemetry.addData("Object", "After strafe");
        telemetry.update();
        drivetrain.DrivetrainAutoMove(2, 0.75, 0, 90, telemetry);
        telemetry.addData("Object", "After forward rotate");
        telemetry.update();*/
        if (routine == -1){
            drivetrain.DrivetrainAutoMove(0.5, 50, telemetry);
            drivetrain.DrivetrainAutoMove(2.5, 0.5, 180, telemetry);

            Lift.GotoPosition(-3, 0, 0);
            sleep(1500);
            Intake.Update_intake(1);
            sleep(2000);
            drivetrain.DrivetrainAutoMove(0.5, -50, telemetry);
            drivetrain.DrivetrainAutoMove(44,  0.75, 180, telemetry);
            drivetrain.DrivetrainAutoMove(0.25, 90, telemetry);
            drivetrain.DrivetrainAutoMove(9, 0.5, 360, telemetry);
            Lift.GotoPosition(4, 0, 25);
            sleep(1000);
            drivetrain.DrivetrainAutoMove(1.5, 0.5, 360, telemetry);
            Intake.Update_intake(0);
            sleep(1000);
            Lift.GotoPosition(-2, 0, -70);
            drivetrain.DrivetrainAutoMove(30, 0.5, 180, telemetry);
            drivetrain.DrivetrainAutoMove(0.5, 77, telemetry);
            Intake.Update_intake(1);
        }
        if (routine == 0) { //This code will run if auto routine 0 is selected
            //drivetrain.DrivetrainAutoMove(12, 0.75, 0, telemetry);
            drivetrain.DrivetrainAutoMove(72, 0.5, 180, telemetry);
//hi
        }else if (routine == 1){ //
            Lift.GotoPosition(2, 0, 0);
            drivetrain.DrivetrainAutoMove(34, 0.75, 180, telemetry);
            drivetrain.DrivetrainAutoMove(0.75, 90, telemetry);
            drivetrain.DrivetrainAutoMove(8, 0.75, 270, telemetry);
            if (detected == 1){
                drivetrain.DrivetrainAutoMove(15, 0.75, 180, telemetry);
            }else if (detected == 3){
                drivetrain.DrivetrainAutoMove(15, 0.5, 0, telemetry);
            }

        }
        else if (routine == 2){
            telemetry.addData("detected", detected);
            telemetry.update();
            Lift.GotoPosition(2, 0, 0);
            drivetrain.DrivetrainAutoMove(36, 0.75, 180, telemetry);
            drivetrain.DrivetrainAutoMove(0.75, -90, telemetry);
            Intake.Update_intake(1);
            sleep(2000);
            drivetrain.DrivetrainAutoMove(23, 0.75, 90, telemetry);
            telemetry.addData("Current Detected Value", detected);
            telemetry.update();
            if (detected == 1){
                drivetrain.DrivetrainAutoMove(12, 0.5, 360, telemetry);
            }else if (detected == 3){
                drivetrain.DrivetrainAutoMove(26, 0.5, 180, telemetry);
            }
        }
        else if (routine == 3){
            Lift.GotoPosition(2, 0, 0);
            drivetrain.DrivetrainAutoMove(30, 0.75, 0, telemetry);
            drivetrain.DrivetrainAutoMove(0.75, -90, telemetry);
            drivetrain.DrivetrainAutoMove(13, 0.75, 270, telemetry);
            if (detected == 1){
                drivetrain.DrivetrainAutoMove(15, 0.75, 0, telemetry);
            }else if (detected == 3){
                drivetrain.DrivetrainAutoMove(15, 0.75, 180, telemetry);
            }
        }
        else if (routine == 4){ //This code will run if auto routine 4 is selected
            Lift.GotoPosition(1, 0, 0);
            drivetrain.DrivetrainAutoMove(30, 0.75, 180, telemetry);
            drivetrain.DrivetrainAutoMove(0.75, 90, telemetry);
            drivetrain.DrivetrainAutoMove(13, 0.75, 270, telemetry);
            if (detected == 1){
                drivetrain.DrivetrainAutoMove(15, 0.75, 180, telemetry);
            }else if (detected == 3){
                drivetrain.DrivetrainAutoMove(15, 0.75, 0, telemetry);
            }
        }

/*        if (detected == -1){
            drivetrain.DrivetrainAutoMove(20, 0.75, 180, telemetry);

        }*/
    }
}