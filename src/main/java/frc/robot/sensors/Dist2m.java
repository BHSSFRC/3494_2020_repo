package frc.robot.sensors;

import com.revrobotics.Rev2mDistanceSensor;

public class Dist2m {
    private Rev2mDistanceSensor distanceSensor;
    private static final Dist2m INSTANCE = new Dist2m();

    public Dist2m() {
        distanceSensor = new Rev2mDistanceSensor(Rev2mDistanceSensor.Port.kOnboard);
        distanceSensor.setAutomaticMode(true);
        distanceSensor.setDistanceUnits(Rev2mDistanceSensor.Unit.kInches);
    }

    public double getDist(){
        if(this.distanceSensor.isRangeValid()){
            return this.distanceSensor.getRange();
        }
        return Double.NaN;
    }

    public boolean isNotEnabled(){
        return !this.distanceSensor.isEnabled();
    }

    public static Dist2m getInstance(){
        return INSTANCE;
    }
}
