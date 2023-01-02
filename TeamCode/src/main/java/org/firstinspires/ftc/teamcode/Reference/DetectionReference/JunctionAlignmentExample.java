package org.firstinspires.ftc.teamcode.Reference.DetectionReference;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.Detections.Detection;

@TeleOp(name="JunctionAlignmentExample", group="Iterative Opmode")
public class JunctionAlignmentExample extends OpMode {
    private org.firstinspires.ftc.teamcode.Subsystems.Detections.Detection Detection=null;

    public void init() {
        Detection = new Detection(hardwareMap);
        telemetry.addData("Object Creation", "Done");
    }

    public void loop() {
        Detection.junctionPos();
    }
}
