package frc.robot.sensors;

import com.revrobotics.Rev2mDistanceSensor;
import edu.wpi.first.wpilibj.I2C;
import com.revrobotics.Rev2mDistanceSensor.Port;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Distance2M {
    private Rev2mDistanceSensor distOnboard;
    //private final I2C.Port i2cPort = I2C.Port.kOnboard;

    private Distance2M(){
        //this.distOnboard = new Rev2mDistanceSensor(i2cPort);
        this.distOnboard = new Rev2mDistanceSensor(Port.kOnboard);
        distOnboard.setAutomaticMode(true);
    }

    public double getDist(){
        if(this.distOnboard.isRangeValid()) {
            return this.distOnboard.getRange();
        }else{
            return -1;
        }

    }

    public static Distance2M getInstance(){
        return INSTANCE;
    }

    private static Distance2M INSTANCE = new Distance2M();
}
