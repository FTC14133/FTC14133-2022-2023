package org.firstinspires.ftc.teamcode.Reference.ConfigTests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import java.io.*;
import java.util.Properties;

@TeleOp(name="ReadConfig", group="Iterative Opmode")

public class ReadConfig extends OpMode {
    String configFilePath = "\\FIRST\\java\\templates\\configTestFile.properties";
    Properties prop = new Properties();

    public void init() {
        try {
            FileInputStream propsInput = new FileInputStream(configFilePath);
            prop.load(propsInput);
            telemetry.addData("Test", "Before");
            telemetry.addData("Alliance: ", prop.getProperty("alliance"));
            telemetry.addData("Version: ", prop.getProperty("routine"));
            telemetry.addData("Test", "After");
            telemetry.update();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loop() {
        telemetry.addLine("Go back to Init to Read");
        telemetry.update();
    }
}