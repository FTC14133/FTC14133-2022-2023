
package org.firstinspires.ftc.teamcode.Subsystems;

//This is an example subsystem. Reference this to set up new devices.

//demo on how the device works:
//https://www.youtube.com/watch?v=wMdkM2rr1a4&ab_channel=REVRobotics

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;

import com.qualcomm.robotcore.hardware.HardwareMap;

public class Lights { //Todo: This needs to be reconsidered. Need some way to decide which alliance we are on without switches. Also, do we sense possession?
    RevBlinkinLedDriver blinkinLedDriver;

    public Lights(HardwareMap hardwareMap){ //Run this in Int to map the class items
        blinkinLedDriver = hardwareMap.get(RevBlinkinLedDriver.class, "LED");
    }

    public void Update_Lights(boolean possession, boolean A, boolean dance){
        if (possession){ //The possession is if we have freight in our robot
            blinkinLedDriver.setPattern(RevBlinkinLedDriver.BlinkinPattern.GOLD);
        }
        else if (A){
            blinkinLedDriver.setPattern(RevBlinkinLedDriver.BlinkinPattern.RED);
        }
        else if (!A){
            blinkinLedDriver.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLUE);
        }
    }
}
