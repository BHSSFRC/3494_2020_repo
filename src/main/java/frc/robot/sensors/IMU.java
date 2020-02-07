package frc.robot.sensors;


import com.analog.adis16470.frc.ADIS16470_IMU;
import frc.robot.RobotMap;
//import com.analog.adis16470.*;

public class IMU {
    private static IMU  INSTANCE = new IMU();
    private ADIS16470_IMU imu;

    private IMU(){
        imu = new ADIS16470_IMU();
    }

    public double getYaw(){
        return this.imu.getAngle();
    }

    public static IMU getInstance(){
        return INSTANCE;
    }

    /**public String getDataString(){
        double[] ypr = new double[3];
        ypr = this.imu.getYawPitchRoll()
        return "";
    }*/
}
