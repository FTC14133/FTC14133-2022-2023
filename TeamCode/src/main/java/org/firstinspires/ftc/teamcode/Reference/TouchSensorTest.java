package org.firstinspires.ftc.teamcode.Reference;

import com.qualcomm.hardware.rev.RevTouchSensor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.HardwareMap;

@TeleOp
public class TouchSensorTest extends OpMode{
    // Define variables for our potentiometer and motor
    RevTouchSensor HomeSwitchElevatorUp;

    // Define variable for the current voltage

    public void init() {
        // Get the potentiometer and motor from hardwareMap
        HomeSwitchElevatorUp = hardwareMap.get(RevTouchSensor.class, "HSElevatorUp");
        }

    public void loop() {
        telemetry.addData("Detect: ", HomeSwitchElevatorUp.isPressed());
        telemetry.update();
    }
}
