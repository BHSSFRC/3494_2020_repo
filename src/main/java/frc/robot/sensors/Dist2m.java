package frc.robot.sensors;

import com.revrobotics.Rev2mDistanceSensor;
import edu.wpi.first.wpilibj.I2C;

public class Dist2m {
    private Rev2mDistanceSensor distanceSensor;
    private static final Dist2m INSTANCE = new Dist2m();
    private I2C multiplexer;

    public Dist2m() {
        /**this.multiplexer = new I2C(I2C.Port.kOnboard, 0);
        multiplexer.write(0, 1 << 3);*/
        distanceSensor = new Rev2mDistanceSensor(Rev2mDistanceSensor.Port.kOnboard);
        //d and c 0
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
