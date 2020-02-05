package frc.robot.sensors;

import com.revrobotics.Rev2mDistanceSensor;

public class Dist2m {
    private Rev2mDistanceSensor distanceSensor;
    private static final Dist2m INSTANCE = new Dist2m();

    public Dist2m() {
        distanceSensor = new Rev2mDistanceSensor(Rev2mDistanceSensor.Port.kOnboard);
        distanceSensor.setAutomaticMode(true);
    }

    public double getDist(){
        if(this.distanceSensor.isRangeValid()){
            return this.distanceSensor.getRange();
        }
        return -1;
    }

    public static Dist2m getInstance(){
        return INSTANCE;
    }
}
