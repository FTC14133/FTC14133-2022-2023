package org.firstinspires.ftc.teamcode.Reference.ConfigTests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import java.io.*;
import java.util.Properties;

public class EditConfig extends OpMode {
    String configFilePath = "ConfigTesting\\config.properties";
    Properties prop = new Properties();

    boolean AutoSelected = false;
    int routine = 1;
    boolean AllianceSelected = false;
    String AllianceString = "Not Selected";

    public void init() {
        while (!AllianceSelected){
            telemetry.addLine("Please Select the Alliance");
            telemetry.addData("Alliance: ", AllianceString);
            telemetry.addData("Version: ", routine);
            telemetry.update();

            if (gamepad1.x){
                AllianceString = "Blue";
            }else if (gamepad1.b){
                AllianceString = "Red";
            }if (gamepad1.start){
                AllianceSelected = true;
            }
        }
        while (!AutoSelected){
            telemetry.addLine("Please Select the Version");
            telemetry.addData("Alliance: ", AllianceString);
            telemetry.addData("Version: ", routine);
            telemetry.update();

            if (gamepad1.dpad_up){
                routine = 1;
            }else if (gamepad1.dpad_right){
                routine = 2;
            }else if (gamepad1.dpad_down){
                routine = 3;
            }else if (gamepad1.dpad_left){
                routine = 4;
            }else if (gamepad1.start){
                AutoSelected = true;
            }
        }
        try {
            FileInputStream propsInput = new FileInputStream(configFilePath);
            prop.load(propsInput);

            prop.setProperty("alliance", AllianceString);
            prop.setProperty("routine", String.valueOf(routine));

            prop.store(new FileOutputStream(configFilePath),null);

            telemetry.addData("Done Alliance: ", prop.getProperty("alliance"));
            telemetry.addData("Done Version: ", prop.getProperty("routine"));
            telemetry.update();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void loop() {
        telemetry.addLine("Go back to Init to Write");
        telemetry.update();
    }
}