
package org.firstinspires.ftc.teamcode.Subsystems;

// Generic Lift

import com.qualcomm.hardware.rev.RevTouchSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

//import org.firstinspires.ftc.robotcore.external.Telemetry;

//Todo: We need roughly the same code for the elevator. They can both be controlled here, but all hardware and logic needs to be written.
public class Lift {
    // Instantiate the lift motor variables
    private DcMotorEx elevator;
    private DcMotorEx arm;
    boolean homeElevator = false; //Todo: Need homing for both elevator and arm separately. We may call them at the same time, but should be different functions.
    boolean homeArm = false;
    RevTouchSensor HomeSwitchElevator; //Mapped to port n+1 (odd number ports) Todo: need one for the elevator and one for the arm
    RevTouchSensor HomeSwitchArm;

    public int position = 0; // Integer position of the arm
    int tolerance = 0; // Encoder tolerance
//<<<<<<< Updated upstream
    final double ArmCountsPerRev = 28; // Counts per rev of the motor //Todo: All this math and below needs to be refactored relative to the pivot arm or elevator.
    final double ArmGearRatio = (32/10) * (68/13) * (68/13); //Ratio of the entire Pivot Arm from the motor to the arm
    final double ArmCountsPerDegree = ArmCountsPerRev * ArmGearRatio /360; //Converts counts per motor rev to counts per degree of arm rotation
//=======
    final double ElevatorCountsPerRev = 28; //Counts per revolution of the motor
    final double ElevatorGearRatio = (84/29)*(68/13); //Gear ratio of the motors
    final double ElevatorSpoolDiameter = 1; //The diameter of the wheels (We are converting mm to inch)
    final double ElevatorDTR = Math.PI* ElevatorSpoolDiameter / ElevatorGearRatio; //Distance traveled in one rotation
    final double ElevatorCountsPerInch = ElevatorCountsPerRev / ElevatorDTR; //Counts Per Inch

    final double liftPower =0.75;
    final double armPower=0.75;
    int joystick_int_left;
    int joystick_int_right;

    boolean toggleLift = true;
    boolean toggleFlip = true;

    public Lift(HardwareMap hardwareMap){                 // Motor Mapping
        elevator = hardwareMap.get(DcMotorEx.class, "elevator");//Sets the names of the hardware on the hardware map Todo: Need another hardware for the arm.
        HomeSwitchElevator = hardwareMap.get(RevTouchSensor.class, "HS_Elevator"); //Todo: Need homing switches for both.
        arm = hardwareMap.get(DcMotorEx.class, "arm");//Sets the names of the hardware on the hardware map Todo: Need another hardware for the arm.
        HomeSwitchArm = hardwareMap.get(RevTouchSensor.class, "HS_Arm"); //Todo: Need homing switches for both.
    // "DeviceName" must match the Config EXACTLY

        // Set motor direction based on which side of the robot the motors are on
        elevator.setDirection(DcMotorEx.Direction.REVERSE);
        elevator.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        position=3; //initial arm position
    }

    public void Teleop(Gamepad gamepad2, Telemetry telemetry){ //Code to be run in Op Mode void Loop at top level

        joystick_int_right = (int)(gamepad2.right_stick_y*60);
        joystick_int_left = (int)(gamepad2.right_stick_y*60);

        if (!homeElevator){ //If arm is not homed
            HomeArm(); //Runs the homing sequence for the arm to reset it
        }
        else if (gamepad2.back){ //If the arm is homed, but the back button is pressed
            SetArmHome(false); //Set home variable to false (not-homed)
        }
        else if (toggleFlip && gamepad2.x){
            toggleFlip = false;
            position *= -1;
        }
        else if (!gamepad2.x){
            toggleFlip = true;
        }
        else if (toggleLift && (gamepad2.dpad_up || gamepad2.dpad_down)) {  // Only execute once per Button push
                toggleLift = false;  // Prevents this section of code from being called again until the Button is released and re-pressed
                if (gamepad2.dpad_down) {  // If the d-pad up button is pressed
                    position = position + 1; //Increase Arm position
                    if (position > 3) { //If arm position is above 3
                        position = 3; //Cap it at 3
                    }
                } else if (gamepad2.dpad_up) { // If d-pad down button is pressed
                    position = position - 1; //Decrease arm position
                    if (position < -3) { //If arm position is below -3
                        position = -3; //cap it at -3
                    }
                }
            telemetry.addData("Home", homeElevator); //Todo: Add telemetry data for both portions of the lift
            telemetry.addData("Arm Position", position);
            telemetry.addData("Target Position", elevator.getTargetPosition());
            telemetry.addData("Encoder Position", elevator.getCurrentPosition());
            }
            else if (!gamepad2.dpad_up && !gamepad2.dpad_down) { //if neither button is being pressed
                toggleLift = true; // Button has been released, so this allows a re-press to activate the code above.
            }



            GotoPosition(position, joystick_int_left, joystick_int_right);
            //Todo: Decide how we want to control the arm. I'm thinking one set of elevations (1, 2, and 3) and a side (Front Back) for a total of 6 positions Possibly positive and negative so you can have a multiplier to flip the side quickly..
        }



