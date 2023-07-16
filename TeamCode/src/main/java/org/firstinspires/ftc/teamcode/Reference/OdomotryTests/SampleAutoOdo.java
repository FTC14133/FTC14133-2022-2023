
package org.firstinspires.ftc.teamcode.Reference.OdomotryTests;
// https://first-tech-challenge.github.io/SkyStone/  This is the link to ALL metered of FTC


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Reference.OdomotryTests.OdometrySystem;

@Autonomous(name="FTC_14133_2022_Auto", group="Auto")

public class SampleAutoOdo extends LinearOpMode{


    public void HardwareStart() {

        OdometrySystem thread = new OdometrySystem(hardwareMap);
        thread.start();

    }

    public void runOpMode(){

        HardwareStart();

        sleep(10000);

    }
}