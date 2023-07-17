
package org.firstinspires.ftc.teamcode.Reference.OdomotryTests.ThreadingTest;
// https://first-tech-challenge.github.io/SkyStone/  This is the link to ALL metered of FTC


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.Reference.OdomotryTests.ThreadingTest.LoopTest;

@Autonomous(name="theadingloop", group="Iterative Opmode")

public class ThreadingTelop extends OpMode {

    public LoopTest thread;

    public void init() {

        thread = new LoopTest(hardwareMap, telemetry);
        thread.start();

    }

    public void loop(){


        telemetry.addData("Status", "On");
        telemetry.update();

        try {
            Thread.sleep(3500);
        } catch (Exception e) {
        }

        telemetry.addData("Status", "Off");
        telemetry.update();

        try {
            Thread.sleep(3500);
        } catch (Exception e) {
        }
    }

    @Override
    public void stop() {
        thread.interrupt();
        super.stop();
    }
}