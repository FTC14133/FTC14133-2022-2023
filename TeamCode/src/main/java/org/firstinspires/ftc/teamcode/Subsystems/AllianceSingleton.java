package org.firstinspires.ftc.teamcode.Subsystems;

public class AllianceSingleton {
    private static AllianceSingleton AllianceSelector = null;
    public Boolean Alliance;
    private AllianceSingleton() {
        Alliance = null;
    };
    public static AllianceSingleton AllianceInstance() {
        if (AllianceSelector == null) {
            AllianceSelector = new AllianceSingleton();
        }
        return AllianceSelector;
    }
    public void SetAlliance(Boolean AllianceInput){
        Alliance = AllianceInput;

    }
    public Boolean GetAlliance(){
       return Alliance;
    }
}
