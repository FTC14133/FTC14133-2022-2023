
package org.firstinspires.ftc.teamcode.Subsystems;

// Generic Lift

import com.qualcomm.hardware.rev.RevTouchSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

//import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Lift {
    // Instantiate the lift motor variables
    private DcMotorEx elevator;
    private DcMotorEx arm;
    boolean ElevatorHome = false;
    boolean ArmHome = false;
    RevTouchSensor HomeSwitchElevatorUp;
    RevTouchSensor HomeSwitchElevatorDown;
    RevTouchSensor HomeSwitchArmFront;
    RevTouchSensor HomeSwitchArmBack;

    public int position = 0; // Integer position of the arm
    int tolerance = 0; // Encoder tolerance
//<<<<<<< Updated upstream
    final double ArmCountsPerRev = 28; // Counts per rev of the motor
    final double ArmGearRatio = (32/10) * (68/13) * (68/13); //Ratio of the entire Pivot Arm from the motor to the arm
    final double ArmCountsPerDegree = ArmCountsPerRev * ArmGearRatio /360; //Converts counts per motor rev to counts per degree of arm rotation
//=======
    final double ElevatorCountsPerRev = 28; //Counts per revolution of the motor
    final double ElevatorGearRatio = (84/29)*(68/13); //Gear ratio of the motors
    final double ElevatorSpoolDiameter = 1; //The diameter of the wheels (We are converting mm to inch)
    final double ElevatorDTR = Math.PI* ElevatorSpoolDiameter / ElevatorGearRatio; //Distance traveled in one rotation
    final double ElevatorCountsPerInch = ElevatorCountsPerRev / ElevatorDTR; //Counts Per Inch

    double liftPower =0.75;
    double armPower=0.75;
    int joystick_int_left;
    int joystick_int_right;

    boolean toggleLift = true;
    boolean toggleFlip = true;

    public Lift(HardwareMap hardwareMap){                 // Motor Mapping
        elevator = hardwareMap.get(DcMotorEx.class, "Elevator");//Sets the names of the hardware on the hardware map
        HomeSwitchElevatorUp = hardwareMap.get(RevTouchSensor.class, "HSElevatorUp");
        HomeSwitchElevatorDown = hardwareMap.get(RevTouchSensor.class, "HSElevatorDown");
        arm = hardwareMap.get(DcMotorEx.class, "arm");//Sets the names of the hardware on the hardware map
        HomeSwitchArmFront = hardwareMap.get(RevTouchSensor.class, "HSArmFront");
        HomeSwitchArmBack = hardwareMap.get(RevTouchSensor.class, "HSArmBack");
    // "DeviceName" must match the Config EXACTLY

        // Set motor direction based on which side of the robot the motors are on
        elevator.setDirection(DcMotorEx.Direction.REVERSE);
        elevator.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        position=3; //initial arm position
    }

    public void Teleop(Gamepad gamepad2, Telemetry telemetry){ //Code to be run in Op Mode void Loop at top level

        joystick_int_right = (int)(gamepad2.right_stick_y*60);
        joystick_int_left = (int)(gamepad2.right_stick_y*60);

        if (!ElevatorHome){ //If arm is not homed Todo: Needs to use the generic home function
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
            telemetry.addData("Home", ElevatorHome);
            telemetry.addData("Arm Position", position);
            telemetry.addData("Elev Target Position", elevator.getTargetPosition());
            telemetry.addData("Elev Encoder Position", elevator.getCurrentPosition());
            telemetry.addData("Arm Target Position", arm.getTargetPosition());
            telemetry.addData("Arm Encoder Position", arm.getCurrentPosition());
            }
            else if (!gamepad2.dpad_up && !gamepad2.dpad_down) { //if neither button is being pressed
                toggleLift = true; // Button has been released, so this allows a re-press to activate the code above.
            }



            GotoPosition(position, joystick_int_left, joystick_int_right);

    }

    public void Limits(){
        if (HomeSwitchArmFront.isPressed()){
            armPower = Math.min(armPower, 0);
        }else if (HomeSwitchArmBack.isPressed()){
            armPower = Math.max(armPower, 0);
        }
        if (HomeSwitchElevatorDown.isPressed()){
            liftPower = Math.min(liftPower, 0);
        }else if (HomeSwitchElevatorUp.isPressed()){
            liftPower = Math.max(liftPower, 0);
        }

        elevator.setPower(liftPower);
        arm.setPower(armPower);//Sets the power for the lift
    }

    public void GotoPosition(int position, int Ljoystick, int Rjoystick){
        Limits();
        switch (position) {
            case 4: // Intake Front
                arm.setTargetPosition((int)(0* ArmCountsPerDegree +Ljoystick)); //Todo: Determine all positions for the arm/lift
                elevator.setTargetPosition((int)(0* ElevatorCountsPerInch +Rjoystick));
                break;
            case 3: // Short Level Front
                arm.setTargetPosition((int)(90* ArmCountsPerDegree +Ljoystick));
                elevator.setTargetPosition((int)(0* ElevatorCountsPerInch +Rjoystick));
                break;
            case 2: // Mid Level Front
                arm.setTargetPosition((int)(90* ArmCountsPerDegree +Ljoystick));
                elevator.setTargetPosition((int)(4* ElevatorCountsPerInch +Rjoystick));
                break;

            case 1: //Tall Level Front
                arm.setTargetPosition((int)(90* ArmCountsPerDegree +Ljoystick));
                elevator.setTargetPosition((int)(10.5* ElevatorCountsPerInch +Rjoystick));
                break;

            case 0: //Straight Up
                arm.setTargetPosition((int)(140* ArmCountsPerDegree +Ljoystick));
                elevator.setTargetPosition((int)(0 * ElevatorCountsPerInch +Rjoystick));
                break;
            case -1: //Tall Level Back
                arm.setTargetPosition((int)(180* ArmCountsPerDegree +Ljoystick));
                elevator.setTargetPosition((int)(10.5* ElevatorCountsPerInch +Rjoystick));
                break;
            case -2: //Mid Level Back
                arm.setTargetPosition((int)(180* ArmCountsPerDegree +Ljoystick));
                elevator.setTargetPosition((int)(4* ElevatorCountsPerInch +Rjoystick));
                break;
            case -3: //Short Level Back
                arm.setTargetPosition((int)(180* ArmCountsPerDegree +Ljoystick));
                elevator.setTargetPosition((int)(0* ElevatorCountsPerInch +Rjoystick));
                break;
            case -4: // Intake Back
                arm.setTargetPosition((int)(280* ArmCountsPerDegree +Ljoystick));
                elevator.setTargetPosition((int)(0* ElevatorCountsPerInch +Rjoystick));
                break;
            default:
                throw new IllegalStateException("Unexpected position value: " + position);
        }

    }

    public int GetPosition(){ // Returns the current position value of the arm
        return position;
    }

    public void SetArmHome(boolean home){ //Sets whether the arm is homed or not, used for homing during a match
        ArmHome = home;
    }
    public boolean GetArmHome(){
        return ArmHome;
    } //Gets whether the arm is homed or not

    public void SetElevatorHome(boolean home){ //Sets whether the elevator is homed or not, used for homing during a match
        ElevatorHome = home;
    }
    public boolean GetElevatorHome(){
        return ElevatorHome;
    } //Gets whether the elevator is homed or not

    public void Home(){
        while (!ElevatorHome){
            HomeElevator();
        }
        while (!ArmHome){
            HomeArm();
        }

    }

    public void HomeArm(){ //Method to home arm
        if (!HomeSwitchElevatorUp.isPressed()){ //If the home switch is not pressed
            arm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            arm.setPower(.5); //run the motor towards the switch
        }
        else { //when the switch is pressed
            arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); //Stop lift motor and set position to 0
            arm.setMode(DcMotor.RunMode.RUN_TO_POSITION); //Change the run mode
            arm.setTargetPositionTolerance(tolerance); //Set the arm encoder tolerance
            ArmHome =true; //Change value of Home to true
        }
    }

    public void HomeElevator(){ //Method to home arm
        if (!HomeSwitchElevatorDown.isPressed()){ //If the home switch is not pressed
            elevator.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            elevator.setPower(-.5); //run the motor towards the switch
        }
        else { //when the switch is pressed
            elevator.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); //Stop lift motor and set position to 0
            elevator.setMode(DcMotor.RunMode.RUN_TO_POSITION); //Change the run mode
            elevator.setTargetPositionTolerance(tolerance); //Set the arm encoder tolerance
            ElevatorHome =true; //Change value of Home to true
        }
    }
}
