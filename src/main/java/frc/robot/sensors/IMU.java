package frc.robot.sensors;

import com.ctre.phoenix.sensors.PigeonIMU;
import frc.robot.RobotMap;

public class IMU {

    public PigeonIMU imu;
    public IMU(){
        this.imu = new PigeonIMU(RobotMap.SENSORS.IMU);
    }

    public double getFusedHeading(){
        return this.imu.getFusedHeading();
    }

    /**public String getDataString(){
        double[] ypr = new double[3];
        ypr = this.imu.getYawPitchRoll()
        return "";
    }*/
}
