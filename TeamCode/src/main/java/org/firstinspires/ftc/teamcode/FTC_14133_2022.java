
package org.firstinspires.ftc.teamcode;
// https://first-tech-challenge.github.io/SkyStone/  This is the link to ALL metered of FTC

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Subsystems.Lift;
import org.firstinspires.ftc.teamcode.Subsystems.Sensors;
import org.firstinspires.ftc.teamcode.Subsystems.Lights;

@TeleOp(name="FTC_14133_2022", group="Iterative Opmode") // Labels program in Driver station Selection

//This is a test
//My favorite shape is a nonagon
//I like to ride dirt bikes RS

//TEST

//COMMENT YOUR CODE (VIHAAN)! We're adding a lot of automation, which is nice, but it is going to be REALLY difficult to troubleshoot.

public class  FTC_14133_2022 extends OpMode {
 private Drivetrain drivetrain=null;
 private Intake Intake=null;
 private Lift Pivot_Arm=null;
 private Sensors Sensors=null;
 private Lights Lights=null;
 boolean [] switches;
 boolean Alliance;
 boolean Warehouse_TurnTable;

 public void init() {
     drivetrain = new Drivetrain(hardwareMap);
     Intake = new Intake(hardwareMap);
     Pivot_Arm = new Lift(hardwareMap);
     Sensors = new Sensors(hardwareMap);
     Lights = new Lights(hardwareMap);
 }

 public void init_loop() {

 }
 public void start() {
     telemetry.addData("Status", "Start");
     telemetry.update();
 }


 public void loop() {
     telemetry.addData("Status", "Looping");

     Pivot_Arm.Teleop(gamepad2, telemetry); //Run the regular arm function

     drivetrain.Teleop(gamepad1,telemetry);

     //Lights.Update_Lights(Intake.getPossession(),Turn_Table.getRotation(), Alliance);

     Intake.Teleop(gamepad2,Pivot_Arm.GetPosition(), telemetry); //Passes position of the arm so intake direction can change.
     Intake.beambreak_print(telemetry);
     telemetry.update();
     Intake.Possession_Check();

 }
}
