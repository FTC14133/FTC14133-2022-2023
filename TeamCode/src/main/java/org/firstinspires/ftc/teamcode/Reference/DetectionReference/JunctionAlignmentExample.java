package org.firstinspires.ftc.teamcode.Reference.DetectionReference;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Subsystems.Detections.AprilTagDetectionPipeline;
//import org.firstinspires.ftc.teamcode.Subsystems.Detections.ColorDetectionPipeline;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

@TeleOp(name="JunctionAlignmentExample", group="Auto")
public class JunctionAlignmentExample extends LinearOpMode {
    OpenCvCamera camera;
    //ColorDetectionPipeline colorDetectionPipeline;


    public void runOpMode(){
        /*    int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
            camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);

            camera.setPipeline(colorDetectionPipeline);
            camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
            {
                @Override
                public void onOpened()
                {
                    camera.startStreaming(800,448, OpenCvCameraRotation.UPRIGHT);
                }

                @Override
                public void onError(int errorCode)
                {

                }
            });

            telemetry.setMsTransmissionInterval(50);

            while (!isStarted() && !isStopRequested()){
                colorDetectionPipeline.returnJunctionPos(telemetry);
            }
        }
}
*/}}