package frc.robot.sensors;
import com.analog.adis16470.frc.ADIS16470_IMU;
import frc.robot.RobotConfig;
import frc.robot.util.QuadTimer;

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

    public double getYaw(){
        double yaw = (this.imu.getAngle() - this.initAngle) - this.timer.get() *
                (RobotConfig.SENSORS.IMU_OFFSET_PER_SECOND_PHASE_ONE + RobotConfig.SENSORS.IMU_OFFSET_PER_SECOND_PHASE_TWO);
        if (yaw < 0 || yaw > 360){
            yaw -= Math.floor(yaw / 360) * 360;
        }
        return yaw;
    }

    public static IMU getInstance(){
        return INSTANCE;
    }
}
