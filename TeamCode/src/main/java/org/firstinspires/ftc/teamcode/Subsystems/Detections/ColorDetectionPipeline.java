package org.firstinspires.ftc.teamcode.Subsystems.Detections;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

@Autonomous
public class ColorDetectionPipeline extends OpenCvPipeline {
    Mat YCbCr = new Mat();
    Mat leftCrop;
    Mat centerCrop;
    Mat rightCrop;
    double leftargfin;
    double centerargfin;
    double rightargfin;
    Mat outPut = new Mat();
    Scalar rectColor = new Scalar(255.0, 0.0, 0.0);
    String colorPos = "No Info";

    public Mat processFrame(Mat input) {
        Imgproc.cvtColor(input, YCbCr, Imgproc.COLOR_RGB2YCrCb);
        telemetry.addLine("pipeline running");

        Rect leftRect = new Rect(1, 1, 266, 440);
        Rect centerRect = new Rect(266, 1, 266, 440);
        Rect rightRect = new Rect(532, 1, 266, 440);

        input.copyTo(outPut);
        Imgproc.rectangle(outPut, leftRect, rectColor, 2);
        Imgproc.rectangle(outPut, centerRect, rectColor, 2);
        Imgproc.rectangle(outPut, rightRect, rectColor, 2);

        leftCrop = YCbCr.submat(leftRect);
        centerCrop = YCbCr.submat(centerRect);
        rightCrop = YCbCr.submat(rightRect);

        Core.extractChannel(leftCrop, leftCrop, 0);
        Core.extractChannel(centerCrop, centerCrop, 0);
        Core.extractChannel(rightCrop, rightCrop, 0);

        Scalar leftavg = Core.mean(leftCrop);
        Scalar centeravg = Core.mean(centerCrop);
        Scalar rightavg = Core.mean(rightCrop);

        leftargfin  = leftavg.val[0];
        centerargfin  = centeravg.val[0];
        rightargfin  = rightavg.val[0];

        if ((centerargfin > leftargfin) && (centerargfin > rightargfin)){
            colorPos = "Center";
        }else{
            if (leftargfin > rightargfin){
                colorPos = "Left";
            }else{
                colorPos = "Right";
            }
        }

        telemetry.addData("Left Avg Color", leftargfin);
        telemetry.addData("Center Avg Color", centerargfin);
        telemetry.addData("Right Avg Color", rightargfin);
        telemetry.addData("Color Position", colorPos);
        telemetry.update();

        return (outPut);
    }

    public String returnJunctionPos(){
        return (colorPos);

    }
}