
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
    final double ElevatorSpoolDiameter = 96 / 25.4; //The diameter of the wheels (We are converting mm to inch)
    final double ElevatorDTR = Math.PI* ElevatorSpoolDiameter / ElevatorGearRatio; //Distance traveled in one rotation
    final double ElevatorCountsPerInch = ElevatorCountsPerRev / ElevatorDTR; //Counts Per Inch

    final double liftpower=0.75;
    int joystick_int_left;
    int joystick_int_right;

    boolean toggle = true;

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
        else { //When arm is homed and back button not pressed

            if (toggle && (gamepad2.dpad_up || gamepad2.dpad_down)) {  // Only execute once per Button push
                toggle = false;  // Prevents this section of code from being called again until the Button is released and re-pressed
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
            }
            else if (!gamepad2.dpad_up && !gamepad2.dpad_down) { //if neither button is being pressed
                toggle = true; // Button has been released, so this allows a re-press to activate the code above.
            }

            GotoPositionArm(position, joystick_int_right);
            GotoPositionElevator(position, joystick_int_left); //Todo: Decide how we want to control the arm. I'm thinking one set of elevations (1, 2, and 3) and a side (Front Back) for a total of 6 positions Possibly positive and negative so you can have a multiplier to flip the side quickly..
        }
        telemetry.addData("Home", homeElevator); //Todo: Add telemetry data for both portions of the lift
        telemetry.addData("Arm Position", position);
        telemetry.addData("Target Position", elevator.getTargetPosition());
        telemetry.addData("Encoder Position", elevator.getCurrentPosition());
    }

    public void GotoPositionArm(int positionArm, int joystick){
        elevator.setPower(liftpower);        //Sets the power for the lift
        switch (positionArm) {
            case 3: // Intake Front
                arm.setTargetPosition((int)(0* ElevatorCountsPerInch +joystick)); //Todo: Determine all positions for the arm/lift
                break;
            case 2: // Mid Level Front
                arm.setTargetPosition((int)(-60* ElevatorCountsPerInch +joystick));
                break;

            case 1: //Upper Level Front
                arm.setTargetPosition((int)(-100* ElevatorCountsPerInch +joystick));
                break;

            case 0: //Straight Up
                arm.setTargetPosition((int)(-140* ElevatorCountsPerInch +joystick));
                break;
            case -1: //Upper Level Back
                arm.setTargetPosition((int)(-185* ElevatorCountsPerInch +joystick));
                break;
            case -2: //Mid Level Back
                arm.setTargetPosition((int)(-220* ElevatorCountsPerInch +joystick));
                break;
            case -3: // Intake Back
                arm.setTargetPosition((int)(-290* ElevatorCountsPerInch +joystick));
                break;
            default:
                throw new IllegalStateException("Unexpected position value: " + position);
        }

    }
    public void GotoPositionElevator(int positionElevator, int joystick){
        elevator.setPower(liftpower);        //Sets the power for the lift
        switch (positionElevator) {
            case 3: // Intake Front
                elevator.setTargetPosition(0* ElevatorCountsPerInchInt +joystick); //Todo: Determine all positions for the arm/lift
                break;
            case 2: // Mid Level Front
                elevator.setTargetPosition(-60* ElevatorCountsPerInchInt +joystick);
                break;

            case 1: //Upper Level Front
                elevator.setTargetPosition(-100* ElevatorCountsPerInchInt +joystick);
                break;

            case 0: //Straight Up
                elevator.setTargetPosition(-140* ElevatorCountsPerInchInt +joystick);
                break;

            case -1: //Upper Level Back
                elevator.setTargetPosition(-185* ElevatorCountsPerInchInt +joystick);
                break;
            case -2: //Mid Level Back
                elevator.setTargetPosition(-220* ElevatorCountsPerInchInt +joystick);
                break;
            case -3: // Intake Back
                elevator.setTargetPosition(-290* ElevatorCountsPerInchInt +joystick);
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