    public void GotoPosition(int position, int Ljoystick, int Rjoystick){
        elevator.setPower(liftPower);
        arm.setPower(armPower);//Sets the power for the lift
        switch (position) {
            case 4: // Intake Front
                arm.setTargetPosition((int)(0* ElevatorCountsPerInch +Ljoystick)); //Todo: Determine all positions for the arm/lift
                elevator.setTargetPosition((int)(0* ElevatorCountsPerInch +Rjoystick));
                break;
            case 3: // Intake Front
                arm.setTargetPosition((int)(0* ElevatorCountsPerInch +Ljoystick)); //Todo: Determine all positions for the arm/lift
                elevator.setTargetPosition((int)(0* ElevatorCountsPerInch +Rjoystick));
                break;
            case 2: // Mid Level Front
                arm.setTargetPosition((int)(-60* ElevatorCountsPerInch +Ljoystick));
                elevator.setTargetPosition((int)(-60* ElevatorCountsPerInch +Rjoystick));
                break;

            case 1: //Upper Level Front
                arm.setTargetPosition((int)(-100* ElevatorCountsPerInch +Ljoystick));
                elevator.setTargetPosition((int)(-60* ElevatorCountsPerInch +Rjoystick));
                break;

            case 0: //Straight Up
                arm.setTargetPosition((int)(-140* ElevatorCountsPerInch +Ljoystick));
                elevator.setTargetPosition((int)(-60* ElevatorCountsPerInch +Rjoystick));
                break;
            case -1: //Upper Level Back
                arm.setTargetPosition((int)(-185* ElevatorCountsPerInch +Ljoystick));
                elevator.setTargetPosition((int)(-60* ElevatorCountsPerInch +Rjoystick));
                break;
            case -2: //Mid Level Back
                arm.setTargetPosition((int)(-220* ElevatorCountsPerInch +Ljoystick));
                elevator.setTargetPosition((int)(-60* ElevatorCountsPerInch +Rjoystick));
                break;
            case -3: // Intake Back
                arm.setTargetPosition((int)(-290* ElevatorCountsPerInch +Ljoystick));
                elevator.setTargetPosition((int)(-60* ElevatorCountsPerInch +Rjoystick));
                break;
            case -4: // Intake Front
                arm.setTargetPosition((int)(0* ElevatorCountsPerInch +Ljoystick)); //Todo: Determine all positions for the arm/lift
                elevator.setTargetPosition((int)(0* ElevatorCountsPerInch +Rjoystick));
                break;
            default:
                throw new IllegalStateException("Unexpected position value: " + position);
        }

    }

    public int GetArmPosition(){ // Returns the current position value of the arm
        return position;
    } //Todo: Make for both portions (Get Elevator Position)

    public void SetArmHome(boolean home){ //Sets whether the arm is homed or not, used for homing during a match
        homeElevator = home;
    } //Todo: Make for both portions (Set Elevator Home)
    public boolean GetArmHome(){
        return homeElevator;
    } //Gets whether the arm is homed or not

    public void HomeArm(){ //Method to home arm Todo: Make for both portions (Home elevator?)
        if (HomeSwitchElevator.isPressed()==false){ //If the home switch is not pressed
            elevator.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            elevator.setPower(.5); //run the motor towards the switch
        }
        else { //when the switch is pressed
            elevator.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); //Stop lift motor and set position to 0
            elevator.setMode(DcMotor.RunMode.RUN_TO_POSITION); //Change the run mode
            elevator.setTargetPositionTolerance(tolerance); //Set the arm encoder tolerance
            homeElevator =true; //Change value of Home to true
        }
    }

}
