package frc.robot.sensors;


import com.analog.adis16470.frc.ADIS16470_IMU;
import frc.robot.RobotConfig;
import frc.robot.util.QuadTimer;
//import com.analog.adis16470.*;

public class IMU {
    private static IMU  INSTANCE = new IMU();
    private ADIS16470_IMU imu;
    private QuadTimer timer;
    private double initAngle = 0;

    private IMU(){
        this.timer = new QuadTimer();
        this.timer.start();
        imu = new ADIS16470_IMU();
        this.imu.configCalTime(ADIS16470_IMU.ADIS16470CalibrationTime._128ms);
        this.reset();
    }

    public void reset(){
        this.initAngle = this.imu.getAngle();
        this.timer.reset();
    }

    public void reset180(){
        this.initAngle = this.imu.getAngle();
    }

    public double getYaw(){
        double yaw = (this.imu.getAngle() - this.initAngle) - this.timer.get() *
                (RobotConfig.SENSORS.IMU_OFFSET_PER_SECOND_PHASE_ONE + RobotConfig.SENSORS.IMU_OFFSET_PER_SECOND_PHASE_TWO);
        while (yaw < 0){
            yaw += 360;
        }
        if (yaw > 360){
            yaw = yaw % 360;
        }
        return yaw;
    }

    //should return angle showing whether robot is tipping forward or backward
    public double getPitch(){
        return this.imu.getGyroInstantY() % 360;//maybe the right one?
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
