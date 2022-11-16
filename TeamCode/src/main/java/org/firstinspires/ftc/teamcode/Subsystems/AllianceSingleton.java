package org.firstinspires.ftc.teamcode.Subsystems;

public class AllianceSingleton {
    public static AllianceSingleton AllianceSelector = null;
    private Boolean AllianceS;
    public AllianceSingleton() {
        AllianceSingleton AllianceSingle = null;
    };
    public static AllianceSingleton getInstance() {
        if (AllianceSelector == null) {
            AllianceSelector = new AllianceSingleton();
        }
        return AllianceSelector;
    }
    public void SetAlliance(Boolean AllianceInput){
        AllianceS = AllianceInput;

    }
}
