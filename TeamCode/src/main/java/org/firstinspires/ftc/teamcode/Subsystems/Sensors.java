
package org.firstinspires.ftc.teamcode.Subsystems;

import android.util.Log;

import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Sensors {
    //DigitalChannel Warehouse_Turntable; //Instantiates the variable of a switch on the robot that the drivers will turn on or off that tells the code if the robot is on the warehouse or turntable side
    //DigitalChannel alliance_s; //Instantiates the variable of a switch on the robot that the drivers will turn on or off that tells the code if the robot is on the red or blue alliance
    boolean WT; //Todo: These are no longer relevant, so remove them. Still need sides possibly?
    boolean A; //Todo: Need to make a small program that takes input from the controller during init to control the

    public Sensors(HardwareMap hardwareMap){
        //Warehouse_Turntable = hardwareMap.get(DigitalChannel.class, "WT"); //Tells the computer on the robot what to name the switches
        //alliance_s = hardwareMap.get(DigitalChannel.class, "A");
    }

    public boolean[] Update_Switches(){
        //WT = Warehouse_Turntable.getState(); // Sees if the switches are on or off. It will return a true or false (boolean).
        //A = alliance_s.getState();
        Log.i("WT", String.valueOf(WT)); //prints the value of the switches
        Log.i("A", String.valueOf(A));



        //IMPORTANT:
        //                        IMPORTANT VERY VERY VERY!!!!!!!!!!!!!!!!!!!!!!!!!!!!:
        //                            WAREHOUSE/TURNTABLE SWITCH:
        //                                IF ON THE ROBOT IS WAREHOUSE SIDE
        //                                IF OFF THE ROBOT IS ON THE TURNTABLE SIDE
        //
        //                            ALLIANCE SWITCH:
        //                                IF ON THE ROBOT IS ON THE RED ALLIANCE
        //                                IF OFF THE ROBOT IS OFF THE BLUE ALLIANCE


        return new boolean[] {WT, A}; //Allows the main program or others using this function to access the variables
    }
}
